package com.divirad.svnguitars.auctions.server.rest.dto;

import com.divirad.svnguitars.auctions.server.MysqlMarker;

@MysqlMarker.TableView(isWholeTable = true, tableName = "user")
public final class UserDTO {

	@MysqlMarker.PrimaryKey
	@MysqlMarker.AutomaticValue
	public int id;
	public String user_name;
	public String password;
	public String first_name;
	public String last_name;
	
	
	public UserDTO() {}
	
	public UserDTO(int id, String user_name, String password, String first_name, String last_name) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	
}
