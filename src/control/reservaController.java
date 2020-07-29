package control;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import conexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cargo;
import model.Cliente;
import model.Local;
import model.Reserva;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class reservaController {

	@FXML
	public TextField txtFecha, txtCliente, txtReporte;

	@FXML
	public Button btnAgregar, btnEliminar, btnModificar, btnBuscar, btnLimpiar, btnVolver, btnCerrar, btnGenerarReporte;

	@FXML
	public Label lbMesa;

	@FXML
	public ComboBox<String> cmbMesas, cmbLocales;

	@FXML
	private ComboBox<String> cmbLocal;

	@FXML
	private ComboBox<String> cmbmesaReservadas;

	@FXML
	public Pane top;

	@FXML
	public TableView<Reserva> tblReservas;

	@FXML
	public TableColumn<Reserva, String> columnFecha, columnMesa, columnCliente, columnReserva;

	@FXML
	private mainController mainController = new mainController();

	private ObservableList<Reserva> reservasObservable;

	private ObservableList<String> mesas;

	private ObservableList<String> locales;

	private ObservableList<String> mesasReservadas;

	public reservaController() throws SQLException {

		reservasObservable = FXCollections.observableArrayList();

		mesas = FXCollections.observableArrayList();

		locales = FXCollections.observableArrayList();

		mesasReservadas = FXCollections.observableArrayList();

		locales = llenarLocales();

		mesasReservadas = llenarMesasReservadas();

		reservasObservable = LlenarReservas();

	}

	@FXML
	private void initialize() {

		cmbMesas.setVisible(false);
		lbMesa.setVisible(false);

		cmbLocales.setItems(locales);
		cmbLocales.getSelectionModel().select("Seleccione el Local");

		cmbmesaReservadas.setItems(mesasReservadas);
		cmbmesaReservadas.getSelectionModel().select("Generar Reporte de mesas reservadas");

		cmbLocal.setItems(locales);
		cmbLocal.getSelectionModel().select("Generar Reporte por local");

		columnReserva.setCellValueFactory(reservaCelda -> reservaCelda.getValue().getIdReserva());
		columnFecha.setCellValueFactory(reservaCelda -> reservaCelda.getValue().getFecha());
		columnCliente.setCellValueFactory(reservaCelda -> reservaCelda.getValue().getIdCedula());
		columnMesa.setCellValueFactory(reservaCelda -> reservaCelda.getValue().getIdMesa());

		tblReservas.setItems(reservasObservable);

	}

	@SuppressWarnings("deprecation")
	@FXML
	private void reporte() {
		String local = cmbLocal.getSelectionModel().getSelectedItem().toString();

		try {
			// Instancia de la clase conexion
			Conexion conexion = new Conexion();
			Connection cn = null;

			cn = (Connection) conexion.conectar();

			Map parametro = new HashMap();
			parametro.put("local", local);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\reportes\\listarReservas.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, cn);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("ReporteReservaPDF.pdf"));
			exporter.exportReport();

			JasperViewer ver = new JasperViewer(jasperPrint);
			ver.setTitle("Reporte Reserva");
			ver.setDefaultCloseOperation(1);
			ver.setVisible(true);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@FXML
	private void reporteFecha() {
		String fecha = txtReporte.getText();

		try {
			// Instancia de la clase conexion
			Conexion conexion = new Conexion();
			Connection cn = null;

			cn = (Connection) conexion.conectar();

			Map parametro = new HashMap();
			parametro.put("fecha", fecha);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\reportes\\reservasPorFecha.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, cn);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("ReporteReservaPorFechaPDF.pdf"));
			exporter.exportReport();

			JasperViewer ver = new JasperViewer(jasperPrint);
			ver.setTitle("Reporte Reserva Por Fecha");
			ver.setDefaultCloseOperation(1);
			ver.setVisible(true);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@FXML
	private void reporteMesas() {
		String local = cmbmesaReservadas.getSelectionModel().getSelectedItem().toString();

		try {
			// Instancia de la clase conexion
			Conexion conexion = new Conexion();
			Connection cn = null;

			cn = (Connection) conexion.conectar();

			Map parametro = new HashMap();
			parametro.put("mesa", local);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\reportes\\reservasMesa.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, cn);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("ReporteMesasReservadasPDF.pdf"));
			exporter.exportReport();

			JasperViewer ver = new JasperViewer(jasperPrint);
			ver.setTitle("Reporte Mesas Reservadas");
			ver.setDefaultCloseOperation(1);
			ver.setVisible(true);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void agregar() throws SQLException {

		String local = cmbLocales.getSelectionModel().getSelectedItem().toString();

		if (!local.equalsIgnoreCase("Seleccione el local")) {

			String fecha = "";
			String cedula = "";
			String Mesa = cmbMesas.getSelectionModel().getSelectedItem().toString();

			if (!txtFecha.getText().equalsIgnoreCase("AA-MM-DD")) {

				if (!txtFecha.getText().isEmpty()) {

					if (!txtCliente.getText().isEmpty()) {

						if (buscar2(txtCliente.getText()) == true) {

							if (!Mesa.equalsIgnoreCase("Seleccione la Mesa")) {

								if (validarFecha(Mesa, txtFecha.getText()) == false) {

									Mesa = cmbMesas.getSelectionModel().getSelectedItem().toString();
									fecha = txtFecha.getText();
									cedula = txtCliente.getText();
									agregar2(fecha, cedula, Mesa);
									txtFecha.setText("");
									txtCliente.setText("");
									cmbMesas.setVisible(false);
									lbMesa.setVisible(false);

								} else {
									JOptionPane.showMessageDialog(null, "Esta Mesa ya esta reservada");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Debe Seleciona la Mesa");
							}

						} else {
							JOptionPane.showMessageDialog(null, "El cliente no se encuentra registrado");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Ingrese la cedula del cliente");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe Ingresar la fecha de la reserva");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Debe Ingresar una fecha de reserva valida");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione el local");
		}

	}

	public void agregar2(String fecha, String cedula, String Mesa) {

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
			boolean re = stm.execute("INSERT INTO reserva (fecha, Cliente_cedula,Mesa_idMesa) VALUES ('" + fecha + "','"
					+ cedula + "','" + Mesa + "')");
			rs = stm.executeQuery("SELECT * FROM cliente");
			reservasObservable.clear();
			LlenarReservas();
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
	private void actualizar() throws HeadlessException, SQLException {

		String local = cmbLocales.getSelectionModel().getSelectedItem().toString();

		if (!local.equalsIgnoreCase("Seleccione el local")) {

			String fecha = "";
			String cedula = "";
			String Mesa = cmbMesas.getSelectionModel().getSelectedItem().toString();

			if (!txtFecha.getText().equalsIgnoreCase("AA-MM-DD")) {

				if (!txtFecha.getText().isEmpty()) {

					if (!txtCliente.getText().isEmpty()) {

						if (buscar2(txtCliente.getText()) == true) {

							if (!Mesa.equalsIgnoreCase("Seleccione la Mesa")) {

								if (validarFecha(Mesa, txtFecha.getText()) == false) {

									Mesa = cmbMesas.getSelectionModel().getSelectedItem().toString();
									fecha = txtFecha.getText();
									cedula = txtCliente.getText();
									actualizar(cedula, fecha, Mesa);
									txtFecha.setText("");
									txtCliente.setText("");
									cmbMesas.setVisible(false);
									lbMesa.setVisible(false);

								} else {
									JOptionPane.showMessageDialog(null, "Esta Mesa ya esta reservada");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Debe Seleciona la Mesa");
							}

						} else {
							JOptionPane.showMessageDialog(null, "El cliente no se encuentra registrado");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Ingrese la cedula del cliente");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe Ingresar la fecha de la reserva");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Debe Ingresar una fecha de reserva valida");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione el local");
		}
	}

	private void actualizar(String cedula, String fecha, String mesa) {

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
			boolean re = stm.execute("UPDATE  reserva set " + "fecha ='" + fecha + "'," + "Mesa_idMesa='" + mesa + "'"
					+ "WHERE Cliente_cedula=" + cedula + "");
			rs = stm.executeQuery("SELECT * FROM reserva");
			reservasObservable.clear();
			LlenarReservas();
			initialize();
			txtCliente.setText("");
			txtFecha.setText("");
			cmbMesas.setVisible(false);
			lbMesa.setVisible(false);

			cmbLocales.setItems(locales);
			cmbLocales.getSelectionModel().select("Seleccione el Local");

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

		if (!txtCliente.getText().isEmpty()) {

			if (!txtFecha.getText().isEmpty()) {

				String cedula = txtCliente.getText();

				String fecha = txtFecha.getText();

				// Instancia de la clase conexion
				Conexion conexion = new Conexion();
				Connection cn = null;

				// Objeto que nos permite crear las sentencias SQL
				Statement stm = null;
				// Asignamos la referencia a la coneccion a la variable cn
				cn = (Connection) conexion.conectar();

				ResultSet rs = null;
				ResultSet rs1 = null;
				ResultSet rs2 = null;

				try {
					stm = (Statement) cn.createStatement();
					// Guardando en la variable rs el resultado de la consulta (en este caso la
					// tabla usuario)
//			boolean re = stm.execute("select cedula, nombre, direccion from cliente where  cedula='" + cedula + "'");
					rs = stm.executeQuery(
							"select fecha, Mesa_idMesa, local from reserva inner join mesa inner join local  where  Cliente_cedula='"
									+ cedula + "'and fecha='" + fecha + "'");

					if (rs.next() == true) {

						while (rs.next()) {

							txtFecha.setText(rs.getString("fecha"));
							txtCliente.setText(cedula);
							cmbMesas.setVisible(true);
							lbMesa.setVisible(true);
							cmbLocales.getSelectionModel().select(rs.getString("local"));
							cmbMesas.getSelectionModel().select(rs.getString("Mesa_idMesa"));

						}
					} else {

						JOptionPane.showMessageDialog(null, "No tiene reservas para esta fecha");
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

			} else {
				JOptionPane.showMessageDialog(null, "Debe ingresar la fecha");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar la cedula de un cliente");
		}

	}

	@FXML
	private void eliminar() {

		if (!txtFecha.getText().isEmpty()) {

			if (!txtCliente.getText().isEmpty()) {

				String cedula = txtCliente.getText();
				String fecha = txtFecha.getText();

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
							"DELETE FROM reserva WHERE Cliente_cedula='" + cedula + "' and fecha='" + fecha + "'");
					rs = stm.executeQuery("SELECT * FROM reserva");

					reservasObservable.clear();
					LlenarReservas();
					initialize();
					txtCliente.setText("");
					txtFecha.setText("");
					cmbMesas.setVisible(false);
					lbMesa.setVisible(false);

					cmbLocales.setItems(locales);
					cmbLocales.getSelectionModel().select("Seleccione el Local");
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
				JOptionPane.showMessageDialog(null, "Debe ingresar la cedula ");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar la fecha ");
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

	private boolean validarFecha(String mesa, String fecha) {

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

			String query = "select fecha from reserva where  Mesa_idMesa='" + mesa + "'";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				if (rs.getString("fecha").equalsIgnoreCase(fecha)) {
					return true;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return false;

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

				locales.add(rs.getString("local").toString());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return locales;

	}

	private ObservableList<String> llenarMesasReservadas() {

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

			String query = "SELECT * FROM chocolate.mesa ";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				mesasReservadas.add(rs.getString("idMesa").toString());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return mesasReservadas;

	}

	@FXML
	private ObservableList<String> llenarmesas() throws SQLException {

		String local = cmbLocales.getSelectionModel().getSelectedItem().toString();

		String local1 = buscarLocal(local);

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

			String query = "select idMesa from mesa where  Local_idlocal='" + local1 + "'";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			mesas.clear();

			while (rs.next()) {

				mesas.add(rs.getString("idMesa").toString());

			}

			cmbMesas.setItems(mesas);
			cmbMesas.getSelectionModel().select("Seleccione la Mesa");
			cmbMesas.setVisible(true);
			lbMesa.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return mesas;

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
	private void registrar(String path) {
		mainController.abrirStage((Stage) top.getScene().getWindow(), path);
	}

	@FXML
	private ObservableList<Reserva> LlenarReservas() {

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

			String query = "SELECT * FROM chocolate.reserva";

			boolean re = stm.execute(query);

			rs = stm.executeQuery(query);

			while (rs.next()) {

				reservasObservable.add(new Reserva(rs.getString("idReserva"), rs.getString("fecha"),
						rs.getString("Cliente_cedula"), rs.getString("Mesa_idMesa")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return reservasObservable;

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
