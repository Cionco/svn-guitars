package com.divirad.svnguitars.auctions.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Base Dao class.
 * Uses reflection to create to automatically create sql commands:
 * -select via Primary Key
 * -insert all
 * -update all via Primary Key
 * <p>
 * The annotations of {@see MysqlMarker}
 * Shares a few intermediate values with sub classes for easier creation of complex commands.
 *
 * @param <T> Class which fields represent the columns of a mysql table.
 *            Must be final and not extend another class.
 *            Must have annotation MysqlMarker.TableView
 */
public class Dao<T> {
    protected String field_update_list;
    protected String field_list;
    protected String param_list;
    protected String primary_list;
    protected String tableName;

    protected String sql_insert;
    protected String sql_select;
    protected String sql_update;

    protected Field[] primaryKeys;
    protected Field[] notAutomatedKeys;
    protected Field[] allFields;

    protected Class<T> cls;
    protected Constructor<T> constructor;
    protected boolean isWholeTable;

    public Dao(Class<T> cls) {
        this.cls = cls;
        if (!Modifier.isFinal(cls.getModifiers()))
            throw new IllegalArgumentException("Can't use class: must be final");
        if (cls.getSuperclass() != Object.class)
            throw new IllegalArgumentException("Can't use class: must not extend another class");
        MysqlMarker.TableView annotation = cls.getAnnotation(MysqlMarker.TableView.class);
        if (annotation == null)
            throw new IllegalArgumentException("Can't use class: must have annotation MysqlMarker.TableView");
        this.tableName = "`" + annotation.tableName() + "`";
        this.isWholeTable = annotation.isWholeTable();
        try {
            this.constructor = cls.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Can't use class: No default constructor");
        }
        analyzeFields();
        makeStrings();
    }

    private void analyzeFields() {
        List<Field> primaryKeys = new ArrayList<>();
        List<Field> notAutomatedKeys = new ArrayList<>();
        List<Field> allFields = new ArrayList<>();
        for (Field field : this.cls.getDeclaredFields()) {
            if (field.getAnnotation(MysqlMarker.IgnoreField.class) != null)
                continue;
            allFields.add(field);
            if (field.getAnnotation(MysqlMarker.PrimaryKey.class) != null) {
                primaryKeys.add(field);
            }
            if (field.getAnnotation(MysqlMarker.AutomaticValue.class) == null) {
                notAutomatedKeys.add(field);
            }
        }
        this.primaryKeys = primaryKeys.toArray(new Field[0]);
        this.notAutomatedKeys = notAutomatedKeys.toArray(new Field[0]);
        this.allFields = allFields.toArray(new Field[0]);
    }

    private void makeStrings() {
        StringJoiner field_joiner = new StringJoiner(",", "(", ")");
        StringJoiner param_joiner = new StringJoiner(",", "(", ")");
        StringJoiner primary_joiner = new StringJoiner(" AND ");
        StringJoiner update_joiner = new StringJoiner(",");
        for (Field field : this.allFields) {
            update_joiner.add(field.getName() + "=?");
            field.setAccessible(true);
        }
        for (Field field : this.primaryKeys) {
            primary_joiner.add(field.getName() + "=?");
        }
        for (Field field : this.notAutomatedKeys) {
            field_joiner.add(field.getName());
            param_joiner.add("?");
        }
        field_list = field_joiner.toString();
        param_list = param_joiner.toString();
        primary_list = primary_joiner.toString();
        field_update_list = update_joiner.toString();

        if (this.isWholeTable)
            sql_insert = "INSERT INTO " + this.tableName + " " + field_list + " VALUES " + param_list;
        sql_select = "SELECT * FROM " + this.tableName + " WHERE " + primary_list;
        sql_update = "UPDATE " + this.tableName + " SET " + field_update_list + " WHERE " + primary_list;
    }

