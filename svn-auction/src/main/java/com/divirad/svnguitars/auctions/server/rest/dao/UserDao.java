package com.divirad.svnguitars.auctions.server.rest.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.divirad.svnguitars.auctions.server.Dao;
import com.divirad.svnguitars.auctions.server.Database;
import com.divirad.svnguitars.auctions.server.rest.dto.UserDTO;

public class UserDao extends Dao<UserDTO>{

	public static UserDao instance = new UserDao();
	
	public UserDao() {
		super(UserDTO.class);
	}
	
	public boolean is_username_available(UserDTO u) {
		return Database.query("SELECT COUNT(*) FROM " + this.tableName + " WHERE user_name = ?;", 
				ps -> ps.setString(1, u.user_name), rs -> { rs.next(); return rs.getInt(1) == 0; });
	}

	public boolean register(UserDTO u) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			u.password = UserDao.get_SHA_256_SecurePassword(u.password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		
		insert(u);
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static String get_SHA_256_SecurePassword(String passwordToHash)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
