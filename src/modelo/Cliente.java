package modelo;

import java.io.Serializable;



public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1827287870763275550L;
	
	private String nombre;
	private double monto;
		
	public Cliente() {
		super();
	}

	public Cliente(String nombre, double monto) {
		super();
		this.nombre = nombre;
		this.monto = monto;
			}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	
}
