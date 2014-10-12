package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cliente implements Serializable {
	
	private String ip;
	private int puerto;
	private String nombre;
	private double monto;

	public int getPuerto() {
		return puerto;
	}
	public Cliente() {
		super();
	}
	public Cliente(String ip, int puerto, String nombre, double monto) {
		super();
		this.ip = ip;
		this.puerto = puerto;
		this.nombre = nombre;
		this.monto = monto;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
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
