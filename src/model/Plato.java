package model;

public class Plato {
	
	private int Id;
	private double precio;
	private String decripcion;
	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}
	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/**
	 * @return the decripcion
	 */
	public String getDecripcion() {
		return decripcion;
	}
	/**
	 * @param decripcion the decripcion to set
	 */
	public void setDecripcion(String decripcion) {
		this.decripcion = decripcion;
	}
	
	

}
