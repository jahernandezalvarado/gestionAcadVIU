/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.AulasVO;
import es.jahernandez.datos.ResultadoBusqAlu;

import es.jahernandez.tablas.TablaAulas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;
import javax.swing.text.TabExpander;

/**
 *
 * @author Alberto
 */
public class AulasDAO
{              
    //Método que genera un nuevo código de Aula
    public static String generarNuevoCodAula()
    {
        boolean   enc       = true;
        int       avc       = 1;
        int       contCar;
        String    codIntrod = "";

        Vector    datAul    = devolverTodosAula();

        while (enc)
        {
            contCar = new Integer(datAul.size()).toString().length();

            if (contCar > 0)
            {
                codIntrod = new Integer(datAul.size() + avc).toString();
            }
            else
            {
                codIntrod = "0";
            }

            codIntrod = codIntrod.trim();

            while (codIntrod.length() < 4)
            {
                codIntrod = "0" + codIntrod;
                contCar = contCar + 1;
            }

            //codIntrod = codIntrod.Substring((codIntrod.length() - 7), codIntrod.length());

            enc = false;
            datAul = devolverTodosAula();
            for (int ind = 0; ind < datAul.size(); ind++)
            {
                AulasVO aulVO = (AulasVO) datAul.elementAt(ind);
                if (aulVO.getIdAula().equals(codIntrod))
                {
                    enc = true;
                    break;
                }
            }

            avc = avc + 1;

        }

        return codIntrod;

    }

    //Método que devuelve los datos de aula de todas las aulas
    public static Vector devolverTodosAula()
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;
        
        //String            sql        = "SELECT * FROM TbAul";
        String            sql        = "SELECT " + TablaAulas.CODAULA   + ","
                                                 + TablaAulas.NOMBRE    + ","
                                                 + TablaAulas.CODCENTRO + ","
                                                 + TablaAulas.PLAZAS    + ","
                                                 + TablaAulas.DESCRIP   + ","
                                                 + TablaAulas.ESAULAINF + ","
                                                 + TablaAulas.TIENEPROY + ","
                                                 + TablaAulas.TIENETV   + ","
                                                 + TablaAulas.TIENEAC   + ","
                                                 + TablaAulas.TIENEIMP  + ","
                                                 + TablaAulas.TIENEINT  +
                                        " FROM " + TablaAulas.TABLA ;
        
