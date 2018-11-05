package com.ovr.controller;

import java.io.IOException;
import java.io.InputStream;
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

import com.ovr.dao.BookingDao;
import com.ovr.dao.CommonDao;
import com.ovr.dao.OwnerDao;
import com.ovr.dao.UserDao;
import com.ovr.dao.VehicleDao;
import com.ovr.model.Booking;
import com.ovr.model.Owner;
import com.ovr.model.User;
import com.ovr.model.Vehicle;
import com.ovr.utils.Helper;
import com.ovr.utils.ImageUtil;

@WebServlet(urlPatterns = { "/owner", "/owner/register",
		"/owner/register-success", "/owner/login", "/owner/profile-image",
		"/owner/bookings", "/owner/bookings/user-details",
		"/owner/profile", "/owner/logout", "/owner/vehicles", "/owner/vehicles/reviews",
		"/owner/vehicle/image", "/owner/vehicles/add-edit",
		"/owner/vehicles/delete", "/owner/vehicles/request-feature" })
@MultipartConfig
public class OwnerController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String uri;
	private Owner owner;
	private OwnerDao ownerDao;
	private HttpSession session;
	private Map<String, Object> data;
	private Map<String, Object> condition;
	private int updatedRows;
	private CommonDao commonDao;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		uri = request.getServletPath();
		session = request.getSession();
		session.setMaxInactiveInterval(-1);

		switch (uri) {

		case "/owner":
			if (!Helper.isLoggedIn("owner", session)) {
				response.sendRedirect(request.getContextPath() + "/owner/login");
				return;
			}
			response.sendRedirect(request.getContextPath() + "/owner/profile");
			break;

		case "/owner/register":
			request.getRequestDispatcher("/WEB-INF/views/owner/register.jsp")
					.forward(request, response);
			break;

		case "/owner/login":
			request.getRequestDispatcher("/WEB-INF/views/owner/login.jsp")
					.forward(request, response);
			break;

		case "/owner/logout":
			session.removeAttribute("owner_id");
			session.removeAttribute("owner_email");
			session.removeAttribute("owner_name");
			response.sendRedirect(request.getContextPath() + "/owner/login");
			break;

		case "/owner/profile-image":
			
			ownerDao = new OwnerDao();
			owner = ownerDao.getOwnerDetailsById(Integer
					.parseInt((String) session.getAttribute("owner_id")));
			InputStream imageData = owner.getProfileImage();

			ImageUtil.writeStreamImage(imageData, response);
			break;

		case "/owner/profile":

			// int ownerId = Integer.valueOf(request.getParameter("ownerId"));

			if (session.getAttribute("owner_id") != null) {
				int ownerId = Integer.parseInt((String) session
						.getAttribute("owner_id"));
				ownerDao = new OwnerDao();
				owner = ownerDao.getOwnerDetailsById(ownerId);
				request.setAttribute("owner", owner);
				request.getRequestDispatcher("/WEB-INF/views/owner/profile.jsp")
						.forward(request, response);
			} else {
				Helper.setErrorMsg("You're not logged In. Please log in", session);
				response.sendRedirect(request.getContextPath() + "/owner/login");
			}
			break;

		case "/owner/bookings":
			BookingDao bDao = new BookingDao();
			condition = new HashMap<String, Object>();
			condition.put("owner_id", Integer.valueOf((String) session.getAttribute("owner_id")));
			List<Booking> bookingList = bDao.getWhere(condition, "");
			request.setAttribute("bookingList", bookingList);
			request.getRequestDispatcher("/WEB-INF/views/owner/bookings.jsp").forward(request, response);
			break;
			
		case "/owner/vehicle/image":

			int vehicleId = Integer
					.parseInt(request.getParameter("vehicle_id"));
			Vehicle vehicle = new Vehicle();
			VehicleDao vehicleDao = new VehicleDao();
			vehicle = vehicleDao.getVehicleDetails(vehicleId);

			ImageUtil.writeStreamImage(vehicle.getImage(), response);

			break;

		case "/owner/vehicles":
			if (!Helper.isLoggedIn("owner", session)) {
				response.sendRedirect(request.getContextPath() + "/owner/login");
				return;
			}

			vehicleDao = new VehicleDao();
			condition = new HashMap<String, Object>();
			condition.put("owner_id",
					Integer.valueOf((String) session.getAttribute("owner_id")));
			ArrayList<Vehicle> vehicleList = vehicleDao.getWhere(condition, "");

			request.setAttribute("vehicleList", vehicleList);
			request.getRequestDispatcher("/WEB-INF/views/owner/vehicles.jsp")
					.forward(request, response);

			break;

		case "/owner/vehicles/add-edit":
			if (!Helper.isLoggedIn("owner", session)) { response.sendRedirect(request.getContextPath() + "/owner/login");return;}

			if (request.getParameterMap().containsKey("id")) { // edit
				String id = request.getParameter("id");
				vehicleDao = new VehicleDao();
				vehicle = vehicleDao.getVehicleDetails(Integer.valueOf(id));
				request.setAttribute("vehicle", vehicle);
			}

			request.getRequestDispatcher(
					"/WEB-INF/views/owner/vehicle_form.jsp").forward(request,
					response);

			break;

		case "/owner/vehicles/delete":
			if (!Helper.isLoggedIn("owner", session)) {
				response.sendRedirect(request.getContextPath() + "/owner/login");
				return;
			}

			vehicleId = Integer.valueOf(request.getParameter("id"));
			vehicleDao = new VehicleDao();
			updatedRows = vehicleDao.deleteVehicle(vehicleId);
			
			if(updatedRows>0)
				Helper.setSuccessMsg("Vehicle Deleted Successfully.", session);
			else 
				Helper.setErrorMsg("Something went wrong.", session);
			
			response.sendRedirect(request.getContextPath() + "/owner/vehicles");
			break;

		case "/owner/vehicles/request-feature":

			commonDao = new CommonDao();
			data = new HashMap<String, Object>();
			condition = new HashMap<String, Object>();
			
			data.put("feature_status", request.getParameter("status"));
			condition.put("id", request.getParameter("vehicle_id"));
			commonDao.update("tbl_vehicles", data, condition);

			Helper.setSuccessMsg("Vehicle request to be featured.", session);
			response.sendRedirect(request.getContextPath() + "/owner/vehicles");
			break;
			
		case "/owner/vehicles/reviews":
			bDao = new BookingDao();
			List<Booking> bList = bDao.getAllReviewsByVehicleId(Integer.valueOf(request.getParameter("vehicle_id")));
			request.setAttribute("bookingList", bList);
			request.getRequestDispatcher("/WEB-INF/views/owner/vehicle-reviews.jsp").forward(request, response);
			break;
			
		case "/owner/bookings/user-details":
			int userId = Integer.valueOf(request.getParameter("user_id"));
			UserDao uDao = new UserDao();
			condition = new HashMap<String, Object>();
			condition.put("id", userId);
			List<User> u = uDao.getWhere(condition, "");
			
			request.setAttribute("user", u.get(0));
			request.getRequestDispatcher("/WEB-INF/views/owner/user-details.jsp").forward(request, response);
			break;

		default:
			break;

		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		uri = request.getServletPath();
		session = request.getSession();

		switch (uri) {

		case "/owner/register":
			owner = new Owner();

			owner.setFname(request.getParameter("fname"));
			owner.setLname(request.getParameter("lname"));
			owner.setPhone(request.getParameter("phone"));
			owner.setEmail(request.getParameter("email"));
			owner.setPassword(request.getParameter("password"));
			owner.setProfileImage(request.getPart("profile_image")
					.getInputStream());

			ownerDao = new OwnerDao();
			int result = ownerDao.addOwner(owner);
			if (result == 1) {
				request.getRequestDispatcher(
						"/WEB-INF/views/owner/register-success.jsp").forward(
						request, response);
			} else {
				Helper.setErrorMsg("Something went wrong! Please try again.",
						session);
				response.sendRedirect(request.getContextPath()
						+ "/owner/register");
			}

			break;

		case "/owner/login":

			String email = request.getParameter("email");
			String password = request.getParameter("password");

			ownerDao = new OwnerDao();
			owner = ownerDao.getOwnerDetails(email, password);

			if (owner != null) {

				System.out.println(owner.isStatus());

				if (owner.isStatus()) {
					session.setAttribute("owner_id",
							String.valueOf(owner.getId()));
					session.setAttribute("owner_email", owner.getEmail());
					session.setAttribute("owner_name", owner.getFname() + " "
							+ owner.getLname());
					response.sendRedirect(request.getContextPath()
							+ "/owner/profile");
					return;
				} else {
					Helper.setErrorMsg("User Disabled. Please contact Administrator.", session);
				}

			} else {
				Helper.setErrorMsg("Invalid User/Pass", session);
			}

			response.sendRedirect(request.getContextPath() + "/owner/login");

			break;

		case "/owner/profile":

			ownerDao = new OwnerDao();
			owner = new Owner();

			owner.setId(Integer.valueOf(request.getParameter("ownerId")));
			owner.setFname(request.getParameter("fname"));
			owner.setLname(request.getParameter("lname"));
			owner.setPhone(request.getParameter("phone"));
			
			if(request.getPart("profile_image").getSize() > 0){
				owner.setProfileImage(request.getPart("profile_image").getInputStream());
			} else {
				Owner prevOwnerDetails = ownerDao.getOwnerDetailsById(owner.getId());
				owner.setProfileImage(prevOwnerDetails.getProfileImage());
			}

			updatedRows = ownerDao.updateOwnerDetails(owner);

			response.sendRedirect(request.getContextPath() + "/owner/profile");

			break;

		case "/owner/vehicles/add-edit":

			if (!Helper.isLoggedIn("owner", session)) {
				response.sendRedirect(request.getContextPath() + "/owner/login");
				return;
			}
			
			Vehicle v = new Vehicle();
			VehicleDao vehicleDao = new VehicleDao();
			
			v.setManufacturer(request.getParameter("manufacturer"));
			v.setModel(request.getParameter("model"));
			v.setDailyFare(Double.parseDouble(request
					.getParameter("daily_fare")));
			v.setDescription(request.getParameter("description"));
			
			v.setOwnerId(Integer.parseInt((String) session
					.getAttribute("owner_id")));
			v.setFromDate(request.getParameter("from_date"));
			v.setToDate(request.getParameter("to_date"));
			v.setVehicleCurrentLocationId(Integer.valueOf(request
					.getParameter("vehicle_current_location_id")));

			// EndLocationIds
			String[] endLocationIdsArr = request
					.getParameterValues("end_location_ids");
			List<Integer> endLocationIds = new ArrayList<Integer>();
			for (String s : endLocationIdsArr)
				endLocationIds.add(Integer.parseInt(s));

			v.setVehicleEndLocationIds(endLocationIds);

			if (request.getParameterMap().containsKey("vehicle_id")) {
				v.setId(Integer.valueOf(request.getParameter("vehicle_id")));
				
				if(request.getPart("image").getSize() > 0){
					v.setImage(request.getPart("image").getInputStream());
				} else {
					Vehicle prevVehicle = vehicleDao.getVehicleDetails(v.getId());
					v.setImage(prevVehicle.getImage());
				}
				
				updatedRows = vehicleDao.updateVehicle(v);
				
				if(updatedRows>0) 
					Helper.setSuccessMsg("Vehicle Updated Successfully!", session); 
				else 
					Helper.setErrorMsg("Something went wrong!", session);
				
			} else {
				v.setImage(request.getPart("image").getInputStream());
				updatedRows = vehicleDao.addVehicle(v);
				
				if(updatedRows>0) 
					Helper.setSuccessMsg("New Vehicle Added Successfully!", session); 
				else 
					Helper.setErrorMsg("Something went wrong!", session);
				
			}

			response.sendRedirect(request.getContextPath() + "/owner/vehicles");

		default:
			break;
		}
	}

}
