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
	public synchronized void run() {
		while (true) {
			try {
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
					General.getConexionTemp().setClienteTemp(clienteTemp);
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
					Temporizador reloj = new Temporizador(3,0);						
					if(General.getReloj() != null)
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

	// ESCRIBE LOS DATOS DE ENTRADA AL CLIENTE
	public void entradaDatosConexion(int operacion, Object sMensaje) {
		try {
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
