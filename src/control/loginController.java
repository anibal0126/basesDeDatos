package control;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class loginController {

	@FXML
	private Button btniniciar, btnSalir;
	/**
	 * Etiqueta de usuario
	 */
	@FXML
	private TextField txtUsuario;

	/**
	 * Etiqueta de clave
	 */
	@FXML
	private PasswordField txtClave;

	@FXML
	public Pane top;

	@FXML
	private mainController mainController = new mainController();

	/**
	 * Usuario del correo electronico del cual se envian los corres
	 */
	private static String usuario = "chocolate";

	/**
	 * Contrasenia del correo electronico del cual se envian los corres
	 */
	private static String password = "1234";

	/**
	 * metodo que permite iniciar sesion al usuario
	 */

	@FXML
	private void registrar(String path) {
		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
	}

	@FXML
	public void iniciarSesion() {

		String cedula = txtUsuario.getText();
		String clave = txtClave.getText();

		if (!txtUsuario.getText().isEmpty()) {

			if (!txtClave.getText().isEmpty()) {

				if (cedula.equalsIgnoreCase(usuario) && clave.equalsIgnoreCase(password)) {

					JOptionPane.showMessageDialog(null, "Bienvenido");

					registrar("../vista/principal.fxml");

				} else {
					JOptionPane.showMessageDialog(null, "USUARIO Y/O CONTRASEÑA INCORRECTOS");
				}

			} else {

				JOptionPane.showMessageDialog(null, "USUARIO Y/O CONTRASEÑA INCORRECTOS");
			}
		} else {

			JOptionPane.showMessageDialog(null, "INGRESA LOS DATOS CON LOS QUE TE ENCUENTRAS REGISTRADO");
		}

	}

	@FXML
	private void cerrar() {
		System.exit(0);
	}
}
