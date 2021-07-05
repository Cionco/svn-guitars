package com.divirad.svnguitars.auctions.server.rest.dto;

import java.sql.Blob;

import com.divirad.svnguitars.auctions.server.MysqlMarker;

@MysqlMarker.TableView(isWholeTable = true, tableName = "image")
public final class ImgDTO {

	@MysqlMarker.PrimaryKey
	public String product;
	@MysqlMarker.PrimaryKey
	public int order_img;
	public Blob img;
	
	public ImgDTO() {}

	public ImgDTO(String product, int order_img, Blob img) {
		super();
		this.product = product;
		this.order_img = order_img;
		this.img = img;
	}
}
