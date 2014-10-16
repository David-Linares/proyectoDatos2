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
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class PrincipalSubastaVendedor extends JFrame{

	private JPanel contentPane;
	private JList listConectados = new JList();
	private JLabel labelIp;
	JTextArea taMensajesSubasta = new JTextArea();
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
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		listConectados.setBounds(365, 93, 131, 254);
		contentPane.add(listConectados);
		
		
		listConectados.setModel(listadoConectados);
		
		JButton btnNewButton = new JButton("Finalizar Subasta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
			taMensajesSubasta.setBounds(10, 94, 345, 254);
			contentPane.add(taMensajesSubasta);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void agregarNuevo(Cliente nuevoCliente){
		listadoConectados.addElement(nuevoCliente);
	}

	public void mensajeRecibido(String nuevoMensaje) {
		taMensajesSubasta.append(nuevoMensaje + "\n");
	}
}
