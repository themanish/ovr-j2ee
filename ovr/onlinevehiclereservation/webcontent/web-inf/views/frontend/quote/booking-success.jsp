<%@page import="com.ovr.utils.Helper"%>
<%@page import="com.ovr.model.Booking"%>
<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>

<div id="content">
	<h2>Thank you</h2>
	<%= Helper.printSessionMsg(session) %>
</div>

<%@ include file="../include/footer.jsp"%>