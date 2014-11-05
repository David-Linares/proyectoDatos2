package controlador;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import vista.PrincipalSubastaVendedor;
import modelo.Cliente;
import modelo.Producto;

public class General {

	private static General general;
	private static Producto[] productos;
	private static ArrayList<Conexion> conexiones = new ArrayList<Conexion>();
	private static Temporizador reloj = null;
	private static String tiempo;
	@SuppressWarnings("rawtypes")
	private static DefaultListModel listadoConectados = new DefaultListModel();
	
	private static Producto productoSeleccionado;
	private static CServidor servidor = null;
	private static CCliente cliente = null;
	private static PrincipalSubastaVendedor ventanaServidor;
	private static JTextPane panelSubastaCliente;
	
	public static String getTiempo() {
		return tiempo;
	}


	public static void setTiempo(String tiempo) {
		General.tiempo = tiempo;
	}


	public static Temporizador getReloj() {
		return reloj;
	}


	public static void setReloj(Temporizador reloj) {
		General.reloj = reloj;
	}


	public static Producto[] getProductos() {
		return productos;
	}


	public static void setProductos(Producto[] productos) {
		General.productos = productos;
	}


	@SuppressWarnings("rawtypes")
	public static DefaultListModel getListadoConectados() {
		return listadoConectados;
	}

	
	@SuppressWarnings("rawtypes")
	public static void setListadoConectados(DefaultListModel listadoConectados) {
		General.listadoConectados = listadoConectados;
	}

		
	public static CServidor getServidor() {
		return servidor;
	}


	public static void setServidor(CServidor servidor) {
		General.servidor = servidor;
	}


	public static CCliente getCliente() {
		return cliente;
	}

	public static void setCliente(CCliente cliente) {
		General.cliente = cliente;
	}

	

	public static PrincipalSubastaVendedor getVentanaServidor() {
		return ventanaServidor;
	}

	public static void setVentanaServidor(PrincipalSubastaVendedor ventanaServidor) {
		General.ventanaServidor = ventanaServidor;
	}

	public Icon getIcon(String nombreIcono){
		return new ImageIcon("images/"+nombreIcono+".png");
	}
	
	public JTextPane getPanelSubastaCliente() {
		return panelSubastaCliente;
	}

	public static void setConexiones(ArrayList<Conexion> conexiones) {
		General.conexiones = conexiones;
	}


	public static void setPanelSubastaCliente(JTextPane panelSubastaCliente) {
		General.panelSubastaCliente = panelSubastaCliente;
	}


	private General(){
		Producto producto1 = new Producto(1, "Bicicleta", 200000, "Azul para niño, Halley 19055 Playera Rod 16 con ruedas de apoyo");
		Producto producto2 = new Producto(2, "Carro", 35000000, "Rojo deportivo modelo 2008 marca chevrolet");
		Producto producto3 = new Producto(3, "Guitarra", 400000, "Electrica marca vintage color cafe y blanco");
		Producto producto4 = new Producto(4, "Maleta", 140000, "Marca orion liviana rosada con brillantes");
		Producto producto5 = new Producto(5, "Calculadora", 100000, "Graficadora marca Casio Claspad 330 ");
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
	
	
	
	
	/*
	 * Iterator recorrido;
	 int n=0;
	 int pos = -1;
	 recorrido = conexiones.Iterator();
	 while(recorrido.hasNext()){
	 if (recorrido.next()== cliente){
	 pos=n;
	 }
	 n++;
	 }
	 if(pos!=-1){
		n=0;
	 recorrido = conexiones.Iterator();
	 while(recorrido.hasNext()){
	 if(n!=pos){
	
	 recorrido.next().entradaDatosConexion(3, ""+pos)
	 
	 }
	 }
	 recorrido.remove(ciente);
	 }
	 
	 */
	
	public boolean validarMonto(String valor) {
		try{
			long monto = Long.parseLong(valor);
			
			if (monto > General.cliente.getClienteConectado().getMonto()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Valor superior al monto inicial", "Datos",
						JOptionPane.INFORMATION_MESSAGE, general.getIcon("error"));
				return false;
				
			} else if (monto < General.getProductoSeleccionado().getValor()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Valor debe ser superior al actual", "Datos",
						JOptionPane.INFORMATION_MESSAGE, general.getIcon("error"));
				return false;
				
			}
			return true;			
		}catch(Exception e){
			return false;
		}

	}
	public static boolean validarExistente(Cliente nuevoCliente){
		if (listadoConectados.contains(nuevoCliente.getNombre()))
			return true;
		else
			return false;
	}
}
