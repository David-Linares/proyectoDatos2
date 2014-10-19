package controlador;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import modelo.Producto;

public class General {

	private static General general;
	public static Producto[] productos;
	private ArrayList<Conexion> conexiones = new ArrayList<Conexion>();
	public DefaultListModel listadoConectados = new DefaultListModel();
	public static Producto productoSeleccionado;
	public static CServidor servidor = null;
	public static CCliente cliente = null;
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
	//OK
	public void enviarDatos(int operacion, Object sMensaje){
		for(Conexion con : conexiones){
			con.enviarDatos(operacion, sMensaje);
		}
	}
	//OK
	public void nuevaConexion(Conexion nuevo){
		for(Conexion con: conexiones){
			nuevo.enviarDatos(1, con.getClienteTemp());
		}
		conexiones.add(nuevo);
	}
	
	public void desconecta(Conexion cliente){
		int pos = -1;
		for(int n= 0; n<conexiones.size(); n++){
			if(conexiones.get(n) == cliente){
				pos = n;
			}
		}
		if(pos != -1){
			for(int n= 0; n<conexiones.size(); n++){
				if(n != pos ){
					conexiones.get(n).enviarDatos(3, ""+pos);
				}
			}
		}
		conexiones.remove(pos);
	}
}
