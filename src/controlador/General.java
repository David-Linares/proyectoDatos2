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
	public static int ipServidor;
	public static Socket socket;
	public static int puerto = 9080;

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

}
