package model;

import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reserva {

	private StringProperty idReserva, fecha, idCedula, idMesa;

	public Reserva(String idReserva, String fecha, String idCedulaCLiente, String idMesa) {

		this.idReserva = new SimpleStringProperty(idReserva);
		this.fecha = new SimpleStringProperty(fecha);
		this.idCedula = new SimpleStringProperty(idCedulaCLiente);
		this.idMesa = new SimpleStringProperty(idMesa);

	}

	/**
	 * @return the idReserva
	 */
	public StringProperty getIdReserva() {
		return idReserva;
	}

	/**
	 * @param idReserva the idReserva to set
	 */
	public void setIdReserva(StringProperty idReserva) {
		this.idReserva = idReserva;
	}

	/**
	 * @return the idMesa
	 */
	public StringProperty getIdMesa() {
		return idMesa;
	}

	/**
	 * @param idMesa the idMesa to set
	 */
	public void setIdMesa(StringProperty idMesa) {
		this.idMesa = idMesa;
	}

	/**
	 * @return the fecha
	 */
	public StringProperty getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(StringProperty fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the idCedula
	 */
	public StringProperty getIdCedula() {
		return idCedula;
	}

	/**
	 * @param idCedula the idCedula to set
	 */
	public void setIdCedula(StringProperty idCedula) {
		this.idCedula = idCedula;
	}

}
