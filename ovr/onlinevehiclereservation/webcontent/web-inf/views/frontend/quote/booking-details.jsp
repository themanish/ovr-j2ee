<%@page import="com.ovr.utils.Helper"%>
<%@page import="com.ovr.model.Booking"%>
<%@  include file="../include/header.jsp"%>
<%@  include file="../include/navbar.jsp"%>

<% Booking booking = (Booking) session.getAttribute("booking"); %>

<div id="content">
<form action="<%= request.getContextPath() %>/quote/confirm" method="post" enctype="multipart/form-data" id="booking-details-form">
	<h2>Personal Details</h2>
	<table class="form-table">
		<tr>
			<td>
				<label>Full Name:</label>
				<input type="text" name="fullname" value="<%= booking.getUser().getFullname() %>" required>
			</td>
			<td>
				<label>Contact No:</label>
				<input type="text" name="contact_no" value="<%= booking.getUser().getContactNo() %>" required>
			</td>
			<td>
				<label>Address:</label>
				<input type="text" name="address" value="<%= booking.getUser().getAddress() %>" required>
			</td>
		</tr>
		<tr><td colspan=3><h3>Upload Documents</h3></td></tr>
		<tr>
			<td>
				<label>Profile Image:</label>
				<% if(booking.getUser().getProfileImage() != null){ %>
					<span class="text-success">Already Uploaded</span>
				<% } else { %>
				<input type="file" name="profile_image" required>
				<% } %>
			</td>
			<td>
				<label>Citizenship/Passport:</label>
				<% if(booking.getUser().getCitizenship() != null){ %>
					<span class="text-success">Already Uploaded</span>
				<% } else { %>
					<input type="file" name="citizenship" required>
				<% } %>
				
			</td>
			<td>
				<label>Driving License:</label>
				<% if(booking.getUser().getDrivingLicense() != null){ %>
					<span class="text-success">Already Uploaded</span>
				<% } else { %>
					<input type="file" name="driving_license" required>
				<% } %>
				
			</td>
		</tr>
	</table>
	
	<h2>Booking Details</h2>

	<table class="form-table">
		<tr>
			<td>
				<label>Journey From:</label>
				<input type="text" value="<%= booking.getStartLocation() %>" disabled>
			</td>
			<td>
				<label>Journey To:</label>
				<input type="text" value="<%= booking.getEndLocation() %>" disabled>
			</td>
		</tr>
		<tr>
			<td>
				<label>From Date:</label>
				<input type="text" name="from_date" value="<%= booking.getStartDate() %>" disabled>
			</td>
			<td>
				<label>To Date:</label>
				<input type="text" name="to_date" value="" class="datepicker" required>
			</td>
		</tr>
		<tr><td colspan=2><h3>Vehicle Details</h3></td></tr>
		<tr>
			<td>
				<img src="<%= request.getContextPath() %>/owner/vehicle/image?vehicle_id=<%= booking.getVehicle().getId() %>" class="booking-details-car-image">
			</td>
			<td>
				<table class="fancy-table">
					<tr>
						<th class="odd">Vehicle Model</th>
						<td class="odd"><%= booking.getVehicle().getModel() %></td>
					</tr>
					<tr>
						<th class="even">Manufacturer</th>
						<td class="even"><%= booking.getVehicle().getManufacturer() %></td>
					</tr>
					<tr>
						<th class="odd">Vehicle Owner</th>
						<td class="odd"><%= booking.getVehicle().getOwner().getFname()+" "+booking.getVehicle().getOwner().getLname() %></td>
					</tr>
					<tr>
						<th class="even">Daily Fare</th>
						<td class="even">Rs.<%= (int) booking.getVehicle().getDailyFare() %></td>
					</tr>
					<tr>
						<th class="odd">Booked Days</th>
						<td class="odd">
							<input type="hidden" name="days">
							<span id="days">-</span>
						</td>
					</tr>
					<tr>
						<th class="even">Total Fare</th>
						<td class="even">
							<input type="hidden" name="total_fare">
							<span id="total_fare">-</span>
						</td>
					</tr>
					<tr>
						<th class="odd">Additional Message</th>
						<td class="odd"><textarea name="additional_message"></textarea></td>
					</tr>
					<tr>
						<th class="even">Pay By</th>
						<td class="even">
							<!-- <input type="radio" name="payment_method" value="paypal"> Paypal  -->
							<input type="radio" name="payment_method" value="cash" checked> Cash
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td><button class="btn" style="width:100%">Confirm Booking</button></td>
					</tr>
					
				</table>
			</td>
		</tr>
	</table>
</form>
</div>

<script>
$(function(){
	
	$('#booking-details-form').validate();
	
	var maxBookingDate = new Date("<%= booking.getVehicle().getToDate() %>");
	maxBookingDate.setDate(maxBookingDate.getDate()-1);
	
	$('.datepicker').datepicker({
		minDate: new Date($('input[name="from_date"]').val()),
		maxDate: maxBookingDate,
		dateFormat: 'yy-mm-dd',
		onSelect: function (selectedDate){
			var fromDate = new Date($('input[name="from_date"]').val());
			var toDate = new Date($('input[name="to_date"]').val());
			var dateDiff = (((toDate - fromDate)/(1000*60*60))/24)+1;
			$('#days').html(dateDiff+" days");
			$('input[name="days"]').val(dateDiff);
			
			var totalFare = dateDiff*<%= booking.getVehicle().getDailyFare() %>;
			$('#total_fare').html("Rs."+totalFare);
			$('input[name="total_fare"]').val(totalFare);
			
		}
	});
});
</script>

<%@ include file="../include/footer.jsp"%>