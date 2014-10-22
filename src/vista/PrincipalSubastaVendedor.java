package vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;

import controlador.CServidor;
import controlador.General;
import modelo.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.JTextPane;


@SuppressWarnings("serial")
public class PrincipalSubastaVendedor extends JFrame{

	private JPanel contentPane;
	public JList listConectados = new JList();
	private JLabel labelIp;
	private JTextPane tpMensajesSubasta;
	General general = General.getInstance();
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
		setBounds(100, 100, 522, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		listConectados.setFont(new Font("Dialog", Font.BOLD, 11));
		
		
		listConectados.setBounds(365, 45, 131, 254);
		contentPane.add(listConectados);
		
		
		listConectados.setModel(general.listadoConectados);
		
		JButton btnNewButton = new JButton("Finalizar Subasta");
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(285, 310, 211, 37);
		contentPane.add(btnNewButton);
		
		JLabel productoSubastado = new JLabel("New label");
		productoSubastado.setFont(new Font("Dialog", Font.BOLD, 11));
		productoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		productoSubastado.setBounds(10, 11, 345, 23);
		contentPane.add(productoSubastado);
		
		productoSubastado.setText(general.productoSeleccionado.getNombre() + " = " + general.productoSeleccionado.getValor());
		new CServidor(tpMensajesSubasta).start();
		try {
			labelIp = new JLabel("IP: "+InetAddress.getLocalHost().getHostAddress());
			labelIp.setFont(new Font("Dialog", Font.BOLD, 11));
			labelIp.setBounds(365, 11, 131, 23);
			contentPane.add(labelIp);
			} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(), "SubastaVendedor / Se produjo un error "+e.getMessage());
		}
	}	

	public void mensajeRecibido(String nuevoMensaje) {
	// ??? COMO LO CAMBIO	tpMensajesSubasta.append(nuevoMensaje + "\n");
	}
}
