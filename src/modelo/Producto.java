package modelo;

import java.io.Serializable;

public class Producto implements Serializable {
	
	private static final long serialVersionUID = 4697806040116104218L;
	/*ATRIBUTOS*/
	private int id;
	private String nombre;
	private String descripcion;
	private long valor;
	
	/*CONSTRUCTOR DE LA CLASE*/
	public Producto() {
		super();
	}
	/*CONSTRUCTOR DE LA CLASE*/
	public Producto(int id, String nombre, long valor, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;		
		this.valor = valor;
		this.descripcion= descripcion;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}


	public String toString() {
		return id+". " + nombre + " : " + valor;
	}
	
	
}
