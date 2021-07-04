package com.divirad.svnguitars.auctions.server.rest.dto;

import java.sql.Timestamp;

import com.divirad.svnguitars.auctions.server.MysqlMarker;

@MysqlMarker.TableView(isWholeTable = true, tableName = "bid")
public final class BidDTO {

	@MysqlMarker.PrimaryKey
	@MysqlMarker.AutomaticValue
	public int id;
	public int product;
	public String user_name;
	@MysqlMarker.IgnoreField
	public Timestamp bid_date;
	public double amount;
	
	public BidDTO() {}

	public BidDTO(int product, String user_name, Timestamp bid_date, double amount) {
		super();
		this.product = product;
		this.user_name = user_name;
		this.bid_date = bid_date;
		this.amount = amount;
	}
	
}
