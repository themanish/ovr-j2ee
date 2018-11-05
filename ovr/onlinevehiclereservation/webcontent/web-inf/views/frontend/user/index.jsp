<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>

<div id="content">

<h2>User Profile</h2>

<%= Helper.printSessionMsg(session) %>

<div class="main-content-body">
	<form action="<%= request.getContextPath() %>/user/edit-profile" method="post" enctype="multipart/form-data">
	<input type="hidden" name="ownerId" value="${owner.id}">
	<table class="fancy-table">
		<tr>
			<th><label>Full Name: </label></th>
			<td><input type="text" name="fullname" value="${user.fullname}"></td>
		</tr>
		<tr>
			<th><label>Contact No.: </label></th>
			<td><input type="text" name="contact_no" value="${user.contactNo}"></td>
		</tr>
		<tr>
			<th><label>Email: </label></th>
			<td><input type="email" name="email" value="${user.email}" disabled></td>
		</tr>
		<tr>
			<th><label>Address: </label></th>
			<td><input type="text" name="address" value="${user.address}"></td>
		</tr>
		<tr>
			<th><label>Profile Image: </label></th>
			<td>
				<input type="file" name="profile_image">
				<img src="<%= request.getContextPath() %>/user/image?type=profile-image&user_id=${user_id}" width="200">	
			</td>
		</tr>
		<tr>
			<th><label>Citizenship/Passport ScannedCopy: </label></th>
			<td>
				<input type="file" name="citizenship">
				<img src="<%= request.getContextPath() %>/user/image?type=citizenship&user_id=${user_id}" width="200">	
			</td>
		</tr>
		<tr>
			<th><label>Driving License ScannedCopy: </label></th>
			<td>
				<input type="file" name="driving_license">
				<img src="<%= request.getContextPath() %>/user/image?type=driving-license&user_id=${user_id}" width="200">	
			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td><button class="btn" type="submit">Save Changes</button></td>
		</tr>
		
		
	</table>
	</form>
	</div>


</div>


<%@ include file="../include/footer.jsp"%>