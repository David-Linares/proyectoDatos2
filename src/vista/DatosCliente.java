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
	private JPanel contentPane;
	private JTextField tfNombreCliente;
	private JTextField tfMonto;
	private JLabel lblNombre;
	private JLabel lblMontoInicial;
	private JLabel lblDatosDeConexin;
	ClienteSubasta psubasta;
	private JLabel lblProductoSubastaCliente;
	private JTextArea tADescripcionProducto= new JTextArea();
	private JScrollPane scrollTextArea = new JScrollPane(tADescripcionProducto);


	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public DatosCliente() {
		setFont(new Font("Calibri", Font.BOLD, 12));
		setTitle("Datos del Cliente");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatosCliente.class.getResource("/images/customer.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 339);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100,149,237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfNombreCliente = new JTextField();
		tfNombreCliente.setForeground(new Color(51, 102, 255));
		tfNombreCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					entrarSubasta();
				}
			}
		});
		tfNombreCliente.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfNombreCliente.setBounds(179, 185, 259, 29);
		contentPane.add(tfNombreCliente);
		tfNombreCliente.setColumns(10);

		tfMonto = new JTextField();
		tfMonto.setForeground(new Color(51, 102, 255));

		tfMonto.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if (tfMonto.getText().length() >= 18)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					entrarSubasta();
				}
			}
		});
		tfMonto.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfMonto.setColumns(10);
		tfMonto.setBounds(179, 225, 259, 29);
		contentPane.add(tfMonto);

		JLabel lblNewLabel = new JLabel("Producto en Subasta");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 48, 154, 34);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Descripci\u00F3n de Producto");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 93, 159, 34);
		contentPane.add(lblNewLabel_1);

		lblNombre = new JLabel("Nick");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(10, 177, 159, 34);
		contentPane.add(lblNombre);

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

		JButton btnIngresarSubasta = new JButton("Ingresar a La Subasta");
		btnIngresarSubasta.setBackground(new Color(255, 255, 255));
		btnIngresarSubasta.setForeground(new Color(51, 102, 255));
		btnIngresarSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnIngresarSubasta.setBounds(105, 265, 224, 34);
		contentPane.add(btnIngresarSubasta);

		lblProductoSubastaCliente = new JLabel("");
		lblProductoSubastaCliente.setBackground(new Color(255, 255, 255));
		lblProductoSubastaCliente.setForeground(new Color(255, 255, 255));
		lblProductoSubastaCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductoSubastaCliente.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblProductoSubastaCliente.setBounds(179, 48, 259, 34);
		contentPane.add(lblProductoSubastaCliente);


		tADescripcionProducto.setForeground(new Color(51, 102, 255));
		scrollTextArea.setFont(new Font("SansSerif", Font.BOLD, 12));
		tADescripcionProducto.setWrapStyleWord(true);
		tADescripcionProducto.setLineWrap(true);
		tADescripcionProducto.setEditable(false);
		scrollTextArea.setBounds(179, 93, 259, 81);
		contentPane.add(scrollTextArea);

		btnIngresarSubasta.addActionListener(new ActionListener() {
			// Inicia el hilo! - OK
			public void actionPerformed(ActionEvent arg0) {
				entrarSubasta();
			}

		});

	}

	@SuppressWarnings("unchecked")
	private void entrarSubasta() {
		if (validacion()) {
			try {
				Cliente nuevoCliente = new Cliente(tfNombreCliente.getText().toLowerCase().trim(),
						Long.parseLong(tfMonto.getText()));
				System.out.println("PCliente / Se creó un nuevo cliente = "+nuevoCliente);
				System.out.println("PCliente / la variable getCliente = "+General.getCliente());
				if (General.getCliente() != null) {
					General.getCliente().setClienteConectado(nuevoCliente);
					psubasta = new ClienteSubasta();
					General.setPanelSubastaCliente(psubasta.getPanelSubasta());
					General.getCliente().setVentanaCliente(psubasta);
					System.out.println("PCliente / la variable getCliente = "+General.getCliente()+" después de agregarle el cliente");
					System.out.println("PCliente / la variable getCliente = "+General.getCliente()+" después de agregarle la ventana");
				}
				System.out.println("PCliente / la variable listadoConectados = "+General.getListadoConectados());
				psubasta.getListConectados().setModel(
						General.getListadoConectados());
				psubasta.setVisible(true);
				setVisible(false);
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(new JFrame(),
					//	"PCliente / Se produjo un error " + e.getMessage());
				//General.setCliente(null);
				e.printStackTrace();
			}

		}
	}

	private boolean validacion() {
		String montoV = this.tfMonto.getText();
		String nombreV = this.tfNombreCliente.getText().toLowerCase().trim();

		String mensajeV = "";

		long montoEntero;
		if (nombreV.equals("")) {
			mensajeV = "\u00A1Debe escribir el nombre de usuario!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfNombreCliente.requestFocus();
			return false;
		} else if (General.getListadoConectadosTemp().contains(nombreV)) {
			mensajeV = "\u00A1El Nick "
					+ nombreV
					+ " ya est\u00e1 registrado en la subasta\n Por favor intenta con uno nuevo";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfNombreCliente.setText("");
			tfNombreCliente.requestFocus();
			return false;
		} else if (montoV.equals("")) {
			mensajeV = "\u00A1Debe escribir el monto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfMonto.requestFocus();
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
					"\u00A1Bienvenido!", JOptionPane.INFORMATION_MESSAGE,
					general.getIcon("confirm"));
			return true;
		}

	}

	public JLabel getLblProductoSubastaCliente() {
		return lblProductoSubastaCliente;
	}

	public void setLblProductoSubastaCliente(JLabel lblProductoSubastaCliente) {
		this.lblProductoSubastaCliente = lblProductoSubastaCliente;
	}

	public JTextArea gettADescripcionProducto() {
		return tADescripcionProducto;
	}

	public void settADescripcionProducto(JTextArea tADescripcionProducto) {
		this.tADescripcionProducto = tADescripcionProducto;
	}

	public void keyTyped(java.awt.event.KeyEvent evt) {
		char car = evt.getKeyChar();
		if (tfMonto.getText().length() >= 18)
			evt.consume();
		if ((car < '0' || car > '9')) {
			evt.consume();
		}
	}
}