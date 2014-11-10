package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Producto;
import vista.ClienteSubasta;

public class ConexionCliente extends Thread {

	private Cliente clienteConectado;
	private int puerto;
	private String ip;
	private ClienteSubasta ventanaCliente;
	private boolean conectado;
	private Socket SCliente;
	private ObjectInputStream entrada;

	public ConexionCliente(int puerto, String ip, Cliente clienteConectado) {
		this.ip = ip;
		this.puerto = puerto;
		this.clienteConectado = clienteConectado;
		this.entrada = null;
	}
	public ConexionCliente(int puerto, String ip) {
		this.ip = ip;
		this.puerto = puerto;
		this.entrada = null;
	}

	// OK
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void run() {
		try {
			SCliente = new Socket(ip, puerto);
			enviarDatosCliente(1, null);
			entrada = new ObjectInputStream(
					SCliente.getInputStream());
			conectado = true;
			while (conectado) {
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
			switch (operacion) {
				case 1: //Recibe los datos que tiene el servidor para actualizar los datos del cliente.
					ArrayList datos = (ArrayList) eMensaje;
					General.setListadoConectadosTemp((DefaultListModel) datos.get(0));
					General.setProductoSeleccionado((Producto) datos.get(1));
					General.getVentanaDatosCliente().getLblProductoSubastaCliente().setText(General.getProductoSeleccionado().getNombre() + " = " + General.getProductoSeleccionado().getValor());
					General.getVentanaDatosCliente().gettADescripcionProducto().setText(General.getProductoSeleccionado().getDescripcion());
					break;
				case 2:// Agregar nuevo cliente
					ventanaCliente.agregarNuevo((Cliente) eMensaje);
					break;
				case 3:// Enviar Mensaje
					ventanaCliente.mensajeRecibido((String) eMensaje);
					break;
				case 4:
					ventanaCliente.borrarCliente(Integer
							.parseInt((String) eMensaje));
					break;
				case 5:
					General.setProductoSeleccionado((Producto)eMensaje);
					ventanaCliente.agregarProductoEnSubasta((Producto) eMensaje);
					break;
				case 6: //Finalizó la subasta.
					ventanaCliente.finSubasta((String) eMensaje);
					break;
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
		enviarDatosCliente(3, sMensaje);
		enviarDatosCliente(6, null);
	}

	public void enviarProductoHilo(Producto cambioProducto) {
		enviarDatosCliente(5, cambioProducto);
	}

	// ESCRIBE LOS DATOS A LA CONEXION
	public void enviarDatosCliente(int operacion, Object valor) {
		try {
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
	
	public void setClienteConectado(Cliente clienteConectado) {
		this.clienteConectado = clienteConectado;
	}
	public ClienteSubasta getVentanaCliente() {
		return ventanaCliente;
	}

	public void setVentanaCliente(ClienteSubasta ventanaCliente) {
		this.ventanaCliente = ventanaCliente;
		enviarDatosCliente(2, this.clienteConectado);
	}

}
