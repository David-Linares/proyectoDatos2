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
	public static Producto[] productos;
	private ArrayList<Conexion> conexiones = new ArrayList<Conexion>();
	@SuppressWarnings("rawtypes")
	public static DefaultListModel listadoConectados = new DefaultListModel();
	private static Producto productoSeleccionado;
	public static CServidor servidor = null;
	public static CCliente cliente = null;
	private static PrincipalSubastaVendedor ventanaServidor;
	private JTextPane panelSubastaCliente;
	
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

	public void setPanelSubastaCliente(JTextPane panelSubastaCliente) {
		this.panelSubastaCliente = panelSubastaCliente;
	}
	
	
	//Hay que poner un nuevo textPane que es el que se va a implementar en todas las ventanas nuevas.
	//Crear una variable de Conexión para el servidor (Leer comentario CServidor Linea 35)

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
