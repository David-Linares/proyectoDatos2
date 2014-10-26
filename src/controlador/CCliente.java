package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import modelo.Cliente;
import vista.PrincipalSubastaCliente;

public class CCliente extends Thread {

	private Cliente clienteConectado;
	private int puerto;
	private String ip;
	private PrincipalSubastaCliente ventanaCliente;
	private boolean conectado;
	private Socket SCliente;
	private JTextPane panelCliente;

	public CCliente(int puerto, String ip, Cliente clienteConectado) {
		this.ip = ip;
		this.puerto = puerto;
		this.clienteConectado = clienteConectado;		
	}

	// OK
	public void run() {
		try {
			System.out.println("entró a CCliente");
			SCliente = new Socket(ip, puerto);
			ObjectInputStream entrada = new ObjectInputStream(
					SCliente.getInputStream());
			System.out.println("pasó llegada de datos");
			enviarDatosCliente(1, clienteConectado);
			conectado = true;
			while (conectado) {
				int operacion = entrada.readInt();
				Object eMensaje;				
				eMensaje = entrada.readObject();
				
				switch (operacion) {
				case 1:// Agregar nuevo cliente
					ventanaCliente.agregarNuevo((Cliente) eMensaje);
					break;
				case 2:// Enviar Mensaje
					ventanaCliente.mensajeRecibido((String) eMensaje);
					break;				
				case 3:
					ventanaCliente.borrarCliente(Integer.parseInt((String) eMensaje));
					break;
				}
			}
		} catch (UnknownHostException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"CCCliente / Host desconocido "
							+ e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"CCCliente / IOException "
							+ e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"CCCliente / ClassNotFound "
							+ e.getMessage());
		}
	}

	// OK
	public void enviarMensajeHilo(String sMensaje) {
		enviarDatosCliente(2, sMensaje);
	}

	// ESCRIBE LOS DATOS A LA CONEXION 
	public void enviarDatosCliente(int operacion, Object valor) {
		try {
			//ENVIA LOS DATOS A TRAVÉS DEL HILO
			ObjectOutputStream salida = new ObjectOutputStream(
					SCliente.getOutputStream());
			salida.writeInt(operacion);
			salida.writeObject(valor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaCliente,
					"CCCliente / Se produjo un error al enviar el mensaje "+e.getMessage());
		}
	}

	public Cliente getClienteConectado() {
		return this.clienteConectado;
	}

	public PrincipalSubastaCliente getVentanaCliente() {
		return ventanaCliente;
	}

	public void setVentanaCliente(PrincipalSubastaCliente ventanaCliente) {
		this.ventanaCliente = ventanaCliente;
	}

}
