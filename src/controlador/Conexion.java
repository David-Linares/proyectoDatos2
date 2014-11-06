package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Producto;

public class Conexion extends Thread {

	private Socket s;
	private ObjectOutputStream salida;
	private General general = General.getInstance();
	private Cliente clienteTemp;
	private int contadorId =0;

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
				entradaDatosConexion(4, General.getProductoSeleccionado());
				ObjectInputStream entrada = new ObjectInputStream(
						s.getInputStream());
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				switch (operacion) {
				case 1:
					clienteTemp = (Cliente) eMensaje;
					clienteTemp.setId(contadorId);
					contadorId++;
					general.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemp.getNombre()

											+ " se conect\u00f3 \n");
					General.getListadoConectados().addElement(clienteTemp
							.getNombre());

					break;
				case 2:
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
				case 3:
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
				case 4:
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
				case 5:
					Temporizador temp = (Temporizador) eMensaje;
					general.enviarDatos(operacion,temp);
					General.setReloj(temp);
					General.getVentanaServidor().getLblReloj().setText("0"+temp.getMin()+":"+temp.getSeg());
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

	// ESCRIBE LOS DATOS DE ENTRADA A LA CONEXION
	public void entradaDatosConexion(int operacion, Object sMensaje) {
		try {
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
