<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Página de entrada</title>
		<style type="text/css">
			@import url("css/disenno.css");
	
			body 
			{
				text-align: center;
				margin-top: 150px;
			}
			#frmLogin table 
			{
			text-align: center;
			}
		</style>
	</head>

	<body>
		<script src="./js/validaGlobal.js"></script>

		<script type="text/javascript">

			function valForm()
			{
				
			  var todoCorrecto  = true;
			  
			  if(cadenaVacia(Trim(document.frmLogin.txtUser.value)))
			  {
				alert('El campo usuario no puede quedar vacío');
				document.frmLogin.txtUser.focus();
				todoCorrecto = false;
				return todoCorrecto;
			  }
			  
			  if(cadenaVacia(Trim(document.frmLogin.txtPass.value)))
			  {
				alert('El campo password no puede quedar vacío');
				document.frmLogin.txtPass.focus();
				todoCorrecto = false;
				return todoCorrecto;
			  }
			  
			  document.frmLogin.submit();
		}  
		</script>

		<form action="./ControlAccesoServlet" method="post" name="frmLogin" target="_self" id="frmLogin">
			<table width="629" border="0" align="center">
				<tr>
    				<th colspan="4" class="thDef"> Introduzca su Usuario y Password</th>
  				</tr>
  				<tr>
    				<td width="220" rowspan="15"><img src="./imagenes/login_lat1.jpg" width="233" height="320" alt="loginImg1" /></td>
				    <td height="20" colspan="2">&nbsp;</td>
				    <td width="251" rowspan="15"><img src="./imagenes/login_lat2.jpg" width="233" height="320" alt="logImg2" /></td>
  				</tr>
	 			<tr>
 					<td colspan="2">&nbsp;</td>
  				</tr>
				<tr>
  					<td colspan="2">&nbsp;</td>
  				</tr>
				<tr>
  					<td colspan="2">&nbsp;</td>
  				</tr>
				<tr>
  					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td width="120" class="tdDef">Usuario</td>
					<td width="8">
						<input name="txtUser" type="text" id="txtUser" tabindex="1" size="15" maxlength="8" />
					</td>
				</tr>
				<tr>
  					<td class="tdDef">Password</td>
  					<td><input name="txtPass" type="password" id="txtPass" tabindex="2" size="15" maxlength="8" /></td>
  				</tr>
				<tr>
				  	<td colspan="2">&nbsp;</td>
			  	</tr>
				<tr>
				  	<td colspan="2">&nbsp;</td>
			  	</tr>
				<tr>
				  	<td colspan="2">&nbsp;</td>
		 	 	</tr>
				<tr>
				  	<td colspan="2">&nbsp;</td>
			  	</tr>
				<tr>
				  <td colspan="2">&nbsp;</td>
				  </tr>
				<tr>
				  <td colspan="2">&nbsp;</td>
				  </tr>
				<tr>
  					<td colspan="2" class="cellBtnSub">
    					<input type="button" name="btnAcept" id="btnAcept" value="Aceptar" onclick="valForm()"  tabindex="3" />
   					</td>
				</tr>
				<tr>
  					<td height="21" colspan="2">&nbsp;</td>
  				</tr>
			</table>
		</form>
	</body>
</html>