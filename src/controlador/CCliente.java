package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Producto;
import vista.PrincipalSubastaCliente;

public class CCliente extends Thread {

	private Cliente clienteConectado;
	private int puerto;
	private String ip;
	private PrincipalSubastaCliente ventanaCliente;
	private boolean conectado;
	private Socket SCliente;
	private ObjectInputStream entrada;

	public CCliente(int puerto, String ip, Cliente clienteConectado) {
		this.ip = ip;
		this.puerto = puerto;
		this.clienteConectado = clienteConectado;
		this.entrada = null;
	}

	// OK
	public void run() {
		try {
			SCliente = new Socket(ip, puerto);
			entrada = new ObjectInputStream(
					SCliente.getInputStream());
			enviarDatosCliente(1, clienteConectado);
			conectado = true;
			while (conectado) {
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				// JOptionPane.showMessageDialog(new JFrame(),
				// "CCliente / Pas� entrada y envia los datos de nueva conexi�n");
				switch (operacion) {
				case 1:// Agregar nuevo cliente
					ventanaCliente.agregarNuevo((Cliente) eMensaje);
					break;
				case 2:// Enviar Mensaje
					ventanaCliente.mensajeRecibido((String) eMensaje);
					break;
				case 3:
					ventanaCliente.borrarCliente(Integer
							.parseInt((String) eMensaje));
					break;
				case 4:
					ventanaCliente
							.agregarProductoEnSubasta((Producto) eMensaje);
					break;
				case 5:
					ventanaCliente.mostrarTIempo((String) eMensaje);
				}
			}
		} catch (UnknownHostException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(ventanaCliente,
					"CCCliente / Host desconocido " + e.getMessage());

			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventanaCliente,
					"CCCliente / IOException " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(ventanaCliente,
					"CCCliente / ClassNotFound " + e.getMessage());
		}
	}

	// OK
	public void enviarMensajeHilo(String sMensaje) {
		enviarDatosCliente(2, sMensaje);
	}

	public void enviarProductoHilo(Producto cambioProducto) {
		enviarDatosCliente(4, cambioProducto);
	}
	public void enviarReloj(String tiempo){
		enviarDatosCliente(5, tiempo);
	}

	// ESCRIBE LOS DATOS A LA CONEXION
	public void enviarDatosCliente(int operacion, Object valor) {
		try {
			// ENVIA LOS DATOS A TRAV�S DEL HILO A LA CONEXI�N.
			ObjectOutputStream salida = new ObjectOutputStream(
					SCliente.getOutputStream());
			salida.writeInt(operacion);
			salida.writeObject(valor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"CCCliente / Se produjo un error al enviar el mensaje "
							+ e.getMessage());
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
