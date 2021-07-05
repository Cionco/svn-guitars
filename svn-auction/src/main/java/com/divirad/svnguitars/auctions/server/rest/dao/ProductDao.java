package com.divirad.svnguitars.auctions.server.rest.dao;

import java.sql.Date;
import java.util.ArrayList;

import com.divirad.svnguitars.auctions.server.Dao;
import com.divirad.svnguitars.auctions.server.Database;
import com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO;

public class ProductDao extends Dao<ProductDTO> {

	public static ProductDao instance = new ProductDao();
	
	public ProductDao() {
		super(ProductDTO.class);
	}
	
	public void create(String serial_number, String name, String description, Date auction_start, Date auction_end) {
		ProductDTO p = new ProductDTO(
				serial_number,
				name,
				description,
				auction_start,
				auction_end);
		insert(p);
	}
	
	public ProductDTO get_product_by_serial_number(String sn) {
		ProductDTO p = new ProductDTO();
		p.serial_number = sn;
		return select(p);
	}
	
	/**
	 * 
	 * @return list of all products that have a running auction
	 */
	public ArrayList<ProductDTO> get_open_products() {
		return Database.query("SELECT * FROM " + this.tableName + " WHERE auction_start <= NOW() AND auction_end >= NOW()", rs -> convAllInResultSet(rs));
	}
}
