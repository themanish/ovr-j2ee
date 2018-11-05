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

import com.ovr.model.Booking;
import com.ovr.model.District;
import com.ovr.model.Owner;
import com.ovr.model.Vehicle;
import com.ovr.utils.Database;
import com.ovr.utils.Helper;

public class VehicleDao {
	
	ArrayList<Vehicle> vehicleList;
	Connection conn;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	int queryStatus;
	int updatedRows;
	String sql;
	
	public ArrayList<Vehicle> getAllVehicles(){
		
		conn = Database.connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM `tbl_vehicles`");
			ResultSet result = ps.executeQuery();
			
			vehicleList = new ArrayList<Vehicle>();
			
			while(result.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(result.getInt("id"));
				vehicle.setManufacturer(result.getString("manufacturer"));
				vehicle.setModel(result.getString("model"));
				vehicle.setDescription(result.getString("description"));
				vehicle.setImage(result.getBinaryStream("image"));
				vehicle.setOwnerId(result.getInt("owner_id"));
				vehicle.setFromDate(result.getDate("from_date").toString());
				vehicle.setToDate(result.getDate("to_date").toString());
				vehicle.setFeatureStatus(result.getString("feature_status"));
				vehicleList.add(vehicle);
			}
			
		} catch (SQLException e) {
			ps.toString();
			System.err.println(""+e);
		}
		return vehicleList;
	}
	
	public ArrayList<Vehicle> getWhere(Map<String, Object> condition, String operator){
		
		conn = Database.connect();
		try {
			sql = "SELECT *, v.id as v_id FROM `tbl_vehicles` as v JOIN `tbl_owners` as o ON v.owner_id = o.id WHERE";
			
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
			
			
			vehicleList = new ArrayList<Vehicle>();
			
			while(result.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(result.getInt("v_id"));
				vehicle.setManufacturer(result.getString("manufacturer"));
				vehicle.setModel(result.getString("model"));
				vehicle.setImage(result.getBinaryStream("image"));
				vehicle.setDailyFare(result.getDouble("daily_fare"));
				vehicle.setFromDate(result.getDate("from_date").toString());
				vehicle.setToDate(result.getDate("to_date").toString());
				vehicle.setFeatureStatus(result.getString("feature_status"));
				
				vehicle.setOwnerId(result.getInt("owner_id"));
				Owner owner = new Owner();
				owner.setId(result.getInt("owner_id"));
				owner.setFname(result.getString("fname"));
				owner.setLname(result.getString("lname"));
				
				vehicle.setOwner(owner);
				
				vehicleList.add(vehicle);
			}
			
		} catch (SQLException e) {
			System.err.println(""+e);
		}
		return vehicleList;
	}
	
	public int update(Map<String, Object> data, Map<String, Object> condition){
		conn = Database.connect();
		String sql = "UPDATE `tbl_vehicles` SET";
		
		for(String key : data.keySet()){
			sql += " `"+key+"` = '"+data.get(key)+"',";
		}
		
		sql = sql.substring(0, sql.length()-1);
		
		sql += " WHERE";
		
		for(String key : condition.keySet()){
			sql += " `"+key+"` = '"+condition.get(key)+"' AND";
		}
		
		sql = sql.substring(0, sql.length()-3);
		
		System.out.println(sql);
		
		try {
			stmt = conn.createStatement();
			updatedRows = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("SQL Error: "+e.getMessage());
		}
		
		return updatedRows;
	}
	
	public int addVehicle(Vehicle vehicle){
		
		conn = Database.connect();
		try {
			
			String sql = "INSERT INTO `tbl_vehicles` SET `manufacturer`=?, `model`=?, `description`=?, `image`=?, `owner_id`=?, `vehicle_current_location_id`=?, `from_date` = ?, `to_date`=?, `daily_fare`=?";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, vehicle.getManufacturer());
			ps.setString(2, vehicle.getModel());
			ps.setString(3, vehicle.getDescription());
			ps.setBinaryStream(4, vehicle.getImage());
			ps.setInt(5, vehicle.getOwnerId());
			ps.setInt(6, vehicle.getVehicleCurrentLocationId());
			ps.setDate(7, new java.sql.Date(Helper.stringToDate(vehicle.getFromDate()).getTime()));
			ps.setDate(8, new java.sql.Date(Helper.stringToDate(vehicle.getToDate()).getTime()));
			ps.setDouble(9, vehicle.getDailyFare());
			
			queryStatus = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			int lastInsertedVehicleId;
			if(rs.next())
				lastInsertedVehicleId = rs.getInt(1);
			else
				return 0;
			
			ps.clearBatch();
			
			ps = conn.prepareStatement("INSERT INTO `vehicle_available_loc` SET `vehicle_id` = ?, `end_location_id` = ?");
			for(int endLocationId : vehicle.getVehicleEndLocationIds()){
				ps.setInt(1, lastInsertedVehicleId);
				ps.setInt(2, endLocationId);
				ps.executeUpdate();
			}
				
		} catch (SQLException e) {
			System.err.println("DAO (addVehicle) :"+e);
		}
		
		return queryStatus;
	}
	
	public int updateVehicle(Vehicle vehicle){
		
		conn = Database.connect();
		try {
			String sql = "UPDATE `tbl_vehicles` SET `manufacturer`=?, `model`=?, `daily_fare`=?, `description`=?, `image`=?, `owner_id`=?, `vehicle_current_location_id`=?, `from_date`=?, `to_date`=? WHERE `id` = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vehicle.getManufacturer());
			ps.setString(2, vehicle.getModel());
			ps.setDouble(3, vehicle.getDailyFare());
			ps.setString(4, vehicle.getDescription());
			ps.setBinaryStream(5, vehicle.getImage());
			ps.setInt(6, vehicle.getOwnerId());
			ps.setInt(7, vehicle.getVehicleCurrentLocationId());
			ps.setDate(8, new java.sql.Date(Helper.stringToDate(vehicle.getFromDate()).getTime()));
			ps.setDate(9, new java.sql.Date(Helper.stringToDate(vehicle.getToDate()).getTime()));
			ps.setInt(10, vehicle.getId());
			
			queryStatus = ps.executeUpdate();
			System.out.println(ps.toString());
			
			ps.clearBatch();
			
			// Delete All previous endlocation data - and re-enter new one.
			ps = conn.prepareStatement("DELETE FROM `vehicle_available_loc` WHERE `vehicle_id` = ?");
			ps.setInt(1, vehicle.getId());
			ps.executeUpdate();
			
			ps.clearBatch();
			
			ps = conn.prepareStatement("INSERT INTO `vehicle_available_loc` SET `vehicle_id` = ?, `end_location_id` = ?");
			for(int endLocationId : vehicle.getVehicleEndLocationIds()){
				ps.setInt(1, vehicle.getId());
				ps.setInt(2, endLocationId);
				ps.executeUpdate();
				System.out.println(ps.toString());
			}
			
		} catch (SQLException e) {
			ps.toString();
			System.err.println(e);
		}
		
		return queryStatus;
	}

	public Vehicle getVehicleDetails(Integer vehicleId) {
		Vehicle vehicle = null;
		try {
			conn = Database.connect();
			sql = "SELECT * FROM `tbl_vehicles` as v"
					+ " JOIN `tbl_owners` as o ON v.owner_id = o.id"
					+ " JOIN `tbl_districts` as d ON v.vehicle_current_location_id = d.id"
					+ " WHERE v.id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vehicleId);
			
			rs = ps.executeQuery();

			if (rs.next()) {
				vehicle = new Vehicle();
				vehicle.setId(rs.getInt("id"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setModel(rs.getString("model"));
				vehicle.setDailyFare(rs.getDouble("daily_fare"));
				vehicle.setOwnerId(rs.getInt("owner_id"));
				Owner owner = new Owner();
				owner.setId(rs.getInt("owner_id"));
				owner.setFname(rs.getString("fname"));
				owner.setLname(rs.getString("lname"));
				vehicle.setOwner(owner);
				
				District district = new District();
				district.setName(rs.getString("name"));
				vehicle.setDistrict(district);
				
				sql = "SELECT * FROM `tbl_districts` as d"
						+ " JOIN `vehicle_available_loc` as val ON d.id = val.end_location_id"
						+ " WHERE val.vehicle_id=?";
				ps =  conn.prepareStatement(sql);
				ps.setInt(1, vehicle.getId());
				ResultSet rs1 = ps.executeQuery();
				List<District> districtList = new ArrayList<District>();
				while(rs1.next()){
					district = new District();
					district.setName(rs1.getString("name"));
					districtList.add(district);
				}
				vehicle.setDistrictList(districtList);
				
				vehicle.setFromDate(rs.getDate("from_date").toString());
				vehicle.setToDate(rs.getDate("to_date").toString());
				vehicle.setDescription(rs.getString("description"));
				vehicle.setImage(rs.getBinaryStream("image"));
				vehicle.setVehicleCurrentLocationId(rs.getInt("vehicle_current_location_id"));
				
				// End Location Ids retriving
				sql = "SELECT * FROM `vehicle_available_loc` WHERE `vehicle_id` = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, vehicleId);
				rs = ps.executeQuery();
				List<Integer> locationIds = new ArrayList<Integer>();
				
				while(rs.next()){
					locationIds.add(rs.getInt("end_location_id"));
				}
				
				vehicle.setVehicleEndLocationIds(locationIds);
				
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
		return vehicle;
	}

	public int deleteVehicle(int vehicleId) {
		
		conn = Database.connect();
		try {
			String sql = "DELETE FROM `tbl_vehicles` WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vehicleId);
			queryStatus = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQL ERROR: "+e.getMessage());
		}
		
		return queryStatus;
		
	}
	
	public int getDistrictId(String district){
		
		int districtId = 0;
		conn = Database.connect();
		try {
			String sql = "SELECT id FROM `tbl_districts` WHERE name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, district);
			rs = ps.executeQuery();
			
			if(rs.next())
				districtId = rs.getInt("id");
			
		} catch (SQLException e) {
			System.err.println("SQL ERROR: "+e.getMessage());
		}
		
		return districtId;
	}

	public ArrayList<Vehicle> searchVehicleByStartEnd(int startId, int endId, String date) {
		
		conn = Database.connect();
		try {
//			String sql = "SELECT * FROM `tbl_vehicles` as v"
//					+ " JOIN `vehicle_available_loc` as val ON v.id = val.vehicle_id"
//					+ " JOIN `tbl_owners` as o ON v.owner_id = o.id"
//					+ " WHERE v.vehicle_current_location_id = ? AND val.end_location_id = ? AND `from_date` <= ? AND `to_date` >= ?";
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, startId);
//			ps.setInt(2, endId);
//			ps.setDate(3, new java.sql.Date(Helper.stringToDate(date).getTime()));
//			ps.setDate(4, new java.sql.Date(Helper.stringToDate(date).getTime()));
			
			
			
			
			
			String sql = "SELECT * FROM `tbl_vehicles` as v"
					+ " JOIN `vehicle_available_loc` as val ON v.id = val.vehicle_id"
					+ " JOIN `tbl_owners` as o ON v.owner_id = o.id"
					+ " WHERE v.vehicle_current_location_id = ? AND val.end_location_id = ? AND `from_date` <= ? AND `to_date` >= ? AND o.status = 1";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, startId);
			ps.setInt(2, endId);
			ps.setDate(3, new java.sql.Date(Helper.stringToDate(date).getTime()));
			ps.setDate(4, new java.sql.Date(Helper.stringToDate(date).getTime()));
			
			ResultSet result = ps.executeQuery();
			
			BookingDao bDao = new BookingDao();
			Map<String, Object> condition;
			
			vehicleList = new ArrayList<Vehicle>();
			
			while(result.next()){
				sql = "SELECT * FROM tbl_bookings WHERE vehicle_id = ? AND `start_date` <= ? AND `end_date` >= ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, result.getInt("id"));
				ps.setDate(2, new java.sql.Date(Helper.stringToDate(date).getTime()));
				ps.setDate(3, new java.sql.Date(Helper.stringToDate(date).getTime()));
				
				ResultSet rs1 = ps.executeQuery();
				
				if(rs1.next())
					continue;
				
				Vehicle vehicle = new Vehicle();
				vehicle.setId(result.getInt("id"));
				vehicle.setManufacturer(result.getString("manufacturer"));
				vehicle.setModel(result.getString("model"));
				vehicle.setDescription(result.getString("description"));
				vehicle.setImage(result.getBinaryStream("image"));
				vehicle.setDailyFare(result.getDouble("daily_fare"));
				vehicle.setOwnerId(result.getInt("owner_id"));
				vehicle.setFromDate(date);
				
				
				sql = "SELECT start_date FROM tbl_bookings WHERE vehicle_id = ? AND start_date > ? ORDER BY start_date ASC";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, result.getInt("id"));
				ps.setDate(2, new java.sql.Date(Helper.stringToDate(date).getTime()));
				ResultSet rs2 = ps.executeQuery();
				if(rs2.next()){
					vehicle.setToDate(rs2.getString("start_date"));
				} else {
					vehicle.setToDate(result.getString("to_date"));
				}
				
				System.out.println(vehicle.getFromDate());
				System.out.println(vehicle.getToDate());
				
				Owner owner = new Owner();
				owner.setId(result.getInt("owner_id"));
				owner.setFname(result.getString("fname"));
				owner.setLname(result.getString("lname"));
				
				vehicle.setOwner(owner);
				
				vehicleList.add(vehicle);
				
				
				
			}
			
			
			
			
			
			
			
			
//			ResultSet result = ps.executeQuery();
//			
//			System.out.println("searchVehicle: "+ps.toString());
//			
//			vehicleList = new ArrayList<Vehicle>();
//			
//			while(result.next()){
//				Vehicle vehicle = new Vehicle();
//				vehicle.setId(result.getInt("id"));
//				vehicle.setManufacturer(result.getString("manufacturer"));
//				vehicle.setModel(result.getString("model"));
//				vehicle.setDescription(result.getString("description"));
//				vehicle.setImage(result.getBinaryStream("image"));
//				vehicle.setDailyFare(result.getDouble("daily_fare"));
//				vehicle.setOwnerId(result.getInt("owner_id"));
//				vehicle.setFromDate(result.getString("from_date"));
//				vehicle.setToDate(result.getString("to_date"));
//				
//				Owner owner = new Owner();
//				owner.setId(result.getInt("owner_id"));
//				owner.setFname(result.getString("fname"));
//				owner.setLname(result.getString("lname"));
//				
//				vehicle.setOwner(owner);
//				
//				vehicleList.add(vehicle);
//			}
			
		} catch (SQLException e) {
			ps.toString();
			System.err.println(""+e);
		}
		return vehicleList;
		
	}

}
