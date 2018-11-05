<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="com.ovr.utils.Helper"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>

<div id="content">

	<h2>Search Vehicles</h2>

	<%@ include file="../include/quote-sidebar.jsp" %>
	
	<div id="search-results">
	
	<%
	ArrayList<Vehicle> vehicleList = null;
	vehicleList = (ArrayList<Vehicle>) request.getAttribute("vehicleList");
	
	if(!vehicleList.isEmpty()){
		for(Vehicle v : vehicleList){
		%>
		
			<div class="result-item">
				<img src="<%= request.getContextPath() %>/owner/vehicle/image?vehicle_id=<%= v.getId() %>">
				<div class="result-item-details">
					<h2><%= v.getModel() %></h2>
					<h4>Vehicle Owner: <a href="#"><%= v.getOwner().getFname()+" "+v.getOwner().getLname() %></a></h4>
					<p><strong>Manufacturer: </strong><%= v.getManufacturer() %></p>
					<p><strong>Avaiable for: </strong><%= Helper.getDateDiff(Helper.stringToDate(v.getFromDate()), Helper.stringToDate(v.getToDate()), TimeUnit.DAYS) %> Day(s)</p>
					<h3><strong>Daily Fare: </strong> Rs.<%= v.getDailyFare() %></h3>
					
					<div class="buttons">
						<a href="<%= request.getContextPath() %>/vehicle-details?id=<%= v.getId() %>#vehicle-reviews" class="btn"><i class="fa fa-comments"></i> View Reviews</a>
						<a href="<%= request.getContextPath() %>/quote/book?vehicle_id=<%= v.getId() %>" class="btn"><i class="fa fa-shopping-cart"></i> Book this car</a>
						<a href="<%= request.getContextPath() %>/vehicle-details?id=<%= v.getId() %>" class="btn"><i class="fa fa-automobile"></i> Complete Details</a>
					</div>
					
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>	
		<%
		} // end for;
	} else {
	%>
			<div class="result-item">
				<h2>No vehicles found for Journey:</h2>
				<strong>From: </strong>${quote.start}<br>
				<strong>To: </strong>${quote.end}<br>
				<strong>Date: </strong>${quote.date}
				<br><br>
				<!-- <p><a href="<%= request.getContextPath() %>/contact">Please send us a query</a></p> -->
			</div>
	<%
	} // endif;
	%>
	</div>
	<div class="clear"></div>

</div>
<script>
$(function(){
	$('.datepicker').datepicker({minDate: 0, dateFormat: 'yy-mm-dd'});
});
</script>

<%@  include file="../include/footer.jsp"%>