<!doctype html>
<html lang="en">

<head>
	<meta charset="utf-8" />
    <title>Owner Dashboard | OVR</title>
    
    <script src="<%= request.getContextPath() %>/assets/js/jquery.min.js"></script>
	    
    <!-- JQuery UI -->
    <link href="<%= request.getContextPath() %>/assets/css/jquery-ui.min.css" rel="stylesheet">
    <script src="<%= request.getContextPath() %>/assets/js/jquery-ui.min.js" type="text/javascript"></script>
    
    <!-- STYLESHEETS -->
    <link href="<%= request.getContextPath() %>/assets-owner/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%= request.getContextPath() %>/assets/css/forms.css" rel="stylesheet" type="text/css" />
    
    <!-- JQuery Validate -->
    <script src="<%= request.getContextPath() %>/assets/js/jquery.validate.js" type="text/javascript"></script>
    
    <!-- Font Awesome -->
	<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/font-awesome-4.3.0/css/font-awesome.min.css" />
    
</head>

<body>

<!-- HEADER SECTION -->
<div id="left-bar">
	<div id="logo">
		<h1>OVR <span>Owner</span></h1>
		<p>Dashboard</p>
    </div>
    <div class="logged-in-as">
    	Hi, <%= session.getAttribute("owner_name") %>
    </div>
    <div id="owner-navbar">
    	<ul>
    		<li><a href="<%= request.getContextPath() %>/owner/profile"><i class="fa fa-user"></i> Profile</a></li>
    		<li><a href="<%= request.getContextPath() %>/owner/vehicles"><i class="fa fa-car"></i> Vehicles</a></li>
    		<li><a href="<%= request.getContextPath() %>/owner/bookings"><i class="fa fa-shopping-cart"></i> Bookings</a></li>
    		<li><a href="<%= request.getContextPath() %>/owner/logout"><i class="fa fa-unlock"></i> Logout</a></li>
    	</ul>
    </div>
    <div class="clear"></div>
</div>

<div id="content">
	