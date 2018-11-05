package com.ovr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ovr.model.User;
import com.ovr.utils.Database;

public class UserDao {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private List<User> userList;
	private int lastInsertedId;
	
	public int insert(Map<String, Object> data){
		conn = Database.connect();
		try {
			sql = "INSERT INTO `tbl_users` SET";
			
			for(String key : data.keySet()){
				sql += " `"+key+"` = ?,";
			}
			
			sql = sql.substring(0, sql.length()-1);
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int paramIndex=1;
			for(String key : data.keySet()){
				ps.setObject(paramIndex, data.get(key));
				paramIndex++;
			}
			
			System.out.println(ps.toString());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next())
				lastInsertedId = rs.getInt(1);
			
		} catch (SQLException e) {
			System.err.println("UserDao (insert): "+e.getMessage());
		}
		return lastInsertedId;
	}
	
	public List<User> getWhere(Map<String, Object> condition, String operator){
		
		conn = Database.connect();
		try {
			sql = "SELECT * FROM `tbl_users` WHERE";
			
			for(String key : condition.keySet()){
				sql += " `"+key+"` = '"+condition.get(key)+"' "+operator;
			}
			
			if(operator.equals("AND"))
				sql = sql.substring(0, sql.length()-3);
			else if (operator.equals("OR"))
				sql = sql.substring(0, sql.length()-2);
			
			ps = conn.prepareStatement(sql);
			System.out.println(ps.toString());
			ResultSet result = ps.executeQuery();
			
			
			userList = new ArrayList<User>();
			
			while(result.next()){
				User user = new User();
				user.setId(result.getInt("id"));
				user.setEmail(result.getString("email"));
				user.setPassword(result.getString("password"));
				user.setFullname(result.getString("fullname"));
				user.setContactNo(result.getString("contact_no"));
				user.setAddress(result.getString("address"));
				user.setProfileImage(result.getBinaryStream("profile_image"));
				user.setCitizenship(result.getBinaryStream("citizenship"));
				user.setDrivingLicense(result.getBinaryStream("driving_license"));
				
				userList.add(user);
			}
			
		} catch (SQLException e) {
			System.err.println("UserDao (getWhere): "+e.getMessage());
		}
		return userList;
	}


}
