<%@page import="com.ovr.utils.Helper"%>
<%@ include file="../frontend/include/header.jsp" %>
<%@ include file="../frontend/include/navbar.jsp" %>

<div id="content">
	<h2 align=center>Vehicle Owner Login</h2>
	
	<form action="<%= request.getContextPath() %>/owner/login" method="post" id="login-form-owner">
		<%= Helper.printSessionMsg(session) %>
		<table class="form-table">
			<tr>
				<th>Email:</th>
				<td>
					<input type="email" name="email" placeholder="Email/Username" required>
				</td>
			</tr>
			<tr>
				<th>Password:</th>
				<td>
					<input type="password" name="password" placeholder="********" required>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<button type="submit" class="btn" style="width: 55%">Login</button>
					<a href="<%= request.getContextPath() %>/owner/register" class="btn" style="width: 60%">Register Now</a>
				</td>
			</tr>
		</table>
	
	</form>
	
</div>

<script>
$(function(){
	$('#login-form-owner').validate();
});
</script>

<%@ include file="../frontend/include/footer.jsp" %>

