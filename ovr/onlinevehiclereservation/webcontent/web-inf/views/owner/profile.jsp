<%@page import="com.ovr.utils.ImageUtil"%>
<%@ include file="include/header.jsp" %>


<div id="main-content">
	<div class="main-content-header">
		<h1 class="left"><i class="fa fa-user"></i> Profile</h1>
		<div class="clear"></div>
	</div>
	
	<div class="main-content-body">
	<form action="<%= request.getContextPath() %>/owner/profile" method="post" enctype="multipart/form-data" id="owner-profile-form">
	<input type="hidden" name="ownerId" value="${owner.id}">
	<table>
		<tr>
			<th><label>First Name: </label></th>
			<td><input type="text" name="fname" value="${owner.fname}" required></td>
		</tr>
		<tr>
			<th><label>Last Name: </label></th>
			<td><input type="text" name="lname" value="${owner.lname}" required></td>
		</tr>
		<tr>
			<th><label>Phone No.: </label></th>
			<td><input type="text" name="phone" value="${owner.phone}" required></td>
		</tr>
		<tr>
			<th><label>Email: </label></th>
			<td><input type="text" name="email" value="${owner.email}" disabled></td>
		</tr>
		<tr>
			<th><label>Profile Image: </label></th>
			<td>
				<input type="file" name="profile_image" required>
				<%-- <img src="<%= ImageUtil.getImagePath(request) %>${owner.profileImage}" width=200> --%>
				<img src="<%= request.getContextPath() %>/owner/profile-image" width="200">	
			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td><button type="submit">Update Profile</button></td>
		</tr>	
	</table>
	</form>
	</div>
</div>

<script>

</script>

<%@ include file="include/footer.jsp" %>