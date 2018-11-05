<%@ include file="include/header.jsp" %>
<body>
<div id="main_wrapper">
  <div class="wrapper">
  	<%@ include file="include/top-sec.jsp" %>
    <%@ include file="include/nav.jsp" %>
  </div>
  <div class="wrapper">
    <div id="maincontent">
    
    	<%= Helper.printSessionMsg(session) %>
    
      <div class="contentainer">
        <div class="page-header">Change Admin Password</div>
        <div class="content-box">
        <form action="<%= request.getContextPath() %>/admin/change-password" method="post">
      		<table class="fancy-table">
      		
      			<tr>
      				<th>New Password</th>
      				<td><input type="password" name="new_pass"></td>
      			</tr>
      			
      			<tr>
      				<th>Repeat New Password</th>
      				<td><input type="password" name="new_pass_r"></td>
      			</tr>
      			
      			<tr>
      				<th>&nbsp;</th>
      				<td><button type="submit">Change Password</button></td>
      			</tr>
      		
      		</table>  	
        </form>
        </div>
      </div>
      
    </div>
  </div>
  <div class="push"></div>
</div>

<%@ include file="include/footer.jsp" %>