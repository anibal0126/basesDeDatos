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
import model.Cliente;

public class cargoController {

	@FXML
	public TextField txtId, txtNombre;

	@FXML
	public Button btnAgregar, btnEliminar, btnModifica, btnSalir, btnVolver;

	@FXML
	public Pane top;

	@FXML
	public TableView<Cargo> tblCargos;

	@FXML
	public TableColumn<Cargo, String> columnId, columnNombre;

	@FXML
	private mainController mainController = new mainController();

	private ObservableList<Cargo> cargosObservable;

	public cargoController() throws SQLException {

		cargosObservable = FXCollections.observableArrayList();

		cargosObservable = LlenarCargos();

	}

	@FXML
	private void initialize() {

		columnId.setCellValueFactory(cargoCelda -> cargoCelda.getValue().getId());
		columnNombre.setCellValueFactory(cargoCelda -> cargoCelda.getValue().getNombre());

		tblCargos.setItems(cargosObservable);

	}

	@FXML
	private void registrar(String path) {
		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
	}
	

	@FXML
	private ObservableList<Cargo> LlenarCargos() {

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

			String query = "SELECT * FROM chocolate.cargo";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				cargosObservable.add(new Cargo(rs.getString("idCargo"), rs.getString("nombre")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return cargosObservable;

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
