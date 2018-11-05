<%@page import="java.util.ArrayList"%>
<%@page import="com.ovr.model.Vehicle"%>
<%@page import="java.util.List"%>
<%@  include file="include/header.jsp"%>
<%@  include file="include/navbar.jsp"%>
<script>
  $(function() {
    var districts = [
		'Achham','Arghakhanchi','Baglung','Baitadi','Bajhang','Bajura','Banke','Bara','Bardiya','Bhaktapur','Bhojpur','Chitwan','Dadeldhura','Dailekh','Dang Deukhuri','Darchula','Dhading','Dhankuta','Dhanusa','Dholkha','Dolpa','Doti','Gorkha','Gulmi','Humla','Ilam','Jajarkot','Jhapa','Jumla','Kailali','Kalikot','Kanchanpur','Kapilvastu','Kaski','Kathmandu','Kavrepalanchok','Khotang','Lalitpur','Lamjung','Mahottari','Makwanpur','Manang','Morang','Mugu','Mustang','Myagdi','Nawalparasi','Nuwakot','Okhaldhunga','Palpa','Panchthar','Parbat','Parsa','Pyuthan','Ramechhap','Rasuwa','Rautahat','Rolpa','Rukum','Rupandehi','Salyan','Sankhuwasabha','Saptari','Sarlahi','Sindhuli','Sindhupalchok','Siraha','Solukhumbu','Sunsari','Surkhet','Syangja','Tanahu','Taplejung','Terhathum','Udayapur'
    ];
   
    $( "#start, #end" ).autocomplete({
      source: districts
    });
  });
</script>

<!-- SLIDER SECTION -->
<div id="slider">
	
	<ul class="bxslider">
		<li><img src="<%= request.getContextPath() %>/assets/images/banner1.jpg" alt="Slider Image 1" /></li>
		<li><img src="<%= request.getContextPath() %>/assets/images/banner2.jpg" alt="Slider Image 1" /></li>
	</ul>
	<div class="search-box">
	<form action="<%= request.getContextPath() %>/quote" id="banner-quote-form">
		<input type="text" name="start" id="start" class="journey-input" placeholder="Journey From (District)" required>
		<input type="text" name="end" id="end" class="journey-input" placeholder="Journey To (District)" required>
		<input type="text" name="date" class="datepicker journey-input-date" placeholder="Date..." required>
		<button type="submit">Search Vehicle</button>
	</form>
	</div>
	
</div>

<div class="hr"></div>

<!-- QUICK NAVIGATION SECTION -->
<div id="quick-nav">
	
	<div id="featured-box">
		<h2>Featured Vehicles <span>Vehicles we think are the perfect match for you.</span></h2>
		<ul class="bxslider-featured-box">
			
			<%
			List<Vehicle> featuredVehicles = (ArrayList<Vehicle>) request.getAttribute("featuredVehicles");
				if(!featuredVehicles.isEmpty()){
					for(Vehicle v : featuredVehicles){
			%>
			<li>
				<div class="vehicle-image">
					<img src="<%= request.getContextPath() %>/owner/vehicle/image?vehicle_id=<%= v.getId() %>">
				</div>
				<div class="vehicle-desc">
					<h2><%= v.getModel() %></h2>
					<p><strong>Manufacturer:</strong> <%= v.getManufacturer() %></p><br>
					<p><strong>Vehicle Owner:</strong> <%= v.getOwner().getFname()+" "+v.getOwner().getLname() %></p><br>
					<p><strong>Available for: <span class="text-success">Rs.<%= (int) v.getDailyFare() %> / Day</span></strong></p>
					<p><strong>Dates Available: <%= v.getFromDate()+" to "+v.getToDate() %></strong></p><br>
					<button type="button" class="btn" onclick="quoteModal(<%= v.getId() %>, '<%= v.getModel() %>')">Book Today</button>
				</div>
			
			</li>
			<%
					}
				}
			%>
				
		</ul>
			
	</div>
	
	<div id="vehicle-owner-login">
		<h2>Login / Register <span>Register your vehicle today</span></h2>
		
		<div id="login-form">
			<form action="<%= request.getContextPath() %>/owner/login" method="post" id="owner-login-form">
			<table>
				<tr>
					<td>
						<label>Email</label><br>
						<input type="email" name="email" required>
						<br><br>
					</td>
				</tr>
				<tr>
					<td>
						<label>Password</label><br>
						<input type="password" name="password" required>
					</td>
				</tr>
				<tr>
					<td class="buttons">
						<button type="submit">Login</button>
						<a href="<%= request.getContextPath() %>/owner/register" class="btn">Register Now</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	
	<div class="clear"></div>
		
</div>

<div class="modal">
<form action="<%= request.getContextPath() %>/quote" id="featured-vehicle-quote-form">
	<table>
		<tr>
			<td>
				<label>Journey From</label>
				<select name="start"></select>
			</td>
		</tr>
		<tr>
			<td>
				<label>Journey To</label>
				<select name="end"></select>
			</td>
		</tr>
		<tr>
			<td>
				<label>Date</label>
				<input type="text" name="date" class="datepicker" required>
			</td>
		</tr>
		<tr>
			<td><button type="submit" class="btn">Search for date</button></td>
		</tr>
	</table>
</form>
</div>



<script>
$(function(){
	$('.modal').dialog({
		autoOpen: false
	});
	
	$('#banner-quote-form').validate();
	$('#owner-login-form').validate();
	$('#featured-vehicle-quote-form').validate();
	
	$('.datepicker').datepicker({minDate: 0, dateFormat: 'yy-mm-dd'});
});

function quoteModal(vehicleId, vehicleModal){
	
	$('.modal').attr('title', 'Quote Vehicle - '+vehicleModal);
	
	$.post(
	"<%= request.getContextPath() %>/ajax_getVehicleCurrentLocation",
	{'vehicle_id': vehicleId},
	function(response1){
		
		var option1 = "<option>"+response1+"</option>";
		$('.modal select[name="start"]').html(option1);
		
		$.post(
				"<%= request.getContextPath() %>/ajax_getVehicleAvailableLocations",
				{'vehicle_id': vehicleId},
				function (response2){
					var locations = response2.split(',');
					var option2 = "";
					for(var i=0; i<locations.length; i++){
						option2 += "<option>"+locations[i]+"</option>";
					}
					$('.modal select[name="end"]').html(option2);
				}
		);
		
	}
	);
	
	$('.modal').dialog({
		'title': "Quote Vehicle - "+vehicleModal
	});
	
	$('.modal').dialog('open');
}
</script>

<%@ include file="include/footer.jsp"%>