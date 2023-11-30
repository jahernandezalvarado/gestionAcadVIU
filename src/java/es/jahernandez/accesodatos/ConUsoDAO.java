

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ConUsuVO;

import es.jahernandez.tablas.TablaUsuarios;

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
public class ConUsoDAO
{
    //Método que devuelve los datos de control de un usuario activo
    public static ConUsuVO buscarUsuario(String user, String password)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String            sql        = "SELECT " + TablaUsuarios.USUARIO   + "," 
                                                 + TablaUsuarios.PASSWORD  + ","
                                                 + TablaUsuarios.ACCESO    + ","
                                                 + TablaUsuarios.NOMBRE    + ","
                                                 + TablaUsuarios.CODCENTRO + ", "
                                                 + TablaUsuarios.ACTIVO    +   
                                       " FROM "  + TablaUsuarios.TABLA     +    
                                       " WHERE " + TablaUsuarios.USUARIO   + " = ? AND "
                                                 + TablaUsuarios.PASSWORD  + " = ? AND "
                                                 + TablaUsuarios.ACTIVO    + " = 1";

        ConUsuVO          conUsoVO   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, user);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next())
            {
                conUsoVO = new ConUsuVO();
                conUsoVO.setUsuario    (rs.getString (TablaUsuarios.USUARIO));
                conUsoVO.setPassword   (rs.getString (TablaUsuarios.PASSWORD));
                conUsoVO.setNivelAcceso(rs.getInt    (TablaUsuarios.ACCESO));
                conUsoVO.setNombre     (rs.getString (TablaUsuarios.NOMBRE));
                conUsoVO.setIdCentro   (rs.getInt    (TablaUsuarios.CODCENTRO));
                conUsoVO.setActivo     (rs.getBoolean(TablaUsuarios.ACTIVO));
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return conUsoVO;

        }
        catch (Exception exc)
        {
            try
            {
                rs.close();
                Conexion.desconectar(con);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

    }

    //Método que devuelve los datos de control de todos los usuarios que no son administradores
    public static Vector devTodUsuarios()
    {

        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT * FROM TbControl WHERE nivelAcceso < 9";
        String            sql        = "SELECT " + TablaUsuarios.USUARIO   + "," 
                                                 + TablaUsuarios.PASSWORD  + ","
                                                 + TablaUsuarios.ACCESO    + ","
                                                 + TablaUsuarios.NOMBRE    + ","
                                                 + TablaUsuarios.CODCENTRO + ","
                                                 + TablaUsuarios.ACTIVO    +
                                       " FROM "  + TablaUsuarios.TABLA     +
                                       " WHERE " + TablaUsuarios.ACCESO    + " < 9";                
        
        ConUsuVO          conUsoVO   = null;

        Vector            listUsers  = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            while (rs.next())
            {
                conUsoVO = new ConUsuVO();
                conUsoVO.setUsuario    (rs.getString (TablaUsuarios.USUARIO));
                conUsoVO.setPassword   (rs.getString (TablaUsuarios.PASSWORD));
                conUsoVO.setNivelAcceso(rs.getInt    (TablaUsuarios.ACCESO));
                conUsoVO.setNombre     (rs.getString (TablaUsuarios.NOMBRE));
                conUsoVO.setIdCentro   (rs.getInt    (TablaUsuarios.CODCENTRO));
                conUsoVO.setActivo     (rs.getBoolean(TablaUsuarios.ACTIVO));

                listUsers.addElement(conUsoVO);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listUsers;

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

            return null;
        }


    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarUser(ConUsuVO conUsVO, String usuario)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        int               resulSql   = 0;

        //String            sql        = "INSERT INTO TbControl(usuario,password,nivelAcceso,nombre,idCentro) VALUES(?,?,?,?,?)";
        String            sql        = "INSERT INTO " + TablaUsuarios.TABLA +  "(" + TablaUsuarios.USUARIO   + " , " 
                                                                                  + TablaUsuarios.PASSWORD  + " , "
                                                                                  + TablaUsuarios.ACCESO    + " , "
                                                                                  + TablaUsuarios.NOMBRE    + " , "
                                                                                  + TablaUsuarios.CODCENTRO + " , " 
                                                                                  + TablaUsuarios.ACTIVO    + " ) " +  
                                       " VALUES(?,?,?,?,?,?)";
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString (1, conUsVO.getUsuario());
            ps.setString (2, conUsVO.getPassword());
            ps.setInt    (3, conUsVO.getNivelAcceso());
            ps.setString (4, conUsVO.getNombre());
            ps.setInt    (5, conUsVO.getIdCentro());
            ps.setBoolean(6, conUsVO.isActivo());
            
            resulSql = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

           
           return resulSql;

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

            return -1;
        }
    }

    //Método que elimina un registro en la base de datos
    public static int borrarUser(String login, String usuario)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        int               resulSql   = 0;

        //String            sql        = "DELETE FROM TbControl WHERE usuario = ?";
        String            sql        = "DELETE FROM " + TablaUsuarios.TABLA   +
                                       " WHERE "      + TablaUsuarios.USUARIO + " = ?";
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, login);

            resulSql = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            //Registro de seuuridad
            //Desactivado temporalmente
            /*LogSegVO logSegVO  = new LogSegVO();
            logSegVO.Usuario   = usuario;
            logSegVO.Tabla     = "TbControl";
            logSegVO.Fecha     = System.DateTime.Now;
            logSegVO.Operacion = "B";

            LogSegDAO.guardarLog(logSegVO);
             */
            
            return resulSql;

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

            return -1;
        }
    }

    //Método que devuelve los datos de un usuario
    public static ConUsuVO devDatUsuario(String user, String usuario)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT * FROM TbControl WHERE usuario = ?";
        String              sql      = "SELECT " + TablaUsuarios.USUARIO   + "," 
                                                 + TablaUsuarios.PASSWORD  + ","
                                                 + TablaUsuarios.ACCESO    + ","
                                                 + TablaUsuarios.NOMBRE    + ","
                                                 + TablaUsuarios.CODCENTRO + ","
                                                 + TablaUsuarios.ACTIVO    + 
                                       " FROM "  + TablaUsuarios.TABLA     +  
                                       " WHERE " + TablaUsuarios.USUARIO   + " = ?";
        
        
        
        ConUsuVO          conUsoVO   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, user);

            rs = ps.executeQuery();



            if (rs.next())
            {
                conUsoVO = new ConUsuVO();
                conUsoVO.setUsuario    (rs.getString (TablaUsuarios.USUARIO));
                conUsoVO.setPassword   (rs.getString (TablaUsuarios.PASSWORD));
                conUsoVO.setNivelAcceso(rs.getInt    (TablaUsuarios.ACCESO));
                conUsoVO.setNombre     (rs.getString (TablaUsuarios.NOMBRE));
                conUsoVO.setIdCentro   (rs.getInt    (TablaUsuarios.CODCENTRO));
                conUsoVO.setActivo     (rs.getBoolean(TablaUsuarios.ACTIVO));
                //Control de usuarios
                //Desactivado temporalmente
                /*LogSegVO logSegVO  = new LogSegVO();
                logSegVO.Usuario   = usuario;
                logSegVO.Tabla     = "TbControl";
                logSegVO.Fecha     = System.DateTime.Now;
                logSegVO.Operacion = "L";

                LogSegDAO.guardarLog(logSegVO);
                */
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return conUsoVO;

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

            return null;
        }
    }

    //Método que edita los datos de un usuario
    public static int editaUser(ConUsuVO conVO, String usuario)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        int               resulSql   = 0;

        /*String            sql        = "UPDATE TbControl SET "     +
                                            "password    = ? , "   +
                                            "nivelAcceso = ? , "   +
                                            "nombre      = ? , "   +
                                            "idCentro=   = ? "     +
                                       "WHERE usuario = ? ";
        */
        String            sql        = "UPDATE " + TablaUsuarios.TABLA  + " SET " + TablaUsuarios.PASSWORD  + " = ? , " 
                                                                                  + TablaUsuarios.ACCESO    + " = ? , " 
                                                                                  + TablaUsuarios.NOMBRE    + " = ? , "   
                                                                                  + TablaUsuarios.CODCENTRO + " = ? , "  
                                                                                  + TablaUsuarios.ACTIVO    + " = ?   "  +
                                       " WHERE "                                  + TablaUsuarios.USUARIO   + " = ?   ";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros a la consulta sql
            ps.setString (1, conVO.getPassword());
            ps.setInt    (2, conVO.getNivelAcceso());
            ps.setString (3, conVO.getNombre());
            ps.setInt    (4, conVO.getIdCentro());
            ps.setBoolean(5, conVO.isActivo());
            ps.setString (6, conVO.getUsuario());

            resulSql = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            //Datos de control
            //Desactivado temporalmente
            /*LogSegVO logSegVO  = new LogSegVO();
            logSegVO.Usuario   = usuario;
            logSegVO.Tabla     = "TbControl";
            logSegVO.Fecha     = System.DateTime.Now;
            logSegVO.Operacion = "M";

            LogSegDAO.guardarLog(logSegVO);
            */
            return resulSql;
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

            return -1;
        }
    }

}



