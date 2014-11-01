package controlador;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextPane;

import modelo.Producto;

public class General {

	private static General general;
	public static Producto[] productos;
	private ArrayList<Conexion> conexiones = new ArrayList<Conexion>();
	@SuppressWarnings("rawtypes")
	public DefaultListModel listadoConectados = new DefaultListModel();
	private static Producto productoSeleccionado;
	public static CServidor servidor = null;
	public static CCliente cliente = null;
	public static int puerto = 9090;
	private JTextPane panelSubastaCliente;	
	@SuppressWarnings("rawtypes")
	private JList contenedorConexiones;
	
	public JTextPane getPanelSubastaCliente() {
		return panelSubastaCliente;
	}

	public void setPanelSubastaCliente(JTextPane panelSubastaCliente) {
		this.panelSubastaCliente = panelSubastaCliente;
	}

	public JTextPane getTextPaneVendedor(){
		return panelSubastaCliente;
	}
	
	public void setTextPaneVendedor(JTextPane panelCliente){
		this.panelSubastaCliente = panelCliente;
	}
	
	
	//Hay que poner un nuevo textPane que es el que se va a implementar en todas las ventanas nuevas.
	//Crear una variable de Conexión para el servidor (Leer comentario CServidor Linea 35)

	@SuppressWarnings("rawtypes")
	public JList getContenedorConexiones() {
		return contenedorConexiones;
	}

	@SuppressWarnings("rawtypes")
	public void setContenedorConexiones(JList contenedorConexiones) {
		this.contenedorConexiones = contenedorConexiones;
	}

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
	
	public static Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public static void setProductoSeleccionado(Producto productoSeleccionado) {
		General.productoSeleccionado = productoSeleccionado;
	}

	public ArrayList<Conexion> getConexiones() {
		return conexiones;
	}

	//RECORRE TODAS LAS CONEXIONES EXISTENTES Y ENVIA A CONEXION UNA NUEVA ENTRADA DE DATOS
	public void enviarDatos(int operacion, Object sMensaje){
		for(Conexion con : conexiones){
			con.entradaDatosConexion(operacion, sMensaje);
		}
	}
	
	//NOTIFICA A UN CLIENTE NUEVO TODAS LAS CONEXIONES EXISTENTES
	public void nuevaConexion(Conexion nuevo){
		for(Conexion con: conexiones){
			nuevo.entradaDatosConexion(1, con.getClienteTemp());
		}
		conexiones.add(nuevo);
	}
	
	//DESCONECTA Y ELIMINA LA CONEXION DE UN CLIENTE
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
					conexiones.get(n).entradaDatosConexion(3, ""+pos);
				}
			}
		}
		conexiones.remove(pos);
	}
	
	public static boolean esNumero(String valor){
		try{
			Integer.parseInt(valor);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
