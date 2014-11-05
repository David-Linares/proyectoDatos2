package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;

import controlador.General;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class PrincipalSubastaVendedor extends JFrame {

	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JList listConectados = new JList();
	private JLabel labelIp;
	private JTextPane tpMensajesSubasta = new JTextPane();
	private JScrollPane panelScroll = new JScrollPane(tpMensajesSubasta);
	General general = General.getInstance();
	private JScrollPane scrollLista = new JScrollPane();
	private JLabel lblProductoSubastado;

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

	
	
	@SuppressWarnings("rawtypes")
	public JList getListConectados() {
		return listConectados;
	}



	@SuppressWarnings("rawtypes")
	public void setListConectados(JList listConectados) {
		this.listConectados = listConectados;
	}



	public JTextPane getTpMensajesSubasta() {
		return tpMensajesSubasta;
	}
	
	public JLabel getLblProductoSubastado() {
		return lblProductoSubastado;
	}



	@SuppressWarnings("unchecked")
	public PrincipalSubastaVendedor() {
		setResizable(false);
		tpMensajesSubasta.setEditable(false);
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollLista.setFont(new Font("Dialog", Font.BOLD, 11));
		scrollLista.setBounds(365, 45, 131, 254);
		scrollLista.setViewportView(listConectados);
		contentPane.add(scrollLista);

		listConectados.setModel(General.getListadoConectados());

		JButton btnNewButton = new JButton("Finalizar Subasta");
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(285, 310, 211, 37);
		contentPane.add(btnNewButton);
		
		lblProductoSubastado = new JLabel();
		lblProductoSubastado.setFont(new Font("Dialog", Font.BOLD, 11));
		lblProductoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductoSubastado.setBounds(10, 11, 345, 23);
		contentPane.add(lblProductoSubastado);

		lblProductoSubastado.setText(General.getProductoSeleccionado().getNombre()
				+ " = " + General.getProductoSeleccionado().getValor());
		try {
			labelIp = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress());
			labelIp.setFont(new Font("Dialog", Font.BOLD, 11));
			labelIp.setBounds(365, 11, 131, 23);
			contentPane.add(labelIp);
			panelScroll.setBounds(10, 45, 345, 254);
			contentPane.add(panelScroll);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"SubastaVendedor / Se produjo un error " + e.getMessage());
		}
	}

	public void mensajeRecibido(String nuevoMensaje) {
		// Con la variable que creaste de conexión de servidor (Leer CServidor
		// linea 35) llamas el textPane
		// que tiene esa variable
		// y le das el método para agregarle el texto, que creo que es
		// textPane.setText("....");
		tpMensajesSubasta.setText(tpMensajesSubasta.getText() + nuevoMensaje
				+ "\n");
	}
}
