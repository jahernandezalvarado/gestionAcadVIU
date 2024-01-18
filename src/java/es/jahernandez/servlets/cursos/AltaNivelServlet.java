/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import es.jahernandez.accesodatos.*;
import es.jahernandez.datos.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author Alberto
 */
public class AltaNivelServlet extends HttpServlet 
{

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
        
        HttpSession   sesion       = request.getSession();
        NivelesVO     nivVO        = new NivelesVO();
        int           resultadoAlt = 0;
        String        pagGesNiv    = "";
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta nivel" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtCodCurso") != null)
        {
            nivVO.setCodCur(request.getParameter("txtCodCurso").trim());
        }
       
        if(request.getParameter("txtNuevoNombre") != null)
        {
            nivVO.setNomNiv(request.getParameter("txtNuevoNombre").trim());
        }

        if(request.getParameter("txtNuevoCont") != null)
        {
            nivVO.setContenidos(request.getParameter("txtNuevoCont").trim());
        }      
        
        if(request.getParameter("valInfNiv") != null)
        {
            pagGesNiv =  request.getParameter("valInfNiv");
        } 
        
        
        
        resultadoAlt = NivelesDAO.guardarNivel(nivVO);
        
        if(resultadoAlt <= 0)
        {
            
            //Redireccionar a gestión niveles
            response.sendRedirect("cursos/gestionNiveles.jsp?codCurso="  + nivVO.getCodCur() 
                                                         + "&errorCode=" + resultadoAlt);
        }
        else
        {            
            //Redireccionar a gestión niveles
            response.sendRedirect("cursos/gestionNiveles.jsp?codCurso="  + nivVO.getCodCur()
                                                         + "&valInfNiv=" + pagGesNiv);
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
        return "Alta Nivel servlet";
    }// </editor-fold>
}
