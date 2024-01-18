<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Resultado Búsqueda Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
//Cargamos datos de módulo y módulos-tema
EdicionesVO ediVO     = new EdicionesVO();
Vector      lisAluMat = new Vector();

//Variables de paginación
int         valInf    = 0;
int         valSup    = 0;
int         valAnt    = 0;

String      nombCur   = "";
String      fechas    = "";

String     cssFI      = "";

if(request.getParameter("codEdi") != null )
{
    ediVO = EdicionesDAO.devolverDatosEdi(request.getParameter("codEdi"));
}

if(request.getParameter("ind") != null)
{
    valInf = new Integer(request.getParameter("ind")).intValue();
}
else
{
    valInf = 0;
}

nombCur = CursosDAO.devolverDatosCurso(ediVO.getIdCur()).getNomCur();
fechas =  new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn())+ " a " +  new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi());

lisAluMat = AluEdiDAO.devAluMatEdi(ediVO.getIdEdi());

valSup = valInf + 10 ;
valAnt = valInf - 10 ;

if(valSup > lisAluMat.size()) valSup = lisAluMat.size();


%>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />


</head>
<!--Funciones jquery ui-->
<script>
	$(function() {
        $( "#mailing" ).dialog({
           autoOpen: false,
            height: 500,
            width: 1100,
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
	document.getElementById("fraMailing").src = "../mailing/mailing.jsp?codEdi=<%=ediVO.getIdEdi()%>&tipoMail=6";
	$('#mailing' ).dialog( 'open' );
	
}
</script>

<body class="fondoFormularios">
<div id="mailing" title="Mailing">
    <iframe name="fraMailing"     id="fraMailing"     frameborder="0" src="" width="100%" height="700" scrolling="no"> </iframe>
</div>

<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
               <td height="69" style="width: 374px"><img  onmouseover="this.src='../imagenes/mailGr.png'"     onmouseout="this.src='../imagenes/mailPe.png'"     src="../imagenes/mailPe.png"     width="64" height="64" onclick="cargaMailing();"/>
               <img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" width="64" height="64" onclick="window.open('./impLisAlu.jsp?codEdi=<%=ediVO.getIdEdi()%>','_self','');"/></td>
               <td style="text-align: right; margin-left: 80px">&nbsp;</td>
               <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table width="100%" class="tablaCursos"> 
            <tr>
              <td width="7%" class="tdDef"><strong>Curso</strong></td>
              <td width="27%"><%=nombCur%></td>
              <td width="6%"><span class="tdDef"><strong>Fechas</strong></span></td>
              <td width="60%"><%=fechas%></td>
  </tr>
</table>
<table width="100%" class="tablaCursos"> 
            <tr>
                <td width="168"><strong>DNI</strong></td>
                <td width="328"><strong>
                Apellidos</strong></td>
                <td width="917"><strong>
                Nombre</strong></td>
  </tr>
            <%for (int ind = valInf; ind < valSup; ind++)
                  {
                    AlumnosVO aluVO = new AlumnosVO();
                    aluVO = AlumnosDAO.devolverDatosAlumno( ((AluEdiVO) lisAluMat.elementAt(ind)).getIdAlu() );
                    
                     cssFI = ind%2 == 0 ? "tablaCursos" : "colorFondoFilaImparListado";
                %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>">
                <td height="21" class="tablaCursos"><%=aluVO.getNumDocAlu()%></td>
                <td class="tablaCursos"><%=aluVO.getAp1Alu()%></td>
                <td class="tablaCursos"><%=aluVO.getNombre()%></td>
            </tr>
<%} %>
</table>
<table width="100%" border="0">
<tr>
        <td width="55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./verAlumEdi.jsp?codEdi=<%=ediVO.getIdEdi()%>&ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" width="50" height="50" border="0" /></a><%}%></td>
        <td width="45%" class="cellBtnSub" scope="col"><%if(valSup < lisAluMat.size()){%><a href="./verAlumEdi.jsp?codEdi=<%=ediVO.getIdEdi()%>&ind=<%=valAnt%>"><img src="../imagenes/btnsig.png" alt="---&gt;" width="50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
</body>
</html>