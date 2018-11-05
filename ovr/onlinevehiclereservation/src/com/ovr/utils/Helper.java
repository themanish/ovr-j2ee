package com.ovr.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Helper {

	public static Date stringToDate(String strDate) {
		DateFormat formatter;
		Date date = null;
		
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(strDate+": === "+date.toString());
		return date;
	}
	
	public static String getURISegment(int i, HttpServletRequest request){
		
		String fullPath = (String) request.getAttribute("javax.servlet.forward.request_uri");
		
		String[] uriSegments = fullPath.split("/");
		
		if(i >= uriSegments.length){
			return "";
		} else {
			return uriSegments[i];
		}
		
	}
	
	public static String printSessionMsg(HttpSession session){
		
		if(session.getAttribute("msg-success") != null){
			String msg = "<div class='alert-success'>"+session.getAttribute("msg-success")+"</div>";
			session.removeAttribute("msg-success");
			return msg;
		} else if (session.getAttribute("msg-error") != null){
			String msg = "<div class='alert-danger'>"+session.getAttribute("msg-error")+"</div>";
			session.removeAttribute("msg-error");
			return msg;
		} else {
			return "";
		}
		
	}
	
	public static void setSuccessMsg(String msg, HttpSession session){
		session.setAttribute("msg-success", msg);
	}
	
	public static void setErrorMsg(String msg, HttpSession session){
		session.setAttribute("msg-error", msg);
	}
	
	/*
	 * @userType (String)
	 * owner, admin, user
	 * 
	 */
	public static boolean isLoggedIn(String userType, HttpSession session) {
		if(userType.equals("admin"))
			return session.getAttribute("admin_id") != null ? true : false;
		else if (userType.equals("owner"))
			return session.getAttribute("owner_id") != null ? true : false;
		else if (userType.equals("user"))
			return session.getAttribute("user_id") != null ? true : false;
		else 
			return false;
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static void toJson(Object object, HttpServletResponse response){
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(object);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
