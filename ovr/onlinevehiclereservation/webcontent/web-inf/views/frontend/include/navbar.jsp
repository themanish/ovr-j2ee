<%@page import="com.ovr.utils.Helper"%>
<div id="nav-bar">
<div class="wrapper">
   	<ul>
   		<li><a href="<%= request.getContextPath() %>" class="">Get a Free Quote</a></li>
       	<li>
       		<a href="<%= request.getContextPath() %>/user/login" class="">User Login/Register</a>
       		<% if(Helper.isLoggedIn("user", session)){ %>
       		<ul>
       			<li><small>Hello,</small><br> <strong><%= session.getAttribute("user_email") %></strong></li>
       			<li><a href="<%= request.getContextPath() %>/user">Profile</a></li>
       			<li><a href="<%= request.getContextPath() %>/user/booking-history">Booking History</a></li>
       			<li><a href="<%= request.getContextPath() %>/user/logout">Logout</a></li>
       		</ul>
       		<% } %>
       	</li>
       	
   </ul>
</div>
</div>
