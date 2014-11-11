package modelo;

import java.io.Serializable;



public class Cliente implements Serializable {

	private static final long serialVersionUID = -1827287870763275550L;
	/*ATRIBUTOS*/
	private String nombre;
	private long monto;
	
	/*CONSTRUCTOR DE LA CLSE*/
	public Cliente() {
		super();
	}
	/*CONSTRUCTOR DE LA CLSE*/
	public Cliente(String nombre, long monto) {
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

	public long getMonto() {
		return monto;
	}

	public void setMonto(long monto) {
		this.monto = monto;
	}

	
}
