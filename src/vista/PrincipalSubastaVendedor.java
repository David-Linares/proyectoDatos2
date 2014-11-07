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
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class PrincipalSubastaVendedor extends JFrame {

	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JList listConectados = new JList();
	private JLabel labelIp;
	private JTextPane tpMensajesSubastaVendedor = new JTextPane();
	private JScrollPane scrollPanel = new JScrollPane(tpMensajesSubastaVendedor);
	General general = General.getInstance();
	private JScrollPane scrollLista = new JScrollPane();
	private JLabel lblProductoSubastado;
	private JLabel lblProductoSeleccionadoDescripcion;
	private JLabel lblReloj;
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
		return tpMensajesSubastaVendedor;
	}
	
	public JLabel getLblProductoSubastado() {
		return lblProductoSubastado;
	}
	
	public JLabel getLblReloj() {
		return lblReloj;
	}

	public void setLblReloj(JLabel lblReloj) {
		this.lblReloj = lblReloj;
	}



	@SuppressWarnings("unchecked")
	public PrincipalSubastaVendedor() {
		setResizable(false);
		tpMensajesSubastaVendedor.setEditable(false);
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollLista.setFont(new Font("Dialog", Font.BOLD, 11));
		scrollLista.setBounds(418, 116, 131, 254);
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
		btnNewButton.setBounds(384, 382, 165, 29);
		contentPane.add(btnNewButton);
		
		lblProductoSubastado = new JLabel();
		lblProductoSubastado.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblProductoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductoSubastado.setBounds(10, 7, 356, 23);
		contentPane.add(lblProductoSubastado);

		lblProductoSubastado.setText(General.getProductoSeleccionado().getNombre()
				+ " = " + General.getProductoSeleccionado().getValor());
		
		lblProductoSeleccionadoDescripcion = new JLabel();
		lblProductoSeleccionadoDescripcion.setFont(new Font("DejaVu Sans", Font.ITALIC, 10));
		lblProductoSeleccionadoDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductoSeleccionadoDescripcion.setBounds(10, 42, 356, 53);
		contentPane.add(lblProductoSeleccionadoDescripcion);
		
		lblProductoSeleccionadoDescripcion.setText(General.getProductoSeleccionado().getDescripcion());
		
		try {
			labelIp = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress());
			labelIp.setHorizontalAlignment(SwingConstants.CENTER);
			labelIp.setFont(new Font("Dialog", Font.BOLD, 11));
			labelIp.setBounds(418, 57, 131, 23);
			contentPane.add(labelIp);
			scrollPanel.setBounds(10, 116, 396, 254);
			contentPane.add(scrollPanel);
			
			lblReloj = new JLabel();
			lblReloj.setHorizontalAlignment(SwingConstants.CENTER);
			lblReloj.setFont(new Font("DialogInput", Font.PLAIN, 20));
			lblReloj.setBounds(394, 7, 155, 37);
			contentPane.add(lblReloj);
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"SubastaVendedor / Se produjo un error " + e.getMessage());
		}
	}

	public void mensajeRecibido(String nuevoMensaje) {
		tpMensajesSubastaVendedor.setText(tpMensajesSubastaVendedor.getText() + nuevoMensaje
				+ "\n");
	}
	
	public void borrarCliente(String nombre) {
		int pos = General.getListadoConectados().indexOf(nombre);
		General.getListadoConectados().remove(pos);
		
	}
}
