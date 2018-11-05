<%@page import="com.ovr.model.Booking"%>
<%@page import="java.util.List"%>
<%@page import="com.ovr.utils.ImageUtil"%>
<%@ include file="include/header.jsp" %>


<div id="main-content">
	<div class="main-content-header">
		<h1 class="left"><i class="fa fa-edit"></i> Vehicle Reviews</h1>
		<div class="clear"></div>
	</div>
	
	<div class="main-content-body">
	
	<table class="fancy-table">
		<%
		
		List<Booking> bList = (List<Booking>) request.getAttribute("bookingList");
		if(!bList.isEmpty()){
			for(Booking b : bList){
				if(b.getReview().isEmpty()) continue;
		%>
		<tr>
			<td>
				<div class="review"><%= b.getReview() %></div>
				<div class="review-by">
					<h2><%= b.getUser().getFullname() %></h2>
					<span><%= b.getReviewedDate() %></span>
				</div>
			</td>
		</tr>
		<%
			}
		} else {
		%>
		<tr><td>No reviews yet.</td></tr>
		<%
		} 
		%>
	</table>
	
	</div>
</div>

<%@ include file="include/footer.jsp" %>