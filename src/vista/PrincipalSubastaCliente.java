package vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlador.General;

@SuppressWarnings("serial")
public class PrincipalSubastaCliente extends JFrame{

	private JPanel contentPane;
	General general = General.getInstance();
	DefaultListModel listadoConectados = new DefaultListModel();
	private JTextField textField;
	public JList listConectados = new JList();
	private JLabel labelIpcliente;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalSubastaCliente frame = new PrincipalSubastaCliente();
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
	public PrincipalSubastaCliente() {/*
		Thread hilo = new Thread(this);
		hilo.start();*/
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listSubasta = new JList();
		listSubasta.setBounds(10, 98, 443, 260);
		contentPane.add(listSubasta);
		
		
		listConectados.setBounds(465, 98, 131, 254);
		contentPane.add(listConectados);
		
		for(int i = 0; i <= General.clientesConectados.size() - 1; i++ ){
			listadoConectados.addElement(General.clientesConectados.get(i).getNombre());
		}
		
		listConectados.setModel(listadoConectados);
		
		JButton btnNewButton = new JButton("Finalizar Subasta");
		btnNewButton.setBounds(385, 428, 211, 37);
		contentPane.add(btnNewButton);
		
		JLabel productoSubastado = new JLabel("New label");
		productoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		productoSubastado.setBounds(10, 12, 443, 74);
		contentPane.add(productoSubastado);
		
		textField = new JTextField();
		textField.setBounds(10, 370, 443, 46);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setBounds(465, 364, 131, 52);
		contentPane.add(btnNewButton_1);
		
		JLabel labelIpcliente = new JLabel("IP servidor: " + general.servidor.getInetAddress().getHostAddress().toString());
		labelIpcliente.setBounds(465, 11, 119, 14);
		contentPane.add(labelIpcliente);
	}

	/*public void run() {
		try {
			ServerSocket clienteServidor = new ServerSocket(general.puerto);
			Socket cliente;
			Cliente conectado;
			
			while (true) {
				cliente = clienteServidor.accept();
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				conectado = (Cliente) entrada.readObject();
				listadoConectados.removeAllElements();
				for(int i = 0; i <= general.clientesConectados.size() - 1; i++ ){
					listadoConectados.addElement(general.clientesConectados.get(i).getNombre());
				}
				listConectados.setModel(listadoConectados);
				cliente.close();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}*/
}
