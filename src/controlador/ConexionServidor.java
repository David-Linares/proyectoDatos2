package controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import vista.Principal;
import vista.SubastaVendedor;

import javax.swing.JOptionPane;

public class ConexionServidor extends Thread {

	private int puerto;
	private General general = General.getInstance();
	private SubastaVendedor ventana;
	
	//COnstructor con el TextPane que va a contener los mensajes del servidor.
	public ConexionServidor(int puerto) {
		this.puerto = puerto;
	}
	
	//OK
	@SuppressWarnings("resource")
	public void run() {
		ServerSocket sServidor = null;
		ConexionClienteServidor nuevaConexion = null;
		try {
			sServidor = new ServerSocket(puerto);
			while (true) {
				
					Socket nuevoSServidor = sServidor.accept();
					nuevaConexion = new ConexionClienteServidor(nuevoSServidor);					
					General.setConexionTemp(nuevaConexion);
				
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(ventana, "Ya hay una subasta abierta. \n Por Favor ingresa como cliente.", "Error en creaci\u00f3n de Subasta", JOptionPane.INFORMATION_MESSAGE, general.getIcon("error"));
			System.exit(0);
		try {
            sServidor.close();
        }catch(IOException e2){
         JOptionPane.showMessageDialog(ventana, "CServidor / no se cerró el puerto + e2.getMessage()");	
        }
	
	}
	
	}
}
