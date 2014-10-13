package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

@SuppressWarnings("serial")
public class Cliente implements Serializable {
	
	
	private DataInputStream dis;
    private DataOutputStream dos;
    
	private String ip;
	private int puerto;
	private String nombre;
	private double monto;
	private Socket cliente;
	
	public Socket getCliente() {
		return cliente;
	}
	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}
	public int getPuerto() {
		return puerto;
	}
	public Cliente() {
		super();
	}
	public Cliente(String ip, String nombre, double monto) {
		super();
		this.ip = ip;
		this.puerto = 9081;
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
	
	public void enviarTrama(int nCodigo, String sTrama){
        try{
           dos.writeInt(nCodigo);
           dos.writeUTF(sTrama);
        }catch(Exception e){
        }
    }

}
