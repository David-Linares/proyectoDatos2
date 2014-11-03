package controlador;

import java.net.ServerSocket;
import java.net.Socket;

import vista.PrincipalSubastaVendedor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CServidor extends Thread {

	private int puerto;
	General general = General.getInstance();
	PrincipalSubastaVendedor ventana;
	
	//COnstructor con el TextPane que va a contener los mensajes del servidor.
	public CServidor(int puerto) {
		this.puerto = puerto;
	}
	
	//OK
	public void run() {
		ServerSocket sServidor = null;
		Conexion nuevaConexion = null;
		try {
			sServidor = new ServerSocket(puerto);
			while (true) {
				Socket nuevoSServidor = sServidor.accept();
				nuevaConexion = new Conexion(nuevoSServidor);
				general.nuevaConexion(nuevaConexion);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventana, "CServidor / Error al Abrir el Puerto"+e.getMessage());
		}
		try {
			sServidor.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "CServidor / Se produjo un error al cerrar el puerto "+e.getMessage());
		}
	}

}
