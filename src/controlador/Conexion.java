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
	public ObjectInputStream entrada;
	public ObjectOutputStream salida;
	General general = General.getInstance();
	public Cliente clienteTemp;
	
	//OK
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
	
	
	public Cliente getClienteTemp() {
		return clienteTemp;
	}


	//Inicializa el hilo - OK
	public void run(){
		while(true){
				try {
					int operacion = entrada.readInt();
					Object eMensaje = entrada.readObject();
					switch (operacion){
						case 1:
							clienteTemp = (Cliente) eMensaje;
							general.enviarDatos(operacion, eMensaje);
							break;
						case 2:
							eMensaje = this.clienteTemp.getNombre() + ": " + eMensaje;
							general.enviarDatos(operacion, eMensaje);
							break;
						case 3:
							//FALTA IMPLEMENTAR
						break;
					}
					
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	//OK
	public void enviarDatos(int operacion, Object sMensaje){
		try {
			salida.writeInt(operacion);
			salida.writeObject(sMensaje);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
