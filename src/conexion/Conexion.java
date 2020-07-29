/**
 * 
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que contiene la conexion a la base de datos
 * 
 * @author Darwin
 *
 */
public class Conexion {

	// Controlador de mysql
	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	// Direccion de la base de datos en mysql
	private static final String URL = "jdbc:mysql://localhost:3306/chocolate";
	// Usuario base de datos mysql
	private static final String USUARIO = "root";
	// Contrasenia para ingresar a mysql
	private static final String CLAVE = "0711";

	private Connection con;
	/**
	 * En este bloque estatico se carga el controlador de mysql
	 */
	static {

		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que efectua la conexion entre la base de datos y la aplicacion
	 */
	public Connection conectar() {

		Connection conexion = null;
		try {
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);

		} catch (SQLException e) {
			System.out.println("Error en la conexionr");
			e.printStackTrace();
		}

		return conexion;

	}

	public Connection getConnection() {
		return con; // Retorno el objeto Connection
	}

}
