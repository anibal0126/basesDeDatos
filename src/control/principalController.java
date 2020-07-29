package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class principalController {

	@FXML
	public Pane top;

	@FXML
	private mainController mainController = new mainController();

	@FXML
	private void registrar(String path) {
		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
	}

	@FXML
	private void registrarTurno() {

		registrar("../vista/turno.fxml");
	}
	
	@FXML
	private void registrarReserva() {

		registrar("../vista/reserva.fxml");
	}

	@FXML
	private void registrarCliente() {

		registrar("../vista/cliente.fxml");
	}

	@FXML
	private void registrarEmpleado() {

		registrar("../vista/empleado.fxml");
	}

	@FXML
	private void registrarCargo() {

		registrar("../vista/Cargo.fxml");
	}

	@FXML
	private void registrarLocal() {

		registrar("../vista/local.fxml");
	}

	@FXML
	private void cerrar() {
		System.exit(0);
	}

}
