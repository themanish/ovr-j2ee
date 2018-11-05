<%@page import="com.ovr.model.Booking"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>

<%

boolean edit = false;
Booking b = (Booking) request.getAttribute("booking");

%>
<div id="content">
	
	<form action="<%= request.getContextPath() %>/user/review-add-edit" method="post">
	
	
	<div class="main-content-header">
		<h2 class="left"><i class="fa fa-edit"></i> Write Vehicle Review</h2>
		<button type="submit"
			class="btn right">Save Review</button>
		<div class="clear"></div>
	</div>
	
	<input type="hidden" name="booking_id" value="<%= request.getParameter("booking_id") %>">
	
	<table class="fancy-table full-width">
	
		<tr>
			<td>
				<textarea rows="10" name="review"><%= b.getReview() %></textarea>
			</td>
		
		</tr>
	
	</table>
	</form>

</div>

<%@ include file="../include/footer.jsp"%>