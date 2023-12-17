/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.DatosBusquedaAlumnos;
import es.jahernandez.datos.ResultadoBusqAlu;

import es.jahernandez.tablas.TablaAlumnos;
import es.jahernandez.tablas.TablaCursosAlumnos;
import es.jahernandez.tablas.TablaProvincias; 

import java.sql.*;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Alberto
 */
public class AlumnosDAO
{           
        
    //Método que devuelve los datos de búsqueda de alumnos con curso
    public static Vector devolverDatosConsulta(String cadenaConsulta)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        Vector           listaAlumnos = new Vector();

        ResultadoBusqAlu datResBus    = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);
            
            rs  = ps.executeQuery();

            while (rs.next())
            {
                datResBus = new ResultadoBusqAlu();

                datResBus.setApe1Alu(rs.getString(1));
                datResBus.setApe2Alu(rs.getString(2));
                datResBus.setNomAlu(rs.getString(3));
                datResBus.setIdAlu(rs.getString(4));
                datResBus.setMovAlu(rs.getString(5));
                datResBus.setEmaAlu(rs.getString(6));
                datResBus.setNombCurso(rs.getString(7));
                datResBus.setFijAlu(rs.getString(8));
                datResBus.setInteres(rs.getString(9));

                listaAlumnos.addElement(datResBus);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAlumnos;

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

    //Método que devuelve los datos de búsqueda de alumnos
    public static Vector devolverDatosConsAlumno(DatosBusquedaAlumnos datBA)
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        Vector            listaAlumnos   = new Vector();

        AlumnosVO         datResBus      = null;

        String            strAux         =  "";
        
        String            cadenaConsulta =  "SELECT " + TablaAlumnos.CODALU         + " , "      
                                                      + TablaAlumnos.TIPODOC        + " , "     
                                                      + TablaAlumnos.NUMERODOC      + " , "
                                                      + TablaAlumnos.NOMBRE         + " , "
                                                      + TablaAlumnos.APE1           + " , " 
                                                      + TablaAlumnos.APE2           + " , "
                                                      + TablaAlumnos.MOVIL          + " , "
                                                      + TablaAlumnos.TELFIJO        + " , "
                                                      + TablaAlumnos.DIRECCION      + " , "
                                                      + TablaAlumnos.CODPOSTAL      + " , "
                                                      + TablaAlumnos.LOCALIDAD      + " , "
                                                      + TablaAlumnos.NIVELFORM      + " , " 
                                                      + TablaAlumnos.EMAIL          + " , "
                                                      + TablaAlumnos.DESEMPLEADO    + " , "
                                                      + TablaAlumnos.CODEMPRESA     + " , "
                                                      + TablaAlumnos.INTERESES      + " , "
                                                      + TablaAlumnos.CODPROV        + " , "
                                                      + TablaAlumnos.CODCENTRO      + " , "
                                                      + TablaAlumnos.FECHANAC       + " , "
                                                      + TablaAlumnos.NODESEABLE     + " , "
                                                      + TablaAlumnos.AUTCESDAT      + " , "
                                                      + TablaAlumnos.AUTCOMCOM      + 
                                            " FROM  " + TablaAlumnos.TABLA ;
        
        //Se construye la cadena de búsqueda
        
        if (datBA.getApellidos().equals("")    && datBA.getNombre().equals("")  &&
            datBA.getNumDoc().equals("")       && datBA.getCodPost().equals("") &&
            datBA.getDesempleado().equals("")  && datBA.getCodEmp().equals("0") &&
            datBA.getNoIntCur().equals("")     && datBA.getNivFor().equals("0") &&
            datBA.geteMail().equals(""))
        {

            //No hay condición de búsqueda
        }
        else
        {
            cadenaConsulta = cadenaConsulta + " WHERE ";
            //Se construye la condición de búsqueda

            if (! datBA.getNumDoc().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.NUMERODOC   + " LIKE '%" + datBA.getNumDoc().toUpperCase()    + "%' AND ";
            }

            if (! datBA.getApellidos().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.APE1        + " LIKE '%" + datBA.getApellidos().toUpperCase() + "%' AND ";
            }

            if (! datBA.getNombre().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.NOMBRE      + " LIKE '%" + datBA.getNombre().toUpperCase()    + "%' AND ";
            }

            if (! datBA.getDesempleado().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.DESEMPLEADO + " = true AND ";                
            }

            if (! datBA.getNivFor().equals("0"))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.NIVELFORM   + " = "      + datBA.getNivFor()                  + " AND ";                
            }

            if (! datBA.getCodEmp().equals("0"))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.CODEMPRESA  + " LIKE '"  + datBA.getCodEmp()                  + "' AND ";                
            }

            if (! datBA.getNoIntCur().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.CODALU      + " NOT IN (SELECT " + TablaCursosAlumnos.CODALUMNO + " FROM " + TablaCursosAlumnos.TABLA  + ") AND ";                
            }

            if (! datBA.getCodPost().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.CODPOSTAL   + " LIKE '%" + datBA.getCodPost()                 + "%' AND ";                
            }

            if (! datBA.geteMail().equals(""))
            {
                cadenaConsulta = cadenaConsulta + TablaAlumnos.EMAIL       + " LIKE '%" + datBA.geteMail()                   + "%' AND ";
            }    

            cadenaConsulta = cadenaConsulta.substring(0, cadenaConsulta.length()- 4);
        }
           
        cadenaConsulta = cadenaConsulta + " ORDER BY "  + TablaAlumnos.APE1;
        
               
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datResBus = new AlumnosVO();

                datResBus.setIdAlu    (rs.getString  (TablaAlumnos.CODALU));
                datResBus.setTipDocAlu(rs.getInt     (TablaAlumnos.TIPODOC));
                datResBus.setNumDocAlu(rs.getString  (TablaAlumnos.NUMERODOC));
                datResBus.setNombre   (rs.getString  (TablaAlumnos.NOMBRE));
                datResBus.setAp1Alu   (rs.getString  (TablaAlumnos.APE1));
                //datResBus.Ap2Alu     = Convert.ToString(reader.GetString(5));
                datResBus.setMovAlu    (rs.getString (TablaAlumnos.MOVIL));
                datResBus.setFijAlu    (rs.getString (TablaAlumnos.TELFIJO));
                datResBus.setDirAlu    (rs.getString (TablaAlumnos.DIRECCION));
                datResBus.setCodPosAlu (rs.getString (TablaAlumnos.CODPOSTAL));
                datResBus.setLocalAlu  (rs.getString (TablaAlumnos.LOCALIDAD));
                datResBus.setNivForm   (rs.getInt    (TablaAlumnos.NIVELFORM));
                datResBus.setEmail     (rs.getString (TablaAlumnos.EMAIL));
                datResBus.setDesemp    (rs.getBoolean(TablaAlumnos.DESEMPLEADO));
                datResBus.setEmpAlu    (rs.getString (TablaAlumnos.CODEMPRESA));
                datResBus.setIntereses (rs.getString (TablaAlumnos.INTERESES));
                datResBus.setCodProvAlu(rs.getString (TablaAlumnos.CODPROV));
                datResBus.setIdCen     (rs.getInt    (TablaAlumnos.CODCENTRO));
                datResBus.setFecNac    (rs.getDate   (TablaAlumnos.FECHANAC));
                datResBus.setAlND      (rs.getBoolean(TablaAlumnos.NODESEABLE));
                datResBus.setAutCesDat (rs.getBoolean(TablaAlumnos.AUTCESDAT));
                datResBus.setAutComCom (rs.getBoolean(TablaAlumnos.AUTCOMCOM));

                listaAlumnos.addElement(datResBus);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAlumnos;
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

    //Método que devuelve los datos de búsqueda de alumnos por curso
    public static Vector devolverDatosConsCurso(String cadenaConsulta)
    {
        Connection        con          = null;
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        Vector            listaAlumnos = new Vector();

        ResultadoBusqAlu  datResBus    = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(cadenaConsulta);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datResBus = new ResultadoBusqAlu();

                datResBus.setIdAlu(rs.getString(1));
                datResBus.setNomAlu(rs.getString(2));
                datResBus.setApe1Alu(rs.getString(3));
                datResBus.setApe2Alu(rs.getString(4));
                datResBus.setMovAlu(rs.getString(5));
                datResBus.setEmaAlu(rs.getString(6));
                datResBus.setFijAlu(rs.getString(11));
                datResBus.setInteres(rs.getString(12));
                //datResBus.NombCurso = Convert.ToString(reader.GetString(6));

                listaAlumnos.addElement(datResBus);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);
            return listaAlumnos;
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

    //Método que devuelve los datos de un alumno
    public static AlumnosVO devolverDatosAlumno(String codAlu)
    {
        Connection        con          = null;
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        AlumnosVO         datAlu       = null;

        //String            sql          = "SELECT * FROM TbAlu WHERE IdAlu = ?";
        String            sql          = "SELECT " + TablaAlumnos.CODALU         + " , "      
                                                   + TablaAlumnos.TIPODOC        + " , "     
                                                   + TablaAlumnos.NUMERODOC      + " , "
                                                   + TablaAlumnos.NOMBRE         + " , "
                                                   + TablaAlumnos.APE1           + " , " 
                                                   + TablaAlumnos.APE2           + " , "
                                                   + TablaAlumnos.MOVIL          + " , "
                                                   + TablaAlumnos.TELFIJO        + " , "
                                                   + TablaAlumnos.DIRECCION      + " , "
                                                   + TablaAlumnos.CODPOSTAL      + " , "
                                                   + TablaAlumnos.LOCALIDAD      + " , "
                                                   + TablaAlumnos.NIVELFORM      + " , " 
                                                   + TablaAlumnos.EMAIL          + " , "
                                                   + TablaAlumnos.DESEMPLEADO    + " , "
                                                   + TablaAlumnos.CODEMPRESA     + " , "
                                                   + TablaAlumnos.INTERESES      + " , "
                                                   + TablaAlumnos.CODPROV        + " , "
                                                   + TablaAlumnos.CODCENTRO      + " , "
                                                   + TablaAlumnos.FECHANAC       + " , "
                                                   + TablaAlumnos.NODESEABLE     + " , "
                                                   + TablaAlumnos.AUTCESDAT      + " , "
                                                   + TablaAlumnos.AUTCOMCOM      + 
                                         " FROM  " + TablaAlumnos.TABLA          + 
                                         " WHERE " + TablaAlumnos.CODALU         + " = ?";
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se añaden los parámetros a la consulta sql
            ps.setString(1,codAlu);

            rs = ps.executeQuery();

            if (rs.next())
            {

                datAlu = new AlumnosVO();

                datAlu.setIdAlu     (rs.getString (TablaAlumnos.CODALU));
                datAlu.setTipDocAlu (rs.getInt    (TablaAlumnos.TIPODOC));
                datAlu.setNumDocAlu (rs.getString (TablaAlumnos.NUMERODOC));
                datAlu.setNombre    (rs.getString (TablaAlumnos.NOMBRE));
                datAlu.setAp1Alu    (rs.getString (TablaAlumnos.APE1));              
                datAlu.setMovAlu    (rs.getString (TablaAlumnos.MOVIL));
                datAlu.setFijAlu    (rs.getString (TablaAlumnos.TELFIJO));
                datAlu.setDirAlu    (rs.getString (TablaAlumnos.DIRECCION));
                datAlu.setCodPosAlu (rs.getString (TablaAlumnos.CODPOSTAL));
                datAlu.setLocalAlu  (rs.getString (TablaAlumnos.LOCALIDAD));
                datAlu.setNivForm   (rs.getInt    (TablaAlumnos.NIVELFORM));
                datAlu.setEmail     (rs.getString (TablaAlumnos.EMAIL));
                datAlu.setDesemp    (rs.getBoolean(TablaAlumnos.DESEMPLEADO));
                datAlu.setEmpAlu    (rs.getString (TablaAlumnos.CODEMPRESA));
                datAlu.setIntereses (rs.getString (TablaAlumnos.INTERESES));
                datAlu.setCodProvAlu(rs.getString (TablaAlumnos.CODPROV));
                datAlu.setIdCen     (rs.getInt    (TablaAlumnos.CODCENTRO));
                datAlu.setFecNac    (rs.getDate   (TablaAlumnos.FECHANAC));
                datAlu.setAlND      (rs.getBoolean(TablaAlumnos.NODESEABLE));
                datAlu.setAutCesDat (rs.getBoolean(TablaAlumnos.AUTCESDAT));
                datAlu.setAutComCom (rs.getBoolean(TablaAlumnos.AUTCOMCOM));

            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return datAlu;
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

    //Método que genera un código nuevo de alumno
    public static String generarNuevoCodAluB()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";
        String  maxAlu    = "";
        int     numMax    = 0;

        maxAlu = devuelveMaxAlu();

        if ( ! maxAlu.equals(""))
        {
            numMax = new Integer(maxAlu).intValue();
        }
        else
        {
            numMax = 0;
        }

        Vector listAlu = devolverTodosAlu();

        while (enc)
        {
            contCar =  new Integer(listAlu.size()).toString().length();

            if (contCar > 0)
            {
                //codIntrod = (listAlu.Count + 1).ToString();
                codIntrod =  new Integer(numMax + 10).toString();
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() <= 7)
            {
                codIntrod = "0" + codIntrod;
                //contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.Length - 5), codIntrod.Length-1);

            //codIntrod = codIntrod;
            enc = false;

            listAlu = devolverTodosAlu();
            for (int ind = 0; ind < listAlu.size(); ind++)
            {
                AlumnosVO aluVO = (AlumnosVO) listAlu.elementAt(ind);
                if (codIntrod.equals(aluVO.getIdAlu()))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Método que devuelve los datos de alumnos
    public static Vector devolverTodosAlu()
    {
        Connection        con          = null;
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
        
        //String            sql          = "SELECT * FROM TbAlu";
        String            sql          = "SELECT " + TablaAlumnos.CODALU         + " , "      
                                                   + TablaAlumnos.TIPODOC        + " , "     
                                                   + TablaAlumnos.NUMERODOC      + " , "
                                                   + TablaAlumnos.NOMBRE         + " , "
                                                   + TablaAlumnos.APE1           + " , " 
                                                   + TablaAlumnos.APE2           + " , "
                                                   + TablaAlumnos.MOVIL          + " , "
                                                   + TablaAlumnos.TELFIJO        + " , "
                                                   + TablaAlumnos.DIRECCION      + " , "
                                                   + TablaAlumnos.CODPOSTAL      + " , "
                                                   + TablaAlumnos.LOCALIDAD      + " , "
                                                   + TablaAlumnos.NIVELFORM      + " , " 
                                                   + TablaAlumnos.EMAIL          + " , "
                                                   + TablaAlumnos.DESEMPLEADO    + " , "
                                                   + TablaAlumnos.CODEMPRESA     + " , "
                                                   + TablaAlumnos.INTERESES      + " , "
                                                   + TablaAlumnos.CODPROV        + " , "
                                                   + TablaAlumnos.CODCENTRO      + " , "
                                                   + TablaAlumnos.FECHANAC       + " , "
                                                   + TablaAlumnos.NODESEABLE     + " , "
                                                   + TablaAlumnos.AUTCESDAT      + " , "
                                                   + TablaAlumnos.AUTCOMCOM      + 
                                         " FROM  " + TablaAlumnos.TABLA;       
        
        AlumnosVO         datAlu       = null;
        Vector            listaAlumnos = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next() )
            {
                datAlu = new AlumnosVO();

                datAlu.setIdAlu     (rs.getString (TablaAlumnos.CODALU));
                datAlu.setTipDocAlu (rs.getInt    (TablaAlumnos.TIPODOC));
                datAlu.setNumDocAlu (rs.getString (TablaAlumnos.NUMERODOC));
                datAlu.setNombre    (rs.getString (TablaAlumnos.NOMBRE));
                datAlu.setAp1Alu    (rs.getString (TablaAlumnos.APE1));              
                datAlu.setMovAlu    (rs.getString (TablaAlumnos.MOVIL));
                datAlu.setFijAlu    (rs.getString (TablaAlumnos.TELFIJO));
                datAlu.setDirAlu    (rs.getString (TablaAlumnos.DIRECCION));
                datAlu.setCodPosAlu (rs.getString (TablaAlumnos.CODPOSTAL));
                datAlu.setLocalAlu  (rs.getString (TablaAlumnos.LOCALIDAD));
                datAlu.setNivForm   (rs.getInt    (TablaAlumnos.NIVELFORM));
                datAlu.setEmail     (rs.getString (TablaAlumnos.EMAIL));
                datAlu.setDesemp    (rs.getBoolean(TablaAlumnos.DESEMPLEADO));
                datAlu.setEmpAlu    (rs.getString (TablaAlumnos.CODEMPRESA));
                datAlu.setIntereses (rs.getString (TablaAlumnos.INTERESES));
                datAlu.setCodProvAlu(rs.getString (TablaAlumnos.CODPROV));
                datAlu.setIdCen     (rs.getInt    (TablaAlumnos.CODCENTRO));
                datAlu.setFecNac    (rs.getDate   (TablaAlumnos.FECHANAC));
                datAlu.setAlND      (rs.getBoolean(TablaAlumnos.NODESEABLE));
                datAlu.setAutCesDat (rs.getBoolean(TablaAlumnos.AUTCESDAT));
                datAlu.setAutComCom (rs.getBoolean(TablaAlumnos.AUTCOMCOM));
                
                listaAlumnos.addElement(datAlu);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAlumnos;
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
    
    //Método que devuelve los datos de alumnos por orden alfabético
    public static Vector devolverTodosAluOrd()
    {
        Connection        con          = null;
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
        
        //String            sql          = "SELECT * FROM TbAlu";
        String            sql          = "SELECT "    + TablaAlumnos.CODALU         + " , "      
                                                      + TablaAlumnos.TIPODOC        + " , "     
                                                      + TablaAlumnos.NUMERODOC      + " , "
                                                      + TablaAlumnos.NOMBRE         + " , "
                                                      + TablaAlumnos.APE1           + " , " 
                                                      + TablaAlumnos.APE2           + " , "
                                                      + TablaAlumnos.MOVIL          + " , "
                                                      + TablaAlumnos.TELFIJO        + " , "
                                                      + TablaAlumnos.DIRECCION      + " , "
                                                      + TablaAlumnos.CODPOSTAL      + " , "
                                                      + TablaAlumnos.LOCALIDAD      + " , "
                                                      + TablaAlumnos.NIVELFORM      + " , " 
                                                      + TablaAlumnos.EMAIL          + " , "
                                                      + TablaAlumnos.DESEMPLEADO    + " , "
                                                      + TablaAlumnos.CODEMPRESA     + " , "
                                                      + TablaAlumnos.INTERESES      + " , "
                                                      + TablaAlumnos.CODPROV        + " , "
                                                      + TablaAlumnos.CODCENTRO      + " , "
                                                      + TablaAlumnos.FECHANAC       + " , "
                                                      + TablaAlumnos.NODESEABLE     + " , "
                                                      + TablaAlumnos.AUTCESDAT      + " , "
                                                      + TablaAlumnos.AUTCOMCOM      + 
                                         " FROM "     + TablaAlumnos.TABLA          +
                                         " ORDER BY " + TablaAlumnos.APE1           + " , "
                                                      + TablaAlumnos.NOMBRE ;
        
        AlumnosVO         datAlu       = null;
        Vector            listaAlumnos = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next() )
            {
                datAlu = new AlumnosVO();

                datAlu.setIdAlu     (rs.getString (TablaAlumnos.CODALU));
                datAlu.setTipDocAlu (rs.getInt    (TablaAlumnos.TIPODOC));
                datAlu.setNumDocAlu (rs.getString (TablaAlumnos.NUMERODOC));
                datAlu.setNombre    (rs.getString (TablaAlumnos.NOMBRE));
                datAlu.setAp1Alu    (rs.getString (TablaAlumnos.APE1));              
                datAlu.setMovAlu    (rs.getString (TablaAlumnos.MOVIL));
                datAlu.setFijAlu    (rs.getString (TablaAlumnos.TELFIJO));
                datAlu.setDirAlu    (rs.getString (TablaAlumnos.DIRECCION));
                datAlu.setCodPosAlu (rs.getString (TablaAlumnos.CODPOSTAL));
                datAlu.setLocalAlu  (rs.getString (TablaAlumnos.LOCALIDAD));
                datAlu.setNivForm   (rs.getInt    (TablaAlumnos.NIVELFORM));
                datAlu.setEmail     (rs.getString (TablaAlumnos.EMAIL));
                datAlu.setDesemp    (rs.getBoolean(TablaAlumnos.DESEMPLEADO));
                datAlu.setEmpAlu    (rs.getString (TablaAlumnos.CODEMPRESA));
                datAlu.setIntereses (rs.getString (TablaAlumnos.INTERESES));
                datAlu.setCodProvAlu(rs.getString (TablaAlumnos.CODPROV));
                datAlu.setIdCen     (rs.getInt    (TablaAlumnos.CODCENTRO));
                datAlu.setFecNac    (rs.getDate   (TablaAlumnos.FECHANAC));
                datAlu.setAlND      (rs.getBoolean(TablaAlumnos.NODESEABLE));
                datAlu.setAutCesDat (rs.getBoolean(TablaAlumnos.AUTCESDAT));
                datAlu.setAutComCom (rs.getBoolean(TablaAlumnos.AUTCOMCOM));
                
                listaAlumnos.addElement(datAlu);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAlumnos;
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
                
                
    //Edita el registro de un interesado
    public static int editaAlumno(AlumnosVO aluVO)
    {

        Connection          con             = null;
        PreparedStatement   ps              = null;
        AlumnosVO           aluPrev         = new AlumnosVO();


        int                 regActualizados = 0;

        /*String              sql = "UPDATE TbAlu SET " +
                                    "TDoAlu    = ? , "   +
                                    "NDoAlu    = ? , "   +
                                    "NomAlu    = ? , "   +
                                    "Ap1Alu    = ? , "   +
                                    "MovAlu    = ? , "   +
                                    "FijAlu    = ? , "   +
                                    "DirAlu    = ? , "   +
                                    "CpoAlu    = ? , "   +
                                    "LocAlu    = ? , "   +
                                    "NFoAlu    = ? , "   +
                                    "EmaAlu    = ? , "   +
                                    "DesAlu    = ? , "   +
                                    "EmpAlu    = ? , "   +
                                    "IntAlu    = ? , "   +
                                    "codProAlu = ? , "   +
                                    "FecNac    = ? , "   +
                                    "alND      = ? , "   +
                                    "autCesDat = ? , "   +
                                    "autComCom = ?   "   +
                                "WHERE IdAlu = ?";
        */
        String              sql = "UPDATE " + TablaAlumnos.TABLA + " SET "     +
                                        TablaAlumnos.TIPODOC     + " = ? , "   +
                                        TablaAlumnos.NUMERODOC   + " = ? , "   +
                                        TablaAlumnos.NOMBRE      + " = ? , "   +
                                        TablaAlumnos.APE1        + " = ? , "   +
                                        TablaAlumnos.MOVIL       + " = ? , "   +
                                        TablaAlumnos.TELFIJO     + " = ? , "   +
                                        TablaAlumnos.DIRECCION   + " = ? , "   +
                                        TablaAlumnos.CODPOSTAL   + " = ? , "   +
                                        TablaAlumnos.LOCALIDAD   + " = ? , "   +
                                        TablaAlumnos.NIVELFORM   + " = ? , "   +
                                        TablaAlumnos.EMAIL       + " = ? , "   +
                                        TablaAlumnos.DESEMPLEADO + " = ? , "   +
                                        TablaAlumnos.CODEMPRESA  + " = ? , "   +
                                        TablaAlumnos.INTERESES   + " = ? , "   +
                                        TablaAlumnos.CODPROV     + " = ? , "   +
                                        TablaAlumnos.FECHANAC    + " = ? , "   +
                                        TablaAlumnos.NODESEABLE  + " = ? , "   +
                                        TablaAlumnos.AUTCESDAT   + " = ? , "   +
                                        TablaAlumnos.AUTCOMCOM   + " = ?   "   +
                                  "WHERE " + TablaAlumnos.CODALU + " = ?";
        
        aluPrev = AlumnosDAO.devolverDatosAlumno(aluVO.getIdAlu());

        if (!aluPrev.getNumDocAlu().trim().equals(aluVO.getNumDocAlu().trim()) && existeDniAlumno(aluVO.getNumDocAlu()))
        {
            return -1; //Se comprueba no se duplique el número de documento
        }

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setInt    ( 1, aluVO.getTipDocAlu());
            ps.setString ( 2, aluVO.getNumDocAlu());
            ps.setString ( 3, aluVO.getNombre());
            ps.setString ( 4, aluVO.getAp1Alu());
            ps.setString ( 5, aluVO.getMovAlu());
            ps.setString ( 6, aluVO.getFijAlu());
            ps.setString ( 7, aluVO.getDirAlu());
            ps.setString ( 8, aluVO.getCodPosAlu());
            ps.setString ( 9, aluVO.getLocalAlu());
            ps.setInt    (10, aluVO.getNivForm());
            ps.setString (11, aluVO.getEmail());
            ps.setBoolean(12, aluVO.isDesemp());
            ps.setString (13, aluVO.getEmpAlu());
            ps.setString (14, aluVO.getIntereses());
            ps.setString (15, aluVO.getCodProvAlu());
            ps.setDate   (16, new Date(aluVO.getFecNac().getTime()));
            ps.setBoolean(17, aluVO.isAlND());
            ps.setBoolean(18, aluVO.isAutCesDat());
            ps.setBoolean(19, aluVO.isAutComCom());
            ps.setString (20, aluVO.getIdAlu());

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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -2;
        }
    }

    //Borra el registro de un alumno
    public static int borraRegAlu(String codAlu)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        int               regActualizados = 0;

        //String            sql             = "DELETE FROM TbAlu WHERE IdAlu = ?";
        String            sql             = "DELETE FROM " + TablaAlumnos.TABLA  + 
                                            " WHERE "      + TablaAlumnos.CODALU + " = ?";
        
        
        //Se comprueba que el alumno no tenga matrículas
      /*  if (AluEdiDAO.devNumMat(codAlu) > 0)
        {
            return -3; //El alumno no se borra porque tiene matrículas
        }
        if (AluEdiDAO.devNumMat(codAlu) == -1)
        {
            return -1; //El alumno no se borra porque no se pudo comprobar si tiene matríiculas
        }
*/
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codAlu);

            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            //eliminar datos cursos
            /*if (CursosAluDAO.borraCurAlu(codAlu) >= 0)
            {
                //Borra datos de seguimientos del alumno a dar de baja
                if (SeguimientosDAO.eliminaSegAlu(codAlu) >= 0)
                {
                    return regActualizados; //Todo borrado correctamente
                }
                else
                {
                    return -4; //Datos de seguimiento no borrados
                }
            }
            else
            {
                return -2; //Datos de curso no borrados y seguimiento no borrados
            }*/
            
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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Devuelve el VALOR MAXIMO de codigo alumno
    public static String devuelveMaxAlu()
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT MAX(IdAlu) FROM TbAlu";
        String            sql        = "SELECT MAX(" + TablaAlumnos.CODALU +")" + 
                                       " FROM " + TablaAlumnos.TABLA;
        
        String            nombreDoc  = "";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if(rs.next())
            {
                nombreDoc = rs.getString(1);
            }
            
            rs.close();
            ps.close();                        
            Conexion.desconectar(con);

            return  nombreDoc;
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

    //Da de alta un nuevo alumno
    public static String insertaAlumno(AlumnosVO aluVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        
        //String            sql             = "INSERT INTO TbAlu (IdAlu,TDoAlu,NDoAlu,NomAlu,Ap1Alu,Ap2Alu,MovAlu,FijAlu,DirAlu,CpoAlu,LocAlu,NFoAlu,EmaAlu,DesAlu,EmpAlu,IntAlu,codProAlu,IdCen,FecNac,alND,autCesDat,autComCom) " +
        //                                           "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
        
        String            sql             = "INSERT INTO " + TablaAlumnos.TABLA + " ("  + TablaAlumnos.CODALU      + " , "
                                                                                        + TablaAlumnos.TIPODOC     + " , "
                                                                                        + TablaAlumnos.NUMERODOC   + " , "
                                                                                        + TablaAlumnos.NOMBRE      + " , "  
                                                                                        + TablaAlumnos.APE1        + " , " 
                                                                                        + TablaAlumnos.APE2        + " , "
                                                                                        + TablaAlumnos.MOVIL       + " , "
                                                                                        + TablaAlumnos.TELFIJO     + " , "
                                                                                        + TablaAlumnos.DIRECCION   + " , "
                                                                                        + TablaAlumnos.CODPOSTAL   + " , "
                                                                                        + TablaAlumnos.LOCALIDAD   + " , "
                                                                                        + TablaAlumnos.NIVELFORM   + " , "
                                                                                        + TablaAlumnos.EMAIL       + " , "
                                                                                        + TablaAlumnos.DESEMPLEADO + " , "
                                                                                        + TablaAlumnos.CODEMPRESA  + " , "
                                                                                        + TablaAlumnos.INTERESES   + " , "
                                                                                        + TablaAlumnos.CODPROV     + " , "
                                                                                        + TablaAlumnos.CODCENTRO   + " , "
                                                                                        + TablaAlumnos.FECHANAC    + " , "
                                                                                        + TablaAlumnos.NODESEABLE  + " , "
                                                                                        + TablaAlumnos.AUTCESDAT   + " , "
                                                                                        + TablaAlumnos.AUTCOMCOM   + ")  " +
                                            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        int               regActualizados = 0;

        //Se comprueba que no exista el número de documento del alumno el la base de datos
        if (existeDniAlumno(aluVO.getNumDocAlu()))
        {
            return "-1";
        }

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            aluVO.setIdAlu(AlumnosDAO.generarNuevoCodAlu());
            //Se pasan los parámetros de la consulta sql
            ps.setString ( 1, aluVO.getIdAlu());
            ps.setInt    ( 2, aluVO.getTipDocAlu());
            ps.setString ( 3, aluVO.getNumDocAlu());
            ps.setString ( 4, aluVO.getNombre());
            ps.setString ( 5, aluVO.getAp1Alu());
            ps.setString ( 6, aluVO.getAp2Alu());
            ps.setString ( 7, aluVO.getMovAlu());
            ps.setString ( 8, aluVO.getFijAlu());
            ps.setString ( 9, aluVO.getDirAlu());
            ps.setString (10, aluVO.getCodPosAlu());
            ps.setString (11, aluVO.getLocalAlu());
            ps.setInt    (12, aluVO.getNivForm());
            ps.setString (13, aluVO.getEmail());
            ps.setBoolean(14, aluVO.isDesemp());
            ps.setString (15, aluVO.getEmpAlu());
            ps.setString (16, aluVO.getIntereses());
            ps.setString (17, aluVO.getCodProvAlu());
            ps.setInt    (18, aluVO.getIdCen());
            ps.setDate   (19, new Date(aluVO.getFecNac().getTime()));
            ps.setBoolean(20, aluVO.isAlND());
            ps.setBoolean(21, aluVO.isAutCesDat());
            ps.setBoolean(22, aluVO.isAutComCom());

            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            if(regActualizados>0)
            {
                return  aluVO.getIdAlu(); //regActualizados;
            }
            else
            {
                return "0";
            }
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

            return "-2";
        }

    }
    
    //Genera un nuevo código de Alumno
    public static String generarNuevoCodAlu()
    {
        boolean enc       = true;
        int     avc       = 1;
        int     contCar;
        String  codIntrod = "";

        Vector datAlu    = devolverTodosAlu();

        while (enc)
        {
            contCar = new Integer(datAlu.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datAlu.size() + avc).toString();
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
            datAlu = devolverTodosAlu();
            for (int ind = 0; ind < datAlu.size(); ind++)
            {
                AlumnosVO aluVO = (AlumnosVO) datAlu.elementAt(ind);
                if (aluVO.getIdAlu().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }
        return codIntrod;
    }

    //Devuelve la provincia del alumno
    public static String devolverNombreProv(String codProv)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        ResultSet         rs              = null;

        //String            sql             = "SELECT Ciudad FROM TbCPo WHERE NCodigo = ? ";
        String            sql             = "SELECT " + TablaProvincias.NOMBRE  + 
                                            " FROM  " + TablaProvincias.TABLA   + 
                                            " WHERE " + TablaProvincias.CODPROV + " = ? ";
                        
        String            nombreProvincia = "";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codProv);

            rs = ps.executeQuery();

            if (rs.next())
            {
                nombreProvincia = rs.getString(1);
            }

            rs.close();
            ps.close();
            
            Conexion.desconectar(con);

            return nombreProvincia;
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

    //Método que devuelve si ya existe el dato identificativo de un alumno
    public static boolean existeDniAlumno(String numDoc)
    {
        Connection        con          = null;
        PreparedStatement ps           = null;
        ResultSet         rs           = null;

        //String            sql          = "SELECT * FROM TbAlu WHERE NDoAlu = ? ";
        String            sql          = "SELECT " + TablaAlumnos.CODALU     + 
                                         " FROM  " + TablaAlumnos.TABLA      + 
                                         " WHERE " + TablaAlumnos.NUMERODOC  + " = ? ";
                
        boolean          existeDNI     = false;

        //Se comprueba que el número de documento no sea una cadena vacía
        if (numDoc.trim().equals(""))
        {
            return false;
        }

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1,numDoc);

            rs = ps.executeQuery();

            if (rs.next())
            {
                existeDNI = true;
            }
            else
            {
                existeDNI = false;
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);
                        
            return existeDNI;

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

            return true;
        }


    }

}
