package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.Cliente;

public class Conexion extends Thread {
	private Socket s;
	public ObjectInputStream entrada;
	public ObjectOutputStream salida;
	General general = General.getInstance();
	public Cliente clienteTemp;

	// OK
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

	// Inicializa el hilo - OK
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
					break;
				case 2:
					eMensaje = this.clienteTemp.getNombre() + ": " + eMensaje;
					general.enviarDatos(operacion, (String) eMensaje);
					break;
				case 3:
					general.desconecta(this);
					break;
				}

			} catch (IOException e) {
				System.out.println(e.getCause() + " " + e.getMessage());
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Conexion / Se produjo un error Class en la salida "+e.getMessage());
			}
		}
	}

	// OK
	public void enviarDatos(int operacion, Object sMensaje) {
		try {
			salida.writeInt(operacion);
			salida.writeObject(sMensaje);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Conexion / Se produjo un error en la llegada "+e.getMessage());
		}
	}

}
