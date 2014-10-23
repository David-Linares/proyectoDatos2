package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import modelo.Cliente;

public class Conexion extends Thread {
	
	private Socket s;
	public ObjectInputStream entrada;
	public ObjectOutputStream salida;
	General general = General.getInstance();
	public Cliente clienteTemp;
	private JTextPane tpMensajesSubasta;
	
	

	// OK
	
	public JTextPane getTpMensajesSubasta() {
		return tpMensajesSubasta;
	}

	public void setTpMensajesSubasta(JTextPane tpMensajesSubasta) {
		this.tpMensajesSubasta = tpMensajesSubasta;
	}

	public Conexion(Socket s) {
		super();
		this.s = s;
	}

	public Conexion(Socket s, JTextPane tpMensajeSubasta ) {
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

	public Cliente getClienteTemp() {
		return clienteTemp;
	}

	// LE LLEGAN LOS DATOS 
	public void run() {
		while (true) {
			try {
				entrada = new ObjectInputStream(s.getInputStream());
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readObject();		
				switch (operacion) {
				case 1:
					clienteTemp = (Cliente) eMensaje;
					general.enviarDatos(operacion, eMensaje);
					this.tpMensajesSubasta.setText(this.tpMensajesSubasta.getText() + clienteTemp.getNombre() + " se conectó \n" );
					break;
				case 2:
					eMensaje = this.clienteTemp.getNombre() + ": " + eMensaje;
					general.enviarDatos(operacion, (String) eMensaje);
					this.tpMensajesSubasta.setText(this.tpMensajesSubasta.getText() + eMensaje  + "\n");
					break;
				case 3:
					general.desconecta(this);
					this.tpMensajesSubasta.setText(this.tpMensajesSubasta.getText() + clienteTemp.getNombre() + " se desconectó \n");
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
