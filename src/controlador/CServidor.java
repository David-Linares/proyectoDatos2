package controlador;

import java.net.ServerSocket;
import java.net.Socket;

import vista.PrincipalSubastaVendedor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class CServidor extends Thread {

	private int puerto;
	General general = General.getInstance();
	PrincipalSubastaVendedor ventana;
	private JTextPane tpMensajesSubasta;
	
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
			
			//Quité el método start() de nuevaConexión, porque esa no arranca acá, vamos a manejar una conexión
			//en la clase general que es para tener la conexión del servidor, y de esta forma no confundirlo con un cliente.
			//aunque tenga las mismas funciones de un cliente, no es cliente, porque no tiene nombre, ni monto.
			
			//Lo que hay que hacer, es crear una variable de tipo conexión en general para almacenar esta 
			//variable nuevaConexión que creaste arriba.
			//(Leer PrincipalSubastaVendedor Linea 108)			
			while (true) {
				//Se queda esperando la conexi�n de un nuevo cliente.
				Socket nuevoSServidor = sServidor.accept();
				System.out.println("Se conect� un nuevo cliente");
				nuevaConexion = new Conexion(nuevoSServidor, general.getPanelSubastaCliente());
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
