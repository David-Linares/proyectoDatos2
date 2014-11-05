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
					if(General.validarExistente(clienteTemp))this.s.close();
					general.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemp.getNombre()
											+ " se conectó \n");
					General.getListadoConectados().addElement(clienteTemp
							.getNombre());
					break;
				case 2:
					System.out.println("Entró a la condi 2");
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
					general.desconecta(this);
					General.getVentanaServidor()
							.getTpMensajesSubasta()
							.setText(
									General.getVentanaServidor()
											.getTpMensajesSubasta().getText()
											+ clienteTemp.getNombre()
											+ " se desconectó \n");
					break;
				case 4:
					eMensaje = (Producto) eMensaje;
					general.enviarDatos(operacion, eMensaje);
					General.setProductoSeleccionado((Producto) eMensaje);
					General.getVentanaServidor().getLblProductoSubastado().setText(General.getProductoSeleccionado().getNombre()+ " = " + General.getProductoSeleccionado().getValor());
					break;
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
