package conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * En esta clase realizamos las consultas a la base de datos
 * 
 * @author Darwin
 *
 */
public class TestConexion {

	public static void main(String[] args) {

		// Instancia de la clase conexion
		Conexion conexion = new Conexion();
		Connection cn = null;

		// Objeto que nos permite crear las sentencias SQL
		Statement stm = null;
		// Asignamos la referencia a la coneccion a la variable cn
		cn = conexion.conectar();

		ResultSet rs = null;
//		ResultSet rs1 = null;

		try {
			stm = cn.createStatement();
			// Guardando en la variable rs el resultado de la consulta (en este caso la
			// tabla usuario)
			boolean re = stm.execute(
					"INSERT INTO cliente (cedula,nombre,fecha_de_nacimiento,direccion) VALUES (1094961217,'Camilo','1996-05-12','Norte Cr 14 calle 2 ')");
			rs = stm.executeQuery("SELECT * FROM cliente");
//			rs1 = stm.executeQuery("SELECT * FROM local");
						
			boolean re1 = stm.execute(
					"INSERT INTO cliente_local (Local_idlocal,Cliente_cedula) VALUES ("+1+","+rs.getInt(1)+")");
//			while (rs.next()) {
//
//				int idUsuario = rs.getInt(1);
//				String nombreUsuario = rs.getString(2);
//				Date edadUsuario = rs.getDate(3);
//				String dir = rs.getString(4);
//
//				System.out.println(idUsuario + " - " + nombreUsuario + " - " + edadUsuario + " - " + dir);
//
//			}

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
	}

}
