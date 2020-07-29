package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Turno {

	private StringProperty id, descripcion, horaFin, horaInicio;

	public Turno(String id, String descripcion, String horaInicio, String horaFin) {

		this.id = new SimpleStringProperty(id);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.horaFin = new SimpleStringProperty(horaFin);
		this.horaInicio = new SimpleStringProperty(horaInicio);
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
	 * @return the descripcion
	 */
	public StringProperty getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(StringProperty descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the horaFin
	 */
	public StringProperty getHoraFin() {
		return horaFin;
	}

	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(StringProperty horaFin) {
		this.horaFin = horaFin;
	}

	/**
	 * @return the horaInicio
	 */
	public StringProperty getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(StringProperty horaInicio) {
		this.horaInicio = horaInicio;
	}

}
