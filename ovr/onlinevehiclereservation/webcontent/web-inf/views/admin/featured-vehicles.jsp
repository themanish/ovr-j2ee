<%@page import="java.util.ArrayList"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@ include file="include/header.jsp"%>
<%@ page import="com.ovr.model.Owner"%>
<body>
	<div id="main_wrapper">
		<div class="wrapper">
			<%@ include file="include/top-sec.jsp"%>
			<%@ include file="include/nav.jsp"%>
		</div>
		<div class="wrapper">
			<div id="maincontent">
			
				<%= Helper.printSessionMsg(session) %>

				<div class="contentainer">
					<div class="page-header">Feature Vehicle Requests</div>
					<div class="content-box">
						<table width="100%" cellspacing=0 cellpadding=0 border=0>
							<tr>
								<th width=3%>SN#</th>
								<th>Vehicle Model</th>
								<th>Manufacturer</th>
								<th>Owner</th>
								<th>Details</th>
								<th>Status</th>
								<th>Options</th>
							</tr>
							<%
								ArrayList<Vehicle> vehicleList = (ArrayList<Vehicle>) request.getAttribute("vehicleList");

								if (!vehicleList.isEmpty()) {
									int count = 1;
									for (Vehicle vehicle : vehicleList) {
							%>
							<tr>
								<td><%= count++ %></td>
								<td><%= vehicle.getModel() %></td>
								<td><%= vehicle.getManufacturer() %></td>
								<td><%= vehicle.getOwner().getFname() +" "+vehicle.getOwner().getLname() %></td>
								<td><a href="<%= request.getContextPath() %>/admin/owners/vehicle-details?id=<%= vehicle.getId() %>" target="_blank">View Complete Details</a></td>
								<td>
									<span class="<%= vehicle.getFeatureStatus().equals("requested") ? "text-danger" : "text-success" %>">
										<%= vehicle.getFeatureStatus() %>
									</span>
								</td>
								<td>
									<a href="<%= request.getContextPath() %>/admin/owners/feature-vehicles?status=featured&vehicle_id=<%= vehicle.getId() %>" class="text-success">Accept</a>
									<a href="<%= request.getContextPath() %>/admin/owners/feature-vehicles?status=rejected&vehicle_id=<%= vehicle.getId() %>" class="text-danger">Reject</a>
								</td>
							</tr>

							<%
								}
								} else {
							%>

							<%
								}
							%>
						</table>
					</div>
				</div>





			</div>
		</div>
		<div class="push"></div>
	</div>

	<%@ include file="include/footer.jsp"%>