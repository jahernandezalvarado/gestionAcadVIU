/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.DatosCmbEmp;
import es.jahernandez.datos.EmpresasVO;

import es.jahernandez.tablas.TablaAlumnos;
import es.jahernandez.tablas.TablaEmpresas;

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
public class EmpresasDAO
{
    //Método que devuelve los datos de todas las empresas
    public static Vector devolverTodosEmp()
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            sql            = "SELECT * FROM TbEmp";
        String            sql            = "SELECT " + TablaEmpresas.CODEMPRESA    + " , "
                                                     + TablaEmpresas.NOMBRE        + " , "
                                                     + TablaEmpresas.CIF           + " , "
                                                     + TablaEmpresas.DIRECCION     + " , "
                                                     + TablaEmpresas.CODPOSTAL     + " , "
                                                     + TablaEmpresas.POBLACION     + " , "
                                                     + TablaEmpresas.CODPROVINCIA  + " , "
                                                     + TablaEmpresas.TELEFONO      + " , "
                                                     + TablaEmpresas.FAX           + " , "
                                                     + TablaEmpresas.MAIL          + " , "
                                                     + TablaEmpresas.RESPONSABLE   + " , "
                                                     + TablaEmpresas.CODSEC        + " , "
                                                     + TablaEmpresas.NOMBRECOMER   + " , "
                                                     + TablaEmpresas.CNAE          + " , "
                                                     + TablaEmpresas.EMPLEADOS     + " , "
                                                     + TablaEmpresas.VOLNEGOCIO    + " , "
                                                     + TablaEmpresas.IMPORTEXPORT  + " , "
                                                     + TablaEmpresas.CARGO1        + " , "
                                                     + TablaEmpresas.CARGO2        + " , "
                                                     + TablaEmpresas.RESPONSABLE2  + " , "
                                                     + TablaEmpresas.CONVEADSCRIT  + " , "
                                                     + TablaEmpresas.FECHACONVEN   + " , "
                                                     + TablaEmpresas.DATOSACTUAL   + " , "
                                                     + TablaEmpresas.CUOTA         + " , "                                                     
                                                     + TablaEmpresas.ESCLIENTE     + " , "
                                                     + TablaEmpresas.DIRECCOMER    + " , "
                                                     + TablaEmpresas.POBLACIONCOM  + " , "
                                                     + TablaEmpresas.CODPROVINCOM  + " , "
                                                     + TablaEmpresas.CODPOSTCOM    + " , "
                                                     + TablaEmpresas.AUTORIZCESDAT + " , "
                                                     + TablaEmpresas.ACCESOARCO    + " , "
                                                     + TablaEmpresas.FECACCCARCO   + " , "
                                                     + TablaEmpresas.RECHAZOARCO   + " , "
                                                     + TablaEmpresas.FECRECARCO    + " , "
                                                     + TablaEmpresas.CANCELARCO    + " , "
                                                     + TablaEmpresas.FECCANARCO    + " , "
                                                     + TablaEmpresas.OPOSICARCO    + " , "
                                                     + TablaEmpresas.FECOPOARCO    +            
                                           " FROM "  + TablaEmpresas.TABLA;                             
        
        Vector            listaEmpresas  = new Vector();

        EmpresasVO        datEmp         = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEmp = new EmpresasVO();

                datEmp.setIdEmpresa    (rs.getString (TablaEmpresas.CODEMPRESA));
                datEmp.setNombreEmpresa(rs.getString (TablaEmpresas.NOMBRE));
                datEmp.setCifEmpresa   (rs.getString (TablaEmpresas.CIF));
                datEmp.setDirEmpresa   (rs.getString (TablaEmpresas.DIRECCION));
                datEmp.setCodPostal    (rs.getString (TablaEmpresas.CODPOSTAL));
                datEmp.setPobEmpresa   (rs.getString (TablaEmpresas.POBLACION));
                datEmp.setCodProv      (rs.getString (TablaEmpresas.CODPROVINCIA));
                datEmp.setTelEmpresa   (rs.getString (TablaEmpresas.TELEFONO));
                datEmp.setFaxEmpresa   (rs.getString (TablaEmpresas.FAX));
                datEmp.setMailEmpresa  (rs.getString (TablaEmpresas.MAIL));
                datEmp.setResEmpresa   (rs.getString (TablaEmpresas.RESPONSABLE));
                datEmp.setCodAct       (rs.getInt    (TablaEmpresas.CODSEC));
                datEmp.setNomComercial (rs.getString (TablaEmpresas.NOMBRECOMER));
                datEmp.setCnae         (rs.getInt    (TablaEmpresas.CNAE));
                datEmp.setNumEmp       (rs.getInt    (TablaEmpresas.EMPLEADOS));
                datEmp.setVolNeg       (rs.getString (TablaEmpresas.VOLNEGOCIO));
                datEmp.setImpExp       (rs.getString (TablaEmpresas.IMPORTEXPORT));
                datEmp.setCar1         (rs.getString (TablaEmpresas.CARGO1));
                datEmp.setCar2         (rs.getString (TablaEmpresas.CARGO2));
                datEmp.setResEmp2      (rs.getString (TablaEmpresas.RESPONSABLE2));
                datEmp.setConvenioAd   (rs.getBoolean(TablaEmpresas.CONVEADSCRIT));
                datEmp.setFecCon       (rs.getDate   (TablaEmpresas.FECHACONVEN));
                datEmp.setDatAct       (rs.getBoolean(TablaEmpresas.DATOSACTUAL));
                datEmp.setCuota        (rs.getInt    (TablaEmpresas.CUOTA));
                datEmp.setEsCliente    (rs.getBoolean(TablaEmpresas.ESCLIENTE));
                datEmp.setDirCom       (rs.getString (TablaEmpresas.DIRECCOMER));
                datEmp.setPobCom       (rs.getString (TablaEmpresas.POBLACIONCOM));
                datEmp.setProCom       (rs.getString (TablaEmpresas.CODPROVINCOM));
                datEmp.setCodPosCom    (rs.getString (TablaEmpresas.CODPOSTCOM));
                datEmp.setAutCesDat    (rs.getBoolean(TablaEmpresas.AUTORIZCESDAT));
                datEmp.setAccArco      (rs.getBoolean(TablaEmpresas.ACCESOARCO));
                datEmp.setFecAccArc    (rs.getDate   (TablaEmpresas.FECACCCARCO));
                datEmp.setRecArco      (rs.getBoolean(TablaEmpresas.RECHAZOARCO));
                datEmp.setFecRecArc    (rs.getDate   (TablaEmpresas.FECRECARCO));
                datEmp.setCanArco      (rs.getBoolean(TablaEmpresas.CANCELARCO));
                datEmp.setFecCanArc    (rs.getDate   (TablaEmpresas.FECCANARCO));
                datEmp.setOpoArco      (rs.getBoolean(TablaEmpresas.OPOSICARCO));
                datEmp.setFecOpoArc    (rs.getDate   (TablaEmpresas.FECOPOARCO));

