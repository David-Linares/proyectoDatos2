package controlador;

import java.net.ServerSocket;
import java.net.Socket;

import vista.PVendedor;
import vista.PrincipalSubastaVendedor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class CServidor extends Thread {

	private int puerto;
	General general = General.getInstance();
	PrincipalSubastaVendedor ventana;

	private JTextPane tpMensajesSubasta;
	
	public CServidor(int puerto) {
		super();
		this.puerto = puerto;

	}
	public CServidor(JTextPane tpMensajesSubasta) {
		this.tpMensajesSubasta=tpMensajesSubasta;
		general.getConexiones();
	}
	//OK
	public void run() {
		ServerSocket sServidor = null;
		Conexion nuevaConexion = null;
		try {
			sServidor = new ServerSocket(puerto);
			nuevaConexion = new Conexion(sServidor.accept(), tpMensajesSubasta);
			while (true) {
				Socket nuevoSServidor = sServidor.accept();
				nuevaConexion.start();
				general.nuevaConexion(new Conexion(nuevoSServidor, tpMensajesSubasta));
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
