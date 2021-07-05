package com.divirad.svnguitars.auctions.server.rest.dao;

import com.divirad.svnguitars.auctions.server.Dao;
import com.divirad.svnguitars.auctions.server.Database;
import com.divirad.svnguitars.auctions.server.rest.dto.BidDTO;

public class BidDao extends Dao<BidDTO> {
	public static BidDao instance = new BidDao();
	public BidDao() {
		super(BidDTO.class);
	}
	
	public int get_bid_count_for_product(String product) {
		return Database.query("SELECT COUNT(*) FROM " + this.tableName + " WHERE product = ?", 
				ps -> ps.setString(1, product), 
				rs -> { rs.next(); return rs.getInt(1); });
	}
	
	public double get_highest_bid_for_product(String product) {
		return Database.query("SELECT MAX(amount) FROM " + this.tableName + " WHERE product = ?", 
				ps -> ps.setString(1, product),
				rs -> { if(rs == null || !rs.next()) return 0.0; return rs.getDouble(1); });
	}
	
	/**
	 * 
	 * @param product the product's id 
	 * @param user_name the bidding user's name
	 * @param amount the amount of the bid
	 * @return -1 when amount is not higher than the currently highest bid for the product
	 */
	public int bid(String product, String user_name, double amount) {
		BidDTO b = new BidDTO(product, user_name, null, amount);
		if(amount <= get_highest_bid_for_product(product)) return -1;
		insert(b);
		return 0;
	}
}
