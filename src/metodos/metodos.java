package metodos;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import conexion.Conexion;

public class metodos {
	
	private ResultSet buscar(String query) {

		// Instancia de la clase conexion
		Conexion conexion = new Conexion();
		Connection cn = null;

		// Objeto que nos permite crear las sentencias SQL
		Statement stm = null;
		// Asignamos la referencia a la coneccion a la variable cn
		cn = (Connection) conexion.conectar();

		ResultSet rs = null;

		try {
			stm = (Statement) cn.createStatement();
			// Guardando en la variable rs el resultado de la consulta (en este caso la
			// tabla usuario)

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			String[] dato = new String[2]; // declaramos un vector para que al momento de insertar datos nos los muestre
											// en nuestra tabla

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (cn != null)
					cn.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return rs;

	}

}
