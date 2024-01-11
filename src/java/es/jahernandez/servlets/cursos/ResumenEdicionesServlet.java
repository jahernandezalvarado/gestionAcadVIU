/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

//Paquetes de manejo de pdf
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.accesodatos.*;
import es.jahernandez.datos.*;
import java.awt.Color;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.servlet.ServletContext;
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
public class ResumenEdicionesServlet extends HttpServlet 
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
        ServletContext    sc             = null;
        HttpSession       sesion         = request.getSession();
        
        Vector            listaEdiciones = new Vector();
        CursosVO          curVO          = null;
        
        String strNomCur    = "";
        String strTipCur    = "";
        String strFecIni    = "";
        String strFecFin    = "";
        String strNumHor    = "";
        String strNumAlu    = "";
        String strPreCur    = "";
        String strPreMes    = "";

        String muestraCond  = "Listados de cursos a celebrar a partir de hoy";

        int    cuentaLineas = 0;  //Cuenta lineas impresas para saber cuando hacer el salto de página

        PdfPTable tablaDatos = new PdfPTable(1);
        PdfPCell celda       = new PdfPCell();
        Font     fuenteDoc   = null; 
        Image    logoImage   = null;
        
        tablaDatos.setWidthPercentage(100);
        tablaDatos.setSpacingBefore(10);
       
        celda.setMinimumHeight(20);

        listaEdiciones = EdicionesDAO.devolverResNueEdi();
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir resumen ediciones" );
               
        }
        
        
        // step 1
        Document document = new Document(PageSize.A4,16,16,16,16);
        
        try
        {
            sc = getServletContext();
            
            fuenteDoc = new Font(BaseFont.createFont(sc.getRealPath(File.separator + "fonts" + File.separator + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            fuenteDoc.setSize(8);
            logoImage = Image.getInstance(sc.getRealPath(File.separator + "imagenes" + File.separator + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);
            
            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            
            // step 3
            document.open();

            // step 4
            
            //Mostramos cabecera
            logoImage.setAlignment(Image.ALIGN_LEFT);
            
            document.add(new Paragraph(""));
            document.add(logoImage);
            document.add(new Paragraph("FECHA " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())) + " RESUMEN CURSOS " + muestraCond ));

            document.add(new Paragraph(""));
            document.add(new Paragraph(""));

            celda.addElement(new Paragraph("       CURSO                                TIPO                INICIO     FIN        HOR  ALUM  P. MAT    P. REC     ", fuenteDoc));
            //document.Add(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO     ", new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
            //document.Add(new Paragraph("------------------------------------------------------------------------------------------------------", new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

            tablaDatos.addCell(celda);

            for (int ind = 0; ind < listaEdiciones.size(); ind++)
            {             
                curVO = CursosDAO.devolverDatosCurso( ((EdicionesVO)listaEdiciones.elementAt(ind)).getIdCur() );
                
                strNomCur = curVO.getNomCur() .trim();
                strTipCur = TipoCursoDAO.devuelveNombreTipo(curVO.getTipCur());
                strFecIni = new SimpleDateFormat("dd/MM/yyyy").format(((EdicionesVO)listaEdiciones.elementAt(ind)).getFecIn());
                strFecFin = new SimpleDateFormat("dd/MM/yyyy").format(((EdicionesVO)listaEdiciones.elementAt(ind)).getFecFi());
                strNumHor = String.valueOf(((EdicionesVO)listaEdiciones.elementAt(ind)).getNumHor());  
                strNumAlu = String.valueOf(AluEdiDAO.devNumAluEdi(((EdicionesVO)listaEdiciones.elementAt(ind)).getIdEdi()));
                strPreCur = String.valueOf(((EdicionesVO)listaEdiciones.elementAt(ind)).getPrecioM());   
                strPreMes = String.valueOf(((EdicionesVO)listaEdiciones.elementAt(ind)).getPrecioR());

                if (cuentaLineas > 32)
                {
                    cuentaLineas = 0;

                    //Hacemos salto de página
                    document.add(tablaDatos);
                    document.newPage();

                    //Reiniciamos la tabla
                    tablaDatos = new PdfPTable(1);
                    tablaDatos.setWidthPercentage(100);
                    tablaDatos.setSpacingBefore(10);

                    //Mostramos cabecera
                    document.add(logoImage);
                    document.add(new Paragraph("FECHA " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())) + " RESUMEN CURSOS " + muestraCond));

                    document.add(new Paragraph(""));
                    document.add(new Paragraph(""));
                    celda = new PdfPCell();
                    celda.setMinimumHeight(20);

                    celda.addElement(new Paragraph("       CURSO                                TIPO                INICIO     FIN        HOR  ALUM  P. MAT    P. REC     ", fuenteDoc));
                    //document.Add(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO     " , new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
                    //document.Add(new Paragraph("------------------------------------------------------------------------------------------------------", new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
                    tablaDatos.addCell(celda);
                }


               
                strNomCur = strNomCur + "                                  ";
                    
                strTipCur = strTipCur + "                                  ";

                strFecIni = strFecIni + "                                  ";
                  
                strFecFin = strFecFin + "                                  ";
                
                strNumHor = strNumHor + "                                  ";

                strNumAlu = strNumAlu + "                                  ";

                strPreCur = strPreCur + "                                      ";

                strPreMes = strPreMes + "                                      ";

                strNomCur = strNomCur.substring(0, 35);
                strTipCur = strTipCur.substring(0, 20);
                strFecIni = strFecIni.substring(0, 10);
                strFecFin = strFecFin.substring(0, 10);
                strNumHor = strNumHor.substring(0, 4);
                strNumAlu = strNumAlu.substring(0, 3);
                strPreCur = strPreCur.substring(0,5);
                strPreMes = strPreMes.substring(0,5);
                
                
                celda = new PdfPCell();
                celda.setMinimumHeight(20);

                celda.addElement(new Paragraph("  " + strNomCur + "    " + strTipCur + " " + strFecIni+ " " + strFecFin + "   " + strNumHor + "  " + strNumAlu + "  "  + strPreCur + "     " + strPreMes,fuenteDoc));
                //document.Add(new Paragraph("  " + strNombre + "    " + strApe1 + " " + strPob+ " " + strMov + "   " + strFij,new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

                tablaDatos.addCell(celda);
                
                
                cuentaLineas++;
            }
            if (listaEdiciones.size() > 0 && listaEdiciones.size() <= 21)
            {
                document.add(tablaDatos);
            }
        }
        catch (Exception ex)
        {
           
        }
        // step 5: Close document
        document.close();
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
        return "Resumen ediciones servlet";
    }// </editor-fold>
}
