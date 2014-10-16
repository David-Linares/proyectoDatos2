package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modelo.Cliente;

public class Conexion extends Thread {
	private Socket s;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	General general = General.getInstance();
	private Cliente clienteTemp;
	public Conexion(Socket s) {
		try {
			this.s = s;
			entrada = new ObjectInputStream(s.getInputStream());
			salida = new ObjectOutputStream(s.getOutputStream());
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				int operacion = entrada.readInt();
				Object eMensaje = entrada.readUTF();
				switch (operacion){
					case 1:
						this.clienteTemp = (Cliente) eMensaje;
						break;
					case 2:
						eMensaje = this.clienteTemp.getNombre() + ": " + eMensaje;
						break;
				}
				general.enviarDatos(operacion, eMensaje);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void enviarDatos(int operacion, Object sMensaje){
		try {
			salida.writeInt(operacion);
			salida.writeObject(sMensaje);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
