package control;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import conexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cliente;
import model.Turno;

public class turnoController {

	@FXML
	public TextField txtDescripcion, txtHoraInicio, txtHoraFin, txtCodigo;

	@FXML
	public Button btnAgregar, btnEliminar, btnModificar, btnVolver, btnCerrar;

	/**
	 * Tabla donde se almacena el listado de familias de plantas
	 */
	@FXML
	private TableView<Turno> tblTurnos;

	@FXML
	private TableColumn<Turno, String> columnId;

	@FXML
	private TableColumn<Turno, String> columnDescripcion;
	/**
	 * Columna donde se almacena el codigo
	 */
	@FXML
	private TableColumn<Turno, String> columnHoraInicio;

	// Columna donde se almacena el nombre

	@FXML
	private TableColumn<Turno, String> columnHoraFin;

	private ObservableList<Turno> turnos;

	@FXML
	public Pane top;

	@FXML
	private mainController mainController = new mainController();

	public turnoController() throws SQLException {

		turnos = FXCollections.observableArrayList();

		turnos = LlenarTurno();

	}

	@FXML
	private void initialize() {

		columnId.setCellValueFactory(turnoCelda -> turnoCelda.getValue().getId());
		columnDescripcion.setCellValueFactory(turnoCelda -> turnoCelda.getValue().getDescripcion());
		columnHoraInicio.setCellValueFactory(turnoCelda -> turnoCelda.getValue().getHoraInicio());
		columnHoraFin.setCellValueFactory(turnoCelda -> turnoCelda.getValue().getHoraFin());

		tblTurnos.setItems(turnos);

	}

	@FXML
	private void agregar() throws HeadlessException, SQLException {

		String codigo = "";
		String descripcion = "";
		String horaInicio = "";
		String horaFin = "";

		if (!txtCodigo.getText().isEmpty()) {

			if (!txtDescripcion.getText().isEmpty()) {

				if (!txtHoraInicio.getText().isEmpty()) {

					if (!txtHoraFin.getText().isEmpty()) {

						codigo = txtCodigo.getText();
						descripcion = txtDescripcion.getText();
						horaInicio = txtHoraInicio.getText();
						horaFin = txtHoraFin.getText();
						agregar2(codigo, descripcion, horaInicio, horaFin);

						txtCodigo.setText("");
						txtDescripcion.setText("");
						txtHoraInicio.setText("");
						txtHoraFin.setText("");

					} else {
						JOptionPane.showMessageDialog(null, "Debe ingresar una descripcion");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Debe ingresar la hora de inicio del turno");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debe ingresar la hora fin del turno");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar el codigo del turno");
		}

	}

	public void agregar2(String codigo, String descripcion, String horaInicio, String horaFin) {

		// Instancia de la clase conexion
		Conexion conexion = new Conexion();
		Connection cn = null;

		// Objeto que nos permite crear las sentencias SQL
		Statement stm = null;
		// Asignamos la referencia a la coneccion a la variable cn
		cn = (Connection) conexion.conectar();

		ResultSet rs = null;
//			ResultSet rs1 = null;

		try {
			stm = (Statement) cn.createStatement();
			// Guardando en la variable rs el resultado de la consulta (en este caso la
			// tabla usuario)
			boolean re = stm.execute("INSERT INTO turno (idTurno, descripcion, hora_inicio, hora_fin) VALUES ('"
					+ codigo + "','" + descripcion + "','" + horaInicio + "','" + horaFin + "')");
			rs = stm.executeQuery("SELECT * FROM turno");
			turnos.clear();
			LlenarTurno();
			initialize();

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

	@FXML
	private void registrar(String path) {

		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
	}

	@FXML
	private ObservableList<Turno> LlenarTurno() {

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

			String query = "SELECT * FROM chocolate.turno ";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				turnos.add(new Turno(rs.getString("idTurno"), rs.getString("descripcion"), rs.getString("hora_inicio"),
						rs.getString("hora_fin")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return turnos;

	}

	@FXML
	private void eliminar() {

		String codigo = txtCodigo.getText();

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

			boolean re = stm.execute("Delete  from turno WHERE idTurno='" + codigo + "'");
			rs = stm.executeQuery("SELECT * FROM turno");

			turnos.clear();
			LlenarTurno();
			initialize();
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtHoraFin.setText("");
			txtHoraInicio.setText("");
//				
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

	@FXML
	private void Actualizar() {

		String codigo = txtCodigo.getText();
		String descripcion = txtDescripcion.getText();
		String horaInicio = txtHoraInicio.getText();
		String horaFin = txtHoraFin.getText();

		if (!txtCodigo.getText().isEmpty()) {

			if (!txtDescripcion.getText().isEmpty()) {

				if (!txtHoraInicio.getText().isEmpty()) {

					if (!txtHoraFin.getText().isEmpty()) {

						actualizar(codigo, descripcion, horaInicio, horaFin);

						txtCodigo.setText("");
						txtDescripcion.setText("");
						txtHoraFin.setText("");
						txtHoraInicio.setText("");

					} else {
						JOptionPane.showMessageDialog(null, "Debe ingresar una descripcion");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Debe ingresar la hora de inicio del turno");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debe ingresar la hora fin del turno");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar el codigo del turno");
		}

	}

	private void actualizar(String codigo, String descripcion, String horaInicio, String horaFin) {

		// Instancia de la clase conexion
		Conexion conexion = new Conexion();
		Connection cn = null;

		// Objeto que nos permite crear las sentencias SQL
		Statement stm = null;
		// Asignamos la referencia a la coneccion a la variable cn
		cn = (Connection) conexion.conectar();

		ResultSet rs = null;
//					ResultSet rs1 = null;

		try {
			stm = (Statement) cn.createStatement();
			// Guardando en la variable rs el resultado de la consulta (en este caso la
			// tabla usuario)
			boolean re = stm.execute("UPDATE  turno set " + "descripcion ='" + descripcion + "'," + "hora_inicio='"
					+ horaInicio + "'," + "hora_fin='" + horaFin + "'" + "WHERE idTurno=" + codigo + "");
			rs = stm.executeQuery("SELECT * FROM cliente");
			turnos.clear();
			LlenarTurno();
			initialize();

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

	@FXML
	private void Volver() {

		registrar("../vista/principal.fxml");
	}

	@FXML
	private void cerrar() {
		System.exit(0);
	}

}
