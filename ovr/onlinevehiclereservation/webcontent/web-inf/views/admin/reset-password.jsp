<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Reset Password</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    
    <link href="<%= request.getContextPath() %>/assets-admin/css/login.css" rel="stylesheet" type="text/css" media="all" />
    <script src="<%= request.getContextPath() %>/assets-admin/js/jquery.min.js" type="text/javascript"></script>
    
</head>
<body>
<div class="member-login">
    <form class="login"  method="post" name="form" id="form" action="">
        <div class="formtitle">Reset Password</div>
        <div class="input">
            <input type="text" name="email" placeholder="Enter admin email address" required autocomplete="off" />
            <span><img src="<%= request.getContextPath() %>/assets-admin/images/select.png"/> </span> </div>
        <div class="buttons">
            <input class="submit" type="submit" value="Reset Password" style="width: 31%"/>
            <div class="clear"> </div>
        </div>
    </form>
</div>
</body>
</html>