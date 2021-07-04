package com.divirad.svnguitars.auctions.server.rest.dao;

import java.util.ArrayList;

import com.divirad.svnguitars.auctions.server.Dao;
import com.divirad.svnguitars.auctions.server.Database;
import com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO;

public class ProductDao extends Dao<ProductDTO> {

	public static ProductDao instance = new ProductDao();
	
	public ProductDao() {
		super(ProductDTO.class);
	}
	
	public void create(ProductDTO p) {
		insert(p);
	}
	
	public ProductDTO get_product_by_id(int id) {
		ProductDTO p = new ProductDTO();
		p.id = id;
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
