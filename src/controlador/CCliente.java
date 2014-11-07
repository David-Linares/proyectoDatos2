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
	public CCliente(int puerto, String ip) {
		this.ip = ip;
		this.puerto = puerto;
		this.entrada = null;
	}

	// OK
	public void run() {
		try {
			SCliente = new Socket(ip, puerto);
			entrada = new ObjectInputStream(
					SCliente.getInputStream());
			conectado = true;
			while (conectado) {
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
			switch (operacion) {
				case 1: //Recibe los datos que tiene el servidor para actualizar los datos del cliente.
					if (eMensaje instanceof ArrayList) {
						ArrayList<?> datos = (ArrayList<?>) eMensaje;
						General.setListadoConectadosTemp((DefaultListModel) datos.get(0));
						General.setProductoSeleccionado((Producto) datos.get(1));						
					}else if(eMensaje instanceof Conexion){
						General.setConexionTemp((Conexion) eMensaje);
					}
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
					//ventanaCliente.agregarProductoEnSubasta((Producto) eMensaje);
					break;
				case 6:
					System.out.println("CCLIENTE / entr� ac�");
					Temporizador temp = (Temporizador) eMensaje;
					ventanaCliente.mostrarTiempo(temp);
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
	public void enviarReloj(Temporizador reloj){
		enviarDatosCliente(5, reloj);
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
	
	public void setClienteConectado(Cliente clienteConectado) {
		this.clienteConectado = clienteConectado;
		enviarDatosCliente(2, this.clienteConectado);
	}
	public PrincipalSubastaCliente getVentanaCliente() {
		return ventanaCliente;
	}

	public void setVentanaCliente(PrincipalSubastaCliente ventanaCliente) {
		this.ventanaCliente = ventanaCliente;
	}

}
