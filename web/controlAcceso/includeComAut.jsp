<%
	//Comprueba si se está autorizado a ver la página
	boolean autorizado = false;
	es.jahernandez.datos.ConUsuVO cVO = null;
	if(session.getAttribute("usuario")==null)
	{
		response.sendRedirect("./errorPages/errorNoAutor.jsp");
	}
	else
	{
		cVO = (es.jahernandez.datos.ConUsuVO) session.getAttribute("usuario");
		if(cVO.getNivelAcceso() >= 0)
		{
			autorizado= true;
		}
		if(! autorizado)
		{
			response.sendRedirect("./errorPages/errorNoAutor.jsp");
		}	
	}

%>