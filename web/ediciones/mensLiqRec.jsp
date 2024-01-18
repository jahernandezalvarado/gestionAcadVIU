<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@page import="es.jahernandez.datos.EdicionesVO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mensaje Matricula</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
    int     codErr    = -99;
	String  mensaje   = "";   
    
    if(request.getParameter("errorCode") != null)
    {
       codErr = new Integer(request.getParameter("errorCode")).intValue();
    }
	

	if(codErr > 0)
	{
		mensaje = "El recibo se marcó como pagado correctamente";
	}
	else
	{
		mensaje = "Se produjo un error liquidando recibo";
	}
            
    
%>


<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios">
<table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdDef"><%=mensaje%></td>
  </tr>
  <tr>
    <td class="cellBtnSub">&nbsp;</td>
  </tr>
</table>

</body>
</html>