package control;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import conexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cargo;
import model.Cliente;
import model.Empleado;
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

public class empleadoController {

	@FXML
	public Pane top;

	@FXML
	public Button btnAgrgar, btnEliminar, btnActualizar, btnBuscar, btnLimpiar, brnSalir, btnVolver;
	@FXML
	public TextField txtCedula, txtNombre, txtDireccion, txtLocal, txtbuscar, txtFecha;
	@FXML
	public ComboBox<String> cmbCargo;

	@FXML
	private ComboBox<String> cmbLocal;

	@FXML
	private ComboBox<String> cmbListar;

	@FXML
	private ComboBox<String> cmbListarPorCargos;

	/**
	 * Tabla donde se almacena el listado de familias de plantas
	 */
	@FXML
	private TableView<Empleado> tblEmpleados;

	@FXML
	private TableColumn<Empleado, String> ColumnNombre;
	/**
	 * Columna donde se almacena el codigo
	 */
	@FXML
	private TableColumn<Empleado, String> ColumnCedula;

	// Columna donde se almacena el nombre

	@FXML
	private TableColumn<Empleado, String> ColumnDireccion;

	@FXML
	private TableColumn<Empleado, String> ColumnLocal;

	@FXML
	private TableColumn<Empleado, String> ColumnCargo;

	private ObservableList<Empleado> empleadosObservable;

	private ObservableList<String> Locales;

	private ObservableList<String> Cargos;

	private ObservableList<String> CargosL;

	@FXML
	private mainController mainController = new mainController();

	public empleadoController() throws SQLException {

		empleadosObservable = FXCollections.observableArrayList();

		Locales = FXCollections.observableArrayList();

		Cargos = FXCollections.observableArrayList();

		Cargos = llenarCargos();

		Locales = llenarLocales();

		empleadosObservable = LlenarEmpleado();

	}

