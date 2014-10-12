package general;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import models.Cliente;
import models.Producto;

public class General {
	
	private static General general;
	public static ArrayList<Producto> productos;
	public static ArrayList<Cliente> clientesConectados = new ArrayList<Cliente>();
	public static String productoSeleccionado;
	public static ServerSocket servidor;
	public static Socket socket;
	public static int puerto = 8129;
	private General(){
		 productos = new ArrayList();
		 Producto producto1 = new Producto("Bicicleta", 200000);
		 Producto producto2 = new Producto("Carro", 35000000);
		 Producto producto3 = new Producto("Guitarra", 400000);
		 Producto producto4 = new Producto("Maleta", 140000);
		 Producto producto5 = new Producto("Calculadora", 100000);
		 productos.add(producto1);
		 productos.add(producto2);
		 productos.add(producto3);
		 productos.add(producto4);
		 productos.add(producto5);
	}
	
	public static General getInstance(){
		if (general == null)
			general = new General();
		
		return general;
	}
	
}
