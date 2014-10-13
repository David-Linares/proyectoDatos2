package controlador;

import java.net.ServerSocket;
import java.net.Socket;
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

	
	public void enviarTrama(int nCodigo, String sTrama){
        for (Cliente ms:clientesConectados){
            ms.enviarTrama(nCodigo, sTrama);
        }
    }
    
    public void conectaNuevo(Cliente nuevo){
        for (Cliente ms:clientesConectados){
            nuevo.enviarTrama(1, ms.getNombre());
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
                    clientesConectados.get(n).enviarTrama(3, ""+nPos);
                }
            }
            clientesConectados.remove(nPos);
        }
    }
}
