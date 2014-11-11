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

	/*ATRIBUTOS*/
	private Socket socketClienteServidor;
	private ObjectOutputStream salida;
	private General general = General.getInstance();
	private Cliente clienteTemp;

	/*CONSTRUCTOR DE LA CLASE
	 RECIBE UN SOCKET Y PREPARA UNA SALIDA DE DATOS*/
	public ConexionClienteServidor(Socket s) {
		try {
			this.socketClienteServidor = s;
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
	
	public String toString() {
		return "Conexion [s=" + socketClienteServidor + ", salida=" + salida + ", general="
				+ general + ", clienteTemp=" + clienteTemp + "]";
	}
	

	/*RECIBE TODOS LOS DATOS QUE LLEGAN DE CLIENTE Y LE RESPONDE SEGUN EL CASO*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		while (true) {
			try {
				ObjectInputStream entrada = new ObjectInputStream(
						socketClienteServidor.getInputStream());
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();
				//System.out.println("ConexiÃ³n / ope = "+operacion+" entrada = "+eMensaje);
				switch (operacion) {
				
				/* RECIBE UNA CLASE CONEXION SIN CLIENTE
				 SE CREA EL ARRAYLIST PARA ENIAR DATOS A ConexionCliente */
				case 1:
					@SuppressWarnings("rawtypes")
					ArrayList datosServidor = new ArrayList();
					datosServidor.add(General.getListadoConectados());
					datosServidor.add(General.getProductoSeleccionado());
					entradaDatosConexion(operacion, datosServidor);
					break;

				/*
				 */	
				case 2:
					//JOptionPane.showMessageDialog(new JFrame(), "COnexion Cliente Servidor / Entró al case 2");
					clienteTemp = (Cliente) eMensaje;
					//JOptionPane.showMessageDialog(new JFrame(), "Conexion Cliente Servidor / "+General.getConexionTemp());
					General.getConexionTemp().setClienteTemp(clienteTemp);
					//JOptionPane.showMessageDialog(new JFrame(), "Conexion Cliente Servidor / se le asignó "+clienteTemp.getNombre());
					General.nuevaConexion(General.getConexionTemp());
					General.enviarDatos(operacion, eMensaje);
					General.getVentanaServidor().getTpMensajesSubasta().setText(General.getVentanaServidor().getTpMensajesSubasta().getText()+ clienteTemp.getNombre()											+ " se conect\u00f3 \n");
					General.getListadoConectados().addElement(clienteTemp.getNombre());
					break;
				/* 
				 */
				case 3:
					eMensaje = this.clienteTemp.getNombre() + " ofrece: "
							+ eMensaje;
					General.enviarDatos(operacion, (String) eMensaje);
					General.getVentanaServidor().getTpMensajesSubasta().setText(General.getVentanaServidor().getTpMensajesSubasta().getText()+ (String) eMensaje + "\n");
					break;
				case 4:
					General.getVentanaServidor().borrarCliente((String) eMensaje);general.desconecta(this);
					General.getVentanaServidor().getTpMensajesSubasta().setText(General.getVentanaServidor().getTpMensajesSubasta().getText()+ clienteTemp.getNombre()+ " se desconect\u00f3 \n");
					break;
				case 5:
					eMensaje = (Producto) eMensaje;
					General.enviarDatos(operacion, eMensaje);
					General.setProductoSeleccionado((Producto) eMensaje);
					General.getVentanaServidor().getLblProductoSubastado().setText(General.getProductoSeleccionado().getNombre()+ " = "+ General.getProductoSeleccionado().getValor());
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

	// ESCRIBE LOS DATOS DE ENTRADA AL CLIENTE - CLASE ConexionCliente
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

	
	
	
}
