package com.divirad.svnguitars.auctions.server.rest.dto;

import java.sql.Date;

import com.divirad.svnguitars.auctions.server.MysqlMarker;

@MysqlMarker.TableView(isWholeTable = true, tableName = "product")
public final class ProductDTO {

	@MysqlMarker.PrimaryKey
	public String serial_number;
	public String name;
	public String description;
	public Date auction_start;
	public Date auction_end;
	
	public ProductDTO() {}
	
	public ProductDTO(String serial_number, String name, String description, Date auction_start, Date auction_end) {
		super();
		this.serial_number = serial_number;
		this.name = name;
		this.description = description;
		this.auction_start = auction_start;
		this.auction_end = auction_end;
	}
	
	
}
