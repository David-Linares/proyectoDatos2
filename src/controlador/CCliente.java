package controlador;

import java.net.Socket;

import javax.swing.JOptionPane;

import vista.PCliente;

public class CCliente extends Thread{

	private int puerto;
	private String ip;
	private PCliente ventanaCliente;
	
	public CCliente(int puerto, String ip){
		this.ip= ip;
		this.puerto=puerto;
	}
	public void run(){
		try {
			Socket SCliente= new Socket(ip, puerto);
			JOptionPane.showMessageDialog(ventanaCliente, "Se Ha Podido Conectar el cliente");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaCliente, "No se pudo establecer la Conexión ");
			// TODO: handle exception
		}
	}
	
}
