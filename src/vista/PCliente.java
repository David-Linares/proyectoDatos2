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

@SuppressWarnings("serial")
public class PCliente extends JFrame {

	General general = General.getInstance();
	private JPanel contentPane;
	private JTextField tfIp;
	private JTextField tfPuerto;
	private JTextField tfNombreCliente;
	private JTextField tfMonto;
	private JLabel lblNombre;
	private JLabel lblMontoInicial;
	private JLabel lblDatosDeConexin;
	PrincipalSubastaCliente psubasta;

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfIp = new JTextField("127.0.0.1");
		tfIp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char car = evt.getKeyChar();
				if ((car < '0' || car > '9')) {
					if (car != '.') {
						evt.consume();
					}
				}
			}
		});

		tfIp.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		tfIp.setBounds(217, 58, 209, 23);
		contentPane.add(tfIp);
		tfIp.setColumns(10);

		tfPuerto = new JTextField("8090");
		tfPuerto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car2 = evt.getKeyChar();
				if ((car2 < '0' || car2 > '9'))
					evt.consume();
			}
		});
		tfPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		tfPuerto.setColumns(10);
		tfPuerto.setBounds(217, 93, 209, 23);
		contentPane.add(tfPuerto);

		tfNombreCliente = new JTextField();
		tfNombreCliente.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		tfNombreCliente.setBounds(217, 128, 209, 23);
		contentPane.add(tfNombreCliente);
		tfNombreCliente.setColumns(10);

		tfMonto = new JTextField();
		tfMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
		});
		tfMonto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		tfMonto.setColumns(10);
		tfMonto.setBounds(217, 163, 209, 23);
		contentPane.add(tfMonto);

		JLabel lblNewLabel = new JLabel("Indique la Ip del Servidor");
		lblNewLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(45, 58, 154, 23);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Indique el Puerto");
		lblNewLabel_1.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(90, 93, 109, 23);
		contentPane.add(lblNewLabel_1);

		lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(139, 128, 60, 23);
		contentPane.add(lblNombre);

		lblMontoInicial = new JLabel("Monto Inicial");
		lblMontoInicial.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblMontoInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoInicial.setBounds(115, 163, 84, 23);
		contentPane.add(lblMontoInicial);

		lblDatosDeConexin = new JLabel("Datos de Conexi\u00f3n");
		lblDatosDeConexin.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDatosDeConexin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosDeConexin.setBounds(115, 12, 247, 34);
		contentPane.add(lblDatosDeConexin);

		JButton btnNewButton = new JButton("Conectarme");
		btnNewButton.setBounds(105, 198, 224, 34);
		contentPane.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			// Inicia el hilo! - OK
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				// JOptionPane.showMessageDialog(new JFrame(),
				// "PCliente / click a conectarse");
				validacion();

				try {
					int puerto = Integer.parseInt(tfPuerto.getText());
					String ip = tfIp.getText();
					if (!tfNombreCliente.getText().equals("")) {
						return;
					}
					Cliente nuevoCliente = new Cliente(tfNombreCliente
							.getText(), Double.parseDouble(tfMonto.getText()));
					if (General.cliente == null) {
						General.cliente = new CCliente(puerto, ip, nuevoCliente);
						psubasta = new PrincipalSubastaCliente();
						General.cliente.setVentanaCliente(psubasta);
						general.setPanelSubastaCliente(psubasta
								.getPanelSubasta());
						General.cliente.start();
					}
					// PENDIENTE
					psubasta.listConectados.setModel(general.listadoConectados);
					psubasta.setVisible(true);
					setVisible(false);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(),
							"PCliente / Se produjo un error" + e.getMessage());
					General.cliente = null;
				}

			}

		});

	}

	private void validacion() {
		String ip = this.tfIp.getText();
		String puerto = this.tfPuerto.getText();
		String monto = this.tfMonto.getText();
		String nombre = this.tfNombreCliente.getText();

		String mensaje = "";

		if (ip.equals("")) {
			mensaje = "\u00A1Debe escribir la IP!\n";
			JOptionPane.showMessageDialog(null, mensaje, "\u00A1Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (puerto.equals("")) {
			mensaje = "\u00A1Debe escribir el numero de puerto!\n";
			JOptionPane.showMessageDialog(null, mensaje, "\u00A1Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (nombre.equals("")) {
			mensaje = "\u00A1Debe escribir el nombre de usuario!\n";
			JOptionPane.showMessageDialog(null, mensaje, "\u00A1Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (monto.equals("")) {
			mensaje = "\u00A1Debe escribir el monto!\n";
			JOptionPane.showMessageDialog(null, mensaje, "\u00A1Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		return;

	}

}