package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

	private StringProperty cedula;
	private StringProperty nombre, direccion;
	private StringProperty fecha_nacimiento;

	public Cliente(String cedula, String nombre, String direccion) {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.direccion = new SimpleStringProperty(direccion);
	}

	public Cliente(String cedula, String nombre, String direccion, String fecha) {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.direccion = new SimpleStringProperty(direccion);
		this.fecha_nacimiento = new SimpleStringProperty(df.format(fecha_nacimiento));
	}

	/**
	 * @return the cedula
	 */
	public StringProperty getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(StringProperty cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombre
	 */
	public StringProperty getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the direccion
	 */
	public StringProperty getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(StringProperty direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the fecha_nacimiento
	 */
	public StringProperty getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	/**
	 * @param fecha_nacimiento the fecha_nacimiento to set
	 */
	public void setFecha_nacimiento(StringProperty fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

}