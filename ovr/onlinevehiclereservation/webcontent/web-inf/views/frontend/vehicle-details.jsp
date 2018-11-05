<%@page import="com.ovr.model.Booking"%>
<%@page import="java.util.List"%>
<%@page import="com.ovr.model.District"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@ include file="include/header.jsp"%>
<%@ include file="include/navbar.jsp"%>

<div id="content">
	<%@ include file="include/quote-sidebar.jsp"%>

	<div id="search-results">
	
		<%
		Vehicle v = (Vehicle) request.getAttribute("vehicle");
		%>
	
		<h2><%= v.getModel() %> <a href="<%= request.getContextPath() %>/quote/book?vehicle_id=<%= v.getId() %>" class="btn right"><i class="fa fa-shopping-cart"></i> Book this car</a></h2>
		

		<img
			src="<%=request.getContextPath()%>/owner/vehicle/image?vehicle_id=<%= v.getId() %>">
		<br>
		<br>
		<strong>Vehicle Owner: </strong>
		<p><a href="#"><%= v.getOwner().getFname()+" "+v.getOwner().getLname() %></a></p>

		<strong>Manufacturer:</strong>
		<p><%= v.getManufacturer() %></p>
		
		<strong>Available From Date:</strong>
		<p><%= v.getFromDate() %></p>
		
		<strong>Available To Date:</strong>
		<p><%= v.getToDate() %></p>
		
		<strong>Vehicle currently at:</strong>
		<p><%= v.getDistrict().getName() %></p>
		
		<strong>Vehicle available for given locations:</strong>
		<p><% for(District d : v.getDistrictList()){ out.print(d.getName()+", "); } %></p>
			
		<br>
		<h3>
			<strong>Daily Fare: </strong> Rs.<%= v.getDailyFare() %>
		</h3>
		<br>
		<h2 id="vehicle-reviews">Vehicle Reviews</h2>
		<table class="fancy-table full-width">
		<%
		List<Booking> bList = (List<Booking>) request.getAttribute("bookingList");
		if(!bList.isEmpty()){
			for(Booking b : bList){
				if(b.getReview().isEmpty()) continue;
		%>
		<tr>
			<td>
				<div class="review"><%= b.getReview() %></div>
				<div class="review-by" style="float: right">
					<h3><%= b.getUser().getFullname() %></h3>
					<span><%= b.getReviewedDate() %></span>
				</div>
			</td>
		</tr>
		<%
			}
		} else {
		%>
			<tr><td>No reviews yet.</td></tr>
		<%
		} 
		%>
		</table>



	</div>
	<div class="clear"></div>
</div>

<%@ include file="include/footer.jsp"%>