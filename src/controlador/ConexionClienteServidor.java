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

	public String toString() {
		return "Conexion [s=" + socketClienteServidor + ", salida="
				+ objetoSalida + ", general=" + general + ", clienteTemp="
				+ clienteTemporal + "]";
	}

	/* RECIBE TODOS LOS DATOS QUE LLEGAN DE CLIENTE Y LE RESPONDE SEGUN EL CASO */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public synchronized void run() {
		while (true) {
			try {
				ObjectInputStream entrada = new ObjectInputStream(
						socketClienteServidor.getInputStream());
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				// System.out.println("Conexi√≥n / ope = "+operacion+" entrada = "+eMensaje);
				switch (operacion) {

				/*
				 * RECIBE UNA CLASE CONEXION SIN CLIENTE SE CREA EL ARRAYLIST
				 * PARA ENIAR DATOS A ConexionCliente
				 */
				case 1:
					int posconexion = General.getConexionesTemp().size() - 1;
					ArrayList datosServidor = new ArrayList();
					datosServidor.add(General.getListadoConectados());
					datosServidor.add(General.getProductoSeleccionado());
					datosServidor.add(posconexion);
					entradaDatosConexion(operacion, datosServidor);
					break;

				/*
				 * SE CREA UN CLIENTE Y UNA CONEXION QUE SER¿ NOTIFICADA AL
				 * SERVIDOR, AGREGADA AL LISTADO DE CONECTADOS Y NOTICA A TODAS
				 * LAS CONEXIONES LA NUEVA CONEXION
				 */
				case 2:
					clienteTemporal = (Cliente) eMensaje;
					ConexionClienteServidor conexTemp = General
							.getConexionesTemp().get(
									General.getConexionesTemp().size() - 1);
					conexTemp.setClienteTemp(clienteTemporal);
					General.nuevaConexion(conexTemp);
					General.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemporal.getNombre()
											+ " se conect\u00f3 \n");
					General.getListadoConectados().addElement(
							clienteTemporal.getNombre());
					break;

				/*
				 * ENVÃA A LA VENTADA DEL SERVIDOR EL NUEVO OFRECIMIENTO DE UN
				 * CLIENTE Y NOTIFICA A TODOS LOS CLIENTES DE ESE MISMO
				 * OFRECIMIENTO
				 */
				case 3:
					eMensaje = this.clienteTemporal.getNombre() + " ofrece: "
							+ eMensaje;
					General.enviarDatos(operacion, (String) eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ (String) eMensaje + "\n");
					break;
				/*
				 * NOTIFICA AL SERVIDOR QUE UN CLIENTE SE HA DESCONECTADO Y DEL
				 * LISTADO DEL SERVIDOR ELIMINA EL CLIENTE
				 */
				case 4:
					General.getVentanaServidor().borrarCliente(
							(String) eMensaje);
					general.desconecta(this);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemporal.getNombre()
											+ " se desconect\u00f3 \n");
					break;
				/*
				 * NOTIFICA A TODAS LAS CONEXIONES EL PRODUCTO SELECCIONADO Y
				 * ENVÃA AL SERVIDOR EL PRODUCTO QUE SE HA SELECCIONADO
				 */
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
				/*
				 * INICIALIZA EL RELOJ UNA VEZ HAY UN OFRECIMIENTO Y CUANDO ES 3
				 * NOTIFICA QUE LA SUBASTA SE HA ACABADO
				 */
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
				JOptionPane.showMessageDialog(new JFrame(),
						"Conexion / Se produjo un error Class en la salida "
								+ e.getMessage());
			}
		}
	}

	// ESCRIBE LOS DATOS DE ENTRADA AL CLIENTE - CLASE ConexionCliente
	public void entradaDatosConexion(int operacion, Object sMensaje) {
		try {
			objetoSalida.writeInt(operacion);
			objetoSalida.writeObject(sMensaje);
		} catch (Exception e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(
			// new JFrame(),
			// "Conexion / Se produjo un error en la llegada "
			// + e.getMessage());
		}
	}

}