        AulasVO           datAul     = null;
        Vector            listaAulas = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next() )
            {
                datAul = new AulasVO();

                datAul.setIdAula     (rs.getString (TablaAulas.CODAULA));
                datAul.setNombre     (rs.getString (TablaAulas.NOMBRE));
                datAul.setIdCen      (rs.getInt    (TablaAulas.CODCENTRO));
                datAul.setNumPla     (rs.getInt    (TablaAulas.PLAZAS));
                datAul.setDescripcion(rs.getString (TablaAulas.DESCRIP));
                datAul.setEsAulInf   (rs.getBoolean(TablaAulas.ESAULAINF));
                datAul.setTieneProy  (rs.getBoolean(TablaAulas.TIENEPROY));
                datAul.setTieneTV    (rs.getBoolean(TablaAulas.TIENETV));
                datAul.setTieneAC    (rs.getBoolean(TablaAulas.TIENEAC));
                datAul.setTieneImp   (rs.getBoolean(TablaAulas.TIENEIMP));
                datAul.setTieneInt   (rs.getBoolean(TablaAulas.TIENEINT));

                listaAulas.addElement(datAul);
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAulas;
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
                Logger.getLogger(AulasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

             return null;
        }

    }

    //Método que devuelve los datos de aula de un aula
    public static AulasVO devolverDatosAula(String codAul)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT * FROM TbAul WHERE IdAula= ?";
         String            sql        = "SELECT " + TablaAulas.CODAULA   + ","
                                                  + TablaAulas.NOMBRE    + ","
                                                  + TablaAulas.CODCENTRO + ","
                                                  + TablaAulas.PLAZAS    + ","
                                                  + TablaAulas.DESCRIP   + ","
                                                  + TablaAulas.ESAULAINF + ","
                                                  + TablaAulas.TIENEPROY + ","
                                                  + TablaAulas.TIENETV   + ","
                                                  + TablaAulas.TIENEAC   + ","
                                                  + TablaAulas.TIENEIMP  + ","
                                                  + TablaAulas.TIENEINT  +
                                        " FROM "  + TablaAulas.TABLA     +
                                        " WHERE " + TablaAulas.CODAULA   + " = ?";
        
        AulasVO           datAul     = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);


            //Pasamos los parámetros de la consulta
            ps.setString(1, codAul);

            rs = ps.executeQuery();

            if (rs.next() )
            {
                datAul = new AulasVO();

                datAul.setIdAula     (rs.getString (TablaAulas.CODAULA));
                datAul.setNombre     (rs.getString (TablaAulas.NOMBRE));
                datAul.setIdCen      (rs.getInt    (TablaAulas.CODCENTRO));
                datAul.setNumPla     (rs.getInt    (TablaAulas.PLAZAS));
                datAul.setDescripcion(rs.getString (TablaAulas.DESCRIP));
                datAul.setEsAulInf   (rs.getBoolean(TablaAulas.ESAULAINF));
                datAul.setTieneProy  (rs.getBoolean(TablaAulas.TIENEPROY));
                datAul.setTieneTV    (rs.getBoolean(TablaAulas.TIENETV));
                datAul.setTieneAC    (rs.getBoolean(TablaAulas.TIENEAC));
                datAul.setTieneImp   (rs.getBoolean(TablaAulas.TIENEIMP));
                datAul.setTieneInt   (rs.getBoolean(TablaAulas.TIENEINT));

            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return datAul;
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
                Logger.getLogger(AulasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

             return null;
        }
    }

    //Método que guarda un nuevo registro en la base de datos
    //Devuelve el código de aula generado, si se inserta correctamente
    public static String guardarAula(AulasVO aulVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        String            nueCodAul       = generarNuevoCodAula();

        //String            sql             = "INSERT INTO TbAul (IdAula,Nombre,IdCen,numPla,Descripcion,esAulaInf,tieneProy,tieneTV,tieneAC,tieneImp,tieneInt) " +
        //                                    "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String            sql             = "INSERT INTO " + TablaAulas.TABLA + " (" + TablaAulas.CODAULA   + ", "
                                                                                     + TablaAulas.NOMBRE    + ", " 
                                                                                     + TablaAulas.CODCENTRO + ", " 
                                                                                     + TablaAulas.PLAZAS    + ", "
                                                                                     + TablaAulas.DESCRIP   + ", "
                                                                                     + TablaAulas.ESAULAINF + ", "
                                                                                     + TablaAulas.TIENEPROY + ", "
                                                                                     + TablaAulas.TIENETV   + ", "
                                                                                     + TablaAulas.TIENEAC   + ", "
                                                                                     + TablaAulas.TIENEIMP  + ", "
                                                                                     + TablaAulas.TIENEINT  + ")" +
                                            " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros de la consulta
            ps.setString ( 1, nueCodAul);
            ps.setString ( 2, aulVO.getNombre());
            ps.setInt    ( 3, aulVO.getIdCen());
            ps.setInt    ( 4, aulVO.getNumPla());
            ps.setString ( 5, aulVO.getDescripcion());
            ps.setBoolean( 6, aulVO.isEsAulInf());
            ps.setBoolean( 7, aulVO.isTieneProy());
            ps.setBoolean( 8, aulVO.isTieneTV());
            ps.setBoolean( 9, aulVO.isTieneAC());
            ps.setBoolean(10, aulVO.isTieneImp());
            ps.setBoolean(11, aulVO.isTieneInt());

            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            if(regActualizados>0)
            {
                return  nueCodAul;
            }
            else
            {
                return "-1";
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
                Logger.getLogger(AulasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "-2";
        }

    }

    //Edita el registro de un aula
    public static int editaAula(AulasVO aulVO)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        /*String            sql             = "UPDATE TbAul SET "      +
                                                   "Nombre      = ? ," +
                                                   "numPla      = ? ," +
                                                   "Descripcion = ? ," +
                                                   "esAulaInf   = ? ," +
                                                   "tieneProy   = ? ," +
                                                   "tieneTV     = ? ," +
                                                   "tieneAC     = ? ," +
                                                   "tieneImp    = ? ," +
                                                   "tieneInt    = ? "  +
                                            "WHERE IdAula = ?";
        */
        String            sql             = "UPDATE " + TablaAulas.TABLA     + 
                                            " SET   " + TablaAulas.NOMBRE    + " = ? ," 
                                                      + TablaAulas.PLAZAS    + " = ? ," 
                                                      + TablaAulas.DESCRIP   + " = ? ," 
                                                      + TablaAulas.ESAULAINF + " = ? ," 
                                                      + TablaAulas.TIENEPROY + " = ? ," 
                                                      + TablaAulas.TIENETV   + " = ? ," 
                                                      + TablaAulas.TIENEAC   + " = ? ," 
                                                      + TablaAulas.TIENEIMP  + " = ? ," 
                                                      + TablaAulas.TIENEINT  + " = ? "  +
                                            " WHERE " + TablaAulas.CODAULA   + " = ?";
                        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Pasamos los parámetros de la consulta
            ps.setString ( 1, aulVO.getNombre());
            ps.setInt    ( 2, aulVO.getNumPla());
            ps.setString ( 3, aulVO.getDescripcion());
            ps.setBoolean( 4, aulVO.isEsAulInf());
            ps.setBoolean( 5, aulVO.isTieneProy());
            ps.setBoolean( 6, aulVO.isTieneTV());
            ps.setBoolean( 7, aulVO.isTieneAC());
            ps.setBoolean( 8, aulVO.isTieneImp());
            ps.setBoolean( 9, aulVO.isTieneInt());
            ps.setString (10, aulVO.getIdAula());

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
                Logger.getLogger(AulasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }
    }

    //Método que devuelve los datos de aula de todas las aulas de un centro
    public static Vector devolverAulasCentro(int codCentro)
    {
        Connection        con        = null;
        PreparedStatement ps         = null;
        ResultSet         rs         = null;

        //String            sql        = "SELECT * FROM TbAul WHERE idCen = ?";
        String            sql        = "SELECT "  + TablaAulas.CODAULA    + ","
                                                  + TablaAulas.NOMBRE     + ","
                                                  + TablaAulas.CODCENTRO  + ","
                                                  + TablaAulas.PLAZAS     + ","
                                                  + TablaAulas.DESCRIP    + ","
                                                  + TablaAulas.ESAULAINF  + ","
                                                  + TablaAulas.TIENEPROY  + ","
                                                  + TablaAulas.TIENETV    + ","
                                                  + TablaAulas.TIENEAC    + ","
                                                  + TablaAulas.TIENEIMP   + ","
                                                  + TablaAulas.TIENEINT   +
                                        " FROM "  + TablaAulas.TABLA      +
                                        " WHERE " + TablaAulas.CODCENTRO  + " = ?";
        
        
        AulasVO           datAul     = null;
        Vector            listaAulas = new Vector();

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            ps.setInt(1, codCentro);

            rs = ps.executeQuery();

            while (rs.next() )
            {
                datAul = new AulasVO();

                datAul.setIdAula(rs.getString(1));
                datAul.setNombre(rs.getString(2));
                datAul.setIdCen(rs.getInt(3));
                datAul.setNumPla(rs.getInt(4));
                datAul.setDescripcion(rs.getString(5));
                datAul.setEsAulInf(rs.getBoolean(6));
                datAul.setTieneProy(rs.getBoolean(7));
                datAul.setTieneTV(rs.getBoolean(8));
                datAul.setTieneAC(rs.getBoolean(9));
                datAul.setTieneImp(rs.getBoolean(10));
                datAul.setTieneInt(rs.getBoolean(11));

                listaAulas.addElement(datAul);
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaAulas;
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
                Logger.getLogger(AulasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

             return null;
        }

    }


}
