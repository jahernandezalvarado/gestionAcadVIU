/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.NivelForVO;

import es.jahernandez.tablas.TablaNivelFormativo;

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
public class NivelForDAO
{
     //Método que devuelve los datos a mostrar en los combos de nivel formativo
    public static Vector datComNivFor()
    {

        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT IdNivel, NomNivel FROM TbNivFor ORDER BY NomNivel";
        String            sql        = "SELECT "    + TablaNivelFormativo.CODNIVEL + " , " 
                                                    + TablaNivelFormativo.NOMBRE   + 
                                       " FROM  "    + TablaNivelFormativo.TABLA    +
                                       " ORDER BY " + TablaNivelFormativo.NOMBRE;
                
        NivelForVO        nivForVO   = null;

        Vector            listNivFor = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                nivForVO = new NivelForVO();
                nivForVO.setIdNIvel (rs.getInt   (TablaNivelFormativo.CODNIVEL));
                nivForVO.setNomNivel(rs.getString(TablaNivelFormativo.NOMBRE));

                listNivFor.addElement(nivForVO);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listNivFor;

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
                Logger.getLogger(NivelForDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve el nombre de un nivel formativo
    public static String nomNivFor(String idNivFor)
    {

        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT NomNivel FROM TbNivFor WHERE IdNivel = ? ";
        String            sql        = "SELECT " + TablaNivelFormativo.NOMBRE   + 
                                       " FROM "  + TablaNivelFormativo.TABLA    + 
                                       " WHERE " + TablaNivelFormativo.CODNIVEL + " = ? ";
        
        
        String            strNomNF   = "";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            
            //Se le pasan los parámetros a la consulta sql
            ps.setInt(1, new Integer(idNivFor).intValue());

            rs  = ps.executeQuery();


            if (rs.next())
            {
                strNomNF = rs.getString(TablaNivelFormativo.NOMBRE);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return strNomNF;

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
                Logger.getLogger(NivelForDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }


    }   
}
