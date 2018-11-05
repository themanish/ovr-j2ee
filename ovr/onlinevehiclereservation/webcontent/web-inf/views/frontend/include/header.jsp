<!doctype html>

<html lang="en">

<head>
	<meta charset="utf-8" />
    <title>Online Vehicle Reservation</title>
    
    <script src="<%= request.getContextPath() %>/assets/js/jquery.min.js"></script>
    
    <!-- JQuery UI -->
    <link href="<%= request.getContextPath() %>/assets/css/jquery-ui.min.css" rel="stylesheet">
    <script src="<%= request.getContextPath() %>/assets/js/jquery-ui.min.js" type="text/javascript"></script>
    
    <!-- BXSlider -->
    <link href="<%= request.getContextPath() %>/assets/bxslider/jquery.bxslider.css" rel="stylesheet">
    <script src="<%= request.getContextPath() %>/assets/bxslider/jquery.bxslider.min.js" type="text/javascript"></script>
    
    <!-- STYLESHEETS -->
    <link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%= request.getContextPath() %>/assets/css/forms.css" rel="stylesheet" type="text/css" />
    
    <!-- JQuery Validate -->
    <script src="<%= request.getContextPath() %>/assets/js/jquery.validate.js" type="text/javascript"></script>
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/font-awesome-4.3.0/css/font-awesome.min.css">
    
    <script>
    $(document).ready(function () {
        $('.bxslider').bxSlider({
            mode: 'fade',
            auto: true,
            pager: false
        });
        
        $('.bxslider-featured-box').bxSlider({auto:true});
    });
    </script>
    
</head>

<body>

<!-- HEADER SECTION -->

<div id="header">
	<div class="wrapper">
		<div id="logo">
			<h1 align="center"><i class="fa fa-automobile"></i> Online Vehicle Reservation</h1>
	    </div>
    </div>
</div>
<div class="clear"></div>
