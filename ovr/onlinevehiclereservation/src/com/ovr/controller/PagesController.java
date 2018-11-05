package com.ovr.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ovr.dao.BookingDao;
import com.ovr.dao.VehicleDao;
import com.ovr.model.Booking;
import com.ovr.model.District;
import com.ovr.model.Vehicle;
import com.ovr.utils.Helper;

@WebServlet (urlPatterns = {"/home", "/about", "/faqs", "/testimonials", "/contact", "/vehicle-details", "/ajax_getVehicleCurrentLocation", "/ajax_getVehicleAvailableLocations"})
public class PagesController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private Map<String, Object> data = new HashMap<String, Object>();
	private Map<String, Object> condition = new HashMap<String, Object>();
	private VehicleDao vDao;
	private String uri;
	private Vehicle v;
	private PrintWriter out;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		uri = request.getServletPath();
		session = request.getSession();
		session.setMaxInactiveInterval(-1);
		
		switch (uri) {
		
		case "/home":
			vDao = new VehicleDao();
			condition.put("feature_status", "featured");
			List<Vehicle> featuredVehicles = vDao.getWhere(condition, "");
			request.setAttribute("featuredVehicles", featuredVehicles);
			
			request.getRequestDispatcher("/WEB-INF/views/frontend/index.jsp").forward(request, response);
			break;
		
		case "/about":
			request.getRequestDispatcher("/WEB-INF/views/frontend/default.jsp").forward(request, response);
			break;
			
		case "/faqs":
			request.getRequestDispatcher("/WEB-INF/views/frontend/default.jsp").forward(request, response);
			break;
		
		case "/testimonials":
			request.getRequestDispatcher("/WEB-INF/views/frontend/default.jsp").forward(request, response);
			break;
			
		case "/contact":
			request.getRequestDispatcher("/WEB-INF/views/frontend/default.jsp").forward(request, response);
			break;
		
		case "/vehicle-details":
			int vehicleId = Integer.valueOf(request.getParameter("id"));
			vDao = new VehicleDao();
			Vehicle v = vDao.getVehicleDetails(vehicleId);
			
			BookingDao bDao = new BookingDao();
			List<Booking> bList = bDao.getAllReviewsByVehicleId(Integer.valueOf(request.getParameter("id")));
			
			request.setAttribute("vehicle", v);
			request.setAttribute("bookingList", bList);
			
			request.getRequestDispatcher("/WEB-INF/views/frontend/vehicle-details.jsp").forward(request, response);
			break;
			
			
		default:
			System.out.println("No page defined");
			break;
		}
		
			
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		uri = request.getServletPath();
		session = request.getSession();
		
		switch (uri) {
		case "/ajax_getVehicleCurrentLocation":
			
			vDao = new VehicleDao();
			v = vDao.getVehicleDetails(Integer.valueOf(request.getParameter("vehicle_id")));
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(v.getDistrict().getName());
			out.flush();
			out.close();
			break;
			
		case "/ajax_getVehicleAvailableLocations":
			
			vDao = new VehicleDao();
			v = vDao.getVehicleDetails(Integer.valueOf(request.getParameter("vehicle_id")));
			response.setContentType("text/plain");
			out = response.getWriter();
			
			String locations = "";
			for(District d : v.getDistrictList()){ 
				locations += d.getName()+",";
			}
			
			locations = locations.substring(0, locations.length()-1);
			out.write(locations);
			out.flush();
			out.close();
			break;

		default:
			break;
		}
		
	}
	
}
