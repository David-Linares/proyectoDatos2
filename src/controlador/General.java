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
		Producto producto1 = new Producto(1, "Bicicleta", 200000, "Para ni\u00f1o, marco gios numero 28 con ruedas de apoyo");
		Producto producto2 = new Producto(2, "Carro", 35000000, "Rojo deportivo modelo 2008 marca chevrolet, motor 1800, coupe, modificado");
		Producto producto3 = new Producto(3, "Guitarra", 400000, "Electrica marca vintage color cafe y blanco");
		Producto producto4 = new Producto(4, "Morral", 140000, "Marca orion liviana rosada con brillantes decorativos");
		Producto producto5 = new Producto(5, "Teatro en casa", 400000, "Home Cinema LG BH4030S BlueRay 3D 5.1, 5 bocinas");
		Producto producto6 = new Producto(6, "Televisor", 1200000,"Smart TV LED LG 32LB5610 de 42'' ULTRA HD con camara, sensor de movimiento y comandos de voz");
		Producto producto7 = new Producto(7, "Casa", 30000000,"Inmueble ubicado en el norte de Bogota, con 130 metros cuadrados, cercano a vias principales, buena iluminacion");
		Producto producto8 = new Producto(8, "Piano", 180000, "Teclado de marmol, madera de sauce, modelo 1978");
		Producto producto9 = new Producto(9, "Prueba", 100000000, "sadkvmksdlamv sakodjvl askmdvl  lasdkvml sak mv lsadkmv lsak mv lskdmv lskmvskld slkdmvlksdv lskdmvl skmdv l sakdmvlsakdvm  sldkvmslkmvslk vsalkdmvlskdvm lsdkmvlskdvmdslk lskdmvlk sdokvjsadoi soidjvosidvj saoidjvo sivjds osadjvosid vkawarjgoi alkejglk ajkfgj aksfj glaskjlkvgfjg laskfj fsjdlgvkjsf aslfjglkdfjglkjdsf laskfjglk ");
		
		productos = new Producto[]{
				 producto1,
				 producto2,
				 producto3,
				 producto4,
				 producto5,
				 producto6,
				 producto7,
				 producto8,
				 producto9
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
		System.out.println("General / Se GuardÃ³ la variable de ConexiÃ³n Temporal.");
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
		return new ImageIcon("src/images/"+nombreIcono+".png");
		

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
	public static void enviarDatos(int operacion, Object sMensaje){
		JOptionPane.showMessageDialog(new JFrame(), "General / Se va a enviar datos "+sMensaje);
		JOptionPane.showMessageDialog(new JFrame(), "General / COnexiones "+conexiones);
		for(ConexionClienteServidor con : conexiones){
			con.entradaDatosConexion(operacion, sMensaje);
		}
	}	
	
	//NOTIFICA A UN CLIENTE NUEVO TODAS LAS CONEXIONES EXISTENTES
	public static void nuevaConexion(ConexionClienteServidor nuevo){
		JOptionPane.showMessageDialog(new JFrame(), "General / Llegó una nueva conexión para guadar");
		JOptionPane.showMessageDialog(new JFrame(), "General / antes "+conexiones);
		for(ConexionClienteServidor con: conexiones){
			nuevo.entradaDatosConexion(2, con.getClienteTemp());
		}
		conexiones.add(nuevo);
		JOptionPane.showMessageDialog(new JFrame(), "General / Después "+conexiones.size());
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
				
			} else if (monto <= General.getProductoSeleccionado().getValor()) {
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
