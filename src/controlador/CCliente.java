package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import modelo.Cliente;
import vista.PrincipalSubastaCliente;

public class CCliente extends Thread{
	
	private Cliente clienteConectado;
	private int puerto;
	private String ip;
	private PrincipalSubastaCliente ventanaCliente;
	private boolean conectado;
	private Socket SCliente;
	
	public CCliente(int puerto, String ip, Cliente clienteConectado){
		this.ip= ip;
		this.puerto=puerto;
		this.clienteConectado = clienteConectado;
	}
	public void run(){
		try {
			SCliente= new Socket(ip, puerto);
			ObjectInputStream entrada = new ObjectInputStream(SCliente.getInputStream());
			enviarDatos(1, clienteConectado);
			conectado = true;
			while(conectado){
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				switch (operacion){
					case 1:
						ventanaCliente.agregarNuevo(eMensaje);
						break;
					case 2:
						ventanaCliente.mensajeRecibido((String) eMensaje);
						break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getCause());
			JOptionPane.showMessageDialog(ventanaCliente, "No se pudo establecer la Conexi�n "+e.getMessage());
			// TODO: handle exception
		}
	}
	
	public void enviarMensaje(String sMensaje){
		enviarDatos(2, sMensaje);
	}
	
	public void enviarDatos(int operacion, Object valor){
		try {
			DataOutputStream salida = new DataOutputStream(SCliente.getOutputStream());
			salida.writeInt(operacion);
			salida.writeUTF((String) valor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaCliente, "Se produjo un error al enviar el mensaje");
		}
	}
	public Cliente getClienteConectado(){
		return this.clienteConectado;
	}
	public PrincipalSubastaCliente getVentanaCliente() {
		return ventanaCliente;
	}
	public void setVentanaCliente(PrincipalSubastaCliente ventanaCliente) {
		this.ventanaCliente = ventanaCliente;
	}
	
}
