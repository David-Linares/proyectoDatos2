package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
		setBounds(100, 100, 454, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfIp = new JTextField("127.0.0.1");
		tfIp.setBounds(217, 75, 209, 34);
		contentPane.add(tfIp);
		tfIp.setColumns(10);

		tfPuerto = new JTextField("8090");
		tfPuerto.setColumns(10);
		tfPuerto.setBounds(217, 120, 209, 37);
		contentPane.add(tfPuerto);

		tfNombreCliente = new JTextField();
		tfNombreCliente.setBounds(217, 169, 209, 37);
		contentPane.add(tfNombreCliente);
		tfNombreCliente.setColumns(10);

		tfMonto = new JTextField();
		tfMonto.setColumns(10);
		tfMonto.setBounds(217, 218, 209, 37);
		contentPane.add(tfMonto);

		JLabel lblNewLabel = new JLabel("Indique la Ip del Servidor");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(12, 73, 187, 34);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Indique el Puerto");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(12, 121, 187, 34);
		contentPane.add(lblNewLabel_1);

		lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(12, 172, 187, 34);
		contentPane.add(lblNombre);

		lblMontoInicial = new JLabel("Monto Inicial");
		lblMontoInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoInicial.setBounds(12, 218, 187, 34);
		contentPane.add(lblMontoInicial);

		lblDatosDeConexin = new JLabel("Datos de Conexi�n");
		lblDatosDeConexin.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDatosDeConexin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosDeConexin.setBounds(106, 12, 247, 34);
		contentPane.add(lblDatosDeConexin);

		JButton btnNewButton = new JButton("Conectarme");
		btnNewButton.setBounds(133, 287, 175, 34);
		contentPane.add(btnNewButton);
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrincipalSubastaCliente psubasta = new PrincipalSubastaCliente();
				try {
					int puerto = Integer.parseInt(tfPuerto.getText());
					String ip = tfIp.getText();
					Cliente nuevoCliente = new Cliente(tfNombreCliente.getText(), Double.parseDouble(tfMonto.getText()));
					if (general.cliente==null){
						general.cliente = new CCliente(puerto, ip, psubasta, nuevoCliente);
						general.cliente.start();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				//try {
				
					Cliente clientenuevo = null;
					setVisible(false);
					
					//clientenuevo = new Cliente(InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(tfPuerto.getText()), tfNombreCliente.getText(), Double.parseDouble(tfMonto.getText()));
					//clientenuevo.start();
					psubasta.setVisible(true);
					
				//}// catch (NumberFormatException e1) {
					//e1.printStackTrace();
					
				//} //catch (UnknownHostException e1) {
					//e1.printStackTrace();
					
				}
				//general.conectaNuevo(clientenuevo);
			//}
		});
				

			}
	
		

}