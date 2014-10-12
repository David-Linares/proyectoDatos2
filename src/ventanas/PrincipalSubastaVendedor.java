package ventanas;
import general.General;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingConstants;

import models.Cliente;


@SuppressWarnings("serial")
public class PrincipalSubastaVendedor extends JFrame implements Runnable {

	private JPanel contentPane;
	private JList listConectados = new JList();
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
		
		for(int i = 0; i <= General.clientesConectados.size() - 1; i++ ){
			listadoConectados.addElement(General.clientesConectados.get(i).getNombre());
		}
		
		listConectados.setModel(listadoConectados);
		
		JButton btnNewButton = new JButton("Finalizar Subasta");
		btnNewButton.setBounds(285, 357, 211, 37);
		contentPane.add(btnNewButton);
		
		JLabel productoSubastado = new JLabel("New label");
		productoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		productoSubastado.setBounds(10, 11, 345, 71);
		contentPane.add(productoSubastado);
		
		productoSubastado.setText(General.productoSeleccionado.getNombre() + " = " + General.productoSeleccionado.getValor());
	}

	public void run() {
		try {
			ServerSocket vendedor = new ServerSocket(General.puerto);
			Socket nuevaConexion;
			Cliente clienteEntrante;
			while (true) {
				nuevaConexion = vendedor.accept();
				System.out.println("Entró un cliente");
				ObjectInputStream entrada = new ObjectInputStream(nuevaConexion.getInputStream());
				clienteEntrante = (Cliente) entrada.readObject();
				General.clientesConectados.add(clienteEntrante);
				listadoConectados.removeAllElements();
				for(int i = 0; i <= General.clientesConectados.size() - 1; i++ ){
					listadoConectados.addElement(General.clientesConectados.get(i).getNombre());
				}
				listConectados.setModel(listadoConectados);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
}
