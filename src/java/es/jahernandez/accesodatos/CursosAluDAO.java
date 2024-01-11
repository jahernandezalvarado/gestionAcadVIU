/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.accesodatos;

import es.jahernandez.datos.Conexion;
import es.jahernandez.datos.CursosAluVO;

import es.jahernandez.tablas.TablaCursosAlumnos;

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
public class CursosAluDAO
{  
    
    //Método que guarda los cursos de interes de alumnos y su nivel
    public static int guardarCursosInteres(String codAlumno, String codCurso, String codNiv)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        ResultSet         rs              = null;

        //String            sql             = "INSERT INTO TbCurAlu (IdAlu,IdCur,IdNiv,FecInt) VALUES(?,?,?,?)";
        String            sql             = "INSERT INTO " + TablaCursosAlumnos.TABLA + "(" + TablaCursosAlumnos.CODALUMNO + " , " 
                                                                                            + TablaCursosAlumnos.CODCURSO  + " , "
                                                                                            + TablaCursosAlumnos.CODNIVEL  + " , "
                                                                                            + TablaCursosAlumnos.FECHAINT  + " ) " +
                                            " VALUES(?,?,?,?)";
                
        int               regActualizados = 0;

        //Se comprueba que el curso no esté ya dado de alta
        if(compruebaAltaCurNivAlu(codAlumno, codCurso, codNiv))
        {
            return -2;
        }

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAlumno);
            ps.setString(2, codCurso );
            ps.setString(3, codNiv);
            ps.setDate  (4, new Date(System.currentTimeMillis()));

            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.desconectar(con);

            return regActualizados;

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

    //Método que devuelve los cursos que tiene asociados un determinado alumno
    public static Vector devolverCursosAlu(String codAlu)
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            sql            = "SELECT DISTINCT IdAlu,IdCur,IdNiv FROM TbCurAlu WHERE IdAlu = ?";
        String            sql            = "SELECT DISTINCT " + TablaCursosAlumnos.CODALUMNO + " , "
                                                              + TablaCursosAlumnos.CODCURSO  + " , "
                                                              + TablaCursosAlumnos.CODNIVEL  +  
                                           " FROM  "          + TablaCursosAlumnos.TABLA     +  
                                           " WHERE "          + TablaCursosAlumnos.CODALUMNO + " = ? ";
        
        Vector            listaCursosAlu = new Vector();

        CursosAluVO       datResBus      = null;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAlu);

            rs = ps.executeQuery();

            while (rs.next())
            {
                datResBus = new CursosAluVO();

                datResBus.setIdAlu(rs.getString(TablaCursosAlumnos.CODALUMNO));
                datResBus.setIdCur(rs.getString(TablaCursosAlumnos.CODCURSO));
                datResBus.setIdNiv(rs.getString(TablaCursosAlumnos.CODNIVEL));

                listaCursosAlu.addElement(datResBus);
            }

            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return listaCursosAlu;
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

    //Método que elimina un curso de un alumno
    public static int eliminarCurAlu(String codAlumno, String codCurso,String codNiv)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        //String            sql             = "DELETE FROM TbCurAlu WHERE IdAlu = ? AND IdCur = ? AND IdNiv = ?";
        String            sql             = "DELETE FROM " + TablaCursosAlumnos.TABLA + 
                                            " WHERE "      + TablaCursosAlumnos.CODALUMNO + " = ? AND " 
                                                           + TablaCursosAlumnos.CODCURSO  + " = ? AND " 
                                                           + TablaCursosAlumnos.CODNIVEL  + " = ? ";
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            
            //Se le pasan los parámetros a la consulta sql
            ps.setString(1, codAlumno);
            ps.setString(2, codCurso);
            ps.setString(3, codNiv);
            
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

            return 0;
        }

    }

    //Borra los datos de curso de un alumno
    public static int borraCurAlu(String codAlu)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        //String            sql             = "DELETE FROM TbCurAlu WHERE IdAlu =  ?";
        String            sql             = "DELETE FROM " + TablaCursosAlumnos.TABLA + 
                                            " WHERE  "     + TablaCursosAlumnos.CODALUMNO + " = ?";                
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros de la consulta
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
                Logger.getLogger(ConUsoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return -1;
        }

    }

    //Borra todos los datos de un curso
    public static int borrarTodAluCur(String codCur)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;
        
        //String            sql             = "DELETE FROM TbCurAlu WHERE IdCur = ? ";
        String            sql             = "DELETE FROM " + TablaCursosAlumnos.TABLA    + 
                                            " WHERE "      + TablaCursosAlumnos.CODCURSO + " = ? ";
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros de la consulta sql
            ps.setString(1, codCur);
                                    
            regActualizados = ps.executeUpdate();

            ps.close();
            Conexion.conectar();

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

    //Edita el nivel de los alumnos, cuando un nivel se elimina de un curso
    public static int ediNivCurAlu(String codCur)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        //String            sql             = "UPDATE TbCurAlu SET IdNiv='00000000' WHERE IdCur= ? ";
        String            sql             = "UPDATE " + TablaCursosAlumnos.TABLA    +   
                                            " SET "   + TablaCursosAlumnos.CODNIVEL + " = ? " +
                                            " WHERE " + TablaCursosAlumnos.CODCURSO + " = ? ";
        
        
        
        int               regActualizados = 0;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);
            
            //Se pasan los parámetros de la consulta sql
            ps.setString(1, "00000000");                        
            ps.setString(2, codCur);                        
            
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

    //Método que comprueba si ya se ha asociado un curso-nivel a  un alumno
    public static boolean compruebaAltaCurNivAlu(String codAlumno, String codCurso, String codNiv)
    {
        Connection        con            = null;
        PreparedStatement ps             = null;
        ResultSet         rs             = null;

        //String            sql            = "SELECT IdAlu,IdCur,IdNiv FROM TbCurAlu WHERE IdAlu = ? AND idCur=? AND idNiv=?";
        String            sql            = "SELECT " + TablaCursosAlumnos.CODALUMNO + " , "
                                                     + TablaCursosAlumnos.CODCURSO  + " , "
                                                     + TablaCursosAlumnos.CODNIVEL  + 
                                           " FROM "  + TablaCursosAlumnos.TABLA     + 
                                           " WHERE " + TablaCursosAlumnos.CODALUMNO + " = ? AND " 
                                                     + TablaCursosAlumnos.CODCURSO  + " = ? AND " 
                                                     + TablaCursosAlumnos.CODNIVEL  + " = ?";
        
        boolean           exiCurAlu      = false;

        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAlumno);
            ps.setString(2, codCurso);
            ps.setString(3, codNiv);

            rs = ps.executeQuery();

            if (rs.next())
            {
                exiCurAlu = true;
            }
            else
            {
                exiCurAlu = false;
            }
            rs.close();
            ps.close();
            Conexion.desconectar(con);

            return exiCurAlu;
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
                Logger.getLogger(CursosAluDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }

    }

    //Método que guarda los cursos de interes de alumnos
    public static int guardarCursosInteres(String codAlumno, String codCurso)
    {
        Connection        con             = null;
        PreparedStatement ps              = null;

        int               regActualizados = 0;

        //String            sql             = "INSERT INTO TBCURALU (IDALU,IDCUR,FECINT) VALUES(?,?,?)";
        String            sql             = "INSERT INTO " + TablaCursosAlumnos.TABLA + " (" + TablaCursosAlumnos.CODALUMNO + " , "
                                                                                             + TablaCursosAlumnos.CODCURSO  + " , "
                                                                                             + TablaCursosAlumnos.FECHAINT  + " ) " + 
                                            " VALUES(?,?,?)";
        
        try
        {
            con = Conexion.conectar();
            ps  = con.prepareStatement(sql);

            //Se pasan los parámetros a la consulta sql
            ps.setString(1, codAlumno);
            ps.setString(2, codCurso);
            ps.setDate  (3, new Date(System.currentTimeMillis()));

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

            return 0;
        }

    }
}
