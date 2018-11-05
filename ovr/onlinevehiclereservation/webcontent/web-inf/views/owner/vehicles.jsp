<%@page import="com.ovr.utils.Helper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@ include file="include/header.jsp"%>

<div id="main-content">

	<div class="main-content-header">
		<h1 class="left"><i class="fa fa-car"></i> Vehicles</h1>
		<a href="<%= request.getContextPath() %>/owner/vehicles/add-edit" class="btn pull-right">Add New</a>
		<div class="clear"></div>
	</div>
	
	<%= Helper.printSessionMsg(session) %>

	<div class="main-content-body">
		<form action="<%=request.getContextPath()%>/owner/vehicles/add-edit"
			method="post" enctype="multipart/form-data">
			<table class="data-table" cellspacing=0>
				<tr>
					<th width=3%>SN</th>
					<th width=15%>Photo</th>
					<th width=13%>Manufacturer</th>
					<th width=10%>Model</th>
					<th width=17%>Feature Status</th>
					<th width=15%>Date From/To</th>
					<th width=33%>Options</th>
				</tr>
				<%
					List<Vehicle> vehicleList = (ArrayList<Vehicle>) request.getAttribute("vehicleList");
					if(!vehicleList.isEmpty()){
						int count = 1;
						for(Vehicle vehicle: vehicleList){
				%>
				<tr>
					<td><%=count++%></td>
					<td><img
						src="<%=request.getContextPath()%>/owner/vehicle/image?vehicle_id=<%=vehicle.getId()%>"
						width=100></td>
					<td><%=vehicle.getManufacturer()%></td>
					<td><%=vehicle.getModel()%></td>
					<td>
						<%
							if (vehicle.getFeatureStatus() == "") {
						%> <a
						href="<%=request.getContextPath()%>/owner/vehicles/request-feature?status=requested&vehicle_id=<%=vehicle.getId()%>"
						class="text-primary">Request to Feature</a> <%
							 	} else if (vehicle.getFeatureStatus().equals("requested")) {
							 %> <span class="text-warning">Requested</span> <%
							 	} else if(vehicle.getFeatureStatus().equals("rejected")) {
							 %> <a
						href="<%=request.getContextPath()%>/owner/vehicles/request-feature?status=requested&vehicle_id=<%=vehicle.getId()%>"
						class="text-danger">Rejected</a> <%
							 	} else {
							 %> <span class="text-success">Featured</span> <%
							 	}
							 %>
					</td>
					<td><%=vehicle.getFromDate() + " <br> "
						+ vehicle.getToDate()%></td>
					<td><a
						href="<%=request.getContextPath()%>/owner/vehicles/reviews?vehicle_id=<%=vehicle.getId()%>">View
							Reviews</a> &nbsp;|&nbsp; <a
						href="<%=request.getContextPath()%>/owner/vehicles/add-edit?id=<%=vehicle.getId()%>">Edit</a>
						&nbsp;|&nbsp; <a
						href="<%=request.getContextPath()%>/owner/vehicles/delete?id=<%=vehicle.getId()%>"
						onclick="return confirm('Confirm Delete?')">Delete</a></td>
				</tr>
				<%
					} // endfor;
					} else {
				%>
				<tr><td colspan=7 align="center"><span class="text-danger">No vehicles added yet.</span></td></tr>
				<%
					}
				%>
			</table>
		</form>
	</div>
</div>

<%@ include file="include/footer.jsp"%>