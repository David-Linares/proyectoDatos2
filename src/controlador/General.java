package controlador;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import vista.DatosCliente;
import vista.SubastaVendedor;
import modelo.Producto;

@SuppressWarnings("rawtypes")
public class General {

	/* ATRIBUTOS */
	private static General general;
	private static Producto[] arrayProductos;
	private static ArrayList<ConexionClienteServidor> conexiones = new ArrayList<ConexionClienteServidor>();
	private static ArrayList<ConexionClienteServidor> conexionesTemp = new ArrayList<ConexionClienteServidor>();
	private static Temporizador reloj = null;
	private static String tiempo;
	private static int posicionConexionTemp;
	private static DefaultListModel listadoConectados = new DefaultListModel();
	private static DefaultListModel listadoConectadosTemp = new DefaultListModel();
	private static Producto productoSeleccionado;
	private static ConexionClienteServidor conexionTemporal;
	private static ConexionServidor conexServidor = null;
	private static ConexionCliente conexCliente = null;

	/* VENTANAS */
	private static DatosCliente ventanaDatosCliente;
	private static SubastaVendedor ventanaServidor;
	private static JTextPane panelSubastaCliente;
	/* CONSTRUCTOR DE GENERAL, CREA LOS PRODUCTOS PARA LA SUBASTA */
	private General() {
		Producto producto1 = new Producto(1, "Bicicleta", 200000,
				"Para ni\u00f1o, marco gios numero 28 con ruedas de apoyo");
		Producto producto2 = new Producto(2, "Carro", 35000000,
				"Rojo deportivo modelo 2008 marca chevrolet, motor 1800, coupe, modificado");
		Producto producto3 = new Producto(3, "Guitarra", 400000,
				"Electrica marca vintage color cafe y blanco");
		Producto producto4 = new Producto(4, "Morral", 140000,
				"Marca orion liviana rosada con brillantes decorativos");
		Producto producto5 = new Producto(5, "Teatro en casa", 400000,
				"Home Cinema LG BH4030S BlueRay 3D 5.1, 5 bocinas");
		Producto producto6 = new Producto(
				6,
				"Televisor",
				1200000,
				"Smart TV LED LG 32LB5610 de 42'' ULTRA HD con camara, sensor de movimiento y comandos de voz");
		Producto producto7 = new Producto(
				7,
				"Casa",
				30000000,
				"Inmueble ubicado en el norte de Bogota, con 130 metros cuadrados, cercano a vias principales, buena iluminacion");
		Producto producto8 = new Producto(8, "Piano", 180000,
				"Teclado de marmol, madera de sauce, modelo 1978");

		arrayProductos = new Producto[] { producto1, producto2, producto3,
				producto4, producto5, producto6, producto7, producto8,

		};
	}
	/* CREA LA CLASE SINGLETON, EVITA CREAR VARIAS INTANCIAS. SOLO PERMITE UNA A LA VEZ.
	ES SINCRONIZADO CON EL FIN DE QUE LAS INSTANCIAS QUE SE CREAN ACCEDAN A LA CLASE 
	SEA DE FORMA ORDENADA*/
	public static synchronized General getInstance() {
		if (general == null)
			general = new General();

		return general;
	}
		
	public static int getPosicionConexionTemp() {
		return posicionConexionTemp;
	}
	public static void setPosicionConexionTemp(int posicionConexionTemp) {
		General.posicionConexionTemp = posicionConexionTemp;
	}
	public static ArrayList<ConexionClienteServidor> getConexionesTemp() {
		return conexionesTemp;
	}
	public static void setConexionesTemp(
			ArrayList<ConexionClienteServidor> conexionesTemp) {
		General.conexionesTemp = conexionesTemp;
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
		return conexionTemporal;
	}

	public static void setConexionTemp(ConexionClienteServidor conexionTemp) {
		General.conexionTemporal = conexionTemp;
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
		return arrayProductos;
	}

	public static void setProductos(Producto[] productos) {
		General.arrayProductos = productos;
	}

	public static DefaultListModel getListadoConectados() {
		return listadoConectados;
	}

	public static void setListadoConectados(DefaultListModel listadoConectados) {
		General.listadoConectados = listadoConectados;
	}

	public static ConexionServidor getServidor() {
		return conexServidor;
	}

	public static void setServidor(ConexionServidor servidor) {
		General.conexServidor = servidor;
	}

	public static ConexionCliente getConexCliente() {
		return conexCliente;
	}

	public static void setConexCliente(ConexionCliente cliente) {
		General.conexCliente = cliente;
	}

	public static SubastaVendedor getVentanaServidor() {
		return ventanaServidor;
	}

	public static void setVentanaServidor(SubastaVendedor ventanaServidor) {
		General.ventanaServidor = ventanaServidor;
	}

	public Icon getIcon(String nombreIcono) {
		return new ImageIcon("src/images/" + nombreIcono + ".png");

	}

	public JTextPane getPanelSubastaCliente() {
		return panelSubastaCliente;
	}

	public static void setConexiones(
			ArrayList<ConexionClienteServidor> conexiones) {
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

	/*RECORRE TODAS LAS CONEXIONES EXISTENTES Y ENVIA A CONEXION UNA NUEVA
	 ENTRADA DE DATOS*/
	public static void enviarDatos(int operacion, Object sMensaje) {
		for (ConexionClienteServidor con : conexiones) {
			con.entradaDatosConexion(operacion, sMensaje);
		}
	}

	/*NOTIFICA A UN CLIENTE NUEVO TODAS LAS CONEXIONES EXISTENTES*/
	public static void nuevaConexion(ConexionClienteServidor nuevo) {
		for (ConexionClienteServidor con : conexiones) {
			nuevo.entradaDatosConexion(2, con.getClienteTemp());
		}
		conexiones.add(nuevo);

	}
	//Crea una conexión temporal 
	public static void nuevaConexionTemp(ConexionClienteServidor nuevo) {
		System.out.println("G / conexionnuevaTemporal "+nuevo);
		conexionesTemp.add(nuevo);
		posicionConexionTemp = conexionesTemp.size() -1;
		System.out.println("G / "+posicionConexionTemp);
	}

	// DESCONECTA Y ELIMINA LA CONEXION DE UN CLIENTE
	public void desconecta(ConexionClienteServidor cliente) {
		int pos = -1;
		for (int n = 0; n < conexiones.size(); n++) {
			if (conexiones.get(n) == cliente) {
				pos = n;
			}
		}
		if (pos != -1) {
			for (int n = 0; n < conexiones.size(); n++) {
				if (n != pos) {
					conexiones.get(n).entradaDatosConexion(3, "" + pos);
				}
			}
		}
		conexiones.remove(pos);
	}

	/*
	 * VALIDA EL MONTO QUE SE LE ESTA INGRESANDO DEBE SER MAYOR AL ACTUAL Y
	 * MENOR AL QUE INGRESO A LA SUBASTA
	 */
	public boolean validarMonto(String valor) {
		try {
			long monto = Long.parseLong(valor);

			if (monto > General.conexCliente.getClienteConectado().getMonto()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Valor superior al monto inicial", "Datos",
						JOptionPane.INFORMATION_MESSAGE,
						general.getIcon("error"));
				return false;

			} else if (monto <= General.getProductoSeleccionado().getValor()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Valor debe ser superior al actual", "Datos",
						JOptionPane.INFORMATION_MESSAGE,
						general.getIcon("error"));

				return false;

			}
			return true;
		} catch (Exception e) {
			return false;
		}
		
		

	}

}
