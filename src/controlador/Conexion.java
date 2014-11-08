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

public class Conexion extends Thread {

	private Socket s;
	private ObjectOutputStream salida;
	private General general = General.getInstance();
	private Cliente clienteTemp;

	public Conexion(Socket s) {
		try {
			this.s = s;
			salida = new ObjectOutputStream(s.getOutputStream());
			start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"Conexion / Se produjo un error en la salida "
							+ e.getMessage());
		}
	}

	public Cliente getClienteTemp() {
		return clienteTemp;
	}

	// LE LLEGAN LOS DATOS
	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			try {
				//JOptionPane.showMessageDialog(new JFrame(), "Conexion / Entró a Run de conexión");
				//entradaDatosConexion(5, General.getProductoSeleccionado());
				@SuppressWarnings("rawtypes")
				ArrayList datosServidor = new ArrayList();
				datosServidor.add(General.getListadoConectados());
				datosServidor.add(General.getProductoSeleccionado());
				datosServidor.add(general.getConexiones());
				datosServidor.add(General.getConexionTemp());
				//JOptionPane.showMessageDialog(new JFrame(), "Conexion / Envía los datos que hay en el servidor de producto y de clientes");
				entradaDatosConexion(1, datosServidor);
				ObjectInputStream entrada = new ObjectInputStream(
						s.getInputStream());
				//JOptionPane.showMessageDialog(new JFrame(), "Conexion / Entraron Datos a conexión");
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				switch (operacion) {

				// Recibir una conexion sin Cliente
				case 1:
					//JOptionPane.showMessageDialog(new JFrame(), "Conexion / Entró al case 1 y va a enviar datos nuevamente");
					general.enviarDatos(operacion, eMensaje);
					break;

				case 2:
					clienteTemp = (Cliente) eMensaje;

					general.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemp.getNombre()

											+ " se conect\u00f3 \n");
					General.getListadoConectados().addElement(
							clienteTemp.getNombre());

					break;
				case 3:
					eMensaje = this.clienteTemp.getNombre() + " ofrece: "
							+ eMensaje;
					general.enviarDatos(operacion, (String) eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ (String) eMensaje + "\n");
					break;
				case 4:
					General.getVentanaServidor().borrarCliente(
							(String) eMensaje);
					general.desconecta(this);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemp.getNombre()
											+ " se desconect\u00f3 \n");
					break;
				case 5:
					eMensaje = (Producto) eMensaje;
					general.enviarDatos(operacion, eMensaje);
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
				case 6:
					Temporizador temp = (Temporizador) eMensaje;
					general.enviarDatos(operacion, temp);
					General.setReloj(temp);
					if (temp.getSeg() < 10) {
						General.getVentanaServidor()
								.getLblReloj()
								.setText(
										"0" + temp.getMin() + ":0"
												+ temp.getSeg());
						if (temp.getMin() == 0 && temp.getSeg() == 0) {
							JOptionPane.showMessageDialog(new JFrame(),
									"La subasta ha finalizado\n EL ganador es "
											+ clienteTemp.getNombre(),
									"Fin de la Subasta",
									JOptionPane.INFORMATION_MESSAGE,
									general.getIcon("winner"));
						}
					} else {
						General.getVentanaServidor()
								.getLblReloj()
								.setText(
										"0" + temp.getMin() + ":"
												+ temp.getSeg());
					}
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

	// ESCRIBE LOS DATOS DE ENTRADA AL CLIENTE
	public void entradaDatosConexion(int operacion, Object sMensaje) {
		try {
			//JOptionPane.showMessageDialog(new JFrame(), "Conexion / Entró a la función que envía los datos");
			salida.writeInt(operacion);
			salida.writeObject(sMensaje);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"Conexion / Se produjo un error en la llegada "
							+ e.getMessage());
		}
	}

}
