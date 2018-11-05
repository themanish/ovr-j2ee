<%@page import="com.ovr.model.Booking"%>
<%@page import="java.util.List"%>
<%@page import="com.ovr.utils.ImageUtil"%>
<%@ include file="include/header.jsp" %>


<div id="main-content">
	<div class="main-content-header">
		<h1 class="left"><i class="fa fa-shopping-cart"></i> Bookings History</h1>
		<div class="clear"></div>
	</div>
	
	<div class="main-content-body">
		<table class="fancy-data-table full-width" cellspacing=0>
	
		<tr class="header">
			<th width=5%>SN#</th>
			<th width=10%>Car Image</th>
			<th>Car Model</th>
			<th>Journey From/To</th>
			<th>Booked For Date</th>
			<th>Booked Days</th>
			<th>Amount Paid</th>
			<th>Options</th>
		</tr>
		
		<%
		List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
		if(!bookingList.isEmpty()){
			int count = 1;
			for(Booking b : bookingList){
		%>
		
			<tr>
				<td><%= count++ %></td>
				<td><img src="<%= request.getContextPath() %>/owner/vehicle/image?vehicle_id=<%= b.getVehicle().getId() %>" width=100></td>
				<td><%= b.getVehicle().getModel() %></td>
				<td><%= b.getStartLocation()+" / "+b.getEndLocation() %></td>
				<td><%= b.getStartDate() %></td>
				<td><%= b.getDays() %> day(s)</td>
				<td>Rs.<%= b.getTotalFare() %></td>
				<td><a href="<%= request.getContextPath() %>/owner/bookings/user-details?user_id=<%= b.getUser().getId() %>" target="_blank" class="btn"><i class="fa fa-user"></i> User Details</a></td>
			</tr>
		
		<%
			}
		}
		%>
	
	</table>
	</div>
</div>

<%@ include file="include/footer.jsp" %>