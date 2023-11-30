package es.jahernandez.datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* Esta clase realiza la conexión a una base de datos ODBC  mediante el driver puente JDBC-ODBC.
* Tiene los métodos de conexión y desconexión.<br>
* Ejemplo:<br>
* <pre>
* try {
*     Connection con = Conexion.conectar();
*     ...
*     // Codigo de utilización de la base de datos
*     ...
*     Conexion.desconectar(con);
* } catch(java.lang.ClassNotFoundException e) {
*      // Manejo de excepción
* } catch (java.sql.SQLException e) {
*      // Manejo de excepción
* }	
* </pre>
* Fecha: 7/11/2002
* @author Juan Alberto Hernández Alvarado
* @see java.sql.Connection
*/
public class Conexion 
{

   

/**
* Este método realiza una conexión a la base de datos.
* @return Connection Devuelve un objeto <code>Connection</code> con una conexión abierta con la base
* de datos, en caso de fallar devolverá null.
* @exception java.lang.ClassNotFoundException Si no se encuentra el driver arroja esta excepción.
* @exception java.sql.SQLException Si se produce alg�n error sql se arroja esta excepción.
*/	
    public static Connection conectarAccess() throws java.sql.SQLException, java.lang.ClassNotFoundException 
    {
		Connection con = null;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con = DriverManager.getConnection("jdbc:odbc:"+ InformacionConf.dsn ,InformacionConf.user, InformacionConf.pwd);
		return con;		
    }

  
  
  
    public static Connection conectar() throws java.sql.SQLException, java.lang.ClassNotFoundException 
     {
		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://" + InformacionConf.url +"/"+ InformacionConf.dsn,InformacionConf.user, InformacionConf.pwd);
                
		return con;		
     }

/**
* Este método realiza la desconexión de la base de datos.
* @exception java.sql.SQLException Arroja esta excepción si se produce algún error sql durante la desconexión.
*/	
    public static void desconectar(Connection con) throws java.sql.SQLException 
    {
            if(con!=null)
            con.close();
    }
}
