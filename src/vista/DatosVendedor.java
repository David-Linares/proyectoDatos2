package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import controlador.ConexionServidor;
import controlador.General;
import modelo.Producto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;

@SuppressWarnings("serial")
public class DatosVendedor extends JFrame {

	private JPanel contentPane;
	General general = General.getInstance();
	private JTextField textFieldPuerto;
	private JTextArea tADescripcionProducto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatosVendedor frame = new DatosVendedor();
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
	public DatosVendedor() {
		setFont(new Font("Calibri", Font.BOLD, 12));
		setTitle("Principal Vendedor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100,149,237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSeleccioneElProducto = new JLabel(
				"Seleccione el producto a Subastar");
		lblSeleccioneElProducto.setForeground(new Color(255, 255, 255));
		lblSeleccioneElProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccioneElProducto.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 13));
		lblSeleccioneElProducto.setBounds(10, 89, 208, 27);
		contentPane.add(lblSeleccioneElProducto);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox listaProductos = new JComboBox(General.getProductos());
		listaProductos.setForeground(new Color(51, 102, 255));
		listaProductos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Producto item = (Producto) e.getItem();
				tADescripcionProducto.setText(item.getDescripcion());
			}
		});
		listaProductos.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblSeleccioneElProducto.setLabelFor(listaProductos);
		listaProductos.setBounds(228, 88, 294, 30);
		contentPane.add(listaProductos);

		JLabel lblPuerto = new JLabel("Digite el Puerto de Conexi\u00F3n");
		lblPuerto.setForeground(new Color(255, 255, 255));
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuerto.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 13));
		lblPuerto.setBounds(37, 48, 181, 27);
		contentPane.add(lblPuerto);

		textFieldPuerto = new JTextField();
		textFieldPuerto.setForeground(new Color(51, 102, 255));
		textFieldPuerto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char car = evt.getKeyChar();
				if (textFieldPuerto.getText().length() >= 5)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
		});
		textFieldPuerto.setFont(new Font("SansSerif", Font.BOLD, 12));
		textFieldPuerto.setText("8090");
		textFieldPuerto.setBounds(228, 47, 294, 30);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);

		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.setForeground(new Color(51,102,255));
		btnIniciarSubasta.setBackground(new Color(255, 255, 255));
		btnIniciarSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnIniciarSubasta.setBounds(133, 257, 264, 32);
		contentPane.add(btnIniciarSubasta);
		
		tADescripcionProducto = new JTextArea();
		tADescripcionProducto.setForeground(new Color(51, 102, 255));
		tADescripcionProducto.setFont(new Font("SansSerif", Font.BOLD, 12));
		tADescripcionProducto.setLineWrap(true);
		tADescripcionProducto.setWrapStyleWord(true);
		tADescripcionProducto.setEditable(false);
		tADescripcionProducto.setBounds(228, 126, 294, 119);
		contentPane.add(tADescripcionProducto);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n del Producto a Subastar");
		lblDescripcin.setForeground(new Color(255, 255, 255));
		lblDescripcin.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 13));
		lblDescripcin.setBounds(10, 127, 208, 27);
		contentPane.add(lblDescripcin);
		
		JLabel lblVentanaPrincipalDe = new JLabel("Ventana Principal de Vendedor");
		lblVentanaPrincipalDe.setForeground(new Color(255, 255, 255));
		lblVentanaPrincipalDe.setFont(new Font("Kristen ITC", Font.BOLD, 18));
		lblVentanaPrincipalDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblVentanaPrincipalDe.setBounds(12, 0, 510, 35);
		contentPane.add(lblVentanaPrincipalDe);

		btnIniciarSubasta.addActionListener(new ActionListener() {
			// OK
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {

				// METODO VALIDACION
				if (validacion()) {

					General.setProductoSeleccionado((Producto) listaProductos
							.getSelectedItem());
					if (General.getServidor() == null) {
						int puerto = Integer.parseInt(textFieldPuerto.getText());
						try{
							General.setServidor(new ConexionServidor(puerto));
							VendedorSubasta principalSubasta = new VendedorSubasta();
							General.setVentanaServidor(principalSubasta);
							General.getServidor().start();
							setVisible(false);
							General.getVentanaServidor().getListConectados().setModel(General.getListadoConectados());
							principalSubasta.setVisible(true);
						}catch (Exception e){
							
						}
					}

				}
			}
		});

	}

	private boolean validacion() {
		String puerto = this.textFieldPuerto.getText();
		String mensajeV = "";

		if (puerto.equals("")) {
			mensajeV = "\u00A1Debe escribir el n\u00famero de puerto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			textFieldPuerto.requestFocus();
			return false;
		}
		return true;

	}
}
