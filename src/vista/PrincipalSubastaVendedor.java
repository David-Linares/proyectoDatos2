package vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;

import controlador.General;
import modelo.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class PrincipalSubastaVendedor extends JFrame implements Runnable {

	private JPanel contentPane;
	private JList listConectados = new JList();
	private JLabel labelIp;
	General general = General.getInstance();
	DefaultListModel listadoConectados = new DefaultListModel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalSubastaVendedor frame = new PrincipalSubastaVendedor();
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
	public PrincipalSubastaVendedor() {
		Thread hilo = new Thread(this);
		hilo.start();
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listSubasta = new JList();
		listSubasta.setBounds(10, 93, 345, 254);
		contentPane.add(listSubasta);
		
		
		listConectados.setBounds(365, 93, 131, 254);
		contentPane.add(listConectados);
		
		for(int i = 0; i <= general.clientesConectados.size() - 1; i++ ){
			listadoConectados.addElement(general.clientesConectados.get(i).getNombre());
		}
		
		listConectados.setModel(listadoConectados);
		
		JButton btnNewButton = new JButton("Finalizar Subasta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					general.servidor.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnNewButton.setBounds(285, 357, 211, 37);
		contentPane.add(btnNewButton);
		
		JLabel productoSubastado = new JLabel("New label");
		productoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		productoSubastado.setBounds(10, 11, 345, 71);
		contentPane.add(productoSubastado);
		
		productoSubastado.setText(general.productoSeleccionado.getNombre() + " = " + general.productoSeleccionado.getValor());
		
		try {
			labelIp = new JLabel("IP: "+InetAddress.getLocalHost().getHostAddress());
			labelIp.setBounds(365, 22, 131, 31);
			contentPane.add(labelIp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			general.servidor = new ServerSocket(general.puerto);
			labelIp.setText(general.servidor.getInetAddress().getHostAddress().toString());
			Socket nuevaConexion;
			Cliente clienteEntrante;
			while (true) {
				nuevaConexion = general.servidor.accept();
				System.out.println("Entró un cliente");
				ObjectInputStream entrada = new ObjectInputStream(nuevaConexion.getInputStream());
				clienteEntrante = (Cliente) entrada.readObject();
				//general.getInstance().conectaNuevo(clienteEntrante);
				listadoConectados.removeAllElements();
				for(int i = 0; i <= general.clientesConectados.size() - 1; i++ ){
					listadoConectados.addElement(general.clientesConectados.get(i).getNombre());
				}
				listConectados.setModel(listadoConectados);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
}
