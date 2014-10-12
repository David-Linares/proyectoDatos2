package ventanas;

import general.General;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import models.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatosCliente extends JFrame {

	private JPanel contentPane;
	private JTextField tfNombreCliente;
	private JTextField tfMonto;
	General general = General.getInstance();
	Cliente cliente = new Cliente(); //Importante verificar id del cliente.

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfNombreCliente = new JTextField();
		tfNombreCliente.setBounds(133, 28, 243, 37);
		contentPane.add(tfNombreCliente);
		tfNombreCliente.setColumns(10);
		
		tfMonto = new JTextField();
		tfMonto.setColumns(10);
		tfMonto.setBounds(133, 87, 243, 37);
		contentPane.add(tfMonto);
		
		JButton btnNewButton = new JButton("Entrar a la Subasta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cliente.setNombre(tfNombreCliente.getText());
				cliente.setMonto(Double.parseDouble(tfMonto.getText()));
				general.clientesConectados.add(cliente);
				setVisible(false);
				PrincipalSubasta principalSubasta = new PrincipalSubasta();
				principalSubasta.setVisible(true);
			}
		});
		btnNewButton.setBounds(125, 163, 170, 37);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(77, 39, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblMontoInicial = new JLabel("Monto Inicial");
		lblMontoInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoInicial.setBounds(44, 98, 79, 14);
		contentPane.add(lblMontoInicial);
	}

}
