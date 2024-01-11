/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.EdicionesVO;

import es.jahernandez.tablas.TablaAlumnosEdiciones;
import es.jahernandez.tablas.TablaEdiciones;
import es.jahernandez.tablas.TablaAlumnos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

/**
 *
 * @author Alberto
 */
public class AluEdiDAO
{    
    //Método que guarda un nuevo registro en la base de datos
    public static int guardarMatAlu(AluEdiVO aluEdiVO)
    {
        Connection          con = null;
        PreparedStatement   ps  = null;

        int                 regActualizados = 0;


        //String              sql = "INSERT INTO TbAluEdi(IdAlu,IdEdi,fecAlta,esBaja,observ,numCuenta,esCong) VALUES(?,?,?,?,?,?,?)";
        String              sql = "INSERT INTO " + TablaAlumnosEdiciones.TABLA + "(" 
                                                 + TablaAlumnosEdiciones.CODALU    + "," 
                                                 + TablaAlumnosEdiciones.CODEDI    + "," 
                                                 + TablaAlumnosEdiciones.FECHAALT  + "," 
                                                 + TablaAlumnosEdiciones.ESBAJA    + "," 
                                                 + TablaAlumnosEdiciones.OBSERV    + "," 
                                                 + TablaAlumnosEdiciones.NUMCUENTA + "," 
                                                 + TablaAlumnosEdiciones.ESCONGEL  + ")" + 
                                  "VALUES(?,?,?,?,?,?,?)";
        
        
        
        //Se comprueba si hay plazas disponibles en la edición
        if (!EdicionesDAO.hayPlazasLibres(aluEdiVO.getIdEdi()))
        {
            return -2;
        }

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la sql
            ps.setString (1, aluEdiVO.getIdAlu());
            ps.setString (2, aluEdiVO.getIdEdi());
            ps.setDate   (3, new Date(aluEdiVO.getFecAlta().getTime()));
            ps.setBoolean(4, aluEdiVO.isEsBaja());
            ps.setString (5, aluEdiVO.getObserv());
            ps.setString (6, aluEdiVO.getNumCuenta());
            ps.setBoolean(7, aluEdiVO.isEsCong());

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

            return -1;
        }
    }

    //Método que devuelve los datos de alumno-edición de un alumno dado
    public static Vector devAluEdi(String codAlu)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        //String            sql         = "SELECT * FROM TbAluEdi WHERE IdAlu = ? ";
        String            sql         = "SELECT " + TablaAlumnosEdiciones.CODALU    + "," 
                                                  + TablaAlumnosEdiciones.CODEDI    + "," 
                                                  + TablaAlumnosEdiciones.FECHAALT  + "," 
                                                  + TablaAlumnosEdiciones.ESBAJA    + "," 
                                                  + TablaAlumnosEdiciones.OBSERV    + "," 
                                                  + TablaAlumnosEdiciones.NUMCUENTA + "," 
                                                  + TablaAlumnosEdiciones.ESCONGEL  + 
                                        " FROM "  + TablaAlumnosEdiciones.TABLA     + 
                                        " WHERE " + TablaAlumnosEdiciones.CODALU    + " = ? ";
        
        
        Vector            listaAluEdi = new Vector();

        AluEdiVO          datAluEdi   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codAlu);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu    (rs.getString (TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi    (rs.getString (TablaAlumnosEdiciones.CODEDI));
                datAluEdi.setFecAlta  (rs.getDate   (TablaAlumnosEdiciones.FECHAALT));
                datAluEdi.setEsBaja   (rs.getBoolean(TablaAlumnosEdiciones.ESBAJA));
                datAluEdi.setObserv   (rs.getString (TablaAlumnosEdiciones.OBSERV));
                datAluEdi.setNumCuenta(rs.getString (TablaAlumnosEdiciones.NUMCUENTA));
                datAluEdi.setEsCong   (rs.getBoolean(TablaAlumnosEdiciones.ESCONGEL));

                listaAluEdi.addElement(datAluEdi);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAluEdi;

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

    //Método que devuelve los datos de alumno-edición de una edicion y alumno dados
    public static AluEdiVO devDatAluEdi(String codAlu, String codEdi)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        //String            sql         = "SELECT * FROM TbAluEdi WHERE IdAlu = ? AND IdEdi= ?";
        String            sql         = "SELECT " + TablaAlumnosEdiciones.CODALU    + "," 
                                                  + TablaAlumnosEdiciones.CODEDI    + "," 
                                                  + TablaAlumnosEdiciones.FECHAALT  + "," 
                                                  + TablaAlumnosEdiciones.ESBAJA    + "," 
                                                  + TablaAlumnosEdiciones.OBSERV    + "," 
                                                  + TablaAlumnosEdiciones.NUMCUENTA + "," 
                                                  + TablaAlumnosEdiciones.ESCONGEL  + 
                                        " FROM "  + TablaAlumnosEdiciones.TABLA     + 
                                        " WHERE " + TablaAlumnosEdiciones.CODALU    + " = ? AND "
                                                  + TablaAlumnosEdiciones.CODEDI    + " = ?";

        AluEdiVO          datAluEdi   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codAlu);
            ps.setString(2, codEdi);

            rs = ps.executeQuery();

            if(rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu    (rs.getString (TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi    (rs.getString (TablaAlumnosEdiciones.CODEDI));
                datAluEdi.setFecAlta  (rs.getDate   (TablaAlumnosEdiciones.FECHAALT));
                datAluEdi.setEsBaja   (rs.getBoolean(TablaAlumnosEdiciones.ESBAJA));
                datAluEdi.setObserv   (rs.getString (TablaAlumnosEdiciones.OBSERV));
                datAluEdi.setNumCuenta(rs.getString (TablaAlumnosEdiciones.NUMCUENTA));
                datAluEdi.setEsCong   (rs.getBoolean(TablaAlumnosEdiciones.ESCONGEL));

            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return datAluEdi;
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

    //Método que da de baja/alta congela/descongela a un alumno en una edición
    public static int bajaMatAlu(AluEdiVO aluEdiVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        
        //String            sql             = "UPDATE TbAluEdi set esBaja = ? , "  +
        //                                                    "esCong = ? , "  +
        //                                                    "observ = ?   "  +
        //                                    "WHERE IdAlu = ? AND IdEdi = ?";

        String            sql             = "UPDATE " + TablaAlumnosEdiciones.TABLA     + 
                                            " SET "   + TablaAlumnosEdiciones.ESBAJA    + " = ? , " 
                                                      + TablaAlumnosEdiciones.ESCONGEL  + " = ? , "  
                                                      + TablaAlumnosEdiciones.NUMCUENTA + " = ? , "    
                                                      + TablaAlumnosEdiciones.OBSERV    + " = ?   "   +
                                            "WHERE "  + TablaAlumnosEdiciones.CODALU    + " = ? AND " 
                                                      + TablaAlumnosEdiciones.CODEDI    + " = ? ";                                
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setBoolean(1, aluEdiVO.isEsBaja());
            ps.setBoolean(2, aluEdiVO.isEsCong());
            ps.setString (3, aluEdiVO.getNumCuenta());
            ps.setString (4, aluEdiVO.getObserv());
            ps.setString (5, aluEdiVO.getIdAlu());
            ps.setString (6, aluEdiVO.getIdEdi());

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

            return -1;
        }
    }

    //Método que devuelve si un alumno esta matriculado en una edición de un determinado curso
    public static boolean estaAluMatCur(String codCur, String codAlu)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        /*String            sql         = "SELECT "              +
                                            "(SELECT IdCur "   +
                                                " FROM TBEdi " +
                                            " WHERE (IdEdi = TbAluEdi.IdEdi) AND (IdCur = ?) AND (FecFi > CURDATE())) AS IdCur " +
                                        "FROM TbAluEdi " +
                                        "WHERE (IdAlu = ?)";
        */
        String            sql         = "SELECT " +
                                                "(SELECT " +  TablaEdiciones.CODCURSO +    
                                                " FROM "   +  TablaEdiciones.TABLA    +  
                                                " WHERE (" +  TablaEdiciones.CODEDI   +  " = "   + TablaAlumnosEdiciones.TABLA + "."  + TablaAlumnosEdiciones.CODEDI +") AND (" 
                                                           +  TablaEdiciones.CODCURSO +  " = ?) AND (" 
                                                           +  TablaEdiciones.FECHAFIN + "> CURDATE())) AS " +  TablaEdiciones.CODCURSO +  
                                        " FROM  " + TablaAlumnosEdiciones.TABLA  +
                                        " WHERE " + TablaAlumnosEdiciones.CODALU +  " = ?";                
        
        boolean          respuesta   = false;

        Object           compNull    = null;


        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consutla sql
            ps.setString(1, codCur );
            ps.setString(2, codAlu );

            rs = ps.executeQuery();

            while(rs.next())
            {
                //Hay matriculas
                compNull = rs.getObject(codAlu);

                if (compNull != null)
                {
                    respuesta = true;
                }
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return respuesta;

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

            return false;
        }

    }

    //Método que devuelve los datos de alumno-edición de los alumnos que tienen que pagar recibo
    public static Vector devRecAluEdi()
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        /*String            sql            = "SELECT IdAlu, IdEdi " +
                                             "FROM TbAluEdi " +
                                            "WHERE esBaja = false AND esCong = false AND (IdEdi IN " +
                                                "(SELECT IdEdi " +
                                                     "FROM TBEdi " +
                                                "WHERE (CURDATE() >= FecIn) AND (FecFi > CURDATE()) AND (PrecioR > 0))) " +
                                                "ORDER BY IdEdi";
        */
        
        String            sql            = "SELECT "    + TablaAlumnosEdiciones.CODALU   + "," 
                                                        + TablaAlumnosEdiciones.CODEDI   +
                                           " FROM "     + TablaAlumnosEdiciones.TABLA    +
                                           " WHERE "    + TablaAlumnosEdiciones.ESBAJA   + " = false AND " 
                                                        + TablaAlumnosEdiciones.ESCONGEL + " = false AND (" 
                                                        + TablaAlumnosEdiciones.CODEDI   + " IN (" 
                                                               + "SELECT "   + TablaEdiciones.CODEDI  +   
                                                                 " FROM "    + TablaEdiciones.TABLA   + 
                                                                 " WHERE (CURDATE() >= " + TablaEdiciones.FECHAINICIO  +") AND (" 
                                                                                         + TablaEdiciones.FECHAFIN     + "> CURDATE()) AND ("
                                                                                         + TablaEdiciones.PRECIORECIBO + "> 0))) " +
                                           " ORDER BY " + TablaAlumnosEdiciones.CODEDI;
        
        Vector           listaAluEdi    = new Vector();
        AluEdiVO         datAluEdi      = null;
        EdicionesVO      ediVO          = new EdicionesVO();
        int              mes            = new GregorianCalendar().get(Calendar.MONTH) + 1;


        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu(rs.getString(TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi(rs.getString(TablaAlumnosEdiciones.CODEDI));

                ediVO = new EdicionesVO();
                ediVO = EdicionesDAO.devolverDatosEdi(datAluEdi.getIdEdi());

                //Se añade a la lista los datos de todos los alumnos con recibos mensuales y los correspondiente recibos a plazos
                if(! ediVO.isPlazos())
                {
                    listaAluEdi.addElement(datAluEdi);
                }
                else
                {
                    if((mes ==  1 && ediVO.isEne())  ||
                       (mes ==  2 && ediVO.isFeb())  ||
                       (mes ==  3 && ediVO.isMar())  ||
                       (mes ==  4 && ediVO.isAbr())  ||
                       (mes ==  5 && ediVO.isMay())  ||
                       (mes ==  6 && ediVO.isJun())  ||
                       (mes ==  7 && ediVO.isJul())  ||
                       (mes ==  8 && ediVO.isAgo())  ||
                       (mes ==  9 && ediVO.isSep())  ||
                       (mes == 10 && ediVO.isOct())  ||
                       (mes == 11 && ediVO.isNov())  ||
                       (mes == 12 && ediVO.isDic())  )
                    {
                        listaAluEdi.addElement(datAluEdi);
                    }

                }
            }
            
            rs.close();
            ps.close();            
            Conexion.desconectar(con);

            return listaAluEdi;
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

    //Método que devuelve los alumnos inscritos en una edición
    public static Vector devAluMatEdi(String codEdi)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        //String            sql         = "SELECT * FROM TbAluEdi INNER JOIN TbAlu ON TbAluEdi.IdAlu = TbAlu.IdAlu WHERE IdEdi = ? " +
        //                                    " AND esBaja = false ORDER BY TbAlu.Ap1Alu";
        String            sql         = "SELECT "      + TablaAlumnosEdiciones.TABLA + "." + TablaAlumnosEdiciones.CODALU    + "," 
                                                                                           + TablaAlumnosEdiciones.CODEDI    + "," 
                                                                                           + TablaAlumnosEdiciones.FECHAALT  + "," 
                                                                                           + TablaAlumnosEdiciones.ESBAJA    + "," 
                                                                                           + TablaAlumnosEdiciones.OBSERV    + "," 
                                                                                           + TablaAlumnosEdiciones.NUMCUENTA + "," 
                                                                                           + TablaAlumnosEdiciones.ESCONGEL  +
                                        " FROM "       + TablaAlumnosEdiciones.TABLA     + 
                                        " INNER JOIN " + TablaAlumnos.TABLA + " ON "     + TablaAlumnosEdiciones.TABLA + "." + TablaAlumnosEdiciones.CODALU + " = " 
                                                                                         + TablaAlumnos.TABLA          + "." + TablaAlumnos.CODALU          + 
                                        " WHERE "      + TablaAlumnosEdiciones.CODEDI    + " = ? AND " 
                                                       + TablaAlumnosEdiciones.ESBAJA    + " = false " + 
                                        " ORDER BY "   + TablaAlumnos.TABLA + "." + TablaAlumnos.APE1;

        
        Vector            listaAluEdi = new Vector();

        AluEdiVO          datAluEdi   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la conulsta sql
            ps.setString(1, codEdi);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu    (rs.getString (TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi    (rs.getString (TablaAlumnosEdiciones.CODEDI));
                datAluEdi.setFecAlta  (rs.getDate   (TablaAlumnosEdiciones.FECHAALT));
                datAluEdi.setEsBaja   (rs.getBoolean(TablaAlumnosEdiciones.ESBAJA));
                datAluEdi.setObserv   (rs.getString (TablaAlumnosEdiciones.OBSERV));
                datAluEdi.setNumCuenta(rs.getString (TablaAlumnosEdiciones.NUMCUENTA));
                datAluEdi.setEsCong   (rs.getBoolean(TablaAlumnosEdiciones.ESCONGEL));

                listaAluEdi.addElement(datAluEdi);
            }
            rs.close();
            ps.close();                      
            Conexion.desconectar(con);

            return listaAluEdi;
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

    //Método que devuelve el número de matriculas que ha tenido un alumno
    public static int devNumMat(String codAlu)
    {
        Connection        con              = null;
        PreparedStatement ps               = null;
        ResultSet         rs               = null;
        
        //String            sql              = "SELECT COUNT(IdAlu) AS EXPR1 FROM TbAluEdi WHERE(IdAlu = ?)";
        String            sql              = "SELECT COUNT(" + TablaAlumnosEdiciones.CODALU + ") AS MATRICULAS " +
                                             " FROM "        + TablaAlumnosEdiciones.TABLA  + 
                                             " WHERE "       + TablaAlumnosEdiciones.CODALU + " = ?";
        
        
        int               numeroMatriculas = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codAlu);

            rs = ps.executeQuery();

            if(rs.next())
            {
                numeroMatriculas = rs.getInt(1);
            }
            
            rs.close();
            ps.close();            
            Conexion.desconectar(con);

            return numeroMatriculas;
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

    //Método que devuelve los datos de alumno-edición de los alumnos que tienen recibo pendiente
    public static Vector devRecPendAluEdi()
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        /*String            sql         = "SELECT IdAlu, IdEdi " +
                                            "FROM TbAluEdi " +
                                        "WHERE esBaja = 0 AND esCong = 0 AND (IdEdi IN " +
                                                "(SELECT IdEdi " +
                                                 "FROM TBEdi " +
                                                 "WHERE (FecIn> ? ) AND (FecIn < ?) AND (FecFi > CURDATE()) AND (PrecioR > 0))) " +
                                         "ORDER BY IdEdi";
        */
        String            sql         = "SELECT "    + TablaAlumnosEdiciones.CODALU   + "," 
                                                     + TablaAlumnosEdiciones.CODEDI   + 
                                        " FROM "     + TablaAlumnosEdiciones.TABLA    +
                                        " WHERE "    + TablaAlumnosEdiciones.ESBAJA   + " = 0 AND "  
                                                     + TablaAlumnosEdiciones.ESCONGEL + " = 0 AND (" 
                                                     + TablaAlumnosEdiciones.CODEDI   + " IN ("       +
                                                      "SELECT "     + TablaEdiciones.CODEDI           +  
                                                      " FROM "      + TablaEdiciones.TABLA            +  
                                                      " WHERE ("    + TablaEdiciones.FECHAINICIO      +"> ? ) AND ("
                                                                    + TablaEdiciones.FECHAINICIO      +"< ? ) AND ("
                                                                    + TablaEdiciones.FECHAFIN         +"> CURDATE()) AND ("
                                                                    + TablaEdiciones.PRECIORECIBO     +"> 0))) "         +
                                        " ORDER BY " + TablaAlumnosEdiciones.CODEDI;
        
        String            mesSig      = "";
        String            mesAct      = "";
        
        int               mes         = new GregorianCalendar().get(Calendar.MONTH) + 2; //Se añade un mes y empiezan en 0
        int               anno        = new GregorianCalendar().get(Calendar.YEAR);

        Vector            listaAluEdi = new Vector();
        AluEdiVO          datAluEdi   = null;
        EdicionesVO       ediVO       = new EdicionesVO();

        if(mes>12)
        {
            mes = 1;
            anno = anno +1;
        }

        mesSig = "01/" + mes + "/" + anno;
        mesAct = "01/" + (new GregorianCalendar().get(Calendar.MONTH)+ 1) + "/" + new GregorianCalendar().get(Calendar.YEAR);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la sentencia sql
            ps.setDate(1, new Date(df.parse(mesAct).getTime()));
            ps.setDate(2, new Date(df.parse(mesSig).getTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu(rs.getString(TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi(rs.getString(TablaAlumnosEdiciones.CODEDI));

                ediVO = new EdicionesVO();
                ediVO = EdicionesDAO.devolverDatosEdi(datAluEdi.getIdEdi());

                //Se añade a la lista los datos de todos los alumnos con recibos mensuales y los correspondiente recibos a plazos
                if (! ediVO.isPlazos())
                {
                    listaAluEdi.addElement(datAluEdi);
                }
                else
                {
                    if ((mes ==  1 && ediVO.isEne())  ||
                        (mes ==  2 && ediVO.isFeb())  ||
                        (mes ==  3 && ediVO.isMar())  ||
                        (mes ==  4 && ediVO.isAbr())  ||
                        (mes ==  5 && ediVO.isMay())  ||
                        (mes ==  6 && ediVO.isJun())  ||
                        (mes ==  7 && ediVO.isJul())  ||
                        (mes ==  8 && ediVO.isAgo())  ||
                        (mes ==  9 && ediVO.isSep())  ||
                        (mes == 10 && ediVO.isOct())  ||
                        (mes == 11 && ediVO.isNov())  ||
                        (mes == 12 && ediVO.isDic()) )
                    {
                        listaAluEdi.addElement(datAluEdi);
                    }

                }

            }
            
            rs.close();
            ps.close();                       
            Conexion.desconectar(con);

            return listaAluEdi;
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

    //Método que devuelve los alumnos inscritos en un grupo
    public static Vector devAluGruMatEdi(EdicionesVO ediVO)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        /*String            sql         = "SELECT * FROM TbAluEdi WHERE esBaja= false AND IdEdi IN( SELECT IdEdi FROM TBEdi WHERE " +
                                                "IdCur    = ? AND " +
                                                "IdNiv    = ? AND " +
                                                "FecIn    = ? AND " +
                                                "FecFi    = ? AND " +
                                                "NumHor   = ? AND " +
                                                "HorIn    = ? AND " +
                                                "HorFin   = ? AND " +
                                                "HayLun   = ? AND " +
                                                "HayMar   = ? AND " +
                                                "HayMie   = ? AND " +
                                                "HayJue   = ? AND " +
                                                "HayVie   = ? AND " +
                                                "HaySab   = ? AND " +
                                                "idCen    = ? AND " +
                                                "MinIn    = ? AND " +
                                                "MinFin   = ? AND " +
                                                "HorDis   = ? AND " +
                                                "HorTelf  = ? AND " +
                                                "idAula   = ?) ";
        */
         String            sql         = "SELECT " + TablaAlumnosEdiciones.CODALU          + "," 
                                                   + TablaAlumnosEdiciones.CODEDI          + "," 
                                                   + TablaAlumnosEdiciones.FECHAALT        + "," 
                                                   + TablaAlumnosEdiciones.ESBAJA          + "," 
                                                   + TablaAlumnosEdiciones.OBSERV          + "," 
                                                   + TablaAlumnosEdiciones.NUMCUENTA       + "," 
                                                   + TablaAlumnosEdiciones.ESCONGEL        + 
                                         " FROM "  + TablaAlumnosEdiciones.TABLA           + 
                                         " WHERE " + TablaAlumnosEdiciones.ESBAJA          + " = false AND " 
                                                   + TablaAlumnosEdiciones.CODEDI          + " IN("  +
                                                   "SELECT " + TablaEdiciones.CODEDI       + 
                                                   " FROM "  + TablaEdiciones.TABLA        + 
                                                   " WHERE " + TablaEdiciones.CODCURSO     + " = ? AND "
                                                             + TablaEdiciones.CODNIVEL     + " = ? AND " 
                                                             + TablaEdiciones.FECHAINICIO  + " = ? AND " 
                                                             + TablaEdiciones.FECHAFIN     + " = ? AND " 
                                                             + TablaEdiciones.HORAS        + " = ? AND " 
                                                             + TablaEdiciones.HORAINICIO   + " = ? AND " 
                                                             + TablaEdiciones.HORAFIN      + " = ? AND " 
                                                             + TablaEdiciones.CLASELUNES   + " = ? AND " 
                                                             + TablaEdiciones.CLASEMARTES  + " = ? AND " 
                                                             + TablaEdiciones.CLASEMIERC   + " = ? AND " 
                                                             + TablaEdiciones.CLASEJUEVES  + " = ? AND " 
                                                             + TablaEdiciones.CLASEVIERNES + " = ? AND " 
                                                             + TablaEdiciones.CLASESABADO  + " = ? AND " 
                                                             + TablaEdiciones.CODCENTRO    + " = ? AND " 
                                                             + TablaEdiciones.MINUTOINICIO + " = ? AND " 
                                                             + TablaEdiciones.MINUTOFIN    + " = ? AND " 
                                                             + TablaEdiciones.HORASDISTAN  + " = ? AND "
                                                             + TablaEdiciones.HORASTELEF   + " = ? AND " 
                                                             + TablaEdiciones.CODAULA      + " = ?) ";
        Vector            listaAluEdi = new Vector();

        AluEdiVO          datAluEdi  = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la sentencia sql
            ps.setString ( 1, ediVO.getIdCur());
            ps.setString ( 2, ediVO.getIdNiv());
            ps.setDate   ( 3, new Date(ediVO.getFecIn().getTime()));
            ps.setDate   ( 4, new Date(ediVO.getFecFi().getTime()));
            ps.setInt    ( 5, ediVO.getNumHor());
            ps.setInt    ( 6, ediVO.getHorIn());
            ps.setInt    ( 7, ediVO.getHorFi());
            ps.setBoolean( 8, ediVO.isHayLun());
            ps.setBoolean( 9, ediVO.isHayMar());
            ps.setBoolean(10, ediVO.isHayMie());
            ps.setBoolean(11, ediVO.isHayJue());
            ps.setBoolean(12, ediVO.isHayVie());
            ps.setBoolean(13, ediVO.isHaySab());
            ps.setInt    (14, ediVO.getCodCen());
            ps.setInt    (15, ediVO.getMinIn());
            ps.setInt    (16, ediVO.getMinFin());
            ps.setInt    (17, ediVO.getHorDis());
            ps.setInt    (18, ediVO.getHorTelef());
            ps.setString (19, ediVO.getIdAula());

           rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu    (rs.getString (TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi    (rs.getString (TablaAlumnosEdiciones.CODEDI));
                datAluEdi.setFecAlta  (rs.getDate   (TablaAlumnosEdiciones.FECHAALT));
                datAluEdi.setEsBaja   (rs.getBoolean(TablaAlumnosEdiciones.ESBAJA));
                datAluEdi.setObserv   (rs.getString (TablaAlumnosEdiciones.OBSERV));
                datAluEdi.setNumCuenta(rs.getString (TablaAlumnosEdiciones.NUMCUENTA));
                datAluEdi.setEsCong   (rs.getBoolean(TablaAlumnosEdiciones.ESCONGEL));

                listaAluEdi.addElement(datAluEdi);
            }
            
            rs.close();
            ps.close();          
            Conexion.desconectar(con);

            return listaAluEdi;
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

    //Método que elimina los datos de alumno de una edición
    public static int eliminarDatosMatEdi(String codEdi)
    {
        Connection        con    = null;
        PreparedStatement ps     = null;

        //String            sql    = "DELETE FROM TbAluEdi WHERE IdEdi = ? ";
        String            sql    = "DELETE FROM " + TablaAlumnosEdiciones.TABLA  +
                                   " WHERE "      + TablaAlumnosEdiciones.CODEDI + " = ? ";
        
        
        int               devRes = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);

            devRes = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            return devRes;
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

    //Método que devuelve el número de alumnos inscritos en una edición
    public static int devNumAluEdi(String codEdi)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT COUNT(IdEdi) FROM TbAluEdi WHERE IdEdi = ? AND esBaja = false";
        String            sql        = "SELECT COUNT(" + TablaAlumnosEdiciones.CODEDI + ")" +
                                       " FROM "        + TablaAlumnosEdiciones.TABLA  +
                                       " WHERE "       + TablaAlumnosEdiciones.CODEDI + " = ? AND "
                                                       + TablaAlumnosEdiciones.ESBAJA + " = false";
        
        int               numAlumnos = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la conuslta sql
            ps.setString(1, codEdi);

            rs = ps.executeQuery();

            if(rs.next())
            {
                numAlumnos = rs.getInt(1);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return numAlumnos;
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

    //Método que devuelve los alumnos inscritos en una edición dados de baja
    public static Vector devAluMatBajEdi(String codEdi)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        //String            sql         = "SELECT * FROM TbAluEdi INNER JOIN TbAlu ON TbAluEdi.IdAlu = TbAlu.IdAlu WHERE IdEdi = ?" +
        //                                     "AND esBaja = true ORDER BY TbAlu.Ap1Alu";
        String            sql         = "SELECT "     + TablaAlumnosEdiciones.TABLA + "." + TablaAlumnosEdiciones.CODALU      + "," 
                                                                                          + TablaAlumnosEdiciones.CODEDI      + "," 
                                                                                          + TablaAlumnosEdiciones.FECHAALT    + "," 
                                                                                          + TablaAlumnosEdiciones.ESBAJA      + "," 
                                                                                          + TablaAlumnosEdiciones.OBSERV      + "," 
                                                                                          + TablaAlumnosEdiciones.NUMCUENTA   + "," 
                                                                                          + TablaAlumnosEdiciones.ESCONGEL    + 
                                       " FROM "       + TablaAlumnosEdiciones.TABLA       + 
                                            " INNER JOIN " + TablaAlumnos.TABLA + 
                                            " ON "         + TablaAlumnosEdiciones.TABLA + "." + TablaAlumnosEdiciones.CODALU + " = " + TablaAlumnos.TABLA + "." + TablaAlumnos.CODALU + 
                                       " WHERE "      + TablaAlumnosEdiciones.CODEDI      + " = ? AND " 
                                                      + TablaAlumnosEdiciones.ESBAJA      + " = true " +
                                       " ORDER BY "   + TablaAlumnos.TABLA + "." + TablaAlumnos.APE1;
        
        
        Vector           listaAluEdi  = new Vector();

        AluEdiVO         datAluEdi    = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);
            rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu    (rs.getString (TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi    (rs.getString (TablaAlumnosEdiciones.CODEDI));
                datAluEdi.setFecAlta  (rs.getDate   (TablaAlumnosEdiciones.FECHAALT));
                datAluEdi.setEsBaja   (rs.getBoolean(TablaAlumnosEdiciones.ESBAJA));
                datAluEdi.setObserv   (rs.getString (TablaAlumnosEdiciones.OBSERV));
                datAluEdi.setNumCuenta(rs.getString (TablaAlumnosEdiciones.NUMCUENTA));
                datAluEdi.setEsCong   (rs.getBoolean(TablaAlumnosEdiciones.ESCONGEL));

                listaAluEdi.addElement(datAluEdi);
            }
            
            rs.close();
            ps.close();           
            Conexion.desconectar(con);

            return listaAluEdi;
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

    //Método que devuelve los alumnos de baja en un grupo
    public static Vector devAluBajGruMatEdi(EdicionesVO ediVO)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        /*String            sql         = "SELECT * FROM TbAluEdi WHERE esBaja = true AND IdEdi IN( SELECT IdEdi FROM TBEdi WHERE " +
                                            "IdCur   = ? AND " +
                                            "IdNiv   = ? AND " +
                                            "FecIn   = ? AND " +
                                            "FecFi   = ? AND " +
                                            "NumHor  = ? AND " +
                                            "HorIn   = ? AND " +
                                            "HorFin  = ? AND " +
                                            "HayLun  = ? AND " +
                                            "HayMar  = ? AND " +
                                            "HayMie  = ? AND " +
                                            "HayJue  = ? AND " +
                                            "HayVie  = ? AND " +
                                            "HaySab  = ? AND " +
                                            "idCen   = ? AND " +
                                            "MinIn   = ? AND " +
                                            "MinFin  = ? AND " +
                                            "HorDis  = ? AND " +
                                            "HorTelf = ? AND " +
                                            "idAula  = ? ) ";
        */
        
        String            sql         = "SELECT " + TablaAlumnosEdiciones.CODALU      + "," 
                                                  + TablaAlumnosEdiciones.CODEDI      + "," 
                                                  + TablaAlumnosEdiciones.FECHAALT    + "," 
                                                  + TablaAlumnosEdiciones.ESBAJA      + "," 
                                                  + TablaAlumnosEdiciones.OBSERV      + "," 
                                                  + TablaAlumnosEdiciones.NUMCUENTA   + "," 
                                                  + TablaAlumnosEdiciones.ESCONGEL    + 
                                       " FROM "   + TablaAlumnosEdiciones.TABLA       +
                                       " WHERE "  + TablaAlumnosEdiciones.ESBAJA      + " = true AND "  
                                                  + TablaAlumnosEdiciones.CODEDI      + " IN ("       +
                                                  "SELECT "  + TablaEdiciones.CODEDI       + 
                                                   " FROM "  + TablaEdiciones.TABLA        + 
                                                   " WHERE " + TablaEdiciones.CODCURSO     + " = ? AND "
                                                             + TablaEdiciones.CODNIVEL     + " = ? AND " 
                                                             + TablaEdiciones.FECHAINICIO  + " = ? AND " 
                                                             + TablaEdiciones.FECHAFIN     + " = ? AND " 
                                                             + TablaEdiciones.HORAS        + " = ? AND " 
                                                             + TablaEdiciones.HORAINICIO   + " = ? AND " 
                                                             + TablaEdiciones.HORAFIN      + " = ? AND " 
                                                             + TablaEdiciones.CLASELUNES   + " = ? AND " 
                                                             + TablaEdiciones.CLASEMARTES  + " = ? AND " 
                                                             + TablaEdiciones.CLASEMIERC   + " = ? AND " 
                                                             + TablaEdiciones.CLASEJUEVES  + " = ? AND " 
                                                             + TablaEdiciones.CLASEVIERNES + " = ? AND " 
                                                             + TablaEdiciones.CLASESABADO  + " = ? AND " 
                                                             + TablaEdiciones.CODCENTRO    + " = ? AND " 
                                                             + TablaEdiciones.MINUTOINICIO + " = ? AND " 
                                                             + TablaEdiciones.MINUTOFIN    + " = ? AND " 
                                                             + TablaEdiciones.HORASDISTAN  + " = ? AND "
                                                             + TablaEdiciones.HORASTELEF   + " = ? AND " 
                                                             + TablaEdiciones.CODAULA      + " = ?) ";
                
        Vector            listaAluEdi = new Vector();

        AluEdiVO          datAluEdi   = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros de la consulta sql
            ps.setString ( 1, ediVO.getIdCur());
            ps.setString ( 2, ediVO.getIdNiv());
            ps.setDate   ( 3, new Date(ediVO.getFecIn().getTime()));
            ps.setDate   ( 4, new Date(ediVO.getFecFi().getTime()));
            ps.setInt    ( 5, ediVO.getNumHor());
            ps.setInt    ( 6, ediVO.getHorIn());
            ps.setInt    ( 7, ediVO.getHorFi());
            ps.setBoolean( 8, ediVO.isHayLun());
            ps.setBoolean( 9, ediVO.isHayMar());
            ps.setBoolean(10, ediVO.isHayMie());
            ps.setBoolean(11, ediVO.isHayJue());
            ps.setBoolean(12, ediVO.isHayVie());
            ps.setBoolean(13, ediVO.isHaySab());
            ps.setInt    (14, ediVO.getCodCen());
            ps.setInt    (15, ediVO.getMinIn());
            ps.setInt    (16, ediVO.getMinFin());
            ps.setInt    (17, ediVO.getHorDis());
            ps.setInt    (18, ediVO.getHorTelef());
            ps.setString (19, ediVO.getIdAula());

            rs = ps.executeQuery();

            while (rs.next())
            {
                datAluEdi = new AluEdiVO();

                datAluEdi.setIdAlu    (rs.getString (TablaAlumnosEdiciones.CODALU));
                datAluEdi.setIdEdi    (rs.getString (TablaAlumnosEdiciones.CODEDI));
                datAluEdi.setFecAlta  (rs.getDate   (TablaAlumnosEdiciones.FECHAALT));
                datAluEdi.setEsBaja   (rs.getBoolean(TablaAlumnosEdiciones.ESBAJA));
                datAluEdi.setObserv   (rs.getString (TablaAlumnosEdiciones.OBSERV));
                datAluEdi.setNumCuenta(rs.getString (TablaAlumnosEdiciones.NUMCUENTA));
                datAluEdi.setEsCong   (rs.getBoolean(TablaAlumnosEdiciones.ESCONGEL));

                listaAluEdi.addElement(datAluEdi);
            }
            
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAluEdi;

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
    
    //Método que devuelve si una edición tiene alumunos
    public static boolean edicionTieneAlumnos(String codEdi)
    {
        Connection        con         = null;
        PreparedStatement ps          = null;
        ResultSet         rs          = null;

        //String            sql         = "SELECT * FROM TbAluEdi WHERE IdAlu = ? ";
        String            sql         = "SELECT " + TablaAlumnosEdiciones.CODALU    +                                                      
                                        " FROM "  + TablaAlumnosEdiciones.TABLA     + 
                                        " WHERE " + TablaAlumnosEdiciones.CODEDI    + " = ? ";
        
        
        boolean           hayAlumnos  = false;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codEdi);

            rs = ps.executeQuery();

            if (rs.next())
            {
                hayAlumnos = true;
            }
            else
            {   
                hayAlumnos = false;
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return hayAlumnos;

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
