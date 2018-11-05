package com.ovr.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Encoder.BinaryStream;

import com.ovr.dao.BookingDao;
import com.ovr.dao.CommonDao;
import com.ovr.dao.OwnerDao;
import com.ovr.dao.UserDao;
import com.ovr.dao.VehicleDao;
import com.ovr.model.Booking;
import com.ovr.model.Owner;
import com.ovr.model.Quote;
import com.ovr.model.User;
import com.ovr.model.Vehicle;
import com.ovr.utils.Database;
import com.ovr.utils.Helper;

@WebServlet(urlPatterns = { "/quote", "/quote/book", "/quote/confirm" })
@MultipartConfig
public class QuoteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private Quote quote;
	private Map<String, Object> data;
	private Map<String, Object> condition;
	private int updatedRows;
	private CommonDao cDao;
	private VehicleDao vehicleDao;
	private String uri;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		uri = request.getServletPath();
		session = request.getSession();
		// session.setMaxInactiveInterval(-1);

		switch (uri) {
		case "/quote":

			quote = new Quote();
			quote.setStart(request.getParameter("start"));
			quote.setEnd(request.getParameter("end"));
			quote.setDate(request.getParameter("date"));
			session.setAttribute("quote", quote);

			vehicleDao = new VehicleDao();

			int startId = vehicleDao.getDistrictId(quote.getStart());
			int endId = vehicleDao.getDistrictId(quote.getEnd());

			ArrayList<Vehicle> vehicleList = vehicleDao
					.searchVehicleByStartEnd(startId, endId, quote.getDate());

			request.setAttribute("quote", quote);
			request.setAttribute("vehicleList", vehicleList);
			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/quote/quote.jsp").forward(request,
					response);
			break;

		case "/quote/book":

			if (session.getAttribute("booking-details") == null) {

				Booking newBooking = new Booking();
				quote = new Quote();
				quote = (Quote) session.getAttribute("quote");

				newBooking.setStartLocation(quote.getStart());
				newBooking.setEndLocation(quote.getEnd());
				newBooking.setStartDate(quote.getDate());

				vehicleDao = new VehicleDao();
				Vehicle v = vehicleDao.getVehicleDetails(Integer
						.valueOf(request.getParameter("vehicle_id")));
				
				BookingDao bDao = new BookingDao();
				String nextBookedDate = bDao.getNextBookingDate(v.getId(), newBooking.getStartDate()); 
				
				if(nextBookedDate != null)
					v.setToDate(nextBookedDate);
				
				newBooking.setVehicle(v);
				session.setAttribute("booking-details", newBooking);
			}

			Booking bookingDetails = (Booking) session
					.getAttribute("booking-details");

			if (session.getAttribute("user_id") == null) {
				session.setAttribute("login-request", "from-booking");
				response.sendRedirect(request.getContextPath() + "/user/login");
				return;
			} else {
				UserDao uDao = new UserDao();
				condition = new HashMap<String, Object>();
				condition.put("id", Integer.valueOf((String) session.getAttribute("user_id")));
				List<User> userList = uDao.getWhere(condition, "");
				bookingDetails.setUser(userList.get(0));
			}

			// Display a form to enter further Details
			System.out
					.println("-------------------------------------------------");
			System.out.println("Booked Vehicle: "
					+ bookingDetails.getVehicle().getId());
			System.out.println("User ID: " + bookingDetails.getUser().getId());
			System.out.println("For Date: " + bookingDetails.getStartDate());
			System.out.println("From: " + bookingDetails.getStartLocation());
			System.out.println("To: " + bookingDetails.getEndLocation());
			System.out
					.println("-------------------------------------------------");

			session.removeAttribute("booking-details");
			session.setAttribute("booking", bookingDetails);

			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/quote/booking-details.jsp")
					.forward(request, response);
			break;

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		uri = request.getServletPath();
		session = request.getSession();

		switch (uri) {

		case "/quote/confirm":

			User user = new User();
			user.setFullname(request.getParameter("fullname"));
			user.setContactNo(request.getParameter("contact_no"));
			user.setAddress(request.getParameter("address"));
			
			if(request.getPart("profile_image") != null){
				user.setProfileImage(request.getPart("profile_image")
						.getInputStream());
				user.setCitizenship(request.getPart("citizenship").getInputStream());
				user.setDrivingLicense(request.getPart("driving_license")
						.getInputStream());
			} else {
				UserDao uDao = new UserDao();
				condition = new HashMap<String, Object>();
				condition.put("id", Integer.valueOf((String) session.getAttribute("user_id")));
				List<User> prevUserData = uDao.getWhere(condition, "");
				user.setProfileImage(prevUserData.get(0).getProfileImage());
				user.setCitizenship(prevUserData.get(0).getCitizenship());
				user.setDrivingLicense(prevUserData.get(0).getDrivingLicense());
			}
			
			cDao = new CommonDao();
			data = new HashMap<String, Object>();
			condition = new HashMap<String, Object>();

			data.put("fullname", user.getFullname());
			data.put("contact_no", user.getContactNo());
			data.put("address", user.getAddress());
			data.put("profile_image", user.getProfileImage());
			data.put("citizenship", user.getCitizenship());
			data.put("driving_license", user.getDrivingLicense());
			
			condition.put("id",
					Integer.valueOf((String) session.getAttribute("user_id")));
			
			cDao.update("tbl_users", data, condition);
			

			Booking booking = (Booking) session.getAttribute("booking");
			booking.setEndDate(request.getParameter("to_date"));
			booking.setDays(Integer.valueOf(request.getParameter("days")));
			booking.setTotalFare(Double.valueOf(request
					.getParameter("total_fare")));
			booking.setAdditionalMessage(request
					.getParameter("additional_message"));
			booking.setPaymentMethod(request.getParameter("payment_method"));

			cDao = new CommonDao();
			data = new HashMap<String, Object>();
			data.put("start_location", booking.getStartLocation());
			data.put("end_location", booking.getEndLocation());
			data.put("start_date", booking.getStartDate());
			data.put("end_date", booking.getEndDate());
			data.put("days", booking.getDays());
			data.put("total_fare", booking.getTotalFare());
			data.put("payment_method", booking.getPaymentMethod());
			data.put("additional_message", booking.getAdditionalMessage());
			data.put("user_id",
					Integer.valueOf((String) session.getAttribute("user_id")));
			data.put("vehicle_id", booking.getVehicle().getId());

			updatedRows = cDao.insert("tbl_bookings", data);
			if (updatedRows > 0) {
				Helper.setSuccessMsg(
						"Booking Successfull. You can view your booking history thorugh your user dashboard.",
						session);
			} else {
				Helper.setErrorMsg("Something went wrong!", session);
			}

			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/quote/booking-success.jsp")
					.forward(request, response);
			break;

		default:
			break;
		}

	}

}
