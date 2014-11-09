package controlador;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import vista.DatosCliente;
import vista.VendedorSubasta;
import modelo.Producto;

@SuppressWarnings("rawtypes")
public class General{
	
	/*Variables*/
	private static General general;
	private static Producto[] productos;
	private static ArrayList<ConexionClienteServidor> conexiones = new ArrayList<ConexionClienteServidor>();
	private static Temporizador reloj = null;
	private static String tiempo;
	private static DefaultListModel listadoConectados = new DefaultListModel();
	private static DefaultListModel listadoConectadosTemp = new DefaultListModel();
	private static ConexionClienteServidor conexionTemp;
	private static Producto productoSeleccionado;
	private static ConexionServidor servidor = null;
	private static ConexionCliente cliente = null;
	/*Ventanas*/
	private static DatosCliente ventanaDatosCliente;
	private static VendedorSubasta ventanaServidor;
	private static JTextPane panelSubastaCliente;
	
	private General(){
		Producto producto1 = new Producto(1, "Bicicleta", 200000, "Azul para ni\u00f1o, Marco giro con ruedas de apoyo");
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
	
	public static DefaultListModel getListadoConectadosTemp() {
		return listadoConectadosTemp;
	}

	public static void setListadoConectadosTemp(
			DefaultListModel listadoConectadosTemp) {
		General.listadoConectadosTemp = listadoConectadosTemp;
	}

	public static DatosCliente getVentanaDatosCliente() {
		return ventanaDatosCliente;
	}

	public static void setVentanaDatosCliente(DatosCliente ventanaDatosCliente) {
		General.ventanaDatosCliente = ventanaDatosCliente;
	}

	public static ConexionClienteServidor getConexionTemp() {
		return conexionTemp;
	}

	public static void setConexionTemp(ConexionClienteServidor conexionTemp) {
		System.out.println("General / Se Guardó la variable de Conexión Temporal.");
		General.conexionTemp = conexionTemp;
	}

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

		
	public static ConexionServidor getServidor() {
		return servidor;
	}


	public static void setServidor(ConexionServidor servidor) {
		General.servidor = servidor;
	}


	public static ConexionCliente getCliente() {
		return cliente;
	}

	public static void setCliente(ConexionCliente cliente) {
		General.cliente = cliente;
	}

	

	public static VendedorSubasta getVentanaServidor() {
		return ventanaServidor;
	}

	public static void setVentanaServidor(VendedorSubasta ventanaServidor) {
		General.ventanaServidor = ventanaServidor;
	}

	public Icon getIcon(String nombreIcono){
		return new ImageIcon("images/"+nombreIcono+".png");
	}
	
	public JTextPane getPanelSubastaCliente() {
		return panelSubastaCliente;
	}

	public static void setConexiones(ArrayList<ConexionClienteServidor> conexiones) {
		General.conexiones = conexiones;
	}


	public static void setPanelSubastaCliente(JTextPane panelSubastaCliente) {
		General.panelSubastaCliente = panelSubastaCliente;
	}
	
	public static Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public static void setProductoSeleccionado(Producto productoSeleccionado) {
		General.productoSeleccionado = productoSeleccionado;
	}

	public ArrayList<ConexionClienteServidor> getConexiones() {
		return conexiones;
	}

	//RECORRE TODAS LAS CONEXIONES EXISTENTES Y ENVIA A CONEXION UNA NUEVA ENTRADA DE DATOS
	public void enviarDatos(int operacion, Object sMensaje){
		for(ConexionClienteServidor con : conexiones){
			con.entradaDatosConexion(operacion, sMensaje);
		}
	}
	
	
	
	//NOTIFICA A UN CLIENTE NUEVO TODAS LAS CONEXIONES EXISTENTES
	public static void nuevaConexion(ConexionClienteServidor nuevo){
		System.out.println("General / Entró una nueva conexión y se va a recorrer las conexiones ");
		System.out.println("General / la variable Conexiónes = "+conexiones);
		for(ConexionClienteServidor con: conexiones){
			nuevo.entradaDatosConexion(2, con.getClienteTemp());
		}
		conexiones.add(nuevo);
		System.out.println("General / la variable Conexiónes = "+conexiones+" después de agregarle el nuevo.");		
	}
	
	//DESCONECTA Y ELIMINA LA CONEXION DE UN CLIENTE
	public void desconecta(ConexionClienteServidor cliente){
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
	
}
