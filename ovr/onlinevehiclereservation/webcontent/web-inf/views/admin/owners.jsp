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
					<div class="page-header">Vehicle Owners List</div>
					<div class="content-box">
						<table width="100%" cellspacing=0 cellpadding=0 border=0>
							<tr>
								<th width=3%>SN#</th>
								<th>Name</th>
								<th>Phone</th>
								<th>Email</th>
								<th>Status</th>
								<th>Options</th>
							</tr>
							<%
								Owner[] ownerList = (Owner[]) request.getAttribute("ownerList");

								if (ownerList.length > 0) {
									int count = 1;
									for (Owner owner : ownerList) {
							%>
							<tr>
								<td><%= count++ %></td>
								<td><%= owner.getFname()+" "+owner.getLname() %></td>
								<td><%= owner.getPhone() %></td>
								<td><%= owner.getEmail() %></td>
								<td>
								<% if(!owner.isStatus()){ %>
									<a href="<%= request.getContextPath() %>/admin/owners/change-status?status=true&owner_id=<%= owner.getId() %>" class="text-danger">De-Activated</a>
								<% } else { %>
									<a href="<%= request.getContextPath() %>/admin/owners/change-status?status=false&owner_id=<%= owner.getId() %>" class="text-success">Activated</a>
								<% } %>
								
								</td>
								<td><a href="">View Complete Details</a></td>
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