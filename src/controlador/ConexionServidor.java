package controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import vista.SubastaVendedor;

import javax.swing.JOptionPane;

public class ConexionServidor extends Thread {

	/*ATRIBUTOS*/
	private int puertoServidor;
	private General general = General.getInstance();
	private SubastaVendedor ventanaSubastaVendedor;
	private ServerSocket sServidor = null;
	
	/*CONSTRUCTOR DE LA CLASE QUE RECIBE UN PUERTO*/
	public ConexionServidor(int puerto) {
		this.puertoServidor = puerto;
	}
	
	/*METODO A LA ESPERA DE MÁS CONEXIONES*/
	public void run() {
		ConexionClienteServidor nuevaConexion = null;
		try {
			sServidor = new ServerSocket(puertoServidor);
			while (true) {
					Socket nuevoSServidor = sServidor.accept();
					nuevaConexion = new ConexionClienteServidor(nuevoSServidor);
					General.setConexionTemp(nuevaConexion);
					General.nuevaConexionTemp(nuevaConexion);
				
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(ventanaSubastaVendedor, "Ya hay una subasta abierta. \n Por Favor ingresa como cliente.", "Error en creaci\u00f3n de Subasta", JOptionPane.INFORMATION_MESSAGE, general.getIcon("error"));
			System.exit(0);
		try {
            sServidor.close();
        }catch(IOException e2){
         JOptionPane.showMessageDialog(ventanaSubastaVendedor, "CServidor / no se cerró el puerto + e2.getMessage()");	
        }
	

		}
		
	}

	public ServerSocket getsServidor() {
		return sServidor;
	}

	public void setsServidor(ServerSocket sServidor) {
		this.sServidor = sServidor;
	}
}
