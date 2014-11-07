package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import controlador.CCliente;
import controlador.General;
import modelo.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.awt.Component;

@SuppressWarnings("serial")
public class PCliente extends JFrame {

	General general = General.getInstance();
	private JPanel contentPane;
	private JTextField tfNombreCliente;
	private JTextField tfMonto;
	private JLabel lblNombre;
	private JLabel lblMontoInicial;
	private JLabel lblDatosDeConexin;
	PrincipalSubastaCliente psubasta;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PCliente frame = new PCliente();
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
	public PCliente() {
		setTitle("Datos del Cliente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfNombreCliente = new JTextField();
		tfNombreCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10){
					entrarSubasta();
				}
			}
		});
		tfNombreCliente.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		tfNombreCliente.setBounds(179, 185, 259, 29);
		contentPane.add(tfNombreCliente);
		tfNombreCliente.setColumns(10);

		tfMonto = new JTextField();
		/*tfMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if (tfPuerto.getText().length() >= 18)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10){
					entrarSubasta();
				}
			}
		});*/
		tfMonto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		tfMonto.setColumns(10);
		tfMonto.setBounds(179, 225, 259, 29);
		contentPane.add(tfMonto);

		JLabel lblNewLabel = new JLabel("Producto en Subasta");
		lblNewLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 48, 154, 34);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Descripci\u00F3n de Producto");
		lblNewLabel_1.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(20, 94, 149, 23);
		contentPane.add(lblNewLabel_1);

		lblNombre = new JLabel("Nick");
		lblNombre.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(109, 188, 60, 23);
		contentPane.add(lblNombre);

		lblMontoInicial = new JLabel("Monto Inicial");
		lblMontoInicial.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblMontoInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoInicial.setBounds(85, 228, 84, 23);
		contentPane.add(lblMontoInicial);

		lblDatosDeConexin = new JLabel("Datos del Cliente");
		lblDatosDeConexin.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 20));
		lblDatosDeConexin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosDeConexin.setBounds(96, 11, 247, 34);
		contentPane.add(lblDatosDeConexin);

		JButton btnIngresarSubasta = new JButton("Ingresar a La Subasta");
		btnIngresarSubasta.setBounds(105, 265, 224, 34);
		contentPane.add(btnIngresarSubasta);
		
		label = new JLabel(General.getProductoSeleccionado().getNombre() + " = "+General.getProductoSeleccionado().getValor());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 16));
		label.setBounds(179, 48, 259, 34);
		contentPane.add(label);
		
		JTextArea textArea = new JTextArea();
		textArea.append(General.getProductoSeleccionado().getDescripcion());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(179, 93, 259, 81);
		contentPane.add(textArea);

		btnIngresarSubasta.addActionListener(new ActionListener() {
			// Inicia el hilo! - OK
			@SuppressWarnings({ "unchecked", "static-access" })
			public void actionPerformed(ActionEvent arg0) {
				entrarSubasta();
			}

		});

	}
	
	private void entrarSubasta(){
		if (validacion()) { // problema

			/*try {
				int puerto = Integer.parseInt(tfPuerto.getText());
				String ip = tfIp.getText();
				Cliente nuevoCliente = new Cliente(tfNombreCliente
						.getText(), Long.parseLong(tfMonto.getText()));
				if (General.getCliente() == null) {
					General.setCliente(new CCliente(puerto, ip,
							nuevoCliente));
					psubasta = new PrincipalSubastaCliente();
					General.getCliente().setVentanaCliente(psubasta);
					General.setPanelSubastaCliente(psubasta
							.getPanelSubasta());
					General.getCliente().start();
				}
				// PENDIENTE
				psubasta.listConectados
						.setModel(General.getListadoConectados());
				psubasta.setVisible(true);
				setVisible(false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(
						new JFrame(),
						"PCliente / Se produjo un error"
								+ e.getMessage());
				General.setCliente(null);
			}*/

		}
	}
	
	private boolean validacion() {
		/*String ipV = this.tfIp.getText();
		String puertoV = this.tfPuerto.getText();
		String montoV = this.tfMonto.getText();
		String nombreV = this.tfNombreCliente.getText();

		String mensajeV = "";

		if (ipV.equals("")) {
			mensajeV = "\u00A1Debe escribir la IP!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfIp.requestFocus();
			return false;
		} else if (puertoV.equals("")) {
			mensajeV = "\u00A1Debe escribir el n\u00famero de puerto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfPuerto.requestFocus();
			return false;
		} else if (nombreV.equals("")) {
			mensajeV = "\u00A1Debe escribir el nombre de usuario!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfNombreCliente.requestFocus();
			return false;
		} else if (montoV.equals("")) {
			mensajeV = "\u00A1Debe escribir el monto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfMonto.requestFocus();
			return false;
		} else {
			
			JOptionPane.showMessageDialog(null, "Ha ingresado a la subasta",
					"\u00A1Bienvenido!", JOptionPane.INFORMATION_MESSAGE, general.getIcon("confirm"));
			*/return true;
		//}

	}
}