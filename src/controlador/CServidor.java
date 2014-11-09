package controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import vista.Principal;
import vista.PrincipalSubastaVendedor;
import javax.swing.JOptionPane;

public class CServidor extends Thread {

	private int puerto;
	private General general = General.getInstance();
	private PrincipalSubastaVendedor ventana;
	
	//COnstructor con el TextPane que va a contener los mensajes del servidor.
	public CServidor(int puerto) {
		this.puerto = puerto;
	}
	
	//OK
	@SuppressWarnings("resource")
	public void run() {
		ServerSocket sServidor = null;
		Conexion nuevaConexion = null;
		try {
			sServidor = new ServerSocket(puerto);
			while (true) {
				try {
					Socket nuevoSServidor = sServidor.accept();
					System.out.println("CServidor / Se conectó un nuevo cliente ");
					System.out.println("CServidor / variable Conexion TEMP "+General.getConexionTemp());
					nuevaConexion = new Conexion(nuevoSServidor);					
					General.setConexionTemp(nuevaConexion);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(ventana, "CServidor / Se produjo un error en la nueva conexión");
				}
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(ventana, "Ya hay una subasta abierta. \n Por Favor ingresa como cliente.", "Error en Creación de Subasta", JOptionPane.INFORMATION_MESSAGE, general.getIcon("error"));
			Principal ventanaPrincipal = new Principal();
			ventanaPrincipal.getBtnNuevaSubasta().setEnabled(false);
			ventanaPrincipal.setVisible(true);
			return;
		}
	}

}
