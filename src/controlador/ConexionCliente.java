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
import vista.SubastaCliente;

public class ConexionCliente extends Thread {

	/*ATRIBUTOS*/
	private Cliente clienteConectado;
	private int puertoCliente;
	private String ipCliente;
	private SubastaCliente ventanaCliente;
	private boolean verificaConectado;
	private Socket SocketCliente;
	private ObjectInputStream objetoEntrada;

	
	/* CONSTRUCTOR DE ConexionCliente, QUE RECIBE COMO PARÁMETRO 
	UN PUERTO TIPO INT, UNA IP DE TIPO STRING, Y UN CLIENTECONECTADO 
	DE TIPO CLIENTE*/
	public ConexionCliente(int puerto, String ip, Cliente clienteConectado) {
		this.ipCliente = ip;
		this.puertoCliente = puerto;
		this.clienteConectado = clienteConectado;
		this.objetoEntrada = null;
	}
	
	/* CONSTRUCTOR DE ConexionCliente, QUE RECIBE COMO PARÁMETRO 
	UN PUERTO TIPO INT Y UNA IP DE TIPO STRING*/
	public ConexionCliente(int puerto, String ip) {
		this.ipCliente = ip;
		this.puertoCliente = puerto;
		this.objetoEntrada = null;
	}

	public Cliente getClienteConectado() {
		return this.clienteConectado;
	}
	
	public void setClienteConectado(Cliente clienteConectado) {
		this.clienteConectado = clienteConectado;
	}
	public SubastaCliente getVentanaCliente() {
		return ventanaCliente;
	}

	public void setVentanaCliente(SubastaCliente ventanaCliente) {
		this.ventanaCliente = ventanaCliente;
		enviarDatosCliente(2, this.clienteConectado);
	}
	
	/*MÉTODO run(), SE INICIALIZA UNA VEZ ES UTILIZADO EL .start()
	SE CREA UN SOCKET CON PUERTO E IP, SE CREA UNA ESPERA DE ENTRADA DE DATOS AL SOCKET 
	ADEMAS UTILIZA EL MÉTODO enviarDatos() PARA ESCRIBIRLE AL SERVIDOR 
	INFORMANDO QUE HAY UN NUEVA CONEXION - SOLO CON EL 1*/
	@SuppressWarnings("rawtypes")
	public synchronized void run() {

		try {
			SocketCliente = new Socket(ipCliente, puertoCliente);
			enviarDatosCliente(1, null);
			objetoEntrada = new ObjectInputStream(SocketCliente.getInputStream());
			verificaConectado = true;
			while (verificaConectado) {
				//LEE Y SEPARA LA ENTRADA DE DATOS SEGUN EL TIPO
				int operacion = objetoEntrada.readInt();
				Object eMensaje = objetoEntrada.readObject();
				System.out.println("CC / "+General.getConexCliente() + " " + operacion + " " + eMensaje);
				
			switch (operacion) {
				
	     		/* RECIBE EL ARRAYLIST QUE LLEGA DE ConexionClienteServidor
				 CON LA INFORMACION DE LISTADO DE CONECTADOS Y EL PRODUCTO
			    PARA ACTUALIZARLO AL CLIENTE EN ELA VENTANA DatosCliente
			    LA CONEXION DEL CLIENTE LA CREA EN UN LISTADO DE CONECTADOS TEMPORAL*/
				case 1:
					
					ArrayList datos = (ArrayList) eMensaje;
					General.setListadoConectadosTemp((DefaultListModel) datos.get(0));
					General.setProductoSeleccionado((Producto) datos.get(1));
					General.setPosicionConexionTemp((int) datos.get(2));
					General.getVentanaDatosCliente().getLblProductoSubastaCliente().setText(General.getProductoSeleccionado().getNombre() + " = " + General.getProductoSeleccionado().getValor());
					General.getVentanaDatosCliente().gettADescripcionProducto().setText(General.getProductoSeleccionado().getDescripcion());
					break;
				/*AGREGAR UN NUEVO CLIENTE - RECIBE EL CLIENTE Y LO AGREGA AL MODELOS DE LISTADO 
				 DE CONECTADOS */
				case 2:
					ventanaCliente.agregarNuevo((Cliente) eMensaje);
					break;
				/*RECIBE UN MENSAJE Y LO ENVÍA A LA VENTANA DEL CLIENTE */
				case 3: 
					ventanaCliente.mensajeRecibido((String) eMensaje);
					break;
				/*ELIMINA EL CLIENTE DEL LISTADO DE CONECTADOS*/
				case 4:
					ventanaCliente.borrarCliente(Integer
							.parseInt((String) eMensaje));
					break;
				/*ACTUALIZA EL VALOR DEL PRODUCTO AL CLIENTE*/	
				case 5:
					General.setProductoSeleccionado((Producto)eMensaje);
					ventanaCliente.agregarProductoEnSubasta((Producto) eMensaje);
					break;
				/* NOTIFICA DE UN FIN DE SUBASTA CLIENTE */
				case 6: 
					ventanaCliente.finSubasta((String) eMensaje);
					break;
				}
			}
		} catch (UnknownHostException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(ventanaCliente,
					"No se encuentra el Servidor, Vuelva a digitar la IP " + e.getMessage());

			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventanaCliente,
					"CCliente / IOException " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(ventanaCliente,
					"CCliente / ClassNotFound " + e.getMessage());
		}
		
	
	}

	/*RECIBE EL MONTO DEL NUEVO OFRECIMIENTO DEL CLIENTE E INICIA EL RELOG CADA VEZ QUE LE LLEGA UN MENSAJE */
	public void enviarMensajeHilo(String sMensaje) {
		enviarDatosCliente(3, sMensaje);
		enviarDatosCliente(6, null);
	}
	
	/*ACTUALIZA EL PRODUCTO CON EL NUEVO VALOR */
	public void enviarProductoHilo(Producto cambioProducto) {
		enviarDatosCliente(5, cambioProducto);
	}

	/* SE ENVIAN DATOS A LA CLASE ConexionClienteServidor,  
	 PARA QUE SE EJECUTE UNA ACCIÓN SEGUN EL CASO - OPERACION */
	public void enviarDatosCliente(int operacion, Object valor) {
		try {
			System.out.println("CC / "+SocketCliente);
			ObjectOutputStream salida = new ObjectOutputStream(SocketCliente.getOutputStream());
			salida.writeInt(operacion);
			salida.writeObject(valor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"Se produjo un error al enviar el mensaje " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "ConexionCliente [clienteConectado=" + clienteConectado
				+ ", puertoCliente=" + puertoCliente + ", ipCliente="
				+ ipCliente + ", ventanaCliente=" + ventanaCliente + "]";
	}

	public Socket getSocketCliente() {
		return SocketCliente;
	}

	public void setSocketCliente(Socket socketCliente) {
		SocketCliente = socketCliente;
	}
	
	

}
