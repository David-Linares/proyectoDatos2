package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PCliente extends JFrame {

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfIp = new JTextField();
		tfIp.setBounds(189, 32, 212, 34);
		contentPane.add(tfIp);
		tfIp.setColumns(10);
		
		tfPuerto = new JTextField();
		tfPuerto.setColumns(10);
		tfPuerto.setBounds(189, 85, 212, 34);
		contentPane.add(tfPuerto);
		
		JLabel lblNewLabel = new JLabel("Indique la Ip del Servidor");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 32, 169, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Indique el Puerto");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 85, 169, 34);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Conectarme");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				DatosCliente datosCliente = new DatosCliente();
				datosCliente.setVisible(true);
			}
		});
		btnNewButton.setBounds(130, 164, 175, 34);
		contentPane.add(btnNewButton);
	}
}
