package control;

import java.sql.ResultSet;
import java.sql.SQLException;

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
import model.Cargo;
import model.Local;

public class localController {
	

	@FXML
	public TextField txtId, txtNombre, txtDireccion;

	@FXML
	public Button btnAgregar, btnEliminar, btnModifica, btnSalir, btnVolver;

	@FXML
	public Pane top;

	@FXML
	public TableView<Local> tblLocales;

	@FXML
	public TableColumn<Local, String> columnId, columnNombre, columnDireccion;
	

	@FXML
	private mainController mainController = new mainController();

	private ObservableList<Local> localesObservables;

	public localController() throws SQLException {

		localesObservables = FXCollections.observableArrayList();

		localesObservables = LlenarLocales();

	}

	@FXML
	private void initialize() {

		columnId.setCellValueFactory(localCelda -> localCelda.getValue().getId_Local());
		columnNombre.setCellValueFactory(localCelda -> localCelda.getValue().getNombre());
		columnDireccion.setCellValueFactory(localCelda -> localCelda.getValue().getDireccion());

		tblLocales.setItems(localesObservables);

	}

	@FXML
	private void registrar(String path) {
		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
	}

	@FXML
	private ObservableList<Local> LlenarLocales() {

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

			String query = "SELECT * FROM chocolate.local";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				localesObservables.add(new Local(rs.getString("idlocal"), rs.getString("local"), rs.getString("direccion")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return localesObservables;
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
