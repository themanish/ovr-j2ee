<%
String uriSegment = Helper.getURISegment(3, request);
%>
<%@page import="com.ovr.utils.Helper"%>
<ul id="nav">
  <li><a href="<%= request.getContextPath() %>/admin" class="<%= uriSegment.equals("") || uriSegment.equals("change-password") ? "current" : "menulink" %>"><i class="fa fa-dashboard"></i> Dashboard</a></li>
  <!-- <li><a href="#" class="menulink"><i class="fa fa-book"></i> CMS</a></li>  -->
  <li><a href="<%= request.getContextPath() %>/admin/owners" class="<%= uriSegment.equals("owners") ? "current" : "menulink" %>"><i class="fa fa-users"></i> Vehicle Owner Management</a></li>  
</ul>
<div class="clear"></div>
<%  if (uriSegment.equals("") || uriSegment.equals("change-password")) { %>
<div id="nav-wrapper">
  <ul id="subnav">
    <li><a href="<%= request.getContextPath() %>" target="_blank">Preview Site</a></li>
    <li><a href="<%= request.getContextPath() %>/admin/change-password">Change Password</a></li>
    <li><a href="<%= request.getContextPath() %>/admin/logout">Log Out</a></li>
  </ul>
  <div class="clear"></div>
</div>
<% } else if (uriSegment.equals("owners")) { %>
<div id="nav-wrapper">
  <ul id="subnav">
    <li><a href="<%= request.getContextPath() %>/admin/owners">Owners List</a></li>
    <li><a href="<%= request.getContextPath() %>/admin/owners/feature-vehicles">Feature Vehicle Requests</a></li>
  </ul>
  <div class="clear"></div>
</div>
<% } %>