/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.centros;

import es.jahernandez.accesodatos.CentrosDAO;
import es.jahernandez.datos.CentrosVO;
import es.jahernandez.datos.ConUsuVO;

import java.io.IOException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author JuanAlberto
 */
public class EditaCentroServlet extends HttpServlet {

     /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        HttpSession sesion  = request.getSession();
        
        CentrosVO   cenVO   = new CentrosVO();
        int         resEdi  = 0;

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Edicion centro" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("lstCentros") != null)
        {
            cenVO.setIdCentro(new Integer(request.getParameter("lstCentros").trim()).intValue());
        }
        
        if(request.getParameter("txtNombre") != null)
        {
            cenVO.setNombreCentro(request.getParameter("txtNombre").trim());
        }

        resEdi = CentrosDAO.editarCentro(cenVO);

        if(resEdi >= 0)
        {
            //Redireccionar a página de edición de centro
            response.sendRedirect("centros/ediCentro.jsp");
        }
        else
        {
            //Volvemos a edicion de centro
            response.sendRedirect("cursos/ediCentro.jsp?errorCode=" + resEdi);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Edita centro servlet";
    }// </editor-fold>
}
