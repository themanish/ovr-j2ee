<%@page import="com.ovr.model.Quote"%>
<%
Quote quote = (Quote) session.getAttribute("quote");
%>

<div id="quote-form-sidebar">
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
		<form action="<%= request.getContextPath() %>/quote">
		<table>
			<tr>
				<td>
					<label>Journey From:</label><br>
					<input type="text" id="start" name="start" value="<%= quote.getStart() %>">
				</td>
			</tr>
			<tr>
				<td>
					<label>Journey To:</label><br>
					<input type="text" id="end" name="end" value="<%= quote.getEnd() %>">
				</td>
			</tr>
			<tr>
				<td>
					<label>Date:</label><br>
					<input type="text" class="datepicker" name="date" value="<%= quote.getDate() %>">
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn"><i class="fa fa-search"></i> Search Again</button>
				</td>
			</tr>
			
		</table>
		</form>
	</div>