	@FXML
	private void initialize() {

		ColumnCedula.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getCedula());
		ColumnNombre.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getNombre());
		ColumnDireccion.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getDireccion());
		ColumnCargo.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getCargo());
		ColumnLocal.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getLocal());

		tblEmpleados.setItems(empleadosObservable);

		cmbLocal.setItems(Locales);
		cmbLocal.getSelectionModel().select("Seleccione el Local");

		cmbCargo.setItems(Cargos);
		cmbCargo.getSelectionModel().select("Seleccione el Cargo");

		cmbListarPorCargos.setItems(Cargos);
		cmbListarPorCargos.getSelectionModel().select("Generar Reporte por Cargo");

		cmbListar.setItems(Locales);
		cmbListar.getSelectionModel().select("Generar Reporte por local");

		txtFecha.setText("AA-MM-DD");

	}

	@FXML
	private void listarPorLocal() throws SQLException, FileNotFoundException {

		String local = cmbListar.getSelectionModel().getSelectedItem().toString();

		String local1 = buscarLocal(local);

		// Instancia de la clase conexion
		Conexion conexion = new Conexion();
		Connection cn = null;

		// Objeto que nos permite crear las sentencias SQL
		Statement stm = null;
		// Asignamos la referencia a la coneccion a la variable cn
		cn = (Connection) conexion.conectar();

		ResultSet rs = null;
		empleadosObservable.clear();

		try {

			stm = (Statement) cn.createStatement();
			// Guardando en la variable rs el resultado de la consulta (en este caso la
			// tabla usuario)

			String query = "select cedula, nombre, fecha_de_nacimiento, direccion, Local_idlocal, Cargo_idCargo from empleado where  Local_idlocal='"
					+ local1 + "'";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				empleadosObservable.add(new Empleado(rs.getString("cedula"), rs.getString("nombre"),
						rs.getString("direccion"), rs.getString("Local_idlocal"), rs.getString("Cargo_idCargo")));

			}

			tblEmpleados.setItems(empleadosObservable);
			reporte(local);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// documento.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@FXML
	private void listarPorCargo() throws SQLException, FileNotFoundException {

		String cargo = cmbListarPorCargos.getSelectionModel().getSelectedItem().toString();

		String cargo1 = buscarCargo(cargo);

		// Instancia de la clase conexion
		Conexion conexion = new Conexion();
		Connection cn = null;

		// Objeto que nos permite crear las sentencias SQL
		Statement stm = null;
		// Asignamos la referencia a la coneccion a la variable cn
		cn = (Connection) conexion.conectar();

		ResultSet rs = null;
		empleadosObservable.clear();

		try {

			stm = (Statement) cn.createStatement();
			// Guardando en la variable rs el resultado de la consulta (en este caso la
			// tabla usuario)

			String query = "select cedula, nombre, fecha_de_nacimiento, direccion, Local_idlocal, Cargo_idCargo from empleado where  Cargo_idCargo='"
					+ cargo1 + "'";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				empleadosObservable.add(new Empleado(rs.getString("cedula"), rs.getString("nombre"),
						rs.getString("direccion"), rs.getString("Local_idlocal"), rs.getString("Cargo_idCargo")));

			}

			tblEmpleados.setItems(empleadosObservable);
			reporteCargo(cargo);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// documento.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@SuppressWarnings("deprecation")
	@FXML
	private void reporteCargo(String cargo) {
		try {
			// Instancia de la clase conexion
			Conexion conexion = new Conexion();
			Connection cn = null;

			cn = (Connection) conexion.conectar();

			Map parametro = new HashMap();
			parametro.put("cargo", cargo);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\reportes\\listarPorCargo.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, cn);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("ReportePorCargoPDF.pdf"));
			exporter.exportReport();

			JasperViewer ver = new JasperViewer(jasperPrint);
			ver.setTitle("Reporte Cargo");
			ver.setDefaultCloseOperation(1);
			ver.setVisible(true);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@FXML
	private void reporte(String local) {
		try {
			// Instancia de la clase conexion
			Conexion conexion = new Conexion();
			Connection cn = null;

			cn = (Connection) conexion.conectar();

			Map parametro = new HashMap();
			parametro.put("local", local);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\reportes\\EmpleadosPorLocal.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, cn);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("ReportePorLocalPDF.pdf"));
			exporter.exportReport();

			JasperViewer ver = new JasperViewer(jasperPrint);
			ver.setTitle("Reporte Local");
			ver.setVisible(true);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private ObservableList<Empleado> LlenarEmpleado() {

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

			String query = "SELECT * FROM chocolate.empleado ";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				empleadosObservable.add(new Empleado(rs.getString("cedula"), rs.getString("nombre"),
						rs.getString("direccion"), rs.getString("Local_idlocal"), rs.getString("Cargo_idCargo")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return empleadosObservable;

	}

	private ObservableList<String> llenarCargos() {

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

			String query = "SELECT * FROM chocolate.cargo ";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				Cargos.add(rs.getString("nombre").toString());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return Cargos;

	}

	@FXML
	private void limpiar() {

		txtCedula.setText("");
		txtNombre.setText("");
		txtDireccion.setText("");
		txtFecha.setText("AA-MM-DD");

		cmbLocal.setItems(Locales);
		cmbLocal.getSelectionModel().select("Seleccione el Local");

		cmbCargo.setItems(Cargos);
		cmbCargo.getSelectionModel().select("Seleccione el Cargo");

		cmbListar.setItems(Locales);
		cmbListar.getSelectionModel().select("listar por Local");

		empleadosObservable.clear();
		LlenarEmpleado();
		initialize();
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
	private void Actualizar() throws SQLException {

		String cedula = txtCedula.getText();
		String nombre = txtNombre.getText();
		String direccion = txtDireccion.getText();
		String fecha = txtFecha.getText();
		String Local = cmbLocal.getSelectionModel().getSelectedItem().toString();
		String Cargo = cmbCargo.getSelectionModel().getSelectedItem().toString();

		if (!txtNombre.getText().isEmpty()) {

			if (!txtFecha.getText().equalsIgnoreCase("AA-MM-DD")) {

				if (!txtFecha.getText().isEmpty()) {

					if (!txtDireccion.getText().isEmpty()) {

						if (!Cargo.equalsIgnoreCase("Seleccione el Cargo")) {

							if (!Local.equalsIgnoreCase("Seleccione el Local")) {

								String local1 = buscarLocal(Local);
								String cargo1 = buscarCargo(Cargo);

								actualizar(cedula, nombre, direccion, fecha, local1, cargo1);

								txtCedula.setText("");
								txtNombre.setText("");
								txtDireccion.setText("");
								txtFecha.setText("");

								cmbLocal.setItems(Locales);
								cmbLocal.getSelectionModel().select("Seleccione el Local");

								cmbCargo.setItems(Cargos);
								cmbCargo.getSelectionModel().select("Seleccione el Cargo");

							} else {
								JOptionPane.showMessageDialog(null, "Debe Seleciona El local");
							}

						} else {
							JOptionPane.showMessageDialog(null, "Debe Seleciona El cargo");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Debe Ingresar la Direccion");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe Ingresar la fecha de nacimiento ");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Debe Ingresar una fecha de nacimiento valida");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
		}

	}

	private void actualizar(String cedula, String nombre, String direccion, String fecha, String local, String cargo) {

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
			boolean re = stm.execute("UPDATE  empleado set " + "nombre ='" + nombre + "'," + "direccion='" + direccion
					+ "'," + "fecha_de_nacimiento='" + fecha + "'," + "Local_idlocal='" + local + "',"
					+ "Cargo_idCargo='" + cargo + "'" + "WHERE cedula=" + cedula + "");
			rs = stm.executeQuery("SELECT * FROM cliente");
			empleadosObservable.clear();
			LlenarEmpleado();
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
	private void agregar() throws SQLException {

		String nombre = "";
		String cedula = "";
		String fecha_De_Nacimiento = "";
		String direccion = "";
		String Local = cmbLocal.getSelectionModel().getSelectedItem().toString();
		String Cargo = cmbCargo.getSelectionModel().getSelectedItem().toString();

		if (!txtCedula.getText().isEmpty()) {

			if (buscar2(txtCedula.getText()) == false) {

				if (!txtNombre.getText().isEmpty()) {

					if (!txtFecha.getText().equalsIgnoreCase("AA-MM-DD")) {

						if (!txtFecha.getText().isEmpty()) {

							if (!txtDireccion.getText().isEmpty()) {

								if (!Cargo.equalsIgnoreCase("Seleccione el Cargo")) {

									if (!Local.equalsIgnoreCase("Seleccione el Local")) {

										Local = cmbLocal.getSelectionModel().getSelectedItem().toString();
										String local = buscarLocal(Local);
										fecha_De_Nacimiento = txtFecha.getText();
										nombre = txtNombre.getText();
										cedula = txtCedula.getText();
										direccion = txtDireccion.getText();
										Cargo = cmbCargo.getSelectionModel().getSelectedItem().toString();
										String cargo = buscarCargo(Cargo);
										agregar2(cedula, nombre, fecha_De_Nacimiento, direccion, cargo, local);

									} else {
										JOptionPane.showMessageDialog(null, "Debe Seleciona El local");
									}

								} else {
									JOptionPane.showMessageDialog(null, "Debe Seleciona El cargo");
								}

							} else {
								JOptionPane.showMessageDialog(null, "Debe Ingresar la Direccion");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Debe Ingresar la fecha de nacimiento ");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Debe Ingresar una fecha de nacimiento valida");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Este empleado ya esta registrado");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Debe Ingresar la cedula");
		}

	}

	public void agregar2(String cedula, String nombre, String fecha_De_Nacimiento, String direccion, String cargo,
			String local) {
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
			boolean re = stm.execute(
					"INSERT INTO empleado (cedula,nombre,fecha_de_nacimiento,direccion,Local_idlocal,Cargo_idCargo) VALUES ('"
							+ cedula + "','" + nombre + "','" + fecha_De_Nacimiento + "','" + direccion + "','" + local
							+ "','" + cargo + "')");
			rs = stm.executeQuery("SELECT * FROM empleado");

			txtCedula.setText("");
			txtNombre.setText("");
			txtDireccion.setText("");
			txtFecha.setText("");
			txtbuscar.setText("");
			empleadosObservable.clear();
			LlenarEmpleado();
			initialize();
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
			rs = stm.executeQuery("select cedula, nombre from empleado where  cedula='" + cedula + "'");

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
	private void eliminar() {

		String cedula = txtCedula.getText();

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
			boolean re = stm.execute("DELETE FROM empleado WHERE cedula='" + cedula + "'");
			rs = stm.executeQuery("SELECT * FROM empleado");

			txtCedula.setText("");
			txtNombre.setText("");
			txtDireccion.setText("");
			txtFecha.setText("");
			txtbuscar.setText("");

			empleadosObservable.clear();
			LlenarEmpleado();
			initialize();
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
	private void buscar() throws SQLException {
		String cedula = txtbuscar.getText();
		Empleado empleado = null;
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
			boolean re = stm.execute(
					"select cedula, nombre,fecha_de_nacimiento, direccion,Local_idlocal, Cargo_idCargo from empleado where  cedula='"
							+ cedula + "'");
			rs = stm.executeQuery("select cedula, nombre,fecha_de_nacimiento, direccion from empleado where  cedula='"
					+ cedula + "'");

			while (rs.next()) {

				txtCedula.setText(rs.getString("cedula"));
				txtNombre.setText(rs.getString("nombre"));
				txtDireccion.setText(rs.getString("direccion"));
				txtFecha.setText(rs.getString("fecha_de_nacimiento"));

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
	private String buscarCargo(String nombre) throws SQLException {

		Cargo cargo = null;

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
			boolean re = stm.execute("select idCargo,nombre from cargo where  nombre='" + nombre + "'");
			rs = stm.executeQuery("select idCargo,nombre from cargo where  nombre='" + nombre + "'");

			while (rs.next()) {

				id = rs.getString("idCargo");

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
	private void registrar(String path) {
		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
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
