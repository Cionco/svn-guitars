package com.divirad.svnguitars.auctions.server.rest.dto;

import com.divirad.svnguitars.auctions.server.MysqlMarker;

@MysqlMarker.TableView(isWholeTable = true, tableName = "user")
public final class UserDTO {

	@MysqlMarker.PrimaryKey
	public String user_name;
	public String password;
	public String email;
	public String first_name;
	public String last_name;
	
	
	public UserDTO() {}
	
	public UserDTO(String user_name, String password, String email, String first_name, String last_name) {
		super();
		this.user_name = user_name;
		this.password = password;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	@Override
	public String toString() {
		return user_name;
	}
}
