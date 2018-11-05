package com.ovr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ovr.dao.CommonDao;
import com.ovr.dao.OwnerDao;
import com.ovr.dao.VehicleDao;
import com.ovr.model.Owner;
import com.ovr.model.Vehicle;
import com.ovr.utils.Helper;

@WebServlet(urlPatterns = { "/admin", "/admin/login", "/admin/logout",
		"/admin/reset-password", "/admin/owners", "/admin/change-password",
		"/admin/owners/change-status", "/admin/owners/feature-vehicles", "/admin/owners/vehicle-details" })
public class AdminController extends HttpServlet {

	private static final long serialVersionUID = -7451511459638120488L;
	private String uri;
	private HttpSession session;
	private int updatedRows;
	private CommonDao commonDao = new CommonDao();
	private VehicleDao vehicleDao = new VehicleDao();
	private Vehicle vehicle = new Vehicle();
	private Map<String, Object> data;
	private Map<String, Object> condition;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		uri = req.getServletPath();
		session = req.getSession();
		session.setMaxInactiveInterval(-1);

		switch (uri) {
		case "/admin":
			if (!Helper.isLoggedIn("admin", session)) {
				resp.sendRedirect(req.getContextPath() + "/admin/login");
				return;
			}
			;

			req.getRequestDispatcher("/WEB-INF/views/admin/index.jsp").forward(
					req, resp);
			break;

		case "/admin/login":
			req.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(
					req, resp);
			break;

		case "/admin/logout":
			session.removeAttribute("admin_id");
			req.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(
					req, resp);
			break;

		case "/admin/reset-password":
			req.getRequestDispatcher("/WEB-INF/views/admin/reset-password.jsp")
					.forward(req, resp);
			break;

		case "/admin/owners":
			if (!Helper.isLoggedIn("admin", session)) {
				resp.sendRedirect(req.getContextPath() + "/admin/login");
				return;
			}
			;

			OwnerDao ownerDao = new OwnerDao();
			Owner[] ownerList = ownerDao.getAllOwners();
			req.setAttribute("ownerList", ownerList);

			req.getRequestDispatcher("/WEB-INF/views/admin/owners.jsp")
					.forward(req, resp);
			break;

		case "/admin/owners/change-status":
			if (!Helper.isLoggedIn("admin", session)) {
				resp.sendRedirect(req.getContextPath() + "/admin/login");
				return;
			}
			;

			boolean status = Boolean.parseBoolean(req.getParameter("status"));
			int ownerId = Integer.valueOf(req.getParameter("owner_id"));

			ownerDao = new OwnerDao();
			updatedRows = ownerDao.changeStatus(status, ownerId);

			if (updatedRows > 0)
				session.setAttribute("msg", "Status changed successfully");
			else
				session.setAttribute("msg", "Something went wrong");

			resp.sendRedirect(req.getContextPath() + "/admin/owners");

			break;

		case "/admin/owners/feature-vehicles":
			if (!Helper.isLoggedIn("admin", session)) {
				resp.sendRedirect(req.getContextPath() + "/admin/login");
				return;
			}
			;

			if (req.getParameterMap().containsKey("vehicle_id")) {
				data = new HashMap<String, Object>();
				condition = new HashMap<String, Object>();
				data.put("feature_status", req.getParameter("status"));
				condition.put("id",
						Integer.valueOf(req.getParameter("vehicle_id")));
				vehicleDao.update(data, condition);

				resp.sendRedirect(req.getContextPath()
						+ "/admin/owners/feature-vehicles");
			} else {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("feature_status",
						"requested' OR feature_status = 'featured");
				ArrayList<Vehicle> vehicleList = vehicleDao.getWhere(condition,
						"OR");

				req.setAttribute("vehicleList", vehicleList);
				req.getRequestDispatcher(
						"/WEB-INF/views/admin/featured-vehicles.jsp").forward(
						req, resp);
			}

			break;

		case "/admin/owners/vehicle-details":
			int vehicleId = Integer.valueOf(req.getParameter("id"));
			VehicleDao vDao = new VehicleDao();
			Vehicle v = vDao.getVehicleDetails(vehicleId);
			req.setAttribute("vehicle", v);
			req.getRequestDispatcher(
					"/WEB-INF/views/admin/vehicle-details.jsp").forward(
					req, resp);
			break;
			
		case "/admin/change-password":
			req.getRequestDispatcher(
					"/WEB-INF/views/admin/change-password.jsp").forward(req, resp);
			break;
			
		default:
			System.out.println("No page defined");
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		uri = req.getServletPath();
		session = req.getSession();

		switch (uri) {
		case "/admin/login":

			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			CommonDao cDao = new CommonDao();
			condition = new HashMap<String, Object>();
			condition.put("username", username);
			condition.put("password", password);
			int count = cDao.getCount("tbl_admin", condition, "AND");

			if (count > 0) {
				session.setAttribute("admin_id", "1");
				session.setAttribute("admin_email", username);
				resp.sendRedirect(req.getContextPath() + "/admin");
			} else {
				Helper.setErrorMsg("Invalid User/Pass", session);
				resp.sendRedirect(req.getContextPath() + "/admin/login");
			}

			break;
			
		case "/admin/change-password":
			String newPass = req.getParameter("new_pass");
			String newPass_r = req.getParameter("new_pass_r");
			
			if(newPass.equals(newPass_r)){
				cDao = new CommonDao();
				data = new HashMap<String, Object>();
				condition = new HashMap<String, Object>();
				
				data.put("password", newPass);
				condition.put("id", 1);
				int updatedRows = cDao.update("tbl_admin", data, condition);
				
				if(updatedRows>0)
					Helper.setSuccessMsg("Password changed successfully.", session);
				else 
					Helper.setErrorMsg("Something went wrong!", session);
				
				resp.sendRedirect(req.getContextPath()+"/admin/change-password");
				return;
			} else {
				Helper.setErrorMsg("Passwords do not match each other. Please try again.", session);
			}
			
			req.getRequestDispatcher(
					"/WEB-INF/views/admin/change-password.jsp").forward(req, resp);
			break;

		default:
			break;

		}

	}

}