    /**
     * Sets the parameter of a PreparedStatement
     *
     * @param ps     the statement
     * @param data   contains the data of the parameters
     * @param fields array of all fields used as parameter
     * @param index  index of the first parameter
     * @return next index
     */
    protected int setParams(PreparedStatement ps, T data, Field[] fields, int index) throws SQLException {
        try {
            for (int i = 0; i < fields.length; i++, index++) {
                Field f = fields[i];
                if (f.get(data) == null)
                    ps.setObject(index, f.get(data));
                else if (f.getType() == int.class || f.getType() == Integer.class)
                    ps.setInt(index, (Integer) f.get(data));
                else if (f.getType() == boolean.class || f.getType() == Boolean.class)
                    ps.setBoolean(index, (Boolean) f.get(data));
                else if (f.getType() == long.class || f.getType() == Long.class)
                    ps.setLong(index, (Long) f.get(data));
                else if (f.getType() == byte.class || f.getType() == Byte.class)
                    ps.setByte(index, (Byte) f.get(data));
                else if (f.getType() == float.class || f.getType() == Float.class)
                    ps.setFloat(index, (Float) f.get(data));
                else if (f.getType() == double.class || f.getType() == Double.class)
                    ps.setDouble(index, (Double) f.get(data));
                else if (f.getType() == String.class)
                    ps.setString(index, (String) f.get(data));
                else if (f.getType() == byte[].class)
                    ps.setBytes(index, (byte[]) f.get(data));
                else if (f.getType() == Date.class)
                    ps.setDate(index, (Date) f.get(data));
                else if (f.getType() == Time.class)
                    ps.setTime(index, (Time) f.get(data));
                else if (f.getType() == Timestamp.class)
                    ps.setTimestamp(index, (Timestamp) f.get(data));
                else
                    throw new IllegalStateException("Unknown type of field: " + f.getName() + ", " + f.getType().getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return index;
    }

    /**
     * Converts a ResultSet to a T object
     */
    protected T convFirstInResultSet(ResultSet rs) throws SQLException {
        if (rs == null || !rs.next()) return null;
        return convertCurrentFromResultSet(rs);
    }

    /**
     * Converts a ResultSet to a T ArrayList
     */
    protected ArrayList<T> convAllInResultSet(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        ArrayList<T> result = new ArrayList<>();
        while (rs.next())
            result.add(convertCurrentFromResultSet(rs));
        return result;
    }

    protected T convertCurrentFromResultSet(ResultSet rs) throws SQLException {
        try {
            T result = this.constructor.newInstance();

            for (int i = 1; i <= this.allFields.length; i++) {
                Field f = this.allFields[i - 1];
                if (f.getType() == int.class || f.getType() == Integer.class)
                    f.set(result, rs.getInt(i));
                else if (f.getType() == long.class || f.getType() == Long.class)
                    f.set(result, rs.getLong(i));
                else if (f.getType() == byte.class || f.getType() == Byte.class)
                    f.set(result, rs.getByte(i));
                else if (f.getType() == float.class || f.getType() == Float.class)
                    f.set(result, rs.getFloat(i));
                else if (f.getType() == double.class || f.getType() == Double.class)
                    f.set(result, rs.getDouble(i));
                else if (f.getType() == boolean.class || f.getType() == Boolean.class)
                		f.set(result, rs.getBoolean(i));
                else if (f.getType() == String.class)
                    f.set(result, rs.getString(i));
                else if (f.getType() == byte[].class)
                    f.set(result, rs.getBytes(i));
                else if (f.getType() == Date.class)
                    f.set(result, rs.getDate(i));
                else if (f.getType() == Time.class)
                    f.set(result, rs.getTime(i));
                else if (f.getType() == Timestamp.class)
                    f.set(result, rs.getTimestamp(i));
                else
                    throw new IllegalStateException("Unknown type of field");
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserts a new row in the mysql table. Only available if class represents whole mysql table
     *
     * @param data contains the data that should be inserted.
     *             All fields will be inserted.
     */
    protected void insert(T data) {
        Database.execute(sql_insert, ps -> setParams(ps, data, this.notAutomatedKeys, 1));
    }

    /**
     * Updates a row in the mysql table.
     *
     * @param data contains the data that should be inserted. The fields marked as primary keys are used in the WHERE
     *             condition.
     *             All fields will be updated.
     */
    protected void update(T data) {
        if (!this.isWholeTable)
            throw new UnsupportedOperationException(
                    "Usage of udpate is not possible: " + this.cls.getName() + " does not represent whole mysql table");
        Database.execute(sql_update, ps -> {
            int nextIndex = setParams(ps, data, this.allFields, 1);
            setParams(ps, data, this.primaryKeys, nextIndex);
        });
    }

    /**
     * Returns a row of the mysql table
     *
     * @param data contains the primary keys for use in the WHERE condition.
     *             Only fields marked as primary key will be used, all others are ignored
     * @return T object containing the data of the row
     */
    protected T select(T data) {
        return Database.query(sql_select, ps -> setParams(ps, data, this.primaryKeys, 1), this::convFirstInResultSet);
    }
}
