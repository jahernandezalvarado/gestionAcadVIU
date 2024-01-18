/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.NivelesVO;

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
public class NivelesDAO
{
    //Método que devuelve los datos de un nivel
    public static NivelesVO devolverDatosNivel(String codNiv)
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT * FROM TbNiv WHERE IdNiv = ?";
        String            cadenaConsulta = "SELECT " + TablaNiveles.CODNIVEL  + " , " 
                                                     + TablaNiveles.NOMBRE    + " , "
                                                     + TablaNiveles.CONTENIDO + " , "
                                                     + TablaNiveles.CODCURSO  + 
                                           " FROM "  + TablaNiveles.TABLA     +
                                           " WHERE " + TablaNiveles.CODNIVEL  + " = ?";
        
        NivelesVO         datNiv         = new NivelesVO();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codNiv);

            rs  = ps.executeQuery();

            if (rs.next())
            {
                datNiv = new NivelesVO();

                datNiv.setIdNiv     (rs.getString(TablaNiveles.CODNIVEL));
                datNiv.setNomNiv    (rs.getString(TablaNiveles.NOMBRE));
                datNiv.setContenidos(rs.getString(TablaNiveles.CONTENIDO));
                datNiv.setCodCur    (rs.getString(TablaNiveles.CODCURSO));
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return datNiv;

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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    //Método que devuelve los datos de nivel
    public static Vector devolverTodosNiv()
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            cadenaConsulta = "SELECT * FROM TbNiv";
        String            cadenaConsulta = "SELECT " + TablaNiveles.CODNIVEL  + " , " 
                                                     + TablaNiveles.NOMBRE    + " , "
                                                     + TablaNiveles.CONTENIDO + " , "
                                                     + TablaNiveles.CODCURSO  + 
                                           " FROM "  + TablaNiveles.TABLA;
        
        NivelesVO         datNiv         = null;
        Vector            listaNiveles   = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datNiv = new NivelesVO();

                datNiv.setIdNiv     (rs.getString(TablaNiveles.CODNIVEL));
                datNiv.setNomNiv    (rs.getString(TablaNiveles.NOMBRE));
                datNiv.setContenidos(rs.getString(TablaNiveles.CONTENIDO));
                datNiv.setCodCur    (rs.getString(TablaNiveles.CODCURSO));

                listaNiveles.add(datNiv);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaNiveles;
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    public static int guardarNivel(NivelesVO nivVO)
    {
        String            nueCodNiv      = generarNuevoCodNiv();

        Connection        con            = null;
        PreparedStatement ps             = null;
        
        //String            cadenaConsulta = "INSERT INTO TbNiv (IdNiv , NomNiv,ContNiv,idCur) VALUES(?,?,?,?) ";
        String            cadenaConsulta = "INSERT INTO " + TablaNiveles.TABLA     + " ( "  
                                                          + TablaNiveles.CODNIVEL  + " , " 
                                                          + TablaNiveles.NOMBRE    + " , "  
                                                          + TablaNiveles.CONTENIDO + " , " 
                                                          + TablaNiveles.CODCURSO  + " ) " + 
                                           " VALUES(?,?,?,?)";
        
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, nueCodNiv);
            ps.setString(2, nivVO.getNomNiv());
            ps.setString(3, nivVO.getContenidos());
            ps.setString(4, nivVO.getCodCur());

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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Método que genera un nuevo código de nivel
    public static String generarNuevoCodNiv()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datNiv    = devolverTodosNiv();

        while (enc)
        {
            contCar = new Integer(datNiv.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datNiv.size() + avc).toString();
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
            datNiv = devolverTodosNiv();
            for (int ind = 0; ind < datNiv.size(); ind++)
            {
                NivelesVO nivVO = (NivelesVO) datNiv.elementAt(ind);
                if (nivVO.getIdNiv().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Método que devuelve los datos de nivel de un determinado curso
    public static Vector devolverNivCur(String codCur )
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String          cadenaConsulta = "SELECT * FROM TbNiv where idCur = ?";
        String            sql          = "SELECT " + TablaNiveles.CODNIVEL  + " , " 
                                                   + TablaNiveles.NOMBRE    + " , "
                                                   + TablaNiveles.CONTENIDO + " , "
                                                   + TablaNiveles.CODCURSO  + 
                                         " FROM "  + TablaNiveles.TABLA     +
                                         " WHERE " + TablaNiveles.CODCURSO  + " = ?"; 
        NivelesVO         datNiv       = null;
        Vector            listaNiveles = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta
            ps.setString(1, codCur);

            rs  = ps.executeQuery();

            while(rs.next())
            {
                datNiv = new NivelesVO();

                datNiv.setIdNiv     (rs.getString(TablaNiveles.CODNIVEL));
                datNiv.setNomNiv    (rs.getString(TablaNiveles.NOMBRE));
                datNiv.setContenidos(rs.getString(TablaNiveles.CONTENIDO));
                datNiv.setCodCur    (rs.getString(TablaNiveles.CODCURSO));

                listaNiveles.add(datNiv);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaNiveles;
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    
     //Edita el registro de un curso
    public static int editaNivel(NivelesVO nivVO)
    {

        Connection        con = null;
        PreparedStatement ps  = null;

        String            sql = "UPDATE " + TablaNiveles.TABLA     +
                                " SET "   + TablaNiveles.NOMBRE    + " = ? , " 
                                          + TablaNiveles.CONTENIDO + " = ?   " +
                                " WHERE " + TablaNiveles.CODNIVEL  + " = ?";
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, nivVO.getNomNiv());
            ps.setString(2, nivVO.getContenidos());
            ps.setString(3, nivVO.getIdNiv());
           
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }

    //Edita el registro de un curso
    public static int eliminaNivel(String codNivel)
    {

        Connection        con = null;
        PreparedStatement ps  = null;

        String            sql = "DELETE FROM " + TablaNiveles.TABLA  + 
                                " WHERE "      + TablaNiveles.CODNIVEL + " = ?";
        
        int               regActualizados = 0;

        
        if(EdicionesDAO.estaNivelenEdicion(codNivel))
        {
            return -2;
        }
        
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la consulta sql
            ps.setString(1, codNivel);
                       
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
                Logger.getLogger(NivelesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        }
    }        
}
