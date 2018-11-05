package com.ovr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
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
import com.ovr.model.Booking;
import com.ovr.model.User;
import com.ovr.utils.Helper;
import com.ovr.utils.ImageUtil;

@WebServlet(urlPatterns = { "/user", "/user/login", "/user/logout", "/user/edit-profile", "/user/image", "/user/booking-history", "/user/ajax_checkIfEmailExists", "/user/authenticate", "/user/review-add-edit" })
@MultipartConfig
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private UserDao userDao;
	private Map<String, Object> data = new HashMap<String, Object>();
	private Map<String, Object> condition = new HashMap<String, Object>();
	private int updatedRows;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getServletPath();
		session = request.getSession();

		switch (uri) {

		case "/user":
			int userId = Integer.valueOf((String) session.getAttribute("user_id"));
			UserDao uDao = new UserDao();
			condition = new HashMap<String, Object>();
			condition.put("id", userId);
			List<User> u = uDao.getWhere(condition, "");
			request.setAttribute("user", u.get(0));
			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/user/index.jsp").forward(request,
					response);
			break;

		case "/user/login":
			if(Helper.isLoggedIn("user", session)) { response.sendRedirect(request.getContextPath()+"/user"); return; }
			
			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/user/login.jsp").forward(request,
					response);
			break;
			
		case "/user/logout":
			session.removeAttribute("user_id");
			session.removeAttribute("user_email");
			response.sendRedirect(request.getContextPath()+"/user/login");
			break;
			
		case "/user/booking-history":
			BookingDao bDao = new BookingDao();
			condition = new HashMap<String, Object>();
			condition.put("user_id", Integer.valueOf((String) session.getAttribute("user_id")));
			List<Booking> bookingList = bDao.getWhere(condition, "");
			request.setAttribute("bookingList", bookingList);
			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/user/booking-history.jsp").forward(request,
					response);
			break;
			
		case "/user/review-add-edit":
			bDao = new BookingDao();
			condition = new HashMap<String, Object>();
			condition.put("b`.`id", Integer.valueOf(request.getParameter("booking_id")));
			List<Booking> b = bDao.getWhere(condition, "");
			request.setAttribute("booking", b.get(0));
			request.getRequestDispatcher(
					"/WEB-INF/views/frontend/user/review-add-edit.jsp").forward(request, response);
			break;
			
		case "/user/image":
			
			userDao = new UserDao();
			condition = new HashMap<String, Object>();
			
			if(request.getParameterMap().containsKey("user_id"))
				condition.put("id", Integer.valueOf(request.getParameter("user_id")));
			else
				condition.put("id", Integer.valueOf((String) session.getAttribute("user_id")));
					
			List<User> userList = userDao.getWhere(condition, "");
			
			InputStream imageData = null;
			if(request.getParameter("type").equals("profile-image"))
				imageData = userList.get(0).getProfileImage();
			else if(request.getParameter("type").equals("citizenship"))
				imageData = userList.get(0).getCitizenship();
			else if(request.getParameter("type").equals("driving-license"))
				imageData = userList.get(0).getDrivingLicense();
			
			ImageUtil.writeStreamImage(imageData, response);
			break;
			
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String uri = request.getServletPath();
		session = request.getSession();
		session.setMaxInactiveInterval(-1);

		switch (uri) {
		case "/user/ajax_checkIfEmailExists":
			userDao = new UserDao();
			condition = new HashMap<String, Object>();
			condition.put("email", request.getParameter("email"));
			List<User> userList = userDao.getWhere(condition, "");

			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			if (userList.isEmpty()) {
				out.write("noexist");
			} else {
				out.write("exists");
			}
			out.close();
			break;
			
			
		case "/user/authenticate":
			userDao = new UserDao();
			
			if(request.getParameter("action").equals("login")){
				System.out.println("LOGIN");
				condition = new HashMap<String, Object>();
				condition.put("email", request.getParameter("email"));
				condition.put("password", request.getParameter("password"));
				userList = userDao.getWhere(condition, "AND");
				
				if(!userList.isEmpty()){
					
					session.setAttribute("user_id", String.valueOf(userList.get(0).getId()));
					session.setAttribute("user_email", userList.get(0).getEmail());
					
					if(session.getAttribute("login-request") != null && session.getAttribute("login-request").equals("from-booking")){
						session.removeAttribute("login-request");
						response.sendRedirect(request.getContextPath()+"/quote/book");
					} else {
						response.sendRedirect(request.getContextPath()+"/user");
					}
					
					
					
				} else {
					Helper.setErrorMsg("Invalid User/Pass", session);
					response.sendRedirect(request.getContextPath()+"/user/login");
				}
				
			} else if (request.getParameter("action").equals("register")){
				System.out.println("REGISTER");
				data = new HashMap<String, Object>();
				data.put("email", request.getParameter("email"));
				data.put("password", request.getParameter("password"));
				int lastInsertedId = userDao.insert(data);
				
				session.setAttribute("user_id", String.valueOf(lastInsertedId));
				session.setAttribute("user_email", request.getParameter("email"));
				
				if(session.getAttribute("login-request") != null && session.getAttribute("login-request").equals("from-booking")){
					session.removeAttribute("login-request");
					response.sendRedirect(request.getContextPath()+"/quote/book");
				} else {
					response.sendRedirect(request.getContextPath()+"/user");
				}
			}
			
			break;
			
		case "/user/review-add-edit":
			CommonDao cDao = new CommonDao();
			condition = new HashMap<String, Object>();
			condition.put("id", Integer.valueOf(request.getParameter("booking_id")));
			data = new HashMap<String, Object>();
			data.put("review", request.getParameter("review"));
			data.put("reviewed_date", new Date());
			
			updatedRows = cDao.update("tbl_bookings", data, condition);
			
			if(updatedRows>0)
				Helper.setSuccessMsg("Vehicle review added successfully.", session);
			else
				Helper.setErrorMsg("Something went wrong.", session);
			
			response.sendRedirect(request.getContextPath()+"/user/booking-history");
			break;

		case "/user/edit-profile":
			
			User user = new User();
			user.setFullname(request.getParameter("fullname"));
			user.setContactNo(request.getParameter("contact_no"));
			user.setAddress(request.getParameter("address"));
			
			UserDao uDao = new UserDao();
			condition = new HashMap<String, Object>();
			condition.put("id", Integer.valueOf((String) session.getAttribute("user_id")));
			List<User> prevUserData = uDao.getWhere(condition, "");
			
			if(request.getPart("profile_image").getSize() > 0)
				user.setProfileImage(request.getPart("profile_image").getInputStream());
			else
				user.setProfileImage(prevUserData.get(0).getProfileImage());
			
			if(request.getPart("citizenship").getSize() > 0)
				user.setCitizenship(request.getPart("citizenship").getInputStream());
			else
				user.setCitizenship(prevUserData.get(0).getCitizenship());
				
			if(request.getPart("driving_license").getSize() > 0)
				user.setDrivingLicense(request.getPart("driving_license").getInputStream());
			else
				user.setDrivingLicense(prevUserData.get(0).getDrivingLicense());
			
			data = new HashMap<String, Object>();
			data.put("fullname", user.getFullname());
			data.put("contact_no", user.getContactNo());
			data.put("address", user.getAddress());
			data.put("profile_image", user.getProfileImage());
			data.put("citizenship", user.getCitizenship());
			data.put("driving_license", user.getDrivingLicense());
			
			cDao = new CommonDao();
			cDao.update("tbl_users", data, condition);
			
			Helper.setSuccessMsg("User profile updated successfully.", session);
			response.sendRedirect(request.getContextPath()+"/user");
			break;
			
		default:
			break;
		}
	}

}
