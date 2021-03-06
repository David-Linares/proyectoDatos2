package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Producto;

public class ConexionClienteServidor extends Thread {

	/* ATRIBUTOS */
	private Socket socketClienteServidor;
	private ObjectOutputStream objetoSalida;
	private General general = General.getInstance();
	private Cliente clienteTemporal;

	/*
	 * CONSTRUCTOR DE LA CLASE RECIBE UN SOCKET Y PREPARA UNA SALIDA DE DATOS
	 */
	public ConexionClienteServidor(Socket s) {
		try {
			this.socketClienteServidor = s;
			objetoSalida = new ObjectOutputStream(s.getOutputStream());
			start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"Conexion / Se produjo un error en la salida "
							+ e.getMessage());
		}
	}

	public Cliente getClienteTemp() {
		return clienteTemporal;
	}

	public void setClienteTemp(Cliente clienteNuevo) {
		this.clienteTemporal = clienteNuevo;
	}
	

	public Socket getSocketClienteServidor() {
		return socketClienteServidor;
	}

	public void setSocketClienteServidor(Socket socketClienteServidor) {
		this.socketClienteServidor = socketClienteServidor;
	}

	public String toString() {
		return "Conexion [s=" + socketClienteServidor + ", salida="
				+ objetoSalida + ", general=" + general + ", clienteTemp="
				+ clienteTemporal + "]";
	}

	/*RECIBE TODOS LOS DATOS QUE LLEGAN DE CLIENTE Y LE RESPONDE SEGUN EL CASO
	 ESPERA UNA ENTRADA DE DATOS*/
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public synchronized void run() {
		while (true) {
			try {
				ObjectInputStream entrada = new ObjectInputStream(
						socketClienteServidor.getInputStream());
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				switch (operacion) {

				/*RECIBE UNA CONEXION SIN CLIENTE Y CREA EL ARRAYLIST CON EL
				 LISTADO DE CONECTADOS Y EL PRODUCTO PARA ENIAR DATOS A CONEXIONCLIENTE*/
				case 1:
					ArrayList datosServidor = new ArrayList();
					datosServidor.add(General.getListadoConectados());
					datosServidor.add(General.getProductoSeleccionado());
					datosServidor.add(General.getPosicionConexionTemp());
					entradaDatosConexion(operacion, datosServidor);
					break;

				/*
				 * SE CREA UN CLIENTE Y UNA CONEXION QUE SER� NOTIFICADA AL
				 * SERVIDOR, AGREGADA AL LISTADO DE CONECTADOS Y NOTICA A TODAS
				 * LAS CONEXIONES LA NUEVA CONEXION
				 */
				/*case 2:
					System.out.println("CCS / antes cliente: "+ clienteTemporal);
					clienteTemporal = (Cliente) eMensaje;
					System.out.println("CCS / despu�s cliente: "+ clienteTemporal);
					ConexionClienteServidor conexTemp = General
							.getConexionesTemp().get(
									General.getPosicionConexionTemp());
					conexTemp.setClienteTemp(clienteTemporal);
					General.nuevaConexion(conexTemp);
					General.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()*/
				/**/
				case 2:
					clienteTemporal = (Cliente) eMensaje;
					General.getConexionTemp().setClienteTemp(clienteTemporal);
					General.nuevaConexion(General.getConexionTemp());
					General.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubastaVendedor()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubastaVendedor().getText()
											+ clienteTemporal.getNombre()
											+ " se conect\u00f3 \n");
					General.getListadoConectados().addElement(
							clienteTemporal.getNombre());
					break;
				/* ENVIA A LA VENTANA DEL SERVIDOR EL NUEVO OFRECIMIENTO DE UN
				 CLIENTE Y NOTIFICA A TODOS LOS CLIENTES DE ESE MISMO OFRECIMIENTO*/
				case 3:
					eMensaje = this.clienteTemporal.getNombre() + " ofrece: "
							+ eMensaje;
					General.setGanador(this.clienteTemporal);
					General.enviarDatos(operacion, (String) eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubastaVendedor()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubastaVendedor().getText()
											+ (String) eMensaje + "\n");
					break;

				/*NOTIFICA AL SERVIDOR QUE UN CLIENTE SE HA DESCONECTADO Y DEL
				 LISTADO DE CONECTADOS DEL SERVIDOR ELIMINA EL CLIENTE*/
				case 4:
					General.getVentanaServidor().borrarCliente(
							(String) eMensaje);
					general.desconecta(this);
					General.getVentanaServidor()
							.getTpMensajesSubastaVendedor()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubastaVendedor().getText()
											+ clienteTemporal.getNombre()
											+ " se desconect\u00f3 \n");
					break;

				/*ACTUALIZA EL VALOR DEL PRODUCTO UNA VEZ SE HA OFERTADO A LOS CLIENTES SERVIDOR */
				case 5:
					eMensaje = (Producto) eMensaje;
					General.enviarDatos(operacion, eMensaje);
					General.setProductoSeleccionado((Producto) eMensaje);
					General.getVentanaServidor()
							.getLblProductoSubastado()
							.setText(
									General.getProductoSeleccionado()
											.getNombre()
											+ " = "
											+ General.getProductoSeleccionado()
													.getValor());
					break;
				/*INICIALIZA EL RELOJ UNA VEZ HAY UN OFRECIMIENTO Y CUANDO ES 3
				 NOTIFICA QUE LA SUBASTA SE HA ACABADO*/
				case 6:
					Temporizador reloj = new Temporizador(3, 0);
					if (General.getReloj() != null)

						General.getReloj().stop();
					General.setReloj(reloj);
					reloj.start();
				}

			} catch (IOException e) {
				System.out.println(e.getCause() + " " + e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
			
		
	}

	/*ESCRIBE LOS DATOS DE ENTRADA AL CLIENTE - CLASE CONEXIONCLIENTE*/
	public void entradaDatosConexion(int operacion, Object sMensaje) {
		try {
			objetoSalida.writeInt(operacion);
			objetoSalida.writeObject(sMensaje);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( new JFrame(), "Se produjo un error en la ENTRADA DE DATOS " + e.getMessage());
		}
	}

}
