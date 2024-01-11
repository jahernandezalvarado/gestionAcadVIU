/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.TipoCursoVO;

import es.jahernandez.tablas.TablaTipoCurso;

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
public class TipoCursoDAO
{
    //Método que devuelve los datos a mostrar en los combos de Tipo de Curso
    public static Vector devolverDatosTipCur()
    {
        Connection        con           = null;
        PreparedStatement ps            = null;
        ResultSet         rs            = null;

        //String            sql           = "SELECT IDTIPCUR, NOMTIPCUR FROM TBTIPCUR ORDER BY NOMTIPCUR";
        String            sql           = "SELECT "    + TablaTipoCurso.CODTIPOCURSO + " , " 
                                                       + TablaTipoCurso.NOMBRE       + 
                                          " FROM "     + TablaTipoCurso.TABLA        + 
                                          " ORDER BY " + TablaTipoCurso.NOMBRE;
        
        
        Vector            listaTipCur   = new Vector();
        TipoCursoVO       tipCursoVO    = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                tipCursoVO = new TipoCursoVO();

                tipCursoVO.setIdTipoCurso(rs.getInt   (TablaTipoCurso.CODTIPOCURSO));
                tipCursoVO.setNomTipCurso(rs.getString(TablaTipoCurso.NOMBRE));

                listaTipCur.addElement(tipCursoVO);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaTipCur;
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
                Logger.getLogger(TipoCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve los datos a mostrar en los combos de Tipo de Curso
    public static TipoCursoVO devolverTipCur(int codTipo)
    {
        Connection        con           = null;
        PreparedStatement ps            = null;
        ResultSet         rs            = null;

        //String            sql           = "SELECT IDTIPCUR, NOMTIPCUR FROM TBTIPCUR where IDTIPCUR = ?";
        String            sql           = "SELECT " + TablaTipoCurso.CODTIPOCURSO + " , " 
                                                    + TablaTipoCurso.NOMBRE       + 
                                          " FROM "  + TablaTipoCurso.TABLA        + 
                                          " WHERE " + TablaTipoCurso.CODTIPOCURSO + " = ?";
        
        TipoCursoVO       tipCursoVO    = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            ps.setInt(1, codTipo);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                tipCursoVO = new TipoCursoVO();

                tipCursoVO.setIdTipoCurso(rs.getInt   (TablaTipoCurso.CODTIPOCURSO));
                tipCursoVO.setNomTipCurso(rs.getString(TablaTipoCurso.NOMBRE));
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return tipCursoVO;
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
                Logger.getLogger(TipoCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Mátodo que devuelve el nombre de un tipo de curso
    public static String devuelveNombreTipo(int codTipoCur) 
    {
        Connection        con      = null;
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        //String            sql           = "SELECT IDTIPCUR, NOMTIPCUR FROM TBTIPCUR where IDTIPCUR = ?";
        String            sql      = "SELECT " + TablaTipoCurso.NOMBRE       + 
                                          " FROM "  + TablaTipoCurso.TABLA        + 
                                          " WHERE " + TablaTipoCurso.CODTIPOCURSO + " = ?";
        
        String           nombreTip = "";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            ps.setInt(1, codTipoCur);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                nombreTip = rs.getString(TablaTipoCurso.NOMBRE);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return nombreTip;
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
                Logger.getLogger(TipoCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
    //Método que genera un nuevo código de tipo curso
    public static int generarNuevoCodTipCur()
    {
        Connection        con      = null;
        PreparedStatement ps       = null;
        ResultSet         rs       = null;
        
        int               nuevCod  = -99;

        //String            sql           = "SELECT IDTIPCUR, NOMTIPCUR FROM TBTIPCUR where IDTIPCUR = ?";
        String            sql      = "SELECT  MAX(" + TablaTipoCurso.CODTIPOCURSO + ") + 1 " +
                                     " FROM " + TablaTipoCurso.TABLA ;
        

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                nuevCod = rs.getInt(1);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return nuevCod;
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
                Logger.getLogger(TipoCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }
    

    //Método que guarda un tipo de curso
    public static int guardarTipoCurso(TipoCursoVO tipCurVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        
        int               regActualizados = 0;
        
        
        int               nueCodTipCurs   = generarNuevoCodTipCur();

        //String            sql             = "INSERT INTO TbSegEmp (codSeg,idEmp,incidencias,fecha,usuario) VALUES(?,?,?,?,?)";
        String            sql             = "INSERT INTO " + TablaTipoCurso.TABLA        + " ( " 
                                                           + TablaTipoCurso.CODTIPOCURSO + " , "
                                                           + TablaTipoCurso.NOMBRE       + " ) " +
                                            " VALUES (?,?)";
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros a la consulta sql
            ps.setInt   (1, nueCodTipCurs);
            ps.setString(2, tipCurVO.getNomTipCurso());
           
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
                Logger.getLogger(TipoCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
    
    //Edita el registro de un tipo de curso
    public static int editarTipoCurso(TipoCursoVO tipCurVO)
    {

        Connection        con             = null;
        PreparedStatement ps              = null;
        
        int               regActualizados = 0;

        //String            sql             = "UPDATE TbSegEmp SET "  +
        //                                        "incidencias = ? ," +
        //                                        "fecha       = ? ," + 
        //                                        "usuario     = ?  " + 
        //                                   "WHERE codSeg = ?";
        String            sql             = "UPDATE " + TablaTipoCurso.TABLA     +
                                            " SET "   + TablaTipoCurso.NOMBRE    + " = ? " +
                                            " WHERE " + TablaTipoCurso.CODTIPOCURSO + " = ? ";


        try
        {

            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasasn los parámetros a la consulta sql
            ps.setString(1, tipCurVO.getNomTipCurso());
            ps.setInt   (2, tipCurVO.getIdTipoCurso());

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
                Logger.getLogger(TipoCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }
}