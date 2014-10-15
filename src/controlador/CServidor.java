package controlador;

import java.net.ServerSocket;
import java.net.Socket;

import vista.PVendedor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class CServidor extends Thread{

	private int puerto;
	private JFrame ventana;
	
	
	public CServidor(int puerto) {
		super();
		this.puerto = puerto;
		
	}


	public void run(){
	ServerSocket sServidor= null;
	try {
		sServidor= new ServerSocket(puerto);
		Socket nuevoSServidor = sServidor.accept();
		JOptionPane.showMessageDialog(ventana, "Se ha conectado el servidor");
		
	} catch (Exception e) {
		JOptionPane.showMessageDialog(ventana, "Error al Abrir el Puerto");
	}
	try {
		sServidor.close();
	} catch (Exception e) {
		// TODO: handle exception
	}
	}
	
	
}
