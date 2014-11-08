package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import controlador.CCliente;
import controlador.General;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PConexion extends JFrame {

	/**
	 * 
	 */
	PrincipalSubastaCliente psubasta;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfIp;
	private JTextField tfPuerto;
	General general = General.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PConexion frame = new PConexion();
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
	public PConexion() {
		setTitle("Datos de Conexi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 447, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Datos de Conexi\u00F3n");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
		label.setBounds(90, 11, 247, 34);
		contentPane.add(label);

		JLabel lblIpPConexion = new JLabel("Indique la Ip del Servidor");
		lblIpPConexion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIpPConexion.setFont(new Font("Dialog", Font.BOLD, 10));
		lblIpPConexion.setBounds(28, 57, 154, 23);
		contentPane.add(lblIpPConexion);

		tfIp = new JTextField("127.0.0.1");
		tfIp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					iniciarConexion();
				}
			}
		});
		tfIp.setFont(new Font("Dialog", Font.BOLD, 10));
		tfIp.setColumns(10);
		tfIp.setBounds(200, 57, 209, 23);
		contentPane.add(tfIp);

		JLabel lblPuertoConexion = new JLabel("Indique el Puerto");
		lblPuertoConexion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuertoConexion.setFont(new Font("Dialog", Font.BOLD, 10));
		lblPuertoConexion.setBounds(73, 92, 109, 23);
		contentPane.add(lblPuertoConexion);

		tfPuerto = new JTextField("8090");
		tfPuerto.setFont(new Font("Dialog", Font.BOLD, 10));
		tfPuerto.setColumns(10);
		tfPuerto.setBounds(200, 92, 209, 23);
		contentPane.add(tfPuerto);

		JButton btnConectarme = new JButton("Conectarme");
		btnConectarme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarConexion();
			}
		});
		btnConectarme.setBounds(102, 136, 224, 34);
		contentPane.add(btnConectarme);
	}

	private void iniciarConexion() {
		if (validacion()) {
			try {
				//JOptionPane.showMessageDialog(new JFrame(), "PConexion / Entró a Iniciar Conexión");
				int puerto = Integer.parseInt(tfPuerto.getText());
				String ip = tfIp.getText();
				if (General.getCliente() == null) {
					General.setCliente(new CCliente(puerto, ip));
					General.getCliente().start();
				}
				//JOptionPane.showMessageDialog(new JFrame(), "PConexion / Pasó el Run de Cliente y va a crear la nueva ventana");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(),
						"PConexion / Se produjo un error " + e.getMessage());
				General.setCliente(null);
			}
			PCliente pcliente = new PCliente();
			General.setVentanaDatosCliente(pcliente);
			setVisible(false);
			pcliente.setVisible(true);
		}
	}

	private boolean validacion() {
		String ipV = this.tfIp.getText();
		String puertoV = this.tfPuerto.getText();

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
		} else {
			return true;
		}

	}
}
