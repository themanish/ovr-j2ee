<%@page import="com.ovr.utils.Helper"%>
<%@ include file="../frontend/include/header.jsp" %>

<script>
  $(function() {
    var districts = [
		'Achham','Arghakhanchi','Baglung','Baitadi','Bajhang','Bajura','Banke','Bara','Bardiya','Bhaktapur','Bhojpur','Chitwan','Dadeldhura','Dailekh','Dang Deukhuri','Darchula','Dhading','Dhankuta','Dhanusa','Dholkha','Dolpa','Doti','Gorkha','Gulmi','Humla','Ilam','Jajarkot','Jhapa','Jumla','Kailali','Kalikot','Kanchanpur','Kapilvastu','Kaski','Kathmandu','Kavrepalanchok','Khotang','Lalitpur','Lamjung','Mahottari','Makwanpur','Manang','Morang','Mugu','Mustang','Myagdi','Nawalparasi','Nuwakot','Okhaldhunga','Palpa','Panchthar','Parbat','Parsa','Pyuthan','Ramechhap','Rasuwa','Rautahat','Rolpa','Rukum','Rupandehi','Salyan','Sankhuwasabha','Saptari','Sarlahi','Sindhuli','Sindhupalchok','Siraha','Solukhumbu','Sunsari','Surkhet','Syangja','Tanahu','Taplejung','Terhathum','Udayapur'
    ];
    $( "#district" ).autocomplete({
      source: districts
    });
  });
</script>

<div id="content">
	<h2>Vehicle Owner Registration</h2>
	
	<%= Helper.printSessionMsg(session) %>
	
	<form action="<%= request.getContextPath() %>/owner/register" method="post" enctype="multipart/form-data" id="owner-register-form">
	
		<table class="form-table">
		
			<tr>
				<th>Name:</th>
				<td>
				<table>
					<tr>
						<td><input type="text" name="fname" placeholder="First Name" required></td>
						<td><input type="text" name="lname" placeholder="Last Name" required></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<th>Phone:</th>
				<td>
					<input type="text" name="phone" class="digit" placeholder="Phone Number" required>
				</td>
			</tr>
			<tr>
				<th>Email:</th>
				<td>
					<input type="email" name="email" class="email" placeholder="Email Address" required>
				</td>
			</tr>
			<tr>
				<th>Password:</th>
				<td>
					<input type="password" name="password" placeholder="********" required>
				</td>
			</tr>
			<tr>
				<th>Profile Image:</th>
				<td>
					<input type="file" name="profile_image" required>
				</td>
			</tr>
			
			<tr>
				<th>&nbsp;</th>
				<td>
					<button type="submit" class="btn">Register</button>
				</td>
			</tr>
		
		</table>
	
	</form>
	
</div>

<script>
$(function(){
	$('#owner-register-form').validate();
});
</script>

<%@ include file="../frontend/include/footer.jsp" %>