                listaEmpresas.addElement(datEmp);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaEmpresas;
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

    //Devuelve el VALOR MAXIMO de codigo EMPRESA
    public static String devuelveMaxEmp()
    {
        Connection        con          = null;
        PreparedStatement ps           = null;
        ResultSet         rs           = null;
     
        //String            sql          = "SELECT MAX(IdEmp) FROM TbEmp";
        String            sql          = "SELECT MAX(" + TablaEmpresas.CODEMPRESA + ")" + 
                                         " FROM  "     + TablaEmpresas.TABLA;

        String            strMaxCodEmp = "";
        
        try
        {

            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();

            if(rs.next()) strMaxCodEmp = rs.getString(1);

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return strMaxCodEmp;
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

    //Guarda un registro con los datos de EMPRESA
    public static String guardaEmp(EmpresasVO empVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        
        int               regActualizados = 0;       

        /*String            sql             = "INSERT INTO TbEmp (IdEmp,NomEmp,CifEmp,dirEmp,codPost,Poblacion,codProv,TelEmp,FaxEmp,mail,ResEmp,IdSec, " +
                                                    "NomCom,CNAE,NumEmp,VolNeg,ImpExp,Car1,Car2,ResEmp2,ConAd,FecCon,DatAct,Cuota,EsCli, " +
                                                    "dirCom,pobCom,proCom,codPosCom,autCesDat,accArco,fecAccArc,recArco,fecRecArc,canArco, " +
                                                    "fecCanArc,opoArco,fecOpoArc) " +
                                            "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";*/

        String            sql             = "INSERT INTO " + TablaEmpresas.TABLA         + " ( "  
                                                           + TablaEmpresas.CODEMPRESA    + " , "
                                                           + TablaEmpresas.NOMBRE        + " , "
                                                           + TablaEmpresas.CIF           + " , "
                                                           + TablaEmpresas.DIRECCION     + " , "
                                                           + TablaEmpresas.CODPOSTAL     + " , "
                                                           + TablaEmpresas.POBLACION     + " , "
                                                           + TablaEmpresas.CODPROVINCIA  + " , "
                                                           + TablaEmpresas.TELEFONO      + " , "
                                                           + TablaEmpresas.FAX           + " , "
                                                           + TablaEmpresas.MAIL          + " , "
                                                           + TablaEmpresas.RESPONSABLE   + " , "
                                                           + TablaEmpresas.CODSEC        + " , "
                                                           + TablaEmpresas.NOMBRECOMER   + " , "
                                                           + TablaEmpresas.CNAE          + " , "
                                                           + TablaEmpresas.EMPLEADOS     + " , "
                                                           + TablaEmpresas.VOLNEGOCIO    + " , "
                                                           + TablaEmpresas.IMPORTEXPORT  + " , "
                                                           + TablaEmpresas.CARGO1        + " , "
                                                           + TablaEmpresas.CARGO2        + " , "
                                                           + TablaEmpresas.RESPONSABLE2  + " , "
                                                           + TablaEmpresas.CONVEADSCRIT  + " , "
                                                           + TablaEmpresas.FECHACONVEN   + " , "
                                                           + TablaEmpresas.DATOSACTUAL   + " , "
                                                           + TablaEmpresas.CUOTA         + " , "
                                                           + TablaEmpresas.ESCLIENTE     + " , "
                                                           + TablaEmpresas.DIRECCOMER    + " , "
                                                           + TablaEmpresas.POBLACIONCOM  + " , "
                                                           + TablaEmpresas.CODPROVINCOM  + " , "
                                                           + TablaEmpresas.CODPOSTCOM    + " , "
                                                           + TablaEmpresas.AUTORIZCESDAT + " , "
                                                           + TablaEmpresas.ACCESOARCO    + " , "
                                                           + TablaEmpresas.FECACCCARCO   + " , "
                                                           + TablaEmpresas.RECHAZOARCO   + " , "
                                                           + TablaEmpresas.FECRECARCO    + " , "
                                                           + TablaEmpresas.CANCELARCO    + " , "
                                                           + TablaEmpresas.FECCANARCO    + " , "
                                                           + TablaEmpresas.OPOSICARCO    + " , "
                                                           + TablaEmpresas.FECOPOARCO    + " ) " +
                                            "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            empVO.setIdEmpresa(EmpresasDAO.generarNuevoCodEmp());

            //Se introducen los valores correspondientes en la sentencia sql
            ps.setString ( 1, empVO.getIdEmpresa());
            ps.setString ( 2, empVO.getNombreEmpresa());
            ps.setString ( 3, empVO.getCifEmpresa());
            ps.setString ( 4, empVO.getDirEmpresa());
            ps.setString ( 5, empVO.getCodPostal());
            ps.setString ( 6, empVO.getPobEmpresa());
            ps.setString ( 7, empVO.getCodProv());
            ps.setString ( 8, empVO.getTelEmpresa());
            ps.setString ( 9, empVO.getFaxEmpresa());
            ps.setString (10,empVO.getMailEmpresa());
            ps.setString (11, empVO.getResEmpresa());
            ps.setInt    (12, empVO.getCodAct());
            ps.setString (13, empVO.getNomComercial());
            ps.setInt    (14, empVO.getCnae());
            ps.setInt    (15, empVO.getNumEmp());
            ps.setString (16, empVO.getVolNeg());
            ps.setString (17, empVO.getImpExp());
            ps.setString (18, empVO.getCar1());
            ps.setString (19, empVO.getCar2());
            ps.setString (20, empVO.getResEmpresa());
            ps.setBoolean(21, empVO.isConvenioAd());
            ps.setDate(22, new Date(empVO.getFecCon().getTime()));
            ps.setBoolean(23, empVO.isDatAct());
            ps.setInt(24, empVO.getCuota());
            ps.setBoolean(25, empVO.isEsCliente());
            ps.setString(26, empVO.getDirCom());
            ps.setString(27, empVO.getPobCom());
            ps.setString(28, empVO.getProCom());
            ps.setString(29, empVO.getCodPosCom());
            ps.setBoolean(30, empVO.isAutCesDat());
            ps.setBoolean(31, empVO.isAccArco());
            ps.setDate(32, new Date(empVO.getFecAccArc().getTime()));
            ps.setBoolean(33, empVO.isRecArco());            
            ps.setDate(34, new Date(empVO.getFecRecArc().getTime()));
            ps.setBoolean(35, empVO.isCanArco());
            ps.setDate(36, new Date(empVO.getFecCanArc().getTime()));
            ps.setBoolean(37, empVO.isOpoArco());
            ps.setDate(38, new Date(empVO.getFecOpoArc().getTime()));
            
            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            if(regActualizados>0)
            {
                return  empVO.getIdEmpresa(); //regActualizados;
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

            return "-1";
        }

    }

    //Método que genera un código nuevo de empresa
    public static String generarNuevoCodEmp1()
    {
        boolean enc = true;
        int     avc = 1;
        int     contCar;
        String  codIntrod = "";

        Vector  datEmp = devolverTodosEmp();


        while (enc)
        {
            contCar = new Integer(datEmp.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datEmp.size() + avc).toString();
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
            datEmp = devolverTodosEmp();
            for (int ind = 0; ind < datEmp.size(); ind++)
            {
                EmpresasVO empVO = (EmpresasVO) datEmp.elementAt(ind);
                if (empVO.getIdEmpresa().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Método que genera un código nuevo de empresa
     public static String generarNuevoCodEmp()
    {
        Connection        con      = null;
        PreparedStatement ps       = null;
        ResultSet         rs       = null;

        String            sql      = "SELECT MAX(" + TablaEmpresas.CODEMPRESA + ") + 1 FROM "  + TablaEmpresas.TABLA;

        String            nuevoCod = "";

        try
        {

            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();
            
            if(rs.next())
            {
                nuevoCod = new Integer(rs.getInt(1)).toString();
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            
            nuevoCod = nuevoCod.trim();

            while (nuevoCod.length() < 8)
            {
                nuevoCod = "0" + nuevoCod;
            }
            
            
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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }


    }

    
    //Método que devuelve los datos de empresa que se cargan en los combos
    public static Vector devolverDatosEmpCom()
    {
        Connection        con           = null;
        PreparedStatement ps            = null;
        ResultSet         rs            = null;

        //String            sql           = "SELECT IdEmp,concat(NomEmp , ' ' , NomCom) as NomCom FROM TbEmp ORDER BY NomEmp";
        //String            sql           = "SELECT "    + TablaEmpresas.CODEMPRESA +  " , "  
        //                                               + "concat(" +  TablaEmpresas.NOMBRE + ",' '," + TablaEmpresas.NOMBRECOMER + ") as NomCom " +
        //                                  " FROM "     + TablaEmpresas.TABLA +
        //                                  " ORDER BY " + TablaEmpresas.NOMBRE;
        
        String            sql           = "SELECT " + TablaEmpresas.CODEMPRESA + "," + 
                                                    "IF(trim(" + TablaEmpresas.NOMBRE + ")<>''," + TablaEmpresas.NOMBRE + "," + TablaEmpresas.NOMBRECOMER + ") as NOMBRE" + 
                                          " FROM " + TablaEmpresas.TABLA + 
                                          " ORDER BY NOMBRE";
        
        
        Vector            listaEmpresas = new Vector();

        DatosCmbEmp       datEmp        = null;
    
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEmp = new DatosCmbEmp();

                datEmp.setIdEmp  (rs.getString(TablaEmpresas.CODEMPRESA));
                datEmp.setNombEmp(rs.getString("NOMBRE"));                                                             
                
                listaEmpresas.addElement(datEmp);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaEmpresas;
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

    //Método que devuelve los datos de una empresa
    public static EmpresasVO devolverDatEmp(String codEmp)
    {

        Connection        con    = null;
        PreparedStatement ps     = null;
        ResultSet         rs     = null;

        //String            sql    = "SELECT * FROM TbEmp WHERE IdEmp = ? ";
        String            sql    = "SELECT " + TablaEmpresas.CODEMPRESA    + " , "
                                             + TablaEmpresas.NOMBRE        + " , "
                                             + TablaEmpresas.CIF           + " , "
                                             + TablaEmpresas.DIRECCION     + " , "
                                             + TablaEmpresas.CODPOSTAL     + " , "
                                             + TablaEmpresas.POBLACION     + " , "
                                             + TablaEmpresas.CODPROVINCIA  + " , "
                                             + TablaEmpresas.TELEFONO      + " , "
                                             + TablaEmpresas.FAX           + " , "
                                             + TablaEmpresas.MAIL          + " , "
                                             + TablaEmpresas.RESPONSABLE   + " , "
                                             + TablaEmpresas.CODSEC        + " , "
                                             + TablaEmpresas.NOMBRECOMER   + " , "
                                             + TablaEmpresas.CNAE          + " , "
                                             + TablaEmpresas.EMPLEADOS     + " , "
                                             + TablaEmpresas.VOLNEGOCIO    + " , "
                                             + TablaEmpresas.IMPORTEXPORT  + " , "
                                             + TablaEmpresas.CARGO1        + " , "
                                             + TablaEmpresas.CARGO2        + " , "
                                             + TablaEmpresas.RESPONSABLE2  + " , "
                                             + TablaEmpresas.CONVEADSCRIT  + " , "
                                             + TablaEmpresas.FECHACONVEN   + " , "
                                             + TablaEmpresas.DATOSACTUAL   + " , "
                                             + TablaEmpresas.CUOTA         + " , "                                                     
                                             + TablaEmpresas.ESCLIENTE     + " , "
                                             + TablaEmpresas.DIRECCOMER    + " , "
                                             + TablaEmpresas.POBLACIONCOM  + " , "
                                             + TablaEmpresas.CODPROVINCOM  + " , "
                                             + TablaEmpresas.CODPOSTCOM    + " , "
                                             + TablaEmpresas.AUTORIZCESDAT + " , "
                                             + TablaEmpresas.ACCESOARCO    + " , "
                                             + TablaEmpresas.FECACCCARCO   + " , "
                                             + TablaEmpresas.RECHAZOARCO   + " , "
                                             + TablaEmpresas.FECRECARCO    + " , "
                                             + TablaEmpresas.CANCELARCO    + " , "
                                             + TablaEmpresas.FECCANARCO    + " , "
                                             + TablaEmpresas.OPOSICARCO    + " , "
                                             + TablaEmpresas.FECOPOARCO    +            
                                   " FROM "  + TablaEmpresas.TABLA         +  
                                   " WHERE " + TablaEmpresas.CODEMPRESA    +" = ? ";
        
        
        
        EmpresasVO        datEmp = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se le pasan los parámetros a la consulta sql
            ps.setString(1, codEmp);

            rs  = ps.executeQuery();

            if(rs.next())
            {
                datEmp = new EmpresasVO();

                datEmp.setIdEmpresa    (rs.getString (TablaEmpresas.CODEMPRESA));
                datEmp.setNombreEmpresa(rs.getString (TablaEmpresas.NOMBRE));
                datEmp.setCifEmpresa   (rs.getString (TablaEmpresas.CIF));
                datEmp.setDirEmpresa   (rs.getString (TablaEmpresas.DIRECCION));
                datEmp.setCodPostal    (rs.getString (TablaEmpresas.CODPOSTAL));
                datEmp.setPobEmpresa   (rs.getString (TablaEmpresas.POBLACION));
                datEmp.setCodProv      (rs.getString (TablaEmpresas.CODPROVINCIA));
                datEmp.setTelEmpresa   (rs.getString (TablaEmpresas.TELEFONO));
                datEmp.setFaxEmpresa   (rs.getString (TablaEmpresas.FAX));
                datEmp.setMailEmpresa  (rs.getString (TablaEmpresas.MAIL));
                datEmp.setResEmpresa   (rs.getString (TablaEmpresas.RESPONSABLE));
                datEmp.setCodAct       (rs.getInt    (TablaEmpresas.CODSEC));
                datEmp.setNomComercial (rs.getString (TablaEmpresas.NOMBRECOMER));
                datEmp.setCnae         (rs.getInt    (TablaEmpresas.CNAE));
                datEmp.setNumEmp       (rs.getInt    (TablaEmpresas.EMPLEADOS));
                datEmp.setVolNeg       (rs.getString (TablaEmpresas.VOLNEGOCIO));
                datEmp.setImpExp       (rs.getString (TablaEmpresas.IMPORTEXPORT));
                datEmp.setCar1         (rs.getString (TablaEmpresas.CARGO1));
                datEmp.setCar2         (rs.getString (TablaEmpresas.CARGO2));
                datEmp.setResEmp2      (rs.getString (TablaEmpresas.RESPONSABLE2));
                datEmp.setConvenioAd   (rs.getBoolean(TablaEmpresas.CONVEADSCRIT));
                datEmp.setFecCon       (rs.getDate   (TablaEmpresas.FECHACONVEN));
                datEmp.setDatAct       (rs.getBoolean(TablaEmpresas.DATOSACTUAL));
                datEmp.setCuota        (rs.getInt    (TablaEmpresas.CUOTA));
                datEmp.setEsCliente    (rs.getBoolean(TablaEmpresas.ESCLIENTE));
                datEmp.setDirCom       (rs.getString (TablaEmpresas.DIRECCOMER));
                datEmp.setPobCom       (rs.getString (TablaEmpresas.POBLACIONCOM));
                datEmp.setProCom       (rs.getString (TablaEmpresas.CODPROVINCOM));
                datEmp.setCodPosCom    (rs.getString (TablaEmpresas.CODPOSTCOM));
                datEmp.setAutCesDat    (rs.getBoolean(TablaEmpresas.AUTORIZCESDAT));
                datEmp.setAccArco      (rs.getBoolean(TablaEmpresas.ACCESOARCO));
                datEmp.setFecAccArc    (rs.getDate   (TablaEmpresas.FECACCCARCO));
                datEmp.setRecArco      (rs.getBoolean(TablaEmpresas.RECHAZOARCO));
                datEmp.setFecRecArc    (rs.getDate   (TablaEmpresas.FECRECARCO));
                datEmp.setCanArco      (rs.getBoolean(TablaEmpresas.CANCELARCO));
                datEmp.setFecCanArc    (rs.getDate   (TablaEmpresas.FECCANARCO));
                datEmp.setOpoArco      (rs.getBoolean(TablaEmpresas.OPOSICARCO));
                datEmp.setFecOpoArc    (rs.getDate   (TablaEmpresas.FECOPOARCO));

            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);


            return datEmp;

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

    //Método que realiza busqueda de empresas
    public static Vector buscarEmpresas(EmpresasVO conBusVO)
    {
        //String            sql       = "SELECT * FROM TbEmp";
        String            sql       = "SELECT " + TablaEmpresas.CODEMPRESA    + " , "
                                               + TablaEmpresas.NOMBRE        + " , "
                                               + TablaEmpresas.CIF           + " , "
                                               + TablaEmpresas.DIRECCION     + " , "
                                               + TablaEmpresas.CODPOSTAL     + " , "
                                               + TablaEmpresas.POBLACION     + " , "
                                               + TablaEmpresas.CODPROVINCIA  + " , "
                                               + TablaEmpresas.TELEFONO      + " , "
                                               + TablaEmpresas.FAX           + " , "
                                               + TablaEmpresas.MAIL          + " , "
                                               + TablaEmpresas.RESPONSABLE   + " , "
                                               + TablaEmpresas.CODSEC        + " , "
                                               + TablaEmpresas.NOMBRECOMER   + " , "
                                               + TablaEmpresas.CNAE          + " , "
                                               + TablaEmpresas.EMPLEADOS     + " , "
                                               + TablaEmpresas.VOLNEGOCIO    + " , "
                                               + TablaEmpresas.IMPORTEXPORT  + " , "
                                               + TablaEmpresas.CARGO1        + " , "
                                               + TablaEmpresas.CARGO2        + " , "
                                               + TablaEmpresas.RESPONSABLE2  + " , "
                                               + TablaEmpresas.CONVEADSCRIT  + " , "
                                               + TablaEmpresas.FECHACONVEN   + " , "
                                               + TablaEmpresas.DATOSACTUAL   + " , "
                                               + TablaEmpresas.CUOTA         + " , "                                                     
                                               + TablaEmpresas.ESCLIENTE     + " , "
                                               + TablaEmpresas.DIRECCOMER    + " , "
                                               + TablaEmpresas.POBLACIONCOM  + " , "
                                               + TablaEmpresas.CODPROVINCOM  + " , "
                                               + TablaEmpresas.CODPOSTCOM    + " , "
                                               + TablaEmpresas.AUTORIZCESDAT + " , "
                                               + TablaEmpresas.ACCESOARCO    + " , "
                                               + TablaEmpresas.FECACCCARCO   + " , "
                                               + TablaEmpresas.RECHAZOARCO   + " , "
                                               + TablaEmpresas.FECRECARCO    + " , "
                                               + TablaEmpresas.CANCELARCO    + " , "
                                               + TablaEmpresas.FECCANARCO    + " , "
                                               + TablaEmpresas.OPOSICARCO    + " , "
                                               + TablaEmpresas.FECOPOARCO    +            
                                      " FROM " + TablaEmpresas.TABLA;  
        
        Vector            listBusq  = new Vector();

        Connection        con       = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        EmpresasVO        datEmp    = null;

        conBusVO.setNombreEmpresa(conBusVO.getNombreEmpresa().trim());
        conBusVO.setNomComercial(conBusVO.getNomComercial().trim());
        conBusVO.setPobEmpresa(conBusVO.getPobEmpresa().trim());


        if(  conBusVO.getNombreEmpresa().equals("") &&
             conBusVO.getNomComercial().equals("")  &&
             conBusVO.getCodAct()== -1              &&
             conBusVO.getPobEmpresa().equals("")    &&
             conBusVO.getCnae() == 0                &&
             conBusVO.getImpExp().equals("----")    &&
             ! conBusVO.isEsCliente())
        {
            //No hay condición de búsqueda
            sql = sql + " WHERE " +  TablaEmpresas.CODEMPRESA  + " <> '00000000'";
        }
        else
        {
            sql = sql + " WHERE " + TablaEmpresas.CODEMPRESA   + " <> '00000000' AND ";
            //Se construye la condición de búsqueda

            if (! conBusVO.getNombreEmpresa().equals(""))
            {
                sql = sql + TablaEmpresas.NOMBRE       + " LIKE '%" + conBusVO.getNombreEmpresa() + "%' AND ";
            }

            if (! conBusVO.getNomComercial().equals(""))
            {
                sql = sql + TablaEmpresas.NOMBRECOMER  + " LIKE '%" + conBusVO.getNomComercial()  + "%' AND ";
            }

            if ( conBusVO.getCodAct()!= -1)
            {
                sql = sql + TablaEmpresas.CODSEC       + " = '"     + conBusVO.getCodAct()        + "' AND ";
            }

            if (! conBusVO.getPobEmpresa().equals(""))
            {
                sql = sql + TablaEmpresas.POBLACION    + " LIKE '%" + conBusVO.getPobEmpresa()    + "%' AND ";
            }

            if (conBusVO.getCnae() != 0)
            {
                sql = sql + TablaEmpresas.CNAE         + " = "      + conBusVO.getCnae()          + " AND ";
            }

            if (conBusVO.getImpExp().equals("----"))
            {
                sql = sql + TablaEmpresas.IMPORTEXPORT + " LIKE '%" + conBusVO.getImpExp()         + "%' AND ";
            }

            if (conBusVO.isEsCliente())
            {
                sql = sql +  TablaEmpresas.ESCLIENTE   + " = true AND ";
            }
            sql = sql.substring(0, sql.length() - 4);

        }
        sql = sql + " ORDER BY " + TablaEmpresas.NOMBRE;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs  = ps.executeQuery();

            while (rs.next())
            {
                datEmp = new EmpresasVO();

                datEmp.setIdEmpresa    (rs.getString (TablaEmpresas.CODEMPRESA));
                datEmp.setNombreEmpresa(rs.getString (TablaEmpresas.NOMBRE));
                datEmp.setCifEmpresa   (rs.getString (TablaEmpresas.CIF));
                datEmp.setDirEmpresa   (rs.getString (TablaEmpresas.DIRECCION));
                datEmp.setCodPostal    (rs.getString (TablaEmpresas.CODPOSTAL));
                datEmp.setPobEmpresa   (rs.getString (TablaEmpresas.POBLACION));
                datEmp.setCodProv      (rs.getString (TablaEmpresas.CODPROVINCIA));
                datEmp.setTelEmpresa   (rs.getString (TablaEmpresas.TELEFONO));
                datEmp.setFaxEmpresa   (rs.getString (TablaEmpresas.FAX));
                datEmp.setMailEmpresa  (rs.getString (TablaEmpresas.MAIL));
                datEmp.setResEmpresa   (rs.getString (TablaEmpresas.RESPONSABLE));
                datEmp.setCodAct       (rs.getInt    (TablaEmpresas.CODSEC));
                datEmp.setNomComercial (rs.getString (TablaEmpresas.NOMBRECOMER));
                datEmp.setCnae         (rs.getInt    (TablaEmpresas.CNAE));
                datEmp.setNumEmp       (rs.getInt    (TablaEmpresas.EMPLEADOS));
                datEmp.setVolNeg       (rs.getString (TablaEmpresas.VOLNEGOCIO));
                datEmp.setImpExp       (rs.getString (TablaEmpresas.IMPORTEXPORT));
                datEmp.setCar1         (rs.getString (TablaEmpresas.CARGO1));
                datEmp.setCar2         (rs.getString (TablaEmpresas.CARGO2));
                datEmp.setResEmp2      (rs.getString (TablaEmpresas.RESPONSABLE2));
                datEmp.setConvenioAd   (rs.getBoolean(TablaEmpresas.CONVEADSCRIT));
                datEmp.setFecCon       (rs.getDate   (TablaEmpresas.FECHACONVEN));
                datEmp.setDatAct       (rs.getBoolean(TablaEmpresas.DATOSACTUAL));
                datEmp.setCuota        (rs.getInt    (TablaEmpresas.CUOTA));
                datEmp.setEsCliente    (rs.getBoolean(TablaEmpresas.ESCLIENTE));
                datEmp.setDirCom       (rs.getString (TablaEmpresas.DIRECCOMER));
                datEmp.setPobCom       (rs.getString (TablaEmpresas.POBLACIONCOM));
                datEmp.setProCom       (rs.getString (TablaEmpresas.CODPROVINCOM));
                datEmp.setCodPosCom    (rs.getString (TablaEmpresas.CODPOSTCOM));
                datEmp.setAutCesDat    (rs.getBoolean(TablaEmpresas.AUTORIZCESDAT));
                datEmp.setAccArco      (rs.getBoolean(TablaEmpresas.ACCESOARCO));
                datEmp.setFecAccArc    (rs.getDate   (TablaEmpresas.FECACCCARCO));
                datEmp.setRecArco      (rs.getBoolean(TablaEmpresas.RECHAZOARCO));
                datEmp.setFecRecArc    (rs.getDate   (TablaEmpresas.FECRECARCO));
                datEmp.setCanArco      (rs.getBoolean(TablaEmpresas.CANCELARCO));
                datEmp.setFecCanArc    (rs.getDate   (TablaEmpresas.FECCANARCO));
                datEmp.setOpoArco      (rs.getBoolean(TablaEmpresas.OPOSICARCO));
                datEmp.setFecOpoArc    (rs.getDate   (TablaEmpresas.FECOPOARCO));

                listBusq.addElement(datEmp);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listBusq;

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

    //Edita el registro de una empresa
    public static int editaEmpresa(EmpresasVO empVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        int               regActualizados = 0;
        
        /*String            sql             = "UPDATE TbEmp SET " +
                                               "NomEmp      = ?,"  +
                                               "CifEmp      = ?,"  +
                                               "dirEmp      = ?,"  +
                                               "codPost     = ?, " +
                                               "Poblacion   = ?, " +
                                               "codProv     = ?, " +
                                               "TelEmp      = ?, " +
                                               "FaxEmp      = ?, " +
                                               "mail        = ?, " +
                                               "ResEmp      = ?, " +
                                               "IdSec       = ?, " +
                                               "NomCom      = ?, " +
                                               "CNAE        = ?, " +
                                               "NumEmp      = ?, " +
                                               "VolNeg      = ?, " +
                                               "ImpExp      = ?, " +
                                               "Car1        = ?, " +
                                               "Car2        = ?, " +
                                               "ResEmp2     = ?, " +
                                               "ConAd       = ?, " +
                                               "FecCon      = ?, " +
                                               "DatAct      = ?, " +
                                               "Cuota       = ?, " +
                                               "EsCli       = ?, " +
                                               "dirCom      = ?, " +
                                               "pobCom      = ?, " +
                                               "proCom      = ?, " +
                                               "codPosCom   = ?, " +
                                               "autCesDat   = ?, " +
                                               "accArco     = ?, " +
                                               "fecAccArc   = ?, " +
                                               "recArco     = ?, " +
                                               "fecRecArc   = ?, " +
                                               "canArco     = ?, " +
                                               "fecCanArc   = ?, " +
                                               "opoArco     = ?, " +
                                               "fecOpoArc   = ?  " +
                                           " WHERE IdEmp= ?";*/

        String            sql             = "UPDATE " + TablaEmpresas.TABLA         + 
                                            " SET   " + TablaEmpresas.NOMBRE        + " = ? , "  
                                                      + TablaEmpresas.CIF           + " = ? , "  
                                                      + TablaEmpresas.DIRECCION     + " = ? , "  
                                                      + TablaEmpresas.CODPOSTAL     + " = ? , " 
                                                      + TablaEmpresas.POBLACION     + " = ? , " 
                                                      + TablaEmpresas.CODPROVINCIA  + " = ? , " 
                                                      + TablaEmpresas.TELEFONO      + " = ? , " 
                                                      + TablaEmpresas.FAX           + " = ? , " 
                                                      + TablaEmpresas.MAIL          + " = ? , " 
                                                      + TablaEmpresas.RESPONSABLE   + " = ? , " 
                                                      + TablaEmpresas.CODSEC        + " = ? , " 
                                                      + TablaEmpresas.NOMBRECOMER   + " = ? , " 
                                                      + TablaEmpresas.CNAE          + " = ? , " 
                                                      + TablaEmpresas.EMPLEADOS     + " = ? , "
                                                      + TablaEmpresas.VOLNEGOCIO    + " = ? , " 
                                                      + TablaEmpresas.IMPORTEXPORT  + " = ? , " 
                                                      + TablaEmpresas.CARGO1        + " = ? , " 
                                                      + TablaEmpresas.CARGO2        + " = ? , " 
                                                      + TablaEmpresas.RESPONSABLE2  + " = ? , "
                                                      + TablaEmpresas.CONVEADSCRIT  + " = ? , " 
                                                      + TablaEmpresas.FECHACONVEN   + " = ? , " 
                                                      + TablaEmpresas.DATOSACTUAL   + " = ? , " 
                                                      + TablaEmpresas.CUOTA         + " = ? , " 
                                                      + TablaEmpresas.ESCLIENTE     + " = ? , " 
                                                      + TablaEmpresas.DIRECCOMER    + " = ? , " 
                                                      + TablaEmpresas.POBLACIONCOM  + " = ? , " 
                                                      + TablaEmpresas.CODPROVINCOM  + " = ? , " 
                                                      + TablaEmpresas.CODPOSTAL     + " = ? , " 
                                                      + TablaEmpresas.AUTORIZCESDAT + " = ? , " 
                                                      + TablaEmpresas.ACCESOARCO    + " = ? , " 
                                                      + TablaEmpresas.FECACCCARCO   + " = ? , " 
                                                      + TablaEmpresas.RECHAZOARCO   + " = ? , " 
                                                      + TablaEmpresas.FECRECARCO    + " = ? , " 
                                                      + TablaEmpresas.CANCELARCO    + " = ? , " 
                                                      + TablaEmpresas.FECCANARCO    + " = ? , " 
                                                      + TablaEmpresas.OPOSICARCO    + " = ? , " 
                                                      + TablaEmpresas.FECOPOARCO    + " = ?   " + 
                                           " WHERE "  + TablaEmpresas.CODEMPRESA    + " = ?";


        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se actualiza el valor de los parametros de la consulta
            ps.setString ( 1, empVO.getNombreEmpresa());
            ps.setString ( 2, empVO.getCifEmpresa());
            ps.setString ( 3, empVO.getDirEmpresa());
            ps.setString ( 4, empVO.getCodPostal());
            ps.setString ( 5, empVO.getPobEmpresa());
            ps.setString ( 6, empVO.getCodProv());
            ps.setString ( 7, empVO.getTelEmpresa());
            ps.setString ( 8, empVO.getFaxEmpresa());
            ps.setString ( 9, empVO.getMailEmpresa());
            ps.setString (10, empVO.getResEmpresa());
            ps.setInt    (11, empVO.getCodAct());
            ps.setString (12, empVO.getNomComercial());
            ps.setInt    (13, empVO.getCnae());
            ps.setInt    (14, empVO.getNumEmp());
            ps.setString (15, empVO.getVolNeg());
            ps.setString (16, empVO.getImpExp());
            ps.setString (17, empVO.getCar1());
            ps.setString (18, empVO.getCar2());
            ps.setString (19, empVO.getResEmp2());
            ps.setBoolean(20, empVO.isConvenioAd());
            ps.setDate   (21, new Date(empVO.getFecCon().getTime()));
            ps.setBoolean(22, empVO.isDatAct());
            ps.setInt    (23, empVO.getCuota());
            ps.setBoolean(24, empVO.isEsCliente());
            ps.setString (25, empVO.getDirCom());
            ps.setString (26, empVO.getPobCom());
            ps.setString (27, empVO.getProCom());
            ps.setString (28, empVO.getCodPosCom());
            ps.setBoolean(29, empVO.isAutCesDat());
            ps.setBoolean(30, empVO.isAccArco());
            ps.setDate   (31, new Date(empVO.getFecAccArc().getTime()));
            ps.setBoolean(32, empVO.isRecArco());
            ps.setDate   (33, new Date(empVO.getFecRecArc().getTime()));
            ps.setBoolean(34, empVO.isCanArco());
            ps.setDate   (35, new Date(empVO.getFecCanArc().getTime()));
            ps.setBoolean(36, empVO.isOpoArco());
            ps.setDate   (37, new Date(empVO.getFecOpoArc().getTime()));
            ps.setString (38, empVO.getIdEmpresa());

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

    //Método que devuelve el nombre de una actividad de una empresa
    public static String devolverActividad(String codAct)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        String            sql        = "SELECT NomAct FROM TbAct WHERE idAct = ?";

        String            nomActDev  = "";

        try
        {

            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se introducen los parámetros a la consulta sql
            ps.setString(1, codAct);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                nomActDev = rs.getString(1);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return nomActDev;
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

            return "";
        }


    }

    //Método que devuelve el nombre de la provincia de una empresa
    public static String devolverProvincia(String codProv)
    {
        Connection        con       = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        String            sql       = "SELECT Ciudad FROM TbCPo WHERE NCodigo = ?";

        String            nomProDev = "";

        try
        {

            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros a la sentencia sql
            ps.setString(1, codProv);

            rs = ps.executeQuery();

            if(rs.next())
            {
                nomProDev = rs.getString(1);
            }

            return nomProDev;

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

            return "";
        }

    }

    //Borra el registro de una empresa si no tiene empleados asociados en la base de datos
    public static int borraEmpresa(String codEmp)
    {
        //si hay algun alumno que pertenezca a la empresa  no se borra
        if (devolverEmpresaTieneAlu(codEmp))
        {
            return -2;
        }
        else
        {
            
            Connection        con             = null;
            PreparedStatement ps              = null;
            ResultSet         rs              = null;
            
            //String            sql             = "DELETE FROM TbEmp WHERE IdEmp = ?";
            String            sql             = "DELETE FROM " + TablaEmpresas.TABLA      +
                                                " WHERE "      + TablaEmpresas.CODEMPRESA + " = ?";
            
            int               regActualizados = 0;
            int               segBor          = 0;

            //Se borran los seguimientos asociados a la empresa
            segBor = SegEmpDAO.eliminaSegEmp(codEmp);  

            if(segBor >= 0)
            {
                try
                {
                    con = Conexion.conectar();
                    ps  = con.prepareStatement(sql);

                    //Se pasan los parámetros a la consulta sql
                    ps.setString(1,codEmp);

                    regActualizados = ps.executeUpdate();

                    rs.close();
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
            else
            {
                return -3;
            }

        }
    }

    //Método que devuelve si una empresa tiene alumnos
    public static boolean devolverEmpresaTieneAlu(String codEmp)
    {
        Connection        con       = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        //String            sql       = "SELECT * FROM TbAlu WHERE EmpAlu = ?";
        String            sql       = "SELECT " + TablaAlumnos.CODALU     + 
                                      " FROM  " + TablaAlumnos.TABLA      +
                                      " WHERE " + TablaAlumnos.CODEMPRESA + " = ?";
        
        
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codEmp);

            rs = ps.executeQuery();

            if (rs.next())
            {
                rs.close();
                ps.close();
                Conexion.desconectar(con);
                return true; //Hay alumnos que pertenecen a la empresa

            }
            else
            {
                rs.close();
                ps.close();
                Conexion.desconectar(con);
                return false; //No hay alumnos que pertenezcan a la empresa
            }

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
    
    //Devuelve el nombre de la empresa
    public static String devuelveNombreEmpresa(String idEmpresa)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT NOMEMP FROM TBEMP WHERE IDEMP = ?";
        String            sql        = "SELECT " + TablaEmpresas.NOMBRE     +  
                                       " FROM  " + TablaEmpresas.TABLA      +
                                       " WHERE " + TablaEmpresas.CODEMPRESA + " = ?";
        
        
        String            nomEmpresa = "";

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, idEmpresa);

            rs = ps.executeQuery();

            if(rs.next())
            {
                nomEmpresa = rs.getString(1);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return nomEmpresa;
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

}
