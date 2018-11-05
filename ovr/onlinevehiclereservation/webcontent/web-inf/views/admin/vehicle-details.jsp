<%@page import="com.ovr.model.District"%>
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

				<%=Helper.printSessionMsg(session)%>

				<div class="contentainer">
					<div class="page-header">Vehicle Complete Details</div>
					<div class="content-box">
						<%	Vehicle v = (Vehicle) request.getAttribute("vehicle");	%>
						
						<table>			
						<tr>
							<th style="width:20%">Model</th>
							<td><%=v.getModel()%></td>
						</tr>
						<tr>
							<th>Image</th>
							<td><img src="<%=request.getContextPath()%>/owner/vehicle/image?vehicle_id=<%=v.getId()%>" width="300"></td>
						</tr>
						<tr>
							<th>Vehicle Owner</th>
							<td><%=v.getOwner().getFname() + " " + v.getOwner().getLname()%></td>
						</tr>
						<tr>
							<th>Manufacturer</th>
							<td><%=v.getManufacturer()%></td>
						</tr>
						<tr>
							<th>Available From Date</th>
							<td><%= v.getFromDate() %></td>
						</tr>
						<tr>
							<th>Available To Date</th>
							<td><%=v.getToDate()%></td>
						</tr>
						<tr>
							<th>Vehicle Current Location</th>
							<td><%=v.getDistrict().getName()%></td>
						</tr>
						<tr>
							<th>Vehicle available for given locations</th>
							<td>
								<%
								for (District d : v.getDistrictList()) {
									out.print(d.getName() + ", ");
								}
								%>
							</td>
						</tr>
						<tr>
							<th>Daily Fare</th>
							<td>Rs. <%=v.getDailyFare()%></td>
						</tr>
					
						</table>
					</div>
				</div>





			</div>
		</div>
		<div class="push"></div>
	</div>

	<%@ include file="include/footer.jsp"%>