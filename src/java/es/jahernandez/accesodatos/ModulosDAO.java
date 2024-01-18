/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.ModulosVO;

import es.jahernandez.tablas.TablaModulos;

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
public class ModulosDAO 
{
    //Método que devuelve los datos de un módulo
    public static ModulosVO devolverDatosModulo(String codMod)
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;
        
        String            cadenaConsulta = "SELECT " + TablaModulos.CODMODULO   + " , " 
                                                     + TablaModulos.NOMBRE      + " , "
                                                     + TablaModulos.DESCRIPCION + " , "
                                                     + TablaModulos.HORAS       + " , "
                                                     + TablaModulos.CODCURSO    + " , "  
                                                     + TablaModulos.CODAREA     +      
                                           " FROM "  + TablaModulos.TABLA       +
                                           " WHERE " + TablaModulos.CODMODULO   + " = ?";
        
        ModulosVO         datMod         = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codMod);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                datMod = new ModulosVO();

                datMod.setCodMod     (rs.getString(TablaModulos.CODMODULO));
                datMod.setNombre     (rs.getString(TablaModulos.NOMBRE));
                datMod.setDescripcion(rs.getString(TablaModulos.DESCRIPCION));
                datMod.setNumHoras   (rs.getInt   (TablaModulos.HORAS));
                datMod.setCodCur     (rs.getString(TablaModulos.CODCURSO));
                datMod.setCodArea    (rs.getString(TablaModulos.CODAREA));
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return datMod;

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
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que devuelve los datos de módulo
    public static Vector devolverTodosMod()
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;
        
        String            cadenaConsulta = "SELECT " + TablaModulos.CODMODULO   + " , " 
                                                     + TablaModulos.NOMBRE      + " , "
                                                     + TablaModulos.DESCRIPCION + " , "
                                                     + TablaModulos.HORAS       + " , "
                                                     + TablaModulos.CODCURSO    + " , "
                                                     + TablaModulos.CODAREA     + 
                                           " FROM "  + TablaModulos.TABLA;
        
        ModulosVO         datMod         = null;
        Vector            listaModulos   = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datMod = new ModulosVO();

                datMod.setCodMod     (rs.getString(TablaModulos.CODMODULO));
                datMod.setNombre     (rs.getString(TablaModulos.NOMBRE));
                datMod.setDescripcion(rs.getString(TablaModulos.DESCRIPCION));
                datMod.setNumHoras   (rs.getInt   (TablaModulos.HORAS));
                datMod.setCodCur     (rs.getString(TablaModulos.CODCURSO));
                datMod.setCodArea    (rs.getString(TablaModulos.CODAREA));
                
                listaModulos.add(datMod);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaModulos;
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
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarModulo(ModulosVO modVO)
    {
        String            nueCodMod      = generarNuevoCodMod();

        Connection        con            = null;
        PreparedStatement ps             = null;
        
        //String            cadenaConsulta = "INSERT INTO TbNiv (IdNiv , NomNiv,ContNiv,idCur) VALUES(?,?,?,?) ";
        String            cadenaConsulta = "INSERT INTO " + TablaModulos.TABLA       + " ( "  
                                                          + TablaModulos.CODMODULO   + " , " 
                                                          + TablaModulos.NOMBRE      + " , "  
                                                          + TablaModulos.DESCRIPCION + " , " 
                                                          + TablaModulos.HORAS       + " , "  
                                                          + TablaModulos.CODCURSO    + " , " 
                                                          + TablaModulos.CODAREA     + " ) " +
                                           " VALUES(?,?,?,?,?,?)";
        
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, nueCodMod);
            ps.setString(2, modVO.getNombre());
            ps.setString(3, modVO.getDescripcion());
            ps.setInt   (4, modVO.getNumHoras());
            ps.setString(5, modVO.getCodCur());
            ps.setString(6, modVO.getCodArea());
            
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
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de modulo
    synchronized public static String generarNuevoCodMod()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datMod    = devolverTodosMod();

        while (enc)
        {
            contCar = new Integer(datMod.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datMod.size() + avc).toString();
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 8)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datMod  = devolverTodosMod();
            for (int ind = 0; ind < datMod.size(); ind++)
            {
                ModulosVO modVO = (ModulosVO) datMod.elementAt(ind);
                if (modVO.getCodMod().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Método que devuelve los módulos de un curso
    public static Vector devolverModCur(String codCur )
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;
        
        String            sql            = "SELECT " + TablaModulos.CODMODULO   + " , " 
                                                     + TablaModulos.NOMBRE      + " , "
                                                     + TablaModulos.DESCRIPCION + " , "
                                                     + TablaModulos.HORAS       + " , "
                                                     + TablaModulos.CODCURSO    + " , "
                                                     + TablaModulos.CODAREA     +
                                           " FROM "  + TablaModulos.TABLA       +
                                           " WHERE " + TablaModulos.CODCURSO    + " = ?";
        
        ModulosVO         datMod       = null;
        Vector            listaModulos = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codCur);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datMod = new ModulosVO();

                datMod.setCodMod     (rs.getString(TablaModulos.CODMODULO));
                datMod.setNombre     (rs.getString(TablaModulos.NOMBRE));
                datMod.setDescripcion(rs.getString(TablaModulos.DESCRIPCION));
                datMod.setNumHoras   (rs.getInt   (TablaModulos.HORAS));
                datMod.setCodCur     (rs.getString(TablaModulos.CODCURSO));
                datMod.setCodArea    (rs.getString(TablaModulos.CODAREA));

                listaModulos.add(datMod);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaModulos;
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
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
     //Edita el registro de un módulo
    public static int editaModulo(ModulosVO modVO)
    {

        Connection        con = null;
        PreparedStatement ps  = null;

        String            sql = "UPDATE " + TablaModulos.TABLA       +
                                " SET "   + TablaModulos.NOMBRE      + " = ? , " 
                                          + TablaModulos.DESCRIPCION + " = ? , " 
                                          + TablaModulos.HORAS       + " = ? , " 
                                          + TablaModulos.CODAREA     + " = ?   " +
                                " WHERE " + TablaModulos.CODMODULO   + " = ?";
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, modVO.getNombre());
            ps.setString(2, modVO.getDescripcion());
            ps.setInt   (3, modVO.getNumHoras());
            ps.setString(4, modVO.getCodArea());
            ps.setString(5, modVO.getCodMod());
            
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
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Elimina el registro de un módulo
    public static int eliminaModulo(String codMod)
    {

        Connection        con = null;
        PreparedStatement ps  = null;

        String            sql = "DELETE FROM " + TablaModulos.TABLA     + 
                                " WHERE "      + TablaModulos.CODMODULO + " = ?";
        
        int               regActualizados = 0;

        //Comprobar si existe alguna edición con este módulo
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codMod);
                       
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
                Logger.getLogger(ModulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }        
    
}
