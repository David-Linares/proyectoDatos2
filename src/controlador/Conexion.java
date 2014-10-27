package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import modelo.Cliente;
import modelo.Producto;

public class Conexion extends Thread {
	
	private Socket s;
	public ObjectOutputStream salida;
	General general = General.getInstance();
	public Cliente clienteTemp;
	private JTextPane tpMensajesSubasta;
	
	public Conexion(Socket s, JTextPane tpMensajeSubasta) {
		try {
			this.s = s;
			this.tpMensajesSubasta = tpMensajeSubasta;
			salida = new ObjectOutputStream(s.getOutputStream());
			start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					new JFrame(),
					"Conexion / Se produjo un error en la salida "
							+ e.getMessage());
		}
	}

	public JTextPane getTpMensajesSubasta() {
		return tpMensajesSubasta;
	}

	public void setTpMensajesSubasta(JTextPane tpMensajesSubasta) {
		this.tpMensajesSubasta = tpMensajesSubasta;
	}


	public Cliente getClienteTemp() {
		return clienteTemp;
	}

	// LE LLEGAN LOS DATOS 
	public void run() {
		while (true) {
			try {
				//JOptionPane.showMessageDialog(new JFrame(), "Conexión " + General.getProductoSeleccionado());
				entradaDatosConexion(4, General.getProductoSeleccionado());
				ObjectInputStream entrada = new ObjectInputStream(s.getInputStream());
				//JOptionPane.showMessageDialog(new JFrame(), "Conexión / Si pasó la entrada");
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();		
				switch (operacion) {
				case 1:
					//JOptionPane.showMessageDialog(new JFrame(), "Conexión / Entró a caso 1");
					//JOptionPane.showMessageDialog(new JFrame(), "Conexión " + this.productoSubastado);
					clienteTemp = (Cliente) eMensaje;
					general.enviarDatos(operacion, eMensaje);
					general.getTextPaneVendedor().setText(general.getTextPaneVendedor().getText() + clienteTemp.getNombre() + " se conectó \n");
					break;
				case 2:
					System.out.println("Entró a la condi 2");
					eMensaje = this.clienteTemp.getNombre() + " ofrece: " + eMensaje;
					general.enviarDatos(operacion, (String) eMensaje);
					general.getTextPaneVendedor().setText(general.getTextPaneVendedor().getText() + (String) eMensaje + "\n");
					break;
				case 3:
					general.desconecta(this);
					this.tpMensajesSubasta.setText(this.tpMensajesSubasta.getText() + clienteTemp.getNombre() + " se desconectó \n");
					break;
				case 4:
					eMensaje = (Producto) eMensaje;
					general.enviarDatos(operacion, eMensaje);
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
			//JOptionPane.showMessageDialog(new JFrame(), "Conexión / salida de datos");
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
