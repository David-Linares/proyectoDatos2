package vista;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import controlador.General;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import modelo.Cliente;

import java.awt.Color;

@SuppressWarnings("serial")
public class DatosCliente extends JFrame {

	General general = General.getInstance();
	SubastaCliente panelSubastaCliente;
	
	private JPanel contentPane;
	private JLabel lblNick;
	private JLabel lblMontoInicial;
	private JLabel lblDatosDeConexin;
	private JLabel lblProductoSubastaCliente;
	private JTextField tfMontoCliente;
	private JTextField tfNickCliente;
	private JTextArea tADescripcionProductoCliente= new JTextArea();
	private JScrollPane scrollTextArea = new JScrollPane(tADescripcionProductoCliente);

	public JLabel getLblProductoSubastaCliente() {
		return lblProductoSubastaCliente;
	}

	public void setLblProductoSubastaCliente(JLabel lblProductoSubastaCliente) {
		this.lblProductoSubastaCliente = lblProductoSubastaCliente;
	}

	public JTextArea gettADescripcionProducto() {
		return tADescripcionProductoCliente;
	}

	public void settADescripcionProducto(JTextArea tADescripcionProducto) {
		this.tADescripcionProductoCliente = tADescripcionProducto;
	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatosCliente frame = new DatosCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*CREAR EL MARCO*/
	public DatosCliente() {
		setFont(new Font("Calibri", Font.BOLD, 12));
		setTitle("Datos del Cliente");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatosCliente.class.getResource("/images/customer.png")));
		setDefaultCloseOperation(0);
		setBounds(100, 100, 454, 339);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100,149,237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfNickCliente = new JTextField();
		tfNickCliente.setForeground(new Color(51, 102, 255));
		tfNickCliente.addKeyListener(new KeyAdapter() {
			/*EVENTO EN EL NICK DEL CLIENTE AL DARLE ENTER E INGRESAR A LA SUBASTA*/
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					entrarSubasta();
				}
			}
		});
		tfNickCliente.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfNickCliente.setBounds(179, 185, 259, 29);
		tfNickCliente.setColumns(10);
		contentPane.add(tfNickCliente);

		tfMontoCliente = new JTextField();
		tfMontoCliente.setForeground(new Color(51, 102, 255));
		tfMontoCliente.addKeyListener(new KeyAdapter() {
			
			/*EVENTO EN EL MONTOCLIENTE VALIDA QUE NO SEA MAYOR A 18 Y SOLO SEAN NÚMEROS*/
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if (tfMontoCliente.getText().length() >= 18)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
			/*EVENTO EN EL MONTOCLIENTE SI SE LE DA ENTER E INGRESA A LA SUBASTA*/
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					entrarSubasta();
				}
			}
		});
		tfMontoCliente.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfMontoCliente.setColumns(10);
		tfMontoCliente.setBounds(179, 225, 259, 29);
		contentPane.add(tfMontoCliente);

		JLabel lblProductoSubasta = new JLabel("Producto en Subasta");
		lblProductoSubasta.setForeground(new Color(255, 255, 255));
		lblProductoSubasta.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblProductoSubasta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductoSubasta.setBounds(10, 48, 154, 34);
		contentPane.add(lblProductoSubasta);

		JLabel lblDescripcionProducto = new JLabel("Descripci\u00F3n de Producto");
		lblDescripcionProducto.setForeground(new Color(255, 255, 255));
		lblDescripcionProducto.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblDescripcionProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionProducto.setBounds(10, 93, 159, 34);
		contentPane.add(lblDescripcionProducto);

		lblNick = new JLabel("Nick");
		lblNick.setForeground(new Color(255, 255, 255));
		lblNick.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblNick.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNick.setBounds(10, 177, 159, 34);
		contentPane.add(lblNick);

		lblMontoInicial = new JLabel("Monto Inicial");
		lblMontoInicial.setForeground(new Color(255, 255, 255));
		lblMontoInicial.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblMontoInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoInicial.setBounds(20, 217, 149, 34);
		contentPane.add(lblMontoInicial);

		lblDatosDeConexin = new JLabel("Datos del Cliente");
		lblDatosDeConexin.setForeground(new Color(255, 255, 255));
		lblDatosDeConexin.setFont(new Font("Kristen ITC", Font.BOLD, 18));
		lblDatosDeConexin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosDeConexin.setBounds(96, 11, 247, 34);
		contentPane.add(lblDatosDeConexin);

		lblProductoSubastaCliente = new JLabel("");
		lblProductoSubastaCliente.setBackground(new Color(255, 255, 255));
		lblProductoSubastaCliente.setForeground(new Color(255, 255, 255));
		lblProductoSubastaCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductoSubastaCliente.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblProductoSubastaCliente.setBounds(179, 48, 259, 34);
		contentPane.add(lblProductoSubastaCliente);
		
		tADescripcionProductoCliente.setForeground(new Color(51, 102, 255));
		scrollTextArea.setFont(new Font("SansSerif", Font.BOLD, 12));
		tADescripcionProductoCliente.setWrapStyleWord(true);
		tADescripcionProductoCliente.setLineWrap(true);
		tADescripcionProductoCliente.setEditable(false);
		scrollTextArea.setBounds(179, 93, 259, 81);
		contentPane.add(scrollTextArea);

		JButton btnIngresarSubastaCliente = new JButton("Ingresar a La Subasta");
		btnIngresarSubastaCliente.setBackground(new Color(255, 255, 255));
		btnIngresarSubastaCliente.setForeground(new Color(51, 102, 255));
		btnIngresarSubastaCliente.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnIngresarSubastaCliente.setBounds(105, 265, 224, 34);
		contentPane.add(btnIngresarSubastaCliente);
		btnIngresarSubastaCliente.addActionListener(new ActionListener() {
			
			/*SE CREA EL HILO DE ACUERDO A LA INFORMACION QUE DIGITO Y CADA UNA DE SUS VALIDACIONES*/
			public void actionPerformed(ActionEvent arg0) {
				entrarSubasta();
			}

		});

	}
	/*SE CREA UN NUEVO CLIENTE SEGUN LA INFORMACION DIGITADA
	 SI LA CONEXION NO ESTA NULA ENTONCES A ESA CONEXION SE LE ASIGNA ESE CLIENTE COMO CLIENTECONECTADO, 
	 SE CREA LA VENTANA DE SUBASTACLIENTE  
	 
	 */
	@SuppressWarnings("unchecked")
	private void entrarSubasta() {
		if (validacion()) {
			try {
				Cliente nuevoCliente = new Cliente(tfNickCliente.getText().toLowerCase().trim(),Long.parseLong(tfMontoCliente.getText()));
				if (General.getConexCliente() != null) {
					//JOptionPane.showMessageDialog(new JFrame(), "DC / Entró a asignarle el cliente ");
					General.getConexCliente().setClienteConectado(nuevoCliente);
					//JOptionPane.showMessageDialog(new JFrame(), "DC / Le asignó el cliente"+General.getCliente().getClienteConectado().getNombre());
					panelSubastaCliente = new SubastaCliente();
					General.setPanelSubastaCliente(panelSubastaCliente.getPanelSubasta());
					General.getConexCliente().setVentanaCliente(panelSubastaCliente);
				}
				//JOptionPane.showMessageDialog(new JFrame(), "Datos cliente / "+General.getListadoConectados());
				panelSubastaCliente.getListConectados().setModel(General.getListadoConectados());
				panelSubastaCliente.setVisible(true);
				setVisible(false);
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(new JFrame(),
					//	"PCliente / Se produjo un error " + e.getMessage());
				//General.setCliente(null);
				e.printStackTrace();
			}

		}
	}

	/*VALIDACIONES 
	 VERIFICA SI DIGITO UN NICK Y MONTO
	 SI EL NICK YA SE ENCUENTRA REGISTRADO 
	 Y SI EL MONTO CON EL QUE QUIERE INGRESAR ES MAYOR AL ACTUAL DEL PRODUCTO*/
	private boolean validacion() {
		String montoV = this.tfMontoCliente.getText();
		String nombreV = this.tfNickCliente.getText().toLowerCase().trim();

		String mensajeV = "";

		long montoEntero;
		if (nombreV.equals("")) {
			mensajeV = "\u00A1Debe escribir el nick de usuario!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfNickCliente.requestFocus();
			return false;
		} else if (General.getListadoConectadosTemp().contains(nombreV)) {
			mensajeV = "\u00A1El Nick "
					+ nombreV
					+ " ya est\u00e1 registrado en la subasta\n Por favor intenta con uno nuevo";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfNickCliente.setText("");
			tfNickCliente.requestFocus();
			return false;
		} else if (montoV.equals("")) {
			mensajeV = "\u00A1Debe escribir el monto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfMontoCliente.requestFocus();
			return false;
		} else

			montoEntero = Long.parseLong(montoV);

		if (montoEntero <= General.getProductoSeleccionado().getValor()) {
			mensajeV = "\u00A1El monto debe ser mayor al valor actual del producto ("
					+ General.getProductoSeleccionado().getValor() + ")!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));

			return false;
		} else {
			JOptionPane.showMessageDialog(null, "Bienvenido a la subasta",
					"\u00A1Bienvenido! "+nombreV, JOptionPane.INFORMATION_MESSAGE,
					general.getIcon("confirm"));
			return true;
		}

	}

	
	
}