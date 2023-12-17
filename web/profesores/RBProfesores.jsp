<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Resultado Búsqueda Interesados</title>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>


<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
    //Se cargan los datos del servlet y de paginación
    Vector       profBusq = new Vector();
  
    int          valInf   = 0;
    int          valSup   = 0;
    int          valAnt   = 0;

    ProfesoresVO profVO   = null;
    
    String       cssFI    = "";

    profBusq = (Vector) session.getAttribute("busProf");
  
    if(profBusq == null) profBusq = new Vector();
 
    if(request.getParameter("ind") != null)
    {
        valInf = new Integer(request.getParameter("ind")).intValue();
    }
    else
    {
        valInf = 0;
    }

    valSup = valInf + 15 ;
    valAnt = valInf - 15 ;

    if(valSup > profBusq.size()) valSup = profBusq.size();
    //if(valAnt < 0) valAnt = 0;


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

<!--Funciones jquery ui-->
<script>
	$(function() {
        $( "#mailing" ).dialog({
           autoOpen: false,
            height: 800,
            width: 1200,
            modal: true,
            buttons: {
                "Mailing": function() {
                   fraMailing.validaForm();
                },
                "Cerrar": function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
</script>

<script>
function cargaMailing()
{
	document.getElementById("fraMailing").src = "../mailing/mailing.jsp?tipoMail=4";
	$('#mailing' ).dialog( 'open' );
	
}
</script>


</head>

<body class="fondoFormularios">
<div id="mailing" title="Mailing">
    <iframe name="fraMailing"     id="fraMailing"     frameborder="0" src="" width="100%" height="700" scrolling="no"> </iframe>
</div>
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
                <th colspan="3" style="text-align: center; font-size: 14pt;">
                    Lista Profesores</th>
            </tr>
             <tr>
                <td height="69" style="width: 374px">
                    <img  onmouseover="this.src='../imagenes/anadeusrGr.png'" onmouseout="this.src='../imagenes/anadeusrPe.png'" src="../imagenes/anadeusrPe.png" width="64" height="64" onclick="window.open('./altaProfesor.jsp','_self','');"/>
                    <img  onmouseover="this.src='../imagenes/mailGr.png'"     onmouseout="this.src='../imagenes/mailPe.png'"     src="../imagenes/mailPe.png"     width="64" height="64" onclick="cargaMailing();" />
                    <img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" width="64" height="64" onclick="window.open('../ImpLisProfServlet','_listaAlu','');"/></td>
                <td style="text-align: right; margin-left: 80px">&nbsp;</td>
                <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table width="100%" class="tablaListadoExtensa"> 
            <tr>
                <td width="280"><span class="tablaListadoExtensaCabecera">
                  Nombre</span></td>
                <td width="169"><span class="tablaListadoExtensaCabecera">
                  Móvil</span></td>
                <td width="177"><span class="tablaListadoExtensaCabecera">
                  Teléfono</span></td>
                <td width="408"><span class="tablaListadoExtensaCabecera">&Aacute;reas.</span></td>
                <td width="371"><span class="tablaListadoExtensaCabecera">
                  Cursos</span></td>
  </tr>
      <%for (int ind = valInf; ind < valSup; ind++)
              {
                  profVO = (ProfesoresVO) profBusq.elementAt(ind);
                  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";

            %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>" onmouseover="this.className='filaresaltada';"  onmouseout="this.className='<%=cssFI%>';" onclick="location.href='./datosProfesor.jsp?codProf=<%=profVO.getIdProf()%>&ind=<%=valInf%>'">
                <td height="21"><%=profVO.getApellidos()%> , <%=profVO.getNombre()%></td>
                <td><%=profVO.getMov()%></td>
                <td><%=profVO.getTelef()%></td>
                <td>Por definir</td>
                <td></td>
            </tr>
<%} %>
</table>
<table width="100%" border="0">
<tr>
        <td width="27%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./RBProfesores.jsp?ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" width="50" height="50" border="0" /></a><%}%></td>
        <td width="46%" class="cellBtnSub" scope="col"><%=profBusq.size()%> &nbsp;registros</td>
        <td width="27%" class="cellBtnSub" scope="col"><%if(valSup < profBusq.size()){%><a href="./RBProfesores.jsp?ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" width="50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>