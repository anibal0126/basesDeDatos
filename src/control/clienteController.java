package control;

import java.awt.HeadlessException;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import conexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cliente;
import model.Local;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class clienteController {

	@FXML
	public TextField txCedula, txNombre, txTelefono, txDireccion, txBuscar, txFecha;

	@FXML
	public Button btnAgregar, btnEliminar, btnLimpiar, btnActualizar, btnGenerar, btnVolver, btnBuscar, btnCerrar;

	@FXML
	private ComboBox<String> cmbLocal;

	@FXML
	private ComboBox<String> cmbListarPorLocal;

	/**
	 * Tabla donde se almacena el listado de familias de plantas
	 */
	@FXML
	private TableView<Cliente> tblClientes;

	@FXML
	private TableColumn<Cliente, String> ColumnNombre;
	/**
	 * Columna donde se almacena el codigo
	 */
	@FXML
	private TableColumn<Cliente, String> ColumnCedula;

	// Columna donde se almacena el nombre

	@FXML
	private TableColumn<Cliente, String> ColumnDireccion;

	private ObservableList<Cliente> clientesObservable;

	private ObservableList<String> Locales;

	@FXML
	public Pane top;

	@FXML
	private mainController mainController = new mainController();

	public clienteController() throws SQLException {

		clientesObservable = FXCollections.observableArrayList();

		Locales = FXCollections.observableArrayList();

		clientesObservable = LlenarCliente();

		Locales = llenarLocales();

	}

	@FXML
	private void initialize() {

		cmbLocal.setItems(Locales);
		cmbLocal.getSelectionModel().select("Seleccione el Local");

		cmbListarPorLocal.setItems(Locales);
		cmbListarPorLocal.getSelectionModel().select("Generar Reporte de cliente POr Local");

		ColumnCedula.setCellValueFactory(clienteCelda -> clienteCelda.getValue().getCedula());
		ColumnNombre.setCellValueFactory(clienteCelda -> clienteCelda.getValue().getNombre());
		ColumnDireccion.setCellValueFactory(clienteCelda -> clienteCelda.getValue().getDireccion());

		tblClientes.setItems(clientesObservable);

	}

	@SuppressWarnings("deprecation")
	@FXML
	private void reporte() {
		String local = cmbListarPorLocal.getSelectionModel().getSelectedItem().toString();
		
		try {
			// Instancia de la clase conexion
						Conexion conexion = new Conexion();
						Connection cn = null;

						cn = (Connection) conexion.conectar();

						Map parametro = new HashMap();
						parametro.put("local", local);

						JasperReport jasperReport = (JasperReport) JRLoader
								.loadObjectFromFile("src\\reportes\\cliente.jasper");

						JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, cn);

						JRExporter exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("ReportePorLocalPDF.pdf"));
						exporter.exportReport();

						JasperViewer ver = new JasperViewer(jasperPrint);
						ver.setTitle("Reporte Local");
						ver.setDefaultCloseOperation(1);
						ver.setVisible(true);
						
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void limpiar() {

		clientesObservable.clear();
		LlenarCliente();
		initialize();

		txCedula.setText("");
		txNombre.setText("");
		txDireccion.setText("");
		txFecha.setText("AA-MM-DD");
	}

	private ObservableList<String> llenarLocales() {

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

			String query = "SELECT * FROM chocolate.local ";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				Locales.add(rs.getString("local").toString());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return Locales;

	}

	@FXML
	private void Actualizar() {

		String cedula = txCedula.getText();
		String nombre = txNombre.getText();
		String direccion = txDireccion.getText();
		String fecha = txFecha.getText();

		if (!txNombre.getText().isEmpty()) {

			if (!txDireccion.getText().isEmpty()) {

				if (!txFecha.getText().isEmpty()) {

					if (!txFecha.getText().equalsIgnoreCase("AA-MM-DD")) {

						actualizar(cedula, nombre, direccion, fecha);

						txCedula.setText("");
						txNombre.setText("");
						txDireccion.setText("");
						txFecha.setText("");

					} else {
						JOptionPane.showMessageDialog(null, "Debe ingresar una Fecha De Nacimiento valida");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe ingresar la Fecha De Nacimiento");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debe ingresar la Direcciom");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
		}

	}

	private void actualizar(String cedula, String nombre, String direccion, String fecha) {

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
			boolean re = stm.execute("UPDATE  cliente set " + "nombre ='" + nombre + "'," + "direccion='" + direccion
					+ "'," + "fecha_de_nacimiento='" + fecha + "'" + "WHERE cedula=" + cedula + "");
			rs = stm.executeQuery("SELECT * FROM cliente");
			clientesObservable.clear();
			LlenarCliente();
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
	private ObservableList<Cliente> LlenarCliente() {

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

			String query = "SELECT * FROM chocolate.cliente ";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				clientesObservable
						.add(new Cliente(rs.getString("cedula"), rs.getString("nombre"), rs.getString("direccion")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return clientesObservable;

	}

	@FXML
	private void agregar() throws HeadlessException, SQLException {

		String cedula = "";
		String nombre = "";
		String direccion = "";
		String fechaNacimiento = "";
		String local = cmbLocal.getSelectionModel().getSelectedItem().toString();
		;

		if (!txCedula.getText().isEmpty()) {

			if (buscar2(txCedula.getText()) == false) {

				if (!txNombre.getText().isEmpty()) {

					if (!txDireccion.getText().isEmpty()) {

						if (!txFecha.getText().isEmpty()) {

							if (!txFecha.getText().equalsIgnoreCase("AA-MM-DD")) {

								if (!local.equalsIgnoreCase("Seleccione el Local")) {

									String local1 = buscarLocal(local);

									nombre = txNombre.getText();
									direccion = txDireccion.getText();
									cedula = txCedula.getText();
									fechaNacimiento = txFecha.getText();
									agregar2(cedula, nombre, direccion, fechaNacimiento);
									agregarClienteLocal(cedula, local1);

									txCedula.setText("");
									txNombre.setText("");
									txDireccion.setText("");
									txFecha.setText("");

								} else {
									JOptionPane.showMessageDialog(null, "Debe ingresar una Fecha De Nacimiento valida");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Debe ingresar una Fecha De Nacimiento valida");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Debe ingresar la Fecha De Nacimiento");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Debe ingresar la Direcciom");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Este cliente ya esta registrado");
			}

		} else

		{
			JOptionPane.showMessageDialog(null, "Debe ingresar la cedula");
		}
	}

	public void agregar2(String cedula, String nombre, String direccion, String fechaNacimiento) {

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
			boolean re = stm.execute("INSERT INTO cliente (cedula,nombre,fecha_de_nacimiento,direccion) VALUES ('"
					+ cedula + "','" + nombre + "','" + fechaNacimiento + "','" + direccion + "')");
			rs = stm.executeQuery("SELECT * FROM cliente");
			clientesObservable.clear();
			LlenarCliente();
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

	public void agregarClienteLocal(String cedula, String local) {

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
			boolean re = stm.execute("INSERT INTO cliente_local (Local_idlocal, Cliente_cedula) VALUES ('" + local
					+ "','" + cedula + "')");
			rs = stm.executeQuery("SELECT * FROM cliente_local");

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
	private void eliminarReserva() throws HeadlessException, SQLException {

		String cedula = txCedula.getText();

		if (buscar2(cedula)) {

			if (!txCedula.getText().isEmpty()) {
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
					boolean re = stm.execute("DELETE FROM reserva WHERE Cliente_cedula='" + cedula + "'");
					rs = stm.executeQuery("SELECT * FROM reserva");
					eliminarClienteLocal(cedula);
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
			} else {
				JOptionPane.showMessageDialog(null, "Debe ingresar una cedula");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Este cliente no esta registrado");
		}
	}

	@FXML
	private void eliminarClienteLocal(String cedula) {

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

			boolean re = stm.execute("DELETE FROM chocolate.cliente_local WHERE Cliente_cedula='" + cedula + "'");
			rs = stm.executeQuery("SELECT * FROM cliente");

			eliminar(cedula);

			clientesObservable.clear();
			LlenarCliente();
			initialize();
			txCedula.setText("");
			txNombre.setText("");
			txDireccion.setText("");
			txFecha.setText("");
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
	private void eliminar(String cedula) {

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

			boolean re = stm.execute("DELETE FROM chocolate.cliente WHERE cedula='" + cedula + "'");
			rs = stm.executeQuery("SELECT * FROM cliente");

			clientesObservable.clear();
			LlenarCliente();
			initialize();
			txCedula.setText("");
			txNombre.setText("");
			txDireccion.setText("");
			txFecha.setText("");
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
	private String buscarLocal(String nombre) throws SQLException {

		Local local = null;

		String id = null;
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
			boolean re = stm.execute("select idlocal, local, direccion from local where  local='" + nombre + "'");
			rs = stm.executeQuery("select idlocal, local, direccion from local where  local='" + nombre + "'");

			while (rs.next()) {

				id = rs.getString("idlocal");

			}

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

		return id;

	}

	@FXML
	private void buscar() throws SQLException {
		String cedula = txBuscar.getText();
		Cliente cliente = null;
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
//			boolean re = stm.execute("select cedula, nombre, direccion from cliente where  cedula='" + cedula + "'");
			rs = stm.executeQuery("select cedula, nombre, fecha_de_nacimiento, direccion from cliente where  cedula='"
					+ cedula + "'");

			while (rs.next()) {

				txCedula.setText(rs.getString("cedula"));
				txNombre.setText(rs.getString("nombre"));
				txDireccion.setText(rs.getString("direccion"));
				txFecha.setText(rs.getString("fecha_de_nacimiento"));

			}

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

	private boolean buscar2(String cedula) throws SQLException {

		Cliente cliente = null;
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
//			boolean re = stm.execute("select cedula, nombre, direccion from cliente where  cedula='" + cedula + "'");
			rs = stm.executeQuery("select cedula, nombre, fecha_de_nacimiento, direccion from cliente where  cedula='"
					+ cedula + "'");

//				
		} catch (SQLException e) {

		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return rs.next();

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
