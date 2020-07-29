package model;

import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Empleado {
	
	private StringProperty cedula;
	private StringProperty nombre, direccion;
	private StringProperty fecha_De_nacimiento;
	private StringProperty local;
	private StringProperty cargo;
	
	
	public Empleado(String cedula, String nombre, String direccion, String local,
			String cargo) {
	
		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.direccion = new SimpleStringProperty(direccion);
		this.local =new SimpleStringProperty(local);
		this.cargo =new SimpleStringProperty(cargo);
	}
	/**
	 * @return the local
	 */
	public StringProperty getLocal() {
		return local;
	}
	/**
	 * @param local the local to set
	 */
	public void setLocal(StringProperty local) {
		this.local = local;
	}
	/**
	 * @return the cargo
	 */
	public StringProperty getCargo() {
		return cargo;
	}
	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(StringProperty cargo) {
		this.cargo = cargo;
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
	 * @return the fecha_De_nacimiento
	 */
	public StringProperty getFecha_De_nacimiento() {
		return fecha_De_nacimiento;
	}
	/**
	 * @param fecha_De_nacimiento the fecha_De_nacimiento to set
	 */
	public void setFecha_De_nacimiento(StringProperty fecha_De_nacimiento) {
		this.fecha_De_nacimiento = fecha_De_nacimiento;
	}
	
	
	
}
	
	
	