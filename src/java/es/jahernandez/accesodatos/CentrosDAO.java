/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.CentrosVO;

import es.jahernandez.tablas.TablaCentros;

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
public class CentrosDAO
{       
    //Método que devuelve los datos a mostrar en los combos de centros
    public static Vector datComCentros()
    {

        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT IdCen, NomCen FROM TbCen ORDER BY NomCen";
        String            sql        = "SELECT "    + TablaCentros.CODCENTRO + "," 
                                                    + TablaCentros.NOMBRE    +
                                       " FROM  "    + TablaCentros.TABLA     + 
                                       " ORDER BY " + TablaCentros.NOMBRE;
        CentrosVO         centroVO   = null;

        Vector            listCentros = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                centroVO = new CentrosVO();
                centroVO.setIdCentro    (rs.getInt   (TablaCentros.CODCENTRO));
                centroVO.setNombreCentro(rs.getString(TablaCentros.NOMBRE));

                listCentros.addElement(centroVO);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listCentros;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que devuelve el nombre de un centro
    public static String nomCentro(String idCen)
    {

        Connection        con     = null;
        PreparedStatement ps      = null;
        ResultSet         rs      = null;

        //String            sql     = "SELECT NomCen FROM TbCen WHERE IdCen = ? ";
        String            sql     = "SELECT " + TablaCentros.NOMBRE    + 
                                    " FROM  " + TablaCentros.TABLA     + 
                                    " WHERE " + TablaCentros.CODCENTRO + " = ? ";
        
        String            strCen  = "";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se le pasan los parámetros a la consulta sql
            ps.setInt(1, new Integer(idCen).intValue());

            rs  = ps.executeQuery();


            if (rs.next())
            {
                strCen = rs.getString(TablaCentros.NOMBRE);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return strCen;

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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }
    }
    
    //Método que devuelve los datos de un centro
    public static CentrosVO datCentro(int idCen)
    {

        Connection        con     = null;
        PreparedStatement ps      = null;
        ResultSet         rs      = null;

        //String            sql     = "SELECT NomCen FROM TbCen WHERE IdCen = ? ";
        String            sql     = "SELECT " + TablaCentros.CODCENTRO + " ," 
                                              + TablaCentros.NOMBRE    + 
                                    " FROM  " + TablaCentros.TABLA     + 
                                    " WHERE " + TablaCentros.CODCENTRO + " = ? ";
        
        CentrosVO        cenVO    = null;

        
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se le pasan los parámetros a la consulta sql
            ps.setInt(1, idCen);

            rs  = ps.executeQuery();


            if (rs.next())
            {
                cenVO    = new CentrosVO();
                
                cenVO.setIdCentro    (rs.getInt   (TablaCentros.CODCENTRO));
                cenVO.setNombreCentro(rs.getString(TablaCentros.NOMBRE));
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return cenVO;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    
    //Devuelve el nuevo código de centtro generado
    public static int generarNuevoCodCentro()
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT MAX(CONVERT(INT,idAct)) FROM TbAct";
        String            cadenaConsulta = "SELECT MAX(" + TablaCentros.CODCENTRO + ") AS " + TablaCentros.CODCENTRO +  
                                           " FROM "      + TablaCentros.TABLA;
        
        int               nuevoCod       = -1;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);
            rs = ps.executeQuery();

            if(rs.next())
            {
                nuevoCod = rs.getInt(TablaCentros.CODCENTRO) + 1;
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return nuevoCod;

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
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }
    
    //Método que guarda un Centro
    public static int guardarCentro(CentrosVO cenVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        
        int               regActualizados = 0;
        
        
        int               nueCodCentro    = generarNuevoCodCentro();

        //String            sql             = "INSERT INTO TbSegEmp (codSeg,idEmp,incidencias,fecha,usuario) VALUES(?,?,?,?,?)";
        String            sql             = "INSERT INTO " + TablaCentros.TABLA     + " ( " 
                                                           + TablaCentros.CODCENTRO + " , "
                                                           + TablaCentros.NOMBRE    + " ) " +
                                            " VALUES (?,?)";
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros a la consulta sql
            ps.setInt   (1, nueCodCentro);
            ps.setString(2, cenVO.getNombreCentro());
           
           regActualizados = ps.executeUpdate();

           ps.close();
           Conexion.desconectar(con);

           return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
    
    //Edita el registro de un Centro
    public static int editarCentro(CentrosVO cenVO)
    {

        Connection        con             = null;
        PreparedStatement ps              = null;
        
        int               regActualizados = -1;

        String            sql             = "UPDATE " + TablaCentros.TABLA     +
                                            " SET "   + TablaCentros.NOMBRE    + " = ? " +
                                            " WHERE " + TablaCentros.CODCENTRO + " = ? ";
        try
        {

            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasasn los parámetros a la consulta sql
            ps.setString(1, cenVO.getNombreCentro());
            ps.setInt   (2, cenVO.getIdCentro());

            regActualizados = ps.executeUpdate();

            ps.close();;
            Conexion.desconectar(con);

            return regActualizados;
        }
        catch (Exception exc)
        {
            try
            {
                ps.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CentrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

}
