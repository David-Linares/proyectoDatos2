package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import modelo.Cliente;
import vista.PrincipalSubastaCliente;

public class CCliente extends Thread {

	private Cliente clienteConectado;
	private int puerto;
	private String ip;
	private PrincipalSubastaCliente ventanaCliente;
	private boolean conectado;
	private Socket SCliente;

	public CCliente(int puerto, String ip, Cliente clienteConectado) {
		this.ip = ip;
		this.puerto = puerto;
		this.clienteConectado = clienteConectado;
	}

	// OK
	public void run() {
		try {
			SCliente = new Socket(ip, puerto);
			enviarDatos(1, clienteConectado);
			ObjectInputStream entrada = new ObjectInputStream(
					SCliente.getInputStream());
			conectado = true;
			while (conectado) {
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				switch (operacion) {
				case 1:// Agregar nuevo cliente
					ventanaCliente.agregarNuevo((Cliente) eMensaje);
					break;
				case 2:// Enviar Mensaje
					ventanaCliente.mensajeRecibido((String) eMensaje);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getCause());
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"CCCliente / No se pudo establecer la Conexión "
							+ e.getMessage());
			// TODO: handle exception
		}
	}

	// OK
	public void enviarMensajeHilo(String sMensaje) {
		enviarDatos(2, sMensaje);
	}

	// OK
	public void enviarDatos(int operacion, Object valor) {
		try {
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
