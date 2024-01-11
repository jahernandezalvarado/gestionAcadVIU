/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.SeguimientosVO;

import es.jahernandez.tablas.TablaSeguimientos; 

import java.sql.Connection;
import java.sql.Date;
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
public class SeguimientosDAO
{
    //Método que devuelve los datos de un seguimiento
    public static SeguimientosVO devolverDatosSeg(String codSeg)
    {
        Connection        con    = null;
        PreparedStatement ps     = null;
        ResultSet         rs     = null;

        //String            sql    = "SELECT * FROM TbSeg WHERE codSeg = ?";
        String            sql    = "SELECT " + TablaSeguimientos.CODSEGUIMIENTO + " , " 
                                             + TablaSeguimientos.CODALUMNO      + " , "
                                             + TablaSeguimientos.CODCURSO       + " , "
                                             + TablaSeguimientos.INCIDENCIAS    + " , "
                                             + TablaSeguimientos.FECHA          + " , "
                                             + TablaSeguimientos.USUARIO        + 
                                   " FROM "  + TablaSeguimientos.TABLA          +
                                   " WHERE " + TablaSeguimientos.CODSEGUIMIENTO + " = ?";
        
        SeguimientosVO    datSeg = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codSeg);

            rs = ps.executeQuery();

            if (rs.next())
            {
                datSeg = new SeguimientosVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientos.CODSEGUIMIENTO));
                datSeg.setIdAlu      (rs.getString(TablaSeguimientos.CODALUMNO));
                datSeg.setIdCur      (rs.getString(TablaSeguimientos.CODCURSO));
                datSeg.setIncidencias(rs.getString(TablaSeguimientos.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientos.FECHA));
                datSeg.setUsuario    (rs.getString(TablaSeguimientos.USUARIO));
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return datSeg;
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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

    }

    //Método que devuelve los datos de seguimiento
    public static Vector devolverTodosSeg()
    {
        Connection        con      = null;
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        //String            sql      = "SELECT * FROM TbSeg";
        String            sql    = "SELECT " + TablaSeguimientos.CODSEGUIMIENTO + " , " 
                                             + TablaSeguimientos.CODALUMNO      + " , "
                                             + TablaSeguimientos.CODCURSO       + " , "
                                             + TablaSeguimientos.INCIDENCIAS    + " , "
                                             + TablaSeguimientos.FECHA          + " , "
                                             + TablaSeguimientos.USUARIO        + 
                                   " FROM "  + TablaSeguimientos.TABLA;
        
        Vector            listaSeg = new Vector();

        SeguimientosVO    datSeg   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datSeg = new SeguimientosVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientos.CODSEGUIMIENTO));
                datSeg.setIdAlu      (rs.getString(TablaSeguimientos.CODALUMNO));
                datSeg.setIdCur      (rs.getString(TablaSeguimientos.CODCURSO));
                datSeg.setIncidencias(rs.getString(TablaSeguimientos.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientos.FECHA));
                datSeg.setUsuario    (rs.getString(TablaSeguimientos.USUARIO));

                listaSeg.addElement(datSeg);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

             return listaSeg;
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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
       
    }

    //Método que genera un nuevo código de seguimiento
    public static String generarNuevoCodSeg()
    {
        boolean   enc       = true;
        int       avc       = 1;
        int       contCar;
        String    codIntrod = "";

        Vector    datSeg    = devolverTodosSeg();

        while (enc)
        {
            contCar = new Integer(datSeg.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datSeg.size() + avc).toString();
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 10)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datSeg = devolverTodosSeg();
            for (int ind = 0; ind < datSeg.size(); ind++)
            {
                SeguimientosVO segVO =  (SeguimientosVO) datSeg.elementAt(ind);
                if (segVO.getCodSeg().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarSeg(SeguimientosVO segVO)
    {
        Connection        con       = null;
        PreparedStatement ps        = null;
        
        String            nueCodSeg = generarNuevoCodSeg();

        //String            sql       = "INSERT INTO TbSeg (codSeg,idAlu,idCur,incidencias,fecha,usuario) VALUES(?,?,?,?,?,?)";
        String            sql       = "INSERT INTO " + TablaSeguimientos.TABLA          + " ( " 
                                                     + TablaSeguimientos.CODSEGUIMIENTO + " , "  
                                                     + TablaSeguimientos.CODALUMNO      + " , "
                                                     + TablaSeguimientos.CODCURSO       + " , " 
                                                     + TablaSeguimientos.INCIDENCIAS    + " , "
                                                     + TablaSeguimientos.FECHA          + " , " 
                                                     + TablaSeguimientos.USUARIO        + " ) " + 
                                      " VALUES(?,?,?,?,?,?)";
        
        int regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, nueCodSeg);
            ps.setString(2, segVO.getIdAlu());
            ps.setString(3, segVO.getIdCur());
            ps.setString(4, segVO.getIncidencias());
            ps.setDate  (5, new Date(segVO.getFecha().getTime()));
            ps.setString(6, segVO.getUsuario());

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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Edita el registro de un seguimiento
    public static int editaSeg(SeguimientosVO segVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        /*String            sql             = "UPDATE TbSeg SET " +
                                                "idCur         = ?," +
                                                "incidencias   = ?," +
                                                "fecha         = ?," +
                                                "usuario       = ? " +
                                            "WHERE codSeg = ? ";*/

        String            sql             = "UPDATE " + TablaSeguimientos.TABLA          +  
                                            " SET "   + TablaSeguimientos.CODCURSO       + " = ? ," 
                                                      + TablaSeguimientos.INCIDENCIAS    + " = ? ," 
                                                      + TablaSeguimientos.FECHA          + " = ? ," 
                                                      + TablaSeguimientos.USUARIO        + " = ? " +
                                            " WHERE " + TablaSeguimientos.CODSEGUIMIENTO + " = ? ";
                
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, segVO.getIdCur());
            ps.setString(2, segVO.getIncidencias());
            ps.setDate  (3, new Date(segVO.getFecha().getTime()));
            ps.setString(4, segVO.getUsuario());
            ps.setString(5, segVO.getCodSeg());

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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de seguimiento de un alumno
    public static Vector devolverSegAlu(String codAl)
    {
        Connection        con       = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        //String            sql       = "SELECT * FROM TbSeg WHERE idAlu = ? ORDER BY fecha DESC";
        String            sql       = "SELECT "    + TablaSeguimientos.CODSEGUIMIENTO + " , " 
                                                   + TablaSeguimientos.CODALUMNO      + " , "
                                                   + TablaSeguimientos.CODCURSO       + " , "
                                                   + TablaSeguimientos.INCIDENCIAS    + " , "
                                                   + TablaSeguimientos.FECHA          + " , "
                                                   + TablaSeguimientos.USUARIO        + 
                                      " FROM "     + TablaSeguimientos.TABLA          +
                                      " WHERE "    + TablaSeguimientos.CODALUMNO      + " = ? " +
                                      " ORDER BY " + TablaSeguimientos.FECHA          + " DESC ";
        
        Vector            listaSeg  = new Vector();

        SeguimientosVO    datSeg = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAl);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datSeg = new SeguimientosVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientos.CODSEGUIMIENTO));
                datSeg.setIdAlu      (rs.getString(TablaSeguimientos.CODALUMNO));
                datSeg.setIdCur      (rs.getString(TablaSeguimientos.CODCURSO));
                datSeg.setIncidencias(rs.getString(TablaSeguimientos.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientos.FECHA));
                datSeg.setUsuario    (rs.getString(TablaSeguimientos.USUARIO));

                listaSeg.addElement(datSeg);
            }
            rs.close();
            ps.close();                        
            Conexion.desconectar(con);

            return listaSeg;
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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    //Método que devuelve los datos de seguimiento de un curso
    public static Vector devolverSegCur(String codCur)
    {
        Connection        con      = null;
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        //String            sql      = "SELECT * FROM TbSeg WHERE idCur = ?";
        String            sql      = "SELECT "     + TablaSeguimientos.CODSEGUIMIENTO + " , " 
                                                   + TablaSeguimientos.CODALUMNO      + " , "
                                                   + TablaSeguimientos.CODCURSO       + " , "
                                                   + TablaSeguimientos.INCIDENCIAS    + " , "
                                                   + TablaSeguimientos.FECHA          + " , "
                                                   + TablaSeguimientos.USUARIO        + 
                                     " FROM "      + TablaSeguimientos.TABLA          +
                                     " WHERE "     + TablaSeguimientos.CODCURSO       + " = ?";

        Vector            listaSeg = new Vector();

        SeguimientosVO    datSeg = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datSeg = new SeguimientosVO();

                datSeg.setCodSeg     (rs.getString(TablaSeguimientos.CODSEGUIMIENTO));
                datSeg.setIdAlu      (rs.getString(TablaSeguimientos.CODALUMNO));
                datSeg.setIdCur      (rs.getString(TablaSeguimientos.CODCURSO));
                datSeg.setIncidencias(rs.getString(TablaSeguimientos.INCIDENCIAS));
                datSeg.setFecha      (rs.getDate  (TablaSeguimientos.FECHA));
                datSeg.setUsuario    (rs.getString(TablaSeguimientos.USUARIO));

                listaSeg.addElement(datSeg);
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaSeg;
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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    //Método que elimina todos los seguimientos de un alumno
    public static int eliminaSegAlu(String codAlu)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        //String            sql             = "DELETE FROM TbSeg WHERE idAlu = ?";
        String            sql             = "DELETE FROM " + TablaSeguimientos.TABLA     +
                                            " WHERE "      + TablaSeguimientos.CODALUMNO + " = ?";
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAlu);
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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que elimina todos los seguimientos de un curso
    public static int eliminaSegCur(String codCur)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        //String            sql             = "DELETE FROM TbSeg WHERE idCur = ?";
        String            sql             = "DELETE FROM " + TablaSeguimientos.TABLA    + 
                                            " WHERE "      + TablaSeguimientos.CODCURSO + " = ?";
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codCur);
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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que elimina un registro de seguimiento
    public static int eliminaSeg(String codSeg)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        //String            sql             = "DELETE FROM TbSeg WHERE codSeg =  ? ";
        String            sql             = "DELETE FROM " + TablaSeguimientos.TABLA          + 
                                            " WHERE "      + TablaSeguimientos.CODSEGUIMIENTO + " = ?";
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codSeg);
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
                Logger.getLogger(SeguimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve el número de seguimientos de un alumno
    public static int devolverNumSeg(String codAlu)
    {
        Connection        con    = null;
        PreparedStatement ps     = null;
        ResultSet         rs     = null;

        //String            sql    = "SELECT COUNT(idAlu) FROM TbSeg WHERE idAlu = ? ";
        String            sql    = "SELECT COUNT(" + TablaSeguimientos.CODALUMNO + " ) " +
                                   " FROM "        + TablaSeguimientos.TABLA     +
                                   " WHERE "       + TablaSeguimientos.CODALUMNO + " = ? ";
        
        int               numSeg = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAlu);

            rs = ps.executeQuery();

            if(rs.next())
            {
                numSeg = rs.getInt(1);
            }
            
            rs.close();
            ps.close();            
            Conexion.desconectar(con);

            return numSeg;
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

            return -1;
        }

    }
}
