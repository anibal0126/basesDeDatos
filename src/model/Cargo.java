package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cargo {

	private StringProperty id;
	private StringProperty nombre;

	public Cargo(String nombre) {

		this.nombre = new SimpleStringProperty("nombre");
	}

	public Cargo( String id, String nombre) {

		this.nombre = new SimpleStringProperty(nombre);
		this.id = new SimpleStringProperty(id);
	}

	/**
	 * @return the id
	 */
	public StringProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(StringProperty id) {
		this.id = id;
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

	

}
