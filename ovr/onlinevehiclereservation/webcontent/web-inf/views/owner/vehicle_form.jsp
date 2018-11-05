<%@page import="com.ovr.model.District"%>
<%@page import="com.ovr.dao.CommonDao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@ include file="include/header.jsp" %>

<%
List<District> districtList = new ArrayList<District>();
CommonDao commonDao = new CommonDao();
districtList = commonDao.getAllDistrict();

boolean edit = false;
Vehicle vehicle = (Vehicle) request.getAttribute("vehicle");

if(vehicle != null){
	edit = true;
}

%>

<div id="main-content">
	<div class="main-content-header">
		<h1><%= !edit ? "Add New" : "Edit" %> Vehicle</h1>
	</div>
	<form action="<%= request.getContextPath() %>/owner/vehicles/add-edit" method="post" enctype="multipart/form-data">
	
	<% if(request.getParameterMap().containsKey("id")){ %>
	<input type="hidden" name="vehicle_id" value="<%= request.getParameter("id") %>">
	<% } %>
	
	<table class="form-table">
		<tr>
			<th><label>Manufacturer: </label></th>
			<td><input type="text" name="manufacturer" value="<%= edit ? vehicle.getManufacturer() : "" %>"></td>
		</tr>
		<tr>
			<th><label>Model: </label></th>
			<td><input type="text" name="model" value="<%= edit ? vehicle.getModel() : "" %>"></td>
		</tr>
		<tr>
			<th><label>Daily Fare: </label></th>
			<td><input type="text" name="daily_fare" value="<%= edit ? vehicle.getDailyFare() : "" %>"></td>
		</tr>
		<tr>
			<th><label>From Date: </label></th>
			<td><input type="text" name="from_date" class="datepicker" value="<%= edit ? vehicle.getFromDate() : "" %>"></td>
		</tr>
		<tr>
			<th><label>To Date: </label></th>
			<td><input type="text" name="to_date" class="datepicker" value="<%= edit ? vehicle.getToDate() : "" %>"></td>
		</tr>
		<tr>
			<th><label>Vehicle Current Location: </label></th>
			<td>
				<select name="vehicle_current_location_id">
				<% for(District district : districtList){ %>
					<option value="<%= district.getId() %>" <%= edit ? (district.getId() == vehicle.getVehicleCurrentLocationId() ? "selected" : "") : "" %>><%= district.getName() %></option>
				<% } %>					
				</select>
			</td>
		</tr>
		
		<tr>
			<th><label>Vehicle Available For: </label></th>
			<td>
				<select name="end_location_ids" style="min-height: 300px" multiple>
				<% for(District district : districtList){ %>
					<option value="<%= district.getId() %>" <%= edit ? (vehicle.getVehicleEndLocationIds().contains(district.getId()) ? "selected" : "") : "" %>><%= district.getName() %></option>
				<% } %>					
				</select>
			</td>
		</tr>
		
		<tr>
			<th><label>Description: </label></th>
			<td>
				<textarea name="description"><%= edit ? vehicle.getDescription() : "" %></textarea>
			</td>
		</tr>
		<tr>
			<th>Vehicle Photos</th>
			<th>
				<input type="file" name="image" width=200 <%= !edit ? "required" : "" %>>
				<% if(edit){ %>
				<img src="<%= request.getContextPath() %>/owner/vehicle/image?vehicle_id=<%= vehicle.getId() %>" width=200>
				<% } %>
			</th>
			
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td><button type="submit" class="btn">Save</button></td>
		</tr>	
	</table>
	</form>
</div>

<script>
$(function(){
	$('.datepicker').datepicker({minDate: 0, dateFormat: 'yy-mm-dd'});	
});
</script>

<%@ include file="include/footer.jsp" %>