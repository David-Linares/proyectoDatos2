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

import controlador.CServidor;
import controlador.General;
import controlador.Temporizador;
import modelo.Producto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.awt.Component;

@SuppressWarnings("serial")
public class PVendedor extends JFrame {

	private JPanel contentPane;

	General general = General.getInstance();
	private JTextField textFieldPuerto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PVendedor frame = new PVendedor();
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
	public PVendedor() {
		setTitle("Principal Vendedor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSeleccioneElProducto = new JLabel(
				"Seleccione el producto a Subastar");
		lblSeleccioneElProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccioneElProducto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblSeleccioneElProducto.setBounds(12, 47, 198, 20);
		contentPane.add(lblSeleccioneElProducto);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox listaProductos = new JComboBox(General.getProductos());
		listaProductos.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblSeleccioneElProducto.setLabelFor(listaProductos);
		listaProductos.setBounds(228, 47, 294, 30);
		contentPane.add(listaProductos);

		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblPuerto.setBounds(133, 96, 77, 14);
		contentPane.add(lblPuerto);

		textFieldPuerto = new JTextField();
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
		textFieldPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		textFieldPuerto.setText("8090");
		textFieldPuerto.setBounds(228, 89, 294, 30);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);

		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.setBounds(133, 257, 264, 32);
		contentPane.add(btnIniciarSubasta);
		
		JTextArea tADescripcionProducto = new JTextArea();
		tADescripcionProducto.setLineWrap(true);
		tADescripcionProducto.setWrapStyleWord(true);
		tADescripcionProducto.setEditable(false);
		tADescripcionProducto.setBounds(228, 126, 294, 119);
		contentPane.add(tADescripcionProducto);
		
		JLabel lblDescripcin = new JLabel("Descripción");
		lblDescripcin.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblDescripcin.setBounds(140, 127, 70, 15);
		contentPane.add(lblDescripcin);
		
		JLabel lblVentanaPrincipalDe = new JLabel("Ventana Principal de Vendedor");
		lblVentanaPrincipalDe.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 20));
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
							General.setServidor(new CServidor(puerto));
							PrincipalSubastaVendedor principalSubasta = new PrincipalSubastaVendedor();
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
