<%@page import="com.ovr.utils.Helper"%>
<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>


<div id="content">

	<h2 align="center">User Login/Register</h2>
	
	<form class="user-login-form" action="<%= request.getContextPath() %>/user/authenticate" method="post" id="user-login-form">
	
		<%= Helper.printSessionMsg(session) %>
		
		<input type="hidden" name="action">
		
		<label>Email</label>
		<input type="email" name="email" required>
		
		<label>Password</label>
		<input type="password" name="password" required>
		
		<button class="btn" type="submit" id="btn-login" onclick="setLoginClicked(event)">LOGIN</button>
		<button class="btn" type="submit" id="btn-register" onclick="checkIfEmailExists(event)">EASY REGISTER</button>
		
		
		
	</form>
	<div class="clear"></div>
	<% if(session.getAttribute("login-request") == null){ %>
	<div class="center-button" style="width: 215px; margin:25px auto; padding-left: 35px;">
	<h3 style="margin-left: 84px;">OR</h3><br>
	<a href="<%= request.getContextPath() %>/owner/login" style="text-decoration: underline;">Login / Register as Vehicle Owner</a>
	<% } %>
	</div>

</div>

<script>

function setLoginClicked(event){
	event.preventDefault();
	$('input[name="action"]').val("login");
	
	if($('#user-login-form').validate()){
		$('form').submit();
	}
}

function setRegisterClicked(){
	$('input[name="action"]').val("register");

	if($('#user-login-form').validate()){
		$('form').submit();
	}
}

function checkIfEmailExists(event){
	
	event.preventDefault();
	
	var email = $('input[name="email"]').val();
	$.post(
		"<%= request.getContextPath() %>/user/ajax_checkIfEmailExists",
		{'email': email},
		function(response){
			if(response == "exists"){
				alert("Email already exists. Please try to login or register with new email account.");
			} else {
				setRegisterClicked();
			}
		}
	);
	
}


</script>

<%@ include file="../include/footer.jsp" %>