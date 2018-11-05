<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.ovr.utils.Helper"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EW-Admin Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

<link href="<%= request.getContextPath() %>/assets-admin/css/login.css" rel="stylesheet" type="text/css" media="all" />
<script src="<%= request.getContextPath() %>/assets-admin/js/jquery.min.js" type="text/javascript"></script>

</head>
<body>
<div class="member-login">
	
	
	
  <form class="login"  method="post" id="form" action="<%= request.getContextPath() %>/admin/login">
  	<h1 align="center">OVR</h1> 
  	<%= Helper.printSessionMsg(session) %>
    <div class="formtitle" align="center">Admin Login</div>
    <div class="input">
      <input type="text" name="username" placeholder="User Name" required autocomplete="off" />
       </div>
    <div class="input">
      <input type="password" name="password"  placeholder="Password" required/>
       </div>
    <div class="buttons">
      <input class="submit" type="submit" value="Login" />
      <div class="clear"> </div>
    </div>
  </form>
</div>
</body>
</html>