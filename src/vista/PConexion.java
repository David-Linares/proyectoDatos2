package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PConexion extends JFrame {

	private JPanel contentPane;
	private JTextField tfIp;
	private JTextField tfPuerto;

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
		btnConectarme.setBounds(121, 136, 224, 34);
		contentPane.add(btnConectarme);
	}
}
