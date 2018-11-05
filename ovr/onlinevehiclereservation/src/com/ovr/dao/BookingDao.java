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
import com.ovr.model.User;
import com.ovr.model.Vehicle;
import com.ovr.utils.Database;
import com.ovr.utils.Helper;

public class BookingDao {

	Connection conn;
	String sql;
	PreparedStatement ps;
	ResultSet rs;
	int lastInsertedId;
	Booking booking;
	List<Booking> bookingList;
	Vehicle vehicle;
	VehicleDao vehicleDao;

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
			System.err.println("BookingDao (insert): " + e.getMessage());
		}
		return lastInsertedId;
	}

	public List<Booking> getWhere(Map<String, Object> condition, String operator) {

		bookingList = new ArrayList<Booking>();

		conn = Database.connect();
		try {
			sql = "SELECT *, b.id as b_id FROM `tbl_bookings` as b" 
					+ " JOIN `tbl_vehicles` as v ON b.vehicle_id = v.id"
					+ " JOIN `tbl_owners` as o ON o.id = v.owner_id"
					+ " JOIN `tbl_users` as u ON u.id = b.user_id"
					+ " WHERE";

			for (String key : condition.keySet()) {
				sql += " `" + key + "` = '" + condition.get(key) + "' "
						+ operator;
			}

			if (operator.equals("AND"))
				sql = sql.substring(0, sql.length() - 3);
			else if (operator.equals("OR"))
				sql = sql.substring(0, sql.length() - 2);

			ps = conn.prepareStatement(sql);
			System.out.println(ps.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				booking = new Booking();
				booking.setId(rs.getInt("b_id"));
				booking.setStartLocation(rs.getString("start_location"));
				booking.setEndLocation(rs.getString("end_location"));
				booking.setStartDate(rs.getString("start_date"));
				booking.setEndDate(rs.getString("end_date"));
				booking.setDays(rs.getInt("days"));
				booking.setTotalFare(rs.getDouble("total_fare"));
				booking.setPaymentMethod(rs.getString("payment_method"));
				booking.setAdditionalMessage(rs.getString("additional_message"));
				booking.setReview(rs.getString("review"));
				
				vehicleDao = new VehicleDao();
				vehicle = vehicleDao.getVehicleDetails(rs.getInt("vehicle_id"));
				booking.setVehicle(vehicle);

				UserDao uDao = new UserDao();
				condition = new HashMap<String, Object>();
				condition.put("id", rs.getInt("user_id"));
				List<User> userList = uDao.getWhere(condition, "");
				booking.setUser(userList.get(0));
				
				bookingList.add(booking);
			}

		} catch (SQLException e) {
			System.err.println("BookingDao (getWhere): " + e.getMessage());
		}
		return bookingList;
	}
	
	public String getNextBookingDate(int vehicleId, String needToBookDate){
		String date = null;
		conn = Database.connect();
		
		sql = "SELECT start_date FROM tbl_bookings WHERE vehicle_id = ? AND start_date > ? ORDER BY start_date ASC";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vehicleId);
			ps.setDate(2, new java.sql.Date(Helper.stringToDate(needToBookDate).getTime()));
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				date = rs.getString("start_date");
			
		} catch (SQLException e) {
			System.err.println("BookingDAO (getNextBookingDate): "+e.getMessage());
		}
		
		return date;
	}
	
	public List<Booking> getAllReviewsByVehicleId(int vehicleId){
		
		conn = Database.connect();
		List<Booking> bList = new ArrayList<Booking>();
		sql = "SELECT b.id as b_id, review, reviewed_date, fullname FROM tbl_bookings as b"
				+ " JOIN tbl_users as u ON u.id = b.user_id"
				+ " WHERE b.vehicle_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vehicleId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Booking b = new Booking();
				b.setId(rs.getInt("b_id"));
				b.setReview(rs.getString("review"));
				b.setReviewedDate(rs.getString("reviewed_date"));
				
				User u = new User();
				u.setFullname(rs.getString("fullname"));
				b.setUser(u);
				
				bList.add(b);
				
			}
		} catch (SQLException e) {
			System.err.println("BookingDao (getAllREviewsByVehicleId) : "+e.getMessage());
		}
		
		return bList;
	}

}
