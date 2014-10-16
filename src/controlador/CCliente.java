package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	public CCliente(int puerto, String ip, PrincipalSubastaCliente ventanaCliente, Cliente clienteConectado){
		this.ip= ip;
		this.puerto=puerto;
		this.ventanaCliente = ventanaCliente;
		this.clienteConectado = clienteConectado;
	}
	public void run(){
		try {
			SCliente= new Socket(ip, puerto);
			DataInputStream entradamsj = new DataInputStream(SCliente.getInputStream());
			conectado = true;
			while(conectado){
				String eMensaje = entradamsj.readUTF();
				ventanaCliente.mensajeRecibido(eMensaje);
			}
		} catch (Exception e) {
			System.out.println(e.getCause());
			JOptionPane.showMessageDialog(ventanaCliente, "No se pudo establecer la Conexi�n "+e.getMessage());
			// TODO: handle exception
		}
	}
	
	public void enviarMensaje(String sMensaje){
		try {
			DataOutputStream salidamsj = new DataOutputStream(SCliente.getOutputStream());
			salidamsj.writeUTF(sMensaje);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaCliente, "Se produjo un error al enviar el mensaje");
		}
	}
	public Cliente getClienteConectado(){
		return this.clienteConectado;
	}
}
