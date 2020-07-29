package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Local {

	private StringProperty id_Local;
	private StringProperty nombre, direccion;

	public Local(String id_Local, String nombre, String direccion) {
		super();
		this.id_Local = new SimpleStringProperty(id_Local);
		this.nombre = new SimpleStringProperty(nombre);
		this.direccion = new SimpleStringProperty(direccion);
	}

	/**
	 * @return the id_Local
	 */
	public StringProperty getId_Local() {
		return id_Local;
	}

	/**
	 * @param id_Local the id_Local to set
	 */
	public void setId_Local(StringProperty id_Local) {
		this.id_Local = id_Local;
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
}
