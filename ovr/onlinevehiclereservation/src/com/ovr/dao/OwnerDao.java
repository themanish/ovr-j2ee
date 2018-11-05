package com.ovr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ovr.model.Owner;
import com.ovr.utils.Database;

public class OwnerDao {

	Connection conn;
	Statement stmt;
	PreparedStatement ps;
	ResultSet rs = null;

	public int addOwner(Owner owner) {

		int result = 0;
		try {
			conn = Database.connect();
			String sql = "INSERT INTO `tbl_owners` SET `fname` = ?, `lname` = ?, `phone` = ?, `email` = ?, `password` = ?, `profile_image` = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, owner.getFname());
			ps.setString(2, owner.getLname());
			ps.setString(3, owner.getPhone());
			ps.setString(4, owner.getEmail());
			ps.setString(5, owner.getPassword());
			ps.setBinaryStream(6, owner.getProfileImage());

			System.out.println(ps.toString());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		}

		return result;

	}

	public Owner[] getAllOwners() {
		Owner[] ownerList = null;

		try {
			conn = Database.connect();
			String sql = "SELECT * FROM tbl_owners";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			rs.last();
			ownerList = new Owner[rs.getRow()];

			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				Owner owner = new Owner();
				owner.setId(rs.getInt("id"));
				owner.setFname(rs.getString("fname"));
				owner.setLname(rs.getString("lname"));
				owner.setPhone(rs.getString("phone"));
				owner.setEmail(rs.getString("email"));
				owner.setProfileImage(rs.getBinaryStream("profile_image"));
				owner.setStatus(rs.getBoolean("status"));
				ownerList[i] = owner;
				i++;
			}

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ownerList;
	}

	public Owner getOwnerDetails(String user, String pass) {
		Owner owner = null;
		try {
			conn = Database.connect();
			String sql = "SELECT * FROM `tbl_owners` WHERE `email` = ? AND `password` = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pass);
			
			rs = ps.executeQuery();

			if (rs.next()) {
				owner = new Owner();
				owner.setId(rs.getInt("id"));
				owner.setFname(rs.getString("fname"));
				owner.setLname(rs.getString("lname"));
				owner.setEmail(rs.getString("email"));
				owner.setStatus(rs.getBoolean("status"));
			}

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return owner;
	}
	
	public Owner getOwnerDetailsById(int ownerId){

		Owner owner = null;
		try {
			conn = Database.connect();
			String sql = "SELECT * FROM `tbl_owners` WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ownerId);
			
			rs = ps.executeQuery();

			if (rs.next()) {
				owner = new Owner();
				owner.setId(rs.getInt("id"));
				owner.setFname(rs.getString("fname"));
				owner.setLname(rs.getString("lname"));
				owner.setPhone(rs.getString("phone"));
				owner.setEmail(rs.getString("email"));
				owner.setProfileImage(rs.getBinaryStream("profile_image"));
			}

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		} 
		
		return owner;
		
	}

	public int updateOwnerDetails(Owner owner) {
		
		int updatedRows = 0;
		try {
			conn = Database.connect();
//			String sql = "UPDATE `tbl_owners` SET `fname` = ?, `lname` = ?, `phone` = ?, `email` = ?, `password` = ?, `district` = ?, `account_type` = ?, `organization_name` = ?, `profile_image` = ? WHERE `id` = ?";
			String sql = "UPDATE `tbl_owners` SET `fname` = ?, `lname` = ?, `phone` = ?, `profile_image` = ? WHERE `id` = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, owner.getFname());
			ps.setString(2, owner.getLname());
			ps.setString(3, owner.getPhone());
//			ps.setString(4, owner.getEmail());
			ps.setBinaryStream(4, owner.getProfileImage());
			ps.setInt(5, owner.getId());

			System.out.println(ps.toString());
			updatedRows = ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return updatedRows;
		
	}

	public int changeStatus(boolean status, int ownerId) {
		
		int updatedRows = 0;
		try {
			conn = Database.connect();
			String sql = "UPDATE `tbl_owners` SET `status` = ? WHERE `id` = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setBoolean(1, status);
			ps.setInt(2, ownerId);
			updatedRows = ps.executeUpdate();
			System.out.println(ps.toString());

		} catch (SQLException e) {
			System.err.println("SQL ERROR: " + e.getMessage());
		}

		return updatedRows;
		
	}

}
