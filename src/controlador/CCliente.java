package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import vista.PCliente;
import vista.PrincipalSubastaCliente;

public class CCliente extends Thread{

	private int puerto;
	private String ip;
	private PrincipalSubastaCliente ventanaCliente;
	private boolean conectado;
	private Socket SCliente;
	
	public CCliente(int puerto, String ip, PrincipalSubastaCliente ventanaCliente){
		this.ip= ip;
		this.puerto=puerto;
		this.ventanaCliente = ventanaCliente;
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
			JOptionPane.showMessageDialog(ventanaCliente, "No se pudo establecer la Conexión ");
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
	
}
