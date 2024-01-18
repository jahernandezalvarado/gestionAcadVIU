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
    //Se cargan los datos del servlet y de paginación
    Vector           listaModEdi = new Vector();
  
    int              valInf      = 0;
    int              valSup      = 0;
    int              valAnt      = 0;

    EdiModProfAulaVO empaVO      = null;
    
    String           cssFI       = "";
    String           codEdi      = "";
    int              errCode     = 99;

    if(request.getParameter("codEdi") != null)
    {
        codEdi =  request.getParameter("codEdi");
        listaModEdi = EdiModProfAulaDAO.devolverModEdi(codEdi);
    }
    
    if(request.getParameter("errorCode") != null)
    {
        errCode = (new Integer(request.getParameter("errorCode")).intValue());
    }
    
    
    
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

    if(valSup > listaModEdi.size()) valSup = listaModEdi.size();
    //if(valAnt < 0) valAnt = 0;


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />


</head>

<body class="fondoFormularios">
<%if(errCode <= 0){%>
<script>
    alert("Se producjo un error al dar de alta el módulo")
</script>    
<%}%>



<script>
function bajaMod(codEdi, codMod, ind)
{
	window.open("../BorrarModEdiServlet?codEdi=" + codEdi 
	                                + "&codMod=" + codMod 
									+ "&ind="    + ind ,
				"_self","");
}

</script>

      
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
               <td height="66" style="width: 374px">
                   <img  onmouseover="this.src='../imagenes/anaGr.png'" onmouseout="this.src='../imagenes/anaPe.png'" src="../imagenes/anaPe.png" width="64" height="64" onclick="window.open('./anaModEdi.jsp?codEdi=<%=codEdi%>&ind=<%=valInf%>','_self','');"/></td>
               <td style="text-align: right; margin-left: 80px">&nbsp;</td>
               <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table width="100%" class="tablaListadoExtensa"> 
            <tr>
                <td width="170"><span class="tablaListadoExtensaCabecera">
                  M&oacute;dulo</span></td>
                <td width="227"><span class="tablaListadoExtensaCabecera">
                  Profesor</span></td>
                <td width="233"><span class="tablaListadoExtensaCabecera">
                  Aula</span></td>
                <td width="168"><span class="tablaListadoExtensaCabecera">&Aacute;rea</span></td>
                <td width="151"><span class="tablaListadoExtensaCabecera"> Fecha Inicio</span></td>
                <td width="142"><span class="tablaListadoExtensaCabecera"> Fecha Fin</span></td>
                <td width="80"><span class="tablaListadoExtensaCabecera"> Hora Inicio</span></td>
                <td width="62"><span class="tablaListadoExtensaCabecera">Hora Fin</span></td>
                <td width="87"><span class="tablaListadoExtensaCabecera">Días Semana</span></td>
                <td width="65">&nbsp;</td>
  </tr>
      <%for (int ind = valInf; ind < valSup; ind++)
              {
                  empaVO = (EdiModProfAulaVO) listaModEdi.elementAt(ind);
                  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";

            %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>">
                <td height="21"><%=ModulosDAO.devolverDatosModulo(empaVO.getIdMod()).getNombre()%></td>
                <td><%=ProfesoresDAO.devolverDatosProfesor(empaVO.getIdProf()).getNombre() + " " + ProfesoresDAO.devolverDatosProfesor(empaVO.getIdProf()).getApellidos()%></td>
                <td><%if(! empaVO.getIdAul().trim().equals("")){%> <%=AulasDAO.devolverDatosAula(empaVO.getIdAul()).getNombre()%> <%}%></td>
                <td><%=AreasDAO.devuelveNombreArea(ModulosDAO.devolverDatosModulo(empaVO.getIdMod()).getCodArea())%></td>
                <td><%=new SimpleDateFormat("dd/MM/yyyy").format(empaVO.getFecIni())%></td>
                <td><%=new SimpleDateFormat("dd/MM/yyyy").format(empaVO.getFecFin())%></td>
                <td><%=empaVO.getHorIni()%></td>
                <td><%=empaVO.getHorFin()%></td>
                <td><%=empaVO.devuelveDiasClase()%></td>
                <td><img src="../imagenes/papelera.png" width="30" height="30" onclick="bajaMod('<%=codEdi%>','<%=empaVO.getIdMod()%>' ,'<%=valInf%>');" style="cursor:pointer"/></td>
            </tr>
<%} %>
</table>
<table width="100%" border="0">
<tr>
        <td width="55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./verModEd.jsp?codEdi=<%=codEdi%>&ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" width="50" height="50" border="0" /></a><%}%></td>
        <td width="45%" class="cellBtnSub" scope="col"><%if(valSup < listaModEdi.size()){%><a href="./verModEd.jsp?codEdi=<%=codEdi%>&ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" width="50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>