<%@page import="com.ovr.model.Booking"%>
<%@page import="java.util.List"%>
<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>

<div id="content">

	<h2>Booking History</h2>
	<%= Helper.printSessionMsg(session) %>

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
				<td><a href="<%= request.getContextPath() %>/user/review-add-edit?booking_id=<%= b.getId() %>" class="btn"><i class="fa fa-edit"></i> Write a review</a></td>
			</tr>
		
		<%
			}
		}
		%>
	
	</table>

</div>

<%@ include file="../include/footer.jsp"%>