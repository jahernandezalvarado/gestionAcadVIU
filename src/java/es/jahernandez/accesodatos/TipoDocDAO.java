/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.TipoDocVO;

import es.jahernandez.tablas.TablaTipoDocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

/**
 *
 * @author Alberto
 */
public class TipoDocDAO
{

    //Método que devuelve los datos a mostrar en los combos de Tipo de Documento
    public static Vector devolverDatosTipDoc()
    {
        Connection        con           = null;
        PreparedStatement ps            = null;
        ResultSet         rs            = null;

        //String            sql           = "SELECT ID, NOMTDO FROM TBTIPDOC ORDER BY ID";
        String            sql           = "SELECT "    + TablaTipoDocumento.CODTIPDOC + " , " 
                                                       + TablaTipoDocumento.NOMBRE    +   
                                          " FROM "     + TablaTipoDocumento.TABLA     +
                                          " ORDER BY " + TablaTipoDocumento.CODTIPDOC;
        
        Vector            listaTipDoc   = new Vector();
        TipoDocVO         tipDocVO      = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                tipDocVO = new TipoDocVO();

                tipDocVO.setIdTipDoc  (rs.getInt   (TablaTipoDocumento.CODTIPDOC));
                tipDocVO.setNombTipDoc(rs.getString(TablaTipoDocumento.NOMBRE));

                listaTipDoc.addElement(tipDocVO);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaTipDoc;
        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Devuelve el tipo de Dni de un documento
    public static String devuelveNombreTipoDoc(int idDoc)
    {
        Connection        con       = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        //String            sql       = "SELECT NOMTDO FROM TBTIPDOC WHERE ID = ?";
        String            sql       = "SELECT " + TablaTipoDocumento.NOMBRE    +   
                                      " FROM "  + TablaTipoDocumento.TABLA     +
                                      " WHERE " + TablaTipoDocumento.CODTIPDOC + " = ?";
        
        String            nombreDoc = "";


        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setInt(1, idDoc);

            rs = ps.executeQuery();

            if(rs.next())
            {
                nombreDoc = rs.getString(TablaTipoDocumento.NOMBRE);
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return nombreDoc;

        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                ps.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(TipoDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }    
}
