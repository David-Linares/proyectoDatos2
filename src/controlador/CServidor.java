package controlador;

import java.net.ServerSocket;
import java.net.Socket;

import vista.PVendedor;
import vista.PrincipalSubastaVendedor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CServidor extends Thread {

	private int puerto;
	General general = General.getInstance();
	PrincipalSubastaVendedor ventana;

	public CServidor(int puerto) {
		super();
		this.puerto = puerto;

	}
	//OK
	public void run() {
		ServerSocket sServidor = null;
		try {
			sServidor = new ServerSocket(puerto);
			while (true) {
				Socket nuevoSServidor = sServidor.accept();
				general.nuevaConexion(new Conexion(nuevoSServidor));
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
