package com.divirad.svnguitars.auctions.server.rest.dao;

import java.sql.Blob;
import java.util.ArrayList;

import com.divirad.svnguitars.auctions.server.Dao;
import com.divirad.svnguitars.auctions.server.Database;
import com.divirad.svnguitars.auctions.server.rest.dto.ImgDTO;

public class ImgDao extends Dao<ImgDTO> {
	public static ImgDao instance = new ImgDao();
	public ImgDao() {
		super(ImgDTO.class);
	}
	
	public int create(String product, Blob img) {
		ImgDTO i = new ImgDTO(product, -1, img);
		i.order_img = get_image_count_for_product(i.product);
		insert(i);
		return i.order_img;
	}
	
	/**
	 * Load image with order = 0 stored for a product from the database
	 * @param product serial number of the product
	 * @return ImgDTO of the front image
	 */
	public ImgDTO get_front_image(String product) {
		ImgDTO i = new ImgDTO(product, 0, null);
		return select(i);
	}
	
	/**
	 * Load image with specified order for a product from the database
	 * @param product serial number of the product
	 * @param order order index with 0 being the front image
	 * @return ImgDTO of the image
	 */
	public ImgDTO get_image_by_order(String product, int order) {
		return Database.query("SELECT * FROM " + this.tableName + " WHERE product = ? AND order_img = ?", 
				ps -> {
					ps.setString(1, product);
					ps.setInt(2, order);
				}, this::convFirstInResultSet);
	}
	
	/**
	 * Load all images stored for a product from the database
	 * @param product product id of the product
	 * @return ArrayList of ImgDTO objects with all the images
	 */
	@Deprecated
	private ArrayList<ImgDTO> get_images(int product) {
		return Database.query("SELECT * FROM " + this.tableName + " WHERE product = ?", 
				ps -> ps.setInt(1, product), 
				this::convAllInResultSet);
	}
	
	/**
	 * Get number of images for a product from the database
	 * @param product serial number of the product
	 * @return amount of images stored for that product
	 */
	public int get_image_count_for_product(String product) {
		return Database.query("SELECT COUNT(*) FROM " + this.tableName + " WHERE product = ?;", 
				ps -> ps.setString(1, product), 
				rs -> { rs.next(); return rs.getInt(1); });
	}

}
