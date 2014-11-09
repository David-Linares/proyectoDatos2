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
import modelo.Temporizador;

public class ConexionClienteServidor extends Thread{

	private Socket s;
	private ObjectOutputStream salida;
	private General general = General.getInstance();
	private Cliente clienteTemp;

	public ConexionClienteServidor(Socket s) {
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
	
	public void setClienteTemp(Cliente clienteNuevo){
		this.clienteTemp = clienteNuevo;
	}

	// LE LLEGAN LOS DATOS
	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			try {
				//entradaDatosConexion(5, General.getProductoSeleccionado());
				System.out.println("Conexion / Inicio RUn de Conexión");
				ObjectInputStream entrada = new ObjectInputStream(
						s.getInputStream());
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				System.out.println("Conexión / ope = "+operacion+" entrada = "+eMensaje);
				switch (operacion) {
				// Recibir una conexion sin Cliente
				case 1:
					@SuppressWarnings("rawtypes")
					ArrayList datosServidor = new ArrayList();
					datosServidor.add(General.getListadoConectados());
					datosServidor.add(General.getProductoSeleccionado());
					entradaDatosConexion(operacion, datosServidor);
					break;

				case 2:
					clienteTemp = (Cliente) eMensaje;
					System.out.println("Conexion / Entró al case 2 y entró el cliente = "+clienteTemp);
					System.out.println("Conexion / la variable Conexión TEMP = "+General.getConexionTemp());
					General.getConexionTemp().setClienteTemp(clienteTemp);
					System.out.println("Conexion / la variable Conexión TEMP = "+General.getConexionTemp()+" después de Agregarle el cliente");
					General.nuevaConexion(General.getConexionTemp());
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
			System.out.println("Conexión / entró a enviarle datos al cliente = "+sMensaje);
			salida.writeInt(operacion);
			salida.writeObject(sMensaje);
		} catch (Exception e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(
				//	new JFrame(),
					//"Conexion / Se produjo un error en la llegada "
						//	+ e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Conexion [s=" + s + ", salida=" + salida + ", general="
				+ general + ", clienteTemp=" + clienteTemp + "]";
	}
	
	
	
}
