package modelo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JComponent;

import vista.PrincipalSubastaCliente;
import controlador.General;

@SuppressWarnings("serial")
public class Cliente extends Thread implements Serializable {
	
	private String ip;
	private int puerto;
	private String nombre;
	private double monto;
	private Socket cliente;
	private boolean conectado;
	private PrincipalSubastaCliente ventana;
	
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
	public Cliente(String ip, String nombre, double monto, PrincipalSubastaCliente ventanaCliente) {
		super();
		this.ip = ip;
		this.puerto = 9081;
		this.nombre = nombre;
		this.monto = monto;
		this.ventana = ventanaCliente;
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
		
	public boolean isConectado() {
		return conectado;
	}
	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}
	public void run(){
		try{
			cliente = new Socket(General.ipServidor, General.puerto);
			ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
			enviarDatos(1, this);
			setConectado(true);
			while (isConectado()) {
				int codigoEntrada = entrada.readInt();
				Object objetoEntrada = entrada.readObject();
				switch(codigoEntrada){
				case 1:
					General.clientesConectados.add((Cliente) objetoEntrada);
					this.ventana.agregarNuevo((Cliente) objetoEntrada);
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Se ha producido un error en el modelo" + e.getMessage());
		}
	}
	
	public void enviarDatos(int codigo, Object dato){
		try {
			ObjectOutputStream salida = new ObjectOutputStream(getCliente().getOutputStream());
			salida.writeInt(codigo);
			salida.writeObject(dato);
		} catch (Exception e) {
			System.out.println("Se produjó un error en Modelo " + e.getMessage());
		}
	}

}
