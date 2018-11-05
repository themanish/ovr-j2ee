<%@page import="com.ovr.utils.ImageUtil"%>
<%@ include file="include/header.jsp" %>


<div id="main-content">
	<div class="main-content-header">
		<h1 class="left"><i class="fa fa-user"></i> User Details</h1>
		<div class="clear"></div>
	</div>
	
	<div class="main-content-body">
	
	<table class="fancy-table">
		<tr>
			<th><label>Full Name: </label></th>
			<td>${user.fullname}</td>
		</tr>
		<tr>
			<th><label>Contact No.: </label></th>
			<td>${user.contactNo}</td>
		</tr>
		<tr>
			<th><label>Email: </label></th>
			<td>${user.email }
		</tr>
		<tr>
			<th><label>Address: </label></th>
			<td>${user.address }
		</tr>
		<tr>
			<th><label>Profile Image: </label></th>
			<td>
				<img src="<%= request.getContextPath() %>/user/image?type=profile-image&user_id=${user_id}" width="200">	
			</td>
		</tr>
		<tr>
			<th><label>Citizenship/Passport ScannedCopy: </label></th>
			<td>
				<img src="<%= request.getContextPath() %>/user/image?type=citizenship&user_id=${user_id}" width="200">	
			</td>
		</tr>
		<tr>
			<th><label>Driving License ScannedCopy: </label></th>
			<td>
				<img src="<%= request.getContextPath() %>/user/image?type=driving-license&user_id=${user_id}" width="200">	
			</td>
		</tr>
		
		
	</table>
	
	</div>
</div>

<%@ include file="include/footer.jsp" %>