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
import modelo.Producto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		setTitle("Producto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSeleccioneElProducto = new JLabel(
				"Seleccione el producto a Subastar");
		lblSeleccioneElProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccioneElProducto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblSeleccioneElProducto.setBounds(12, 41, 198, 20);
		contentPane.add(lblSeleccioneElProducto);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox listaProductos = new JComboBox(General.productos);
		listaProductos.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblSeleccioneElProducto.setLabelFor(listaProductos);
		listaProductos.setBounds(228, 40, 222, 20);
		contentPane.add(listaProductos);

		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblPuerto.setBounds(133, 86, 77, 14);
		contentPane.add(lblPuerto);

		textFieldPuerto = new JTextField();
		textFieldPuerto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char car = evt.getKeyChar();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
		});
		textFieldPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		textFieldPuerto.setText("8090");
		textFieldPuerto.setBounds(228, 83, 222, 20);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);

		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.setBounds(98, 128, 264, 42);
		contentPane.add(btnIniciarSubasta);

		btnIniciarSubasta.addActionListener(new ActionListener() {
			// OK
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {

				// METODO VALIDACION
				if (validacion()) {

					General.setProductoSeleccionado((Producto) listaProductos
							.getSelectedItem());
					PrincipalSubastaVendedor principalSubasta = new PrincipalSubastaVendedor();
					if (General.servidor == null) {
						int puerto = Integer.parseInt(textFieldPuerto.getText());
						general.setTextPaneVendedor(principalSubasta
								.getTpMensajesSubasta());
						general.setContenedorConexiones(principalSubasta.getListConectados());
						General.servidor = new CServidor(puerto); // Poner el
																	// TextPane
																	// de la
																	// clase
																	// general.
						General.servidor.start();
					}

					setVisible(false);
					general.getContenedorConexiones()
							.setModel(general.listadoConectados);
					principalSubasta.setVisible(true);
				}
			}
		});

	}

	protected void showMessageDialog(ActionListener actionListener,
			String string) {
		// TODO Auto-generated method stub

	}

	private boolean validacion() {
		String puerto = this.textFieldPuerto.getText();
		String mensajeV = "";

		if (puerto.equals("")) {
			mensajeV = "\u00A1Debe escribir el numero de puerto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;

	}
}
