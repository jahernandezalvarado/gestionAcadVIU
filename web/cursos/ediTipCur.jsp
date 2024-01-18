<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.TipoCursoVO"%>
<%@ page import = "es.jahernandez.accesodatos.TipoCursoDAO"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir tipo curso</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Control de errores de alta
int         errCodEdi  = 0;
TipoCursoVO tipCurVO   = new TipoCursoVO();

//Se comprueba si vienen datos de el servlet de alta de interesados para recargar datos
//y los códigos de error
if(request.getParameter("errorCode") != null)
{
    errCodEdi = new Integer(request.getParameter("errorCode")).intValue();
}

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos  = true;		
	
	validarCampos = sprytextfield1.validate() & spryselect1.validate();
			   
	if(validarCampos)
	{
		frmEdiTipCur.action = "../EditaTipoCursoServlet";
		frmEdiTipCur.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
	}
	
}

function cargarComboTipoCurso(idTipCur)
{   
	$.ajax({
	  url: "../CargaTipoCursoServlet?valSel=" + idTipCur,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstTipCurso").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de cursos");
	  }
	});
}

function cargaDatosTipCur()
{
	document.getElementById("txtNombre").value = document.getElementById("lstTipCurso").options[document.getElementById("lstTipCurso").selectedIndex].text;
}

</script>

</head>

<%if(errCodEdi == -1){%>
<script>
    alert("Se produjo un error en la edición del tipo de curso");
</script>
<%}%>
<%if(errCodEdi == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>


<body class="fondoFormularios" onload="cargarComboTipoCurso();">
<form id="frmEdiTipCur" name="frmEdiTipCur" method="post" action="">
<table width="90%" border="0">
  <tr>
    <td colspan="2" class="thDef">Editar Tipo de curso</td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Tipos Curso</td>
    <td class="tdDef"><span id="valTipCurso">
      <select name="lstTipCurso" id="lstTipCurso" onchange="cargaDatosTipCur()" style="width:250px">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
  </tr>
  <tr class="tdDef">
    <td width="8%" class="tdDef">Nombre</td>
    <td class="tdDef">
      <span id="txtValNombre">
        <input name="txtNombre" type="text" id="txtNombre" size="75" maxlength="100" />
        <span class="textfieldRequiredMsg">*</span></span>
    </td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input name="btnEdicion" type="button" class="cellBtnSub" id="btnEdicion" value="Editar" onclick="validaForm();" /></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre");
var spryselect1 = new Spry.Widget.ValidationSelect("valTipCurso", {invalidValue:"-1"});
</script>
</body>
</html>