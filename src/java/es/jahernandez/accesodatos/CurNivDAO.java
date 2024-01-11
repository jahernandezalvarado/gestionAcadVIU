/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.CurNivVO;

import es.jahernandez.tablas.TablaCursoNiveles;
import es.jahernandez.tablas.TablaNiveles;

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
public class CurNivDAO
{    
    //Método que guarda los niveles de un curso
    public static int guardarNivCur(String codCur, String codNiv)
    {
        Connection        con = null;
        PreparedStatement ps  = null;

        //String cadenaConsulta = "INSERT INTO TbCurNiv (IdCur,IdNiv) VALUES(?,?)";
        String            sql = "INSERT INTO " + TablaCursoNiveles.TABLA + " (" + TablaCursoNiveles.CODCURSO + " , "
                                                                                + TablaCursoNiveles.CODNIV   + ")  " +  
                                " VALUES(?,?)";                
        int regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
            ps.setString(2, codNiv);
            
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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que devuelve los niveles que tiene asociados un determinado curso
    public static Vector devolverCurNiv(String codCur)
    {

        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        //String            sql         = "SELECT TbCurNiv.IdCur, TbCurNiv.IdNiv FROM TbCurNiv INNER JOIN TbNiv ON TbCurNiv.IdNiv = TbNiv.IdNiv WHERE (TbCurNiv.IdCur = ?) ORDER BY TbNiv.NomNiv";
        String            sql         = "SELECT "      + TablaCursoNiveles.TABLA   + "." + TablaCursoNiveles.CODCURSO     + " , " 
                                                       + TablaCursoNiveles.TABLA   + "." + TablaCursoNiveles.CODNIV       + 
                                        " FROM "       + TablaCursoNiveles.TABLA   + 
                                        " INNER JOIN " + TablaNiveles.TABLA        + " ON " + TablaCursoNiveles.TABLA     + "."     + TablaCursoNiveles.CODNIV + " = " 
                                                                                            + TablaNiveles.TABLA          + "."     + TablaNiveles.CODNIVEL    + 
                                        " WHERE ("     + TablaCursoNiveles.TABLA   + "."    + TablaCursoNiveles.CODCURSO  + "= ?) " +
                                        " ORDER BY "   + TablaNiveles.TABLA        + "."    + TablaNiveles.NOMBRE;
        
        CurNivVO          datNivCur   = null;

        Vector            listaNivCur = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codCur);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datNivCur = new CurNivVO();

                datNivCur.setIdCur(rs.getString(TablaCursoNiveles.CODCURSO));
                datNivCur.setIdNiv(rs.getString(TablaCursoNiveles.CODNIV));

                listaNivCur.add(datNivCur);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaNivCur;
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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que elimina un nivel de un curso
    public static int eliminarNivCur(String codNiv, String codCur)
    {
        Connection        con = null;
        PreparedStatement ps  = null;

        //String          sql = "DELETE FROM TbCurNiv WHERE IdCur = ? AND IdNiv = ?";
        String            sql = "DELETE FROM " + TablaCursoNiveles.TABLA    +
                                " WHERE "      + TablaCursoNiveles.CODCURSO + " = ? AND "  
                                               + TablaCursoNiveles.CODNIV   + " = ? ";
        
        int regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
            ps.setString(2, codNiv);

            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            //Se actualiza los niveles de interes de los  alumnos
            CursosAluDAO.ediNivCurAlu(codCur);

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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }

    }

    //Borra los datos de nivel de un curso
    public static int borraCurNiv(String codCur)
    {

        Connection        con    = null;
        PreparedStatement ps     = null;

        //String          sql  = "DELETE FROM TbCurNiv WHERE IdCur = ? ";
        String            sql    = "DELETE FROM " + TablaCursoNiveles.TABLA    + 
                                   " WHERE  "     + TablaCursoNiveles.CODCURSO + " = ? ";
        
        int               regAct = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
                        
            regAct = ps.executeUpdate();
            
            ps.close();
            Conexion.desconectar(con);

            return regAct;

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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Borra todos los datos de un curso
    public static int borrarTodNivCur(String codCur)
    {
        Connection        con    = null;
        PreparedStatement ps     = null;

        //String          sql    = "DELETE FROM TbCurNiv WHERE IdCur = ? ";
        String            sql    = "DELETE FROM " + TablaCursoNiveles.TABLA    +  
                                   " WHERE  "     + TablaCursoNiveles.CODCURSO +  " = ? ";
        
        int               regAct = 0;
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);

            regAct = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            return regAct;

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
                Logger.getLogger(CurNivDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }

    }

}
