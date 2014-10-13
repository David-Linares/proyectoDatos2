package controlador;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Conexiones extends Thread {

	private Socket s;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String nick;

	public Conexiones(Socket s) {
		try {
			this.s = s;
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			start();
		} catch (Exception e) {
		}
	}

	public String getNick() {
		return nick;
	}

	public void run() {
		while (true) {
			try {
				int nCodigo = dis.readInt();
				String sTrama = dis.readUTF();
				switch (nCodigo) {
				case 1:
					nick = sTrama;
					General.getInstance().enviarTrama(nCodigo, sTrama);
					break;
				case 2:
					sTrama = "<" + nick + "> - " + sTrama;
					General.getInstance().enviarTrama(nCodigo, sTrama);
					break;
				case 3:
					// General.getInstance().desconecta();
					break;
				}

			} catch (Exception e) {
			}

		}
	}

	public void enviarTrama(int nCodigo, String sTrama) {
		try {
			dos.writeInt(nCodigo);
			dos.writeUTF(sTrama);
		} catch (Exception e) {
		}
	}
}
