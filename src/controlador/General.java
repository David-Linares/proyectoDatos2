package controlador;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Producto;

public class General {

	private static General general;
	public static Producto[] productos;
	public static ArrayList<Cliente> clientesConectados = new ArrayList<Cliente>();
	public static Producto productoSeleccionado;
	public static ServerSocket servidor;
	public static String ipServidor;
	public static Socket cliente;
	public static int puerto = 9090;
	public static int puertoCliente = 9091;
	

	private General(){
		Producto producto1 = new Producto(1, "Bicicleta", 200000);
		Producto producto2 = new Producto(2, "Carro", 35000000);
		Producto producto3 = new Producto(3, "Guitarra", 400000);
		Producto producto4 = new Producto(4, "Maleta", 140000);
		Producto producto5 = new Producto(5, "Calculadora", 100000);
		 productos = new Producto[]{
				 producto1,
				 producto2,
				 producto3,
				 producto4,
				 producto5
		 };
	}

	public static synchronized General getInstance() {
		if (general == null)
			general = new General();

		return general;
	}
	
	public void enviarMensaje(String msj){
		for(Cliente clienteConectado : clientesConectados){
			try {
				Socket s = new Socket(clienteConectado.getIp(), clienteConectado.getPuerto());
				DataOutputStream salida = new DataOutputStream(s.getOutputStream());
				salida.writeUTF(msj);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*public void enviarTrama(int nCodigo, String sTrama){
        for (Cliente ms:clientesConectados){
            ms.enviarTrama(nCodigo, sTrama);
        }
    }*/
    
    public void conectaNuevo(Cliente nuevo){
        for (Cliente clienteConectado : clientesConectados){
            nuevo.enviarDatos(1, clienteConectado);
        }
        clientesConectados.add(nuevo);
    }
    
    public void desconecta(Cliente eliminar){
        int nPos=-1;
        for (int n=0;n<clientesConectados.size();n++){
            if (clientesConectados.get(n)==eliminar){
                nPos=n;
            }
        }
        if (nPos!=-1){
            for (int n=0;n<clientesConectados.size();n++){
                if (n!=nPos){
                    clientesConectados.get(n).enviarDatos(3, nPos);
                }
            }
            clientesConectados.remove(nPos);
        }
    }
}
