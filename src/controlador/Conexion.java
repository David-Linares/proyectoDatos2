package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import modelo.Cliente;

public class Conexion extends Thread {
	private Socket s;
	private DataInputStream entrada;
	private DataOutputStream salida;
	General general = General.getInstance();
	
	public Conexion(Socket s) {
		try {
			this.s = s;
			entrada = new DataInputStream(s.getInputStream());
			salida = new DataOutputStream(s.getOutputStream());
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				String eMensaje = entrada.readUTF();
				general.enviarMensaje(eMensaje);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void enviarMensaje(String sMensaje){
		try {
			salida.writeUTF(sMensaje);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
