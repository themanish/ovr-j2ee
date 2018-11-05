package com.ovr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ovr.model.District;
import com.ovr.utils.Database;

public class CommonDao {

	District district;
	List<District> districtList;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	int updatedRows;
	String sql;
	PreparedStatement ps;
	int lastInsertedId;

	public int insert(String tblName, Map<String, Object> data) {
		conn = Database.connect();
		try {
			sql = "INSERT INTO `" + tblName + "` SET";

			for (String key : data.keySet()) {
				sql += " `" + key + "` = ?,";
			}

			sql = sql.substring(0, sql.length() - 1);

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int paramIndex = 1;
			for (String key : data.keySet()) {
				ps.setObject(paramIndex, data.get(key));
				paramIndex++;
			}

			System.out.println(ps.toString());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				lastInsertedId = rs.getInt(1);

		} catch (SQLException e) {
			System.err.println("CommonDao (insert): " + e.getMessage());
		}
		return lastInsertedId;
	}

	public List<District> getAllDistrict() {
		conn = Database.connect();
		districtList = new ArrayList<District>();

		try {
			String sql = "SELECT * FROM tbl_districts";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				district = new District();
				district.setId(rs.getInt("id"));
				district.setName(rs.getString("name"));

				districtList.add(district);
			}

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		}

		return districtList;
	}

	public List getAll(String tblName) {
		conn = Database.connect();
		List list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM `" + tblName + "`";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				list.add(rs);
			}

		} catch (SQLException e) {
			System.err.println("CommonDao (getAll) : " + e.getMessage());
		}

		return list;

	}

	// public List getWhere(String tblName, Map<String, String> condition){
	//
	// conn = Database.connect();
	// String sql = "SELECT * FROM `"+tblName+"` WHERE";
	//
	// for(String key : condition.keySet()){
	// sql += " `"+key+"` = '"+condition.get(key)+"' AND";
	// }
	// sql = sql.substring(0, sql.length()-3);
	//
	// try {
	// stmt = conn.createStatement();
	// rs = stmt.executeQuery(sql);
	//
	// } catch (SQLException e) {
	// System.err.println("CommonDao (getWhere): "+e.getMessage());
	// }
	//
	// }

	public int update(String tblName, Map<String, Object> data,
			Map<String, Object> condition) {

		conn = Database.connect();
		String sql = "UPDATE `" + tblName + "` SET";

		for (String key : data.keySet()) {
			sql += " `" + key + "` = ?,";
		}

		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE";

		for (String key : condition.keySet()) {
			sql += " `" + key + "` = ? AND";
		}

		sql = sql.substring(0, sql.length() - 3);

		System.out.println(sql);

		try {
			ps = conn.prepareStatement(sql);

			int index = 1;
			for (String key : data.keySet()) {
				ps.setObject(index, data.get(key));
				index++;
			}

			for (String key : condition.keySet()) {
				ps.setObject(index, condition.get(key));
				index++;
			}
			System.out.println(ps.toString());
			updatedRows = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
		}

		return updatedRows;
	}

	public int getCount(String tblName, Map<String, Object> condition,
			String operator) {
		String sql;
		int count=0;

		Connection conn = Database.connect();
		try {
			sql = "SELECT * FROM `" + tblName + "` WHERE";

			for (String key : condition.keySet()) {
				sql += " `" + key + "` = '" + condition.get(key) + "' "
						+ operator;
			}

			if (operator.equals("AND"))
				sql = sql.substring(0, sql.length() - 3);
			else if (operator.equals("OR"))
				sql = sql.substring(0, sql.length() - 2);

			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println(ps.toString());
			ResultSet result = ps.executeQuery();
			
			while(result.next()){
				count++;
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return count;
	}

}
