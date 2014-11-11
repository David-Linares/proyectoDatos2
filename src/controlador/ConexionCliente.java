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

	private Cliente clienteConectado;
	private int puertoCliente;
	private String ipCliente;
	private SubastaCliente ventanaCliente;
	private boolean verificaConectado;
	private Socket SocketCliente;
	private ObjectInputStream objetoEntrada;

	
	/* CONSTRUCTOR DE ConexionCliente, QUE RECIBE COMO PAR¡METRO 
	UN PUERTO TIPO INT, UNA IP DE TIPO STRING, Y UN CLIENTECONECTADO 
	DE TIPO CLIENTE*/
		public ConexionCliente(int puerto, String ip, Cliente clienteConectado) {
		this.ipCliente = ip;
		this.puertoCliente = puerto;
		this.clienteConectado = clienteConectado;
		this.objetoEntrada = null;
	}
	
	/* CONSTRUCTOR DE ConexionCliente, QUE RECIBE COMO PAR¡METRO 
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
	
	/*M…TODO run(), SE INICIALIZA UNA VEZ ES UTILIZADO EL .start()
	SE CREA UN SOCKET CON PUERTO E IP, SE CREA UNA ESPERA DE ENTRADA DE DATOS AL SOCKET 
	ADEMAS UTILIZA EL M…TODO enviarDatos() PARA ESCRIBIRLE AL SERVIDOR 
	INFORMANDO QUE HAY UN NUEVA CONEXION - SOLO CON EL 1*/
	
	public synchronized void run() {

		try {
			SocketCliente = new Socket(ipCliente, puertoCliente);
			objetoEntrada = new ObjectInputStream(SocketCliente.getInputStream());
			enviarDatosCliente(1, null);
			verificaConectado = true;
			while (verificaConectado) {
				//LEE Y SEPARA LA ENTRADA DE DATOS SEGUN EL TIPO
				int operacion = objetoEntrada.readInt();
				Object eMensaje = objetoEntrada.readObject();
			switch (operacion) {
				
			/* RECIBE EL ARRAYLIST QUE LLEGA DE ConexionClienteServidor
				 CON LA INFORMACION DE LISTADO DE CONECTADOS Y EL PRODUCTO
			    PARA ACTUALIZARLO AL CLIENTE EN ELA VENTANA DatosCliente
			    LA CONEXION DEL CLIENTE LA CREA EN UN LISTADO DE CONECTADOS TEMPORAL
			    */
				case 1: 
					ArrayList datos = (ArrayList) eMensaje;
					General.setListadoConectadosTemp((DefaultListModel) datos.get(0));
					General.setProductoSeleccionado((Producto) datos.get(1));
					General.getVentanaDatosCliente().getLblProductoSubastaCliente().setText(General.getProductoSeleccionado().getNombre() + " = " + General.getProductoSeleccionado().getValor());
					General.getVentanaDatosCliente().gettADescripcionProducto().setText(General.getProductoSeleccionado().getDescripcion());
					break;
					
				/*AGREGAR UN NUEVO CLIENTE - RECIBE EL CLIENTE Y LO AGREGA AL MODELOS DE LISTADO 
				 DE CONECTADOS */
				case 2:
					ventanaCliente.agregarNuevo((Cliente) eMensaje);
					break;
					
				/*RECIBE UN MENSAJE Y LO ENVÕA A LA VENTANA DEL CLIENTE  
				 */
				case 3: 
					ventanaCliente.mensajeRecibido((String) eMensaje);
					break;
					
				/*ELIMINA EL CLIENTE DEL LISTADO DE CONECTADOS
				 */
				case 4:
					ventanaCliente.borrarCliente(Integer
							.parseInt((String) eMensaje));
					break;
				case 5:
					General.setProductoSeleccionado((Producto)eMensaje);
					ventanaCliente.agregarProductoEnSubasta((Producto) eMensaje);
					break;
				case 6: //Finaliz√≥ la subasta.
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

	/* SE ENVIAN DATOS A LA CLASE ConexionClienteServidor,  
	 PARA QUE SE EJECUTE UNA ACCI”N SEGUN EL CASO - OPERACION */
	public void enviarDatosCliente(int operacion, Object valor) {
		try {
			ObjectOutputStream salida = new ObjectOutputStream(SocketCliente.getOutputStream());
			salida.writeInt(operacion);
			salida.writeObject(valor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					ventanaCliente,
					"Se produjo un error al enviar el mensaje " + e.getMessage());
		}
	}

	

}
