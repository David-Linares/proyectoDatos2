package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Cliente;
import controlador.General;
import controlador.Temporizador;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class PrincipalSubastaCliente extends JFrame {

	private JPanel contentPane;
	General general = General.getInstance();
	private JTextField tfMensaje;
	public JList listConectados = new JList();
	private JLabel labelIpcliente;
	private JTextPane panelSubasta = new JTextPane();
	private JScrollPane panelScroll = new JScrollPane(panelSubasta);

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

	public JTextPane getPanelSubasta() {
		return panelSubasta;
	}

	public void setPanelSubasta(JTextPane panelSubasta) {
		this.panelSubasta = panelSubasta;
	}

	/**
	 * Create the frame.
	 */
	public PrincipalSubastaCliente() {
		setResizable(false);
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 506);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		listConectados.setFont(new Font("DejaVu Sans", Font.BOLD, 11));

		listConectados.setBounds(465, 92, 131, 254);
		contentPane.add(listConectados);

		JButton btnAbandonarSubasta = new JButton("Abandonar Subasta");
		btnAbandonarSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ok
				if (general.cliente != null) {
					general.cliente.enviarDatosCliente(3, null);
					general.cliente.interrupt();
				}
				general.cliente = null;
				setVisible(false);
				general.listadoConectados.removeAllElements();
				panelSubasta.setText("");
				Principal regreso = new Principal();
				regreso.setVisible(true);
			}
		});
		btnAbandonarSubasta.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		btnAbandonarSubasta.setBounds(385, 428, 211, 37);
		contentPane.add(btnAbandonarSubasta);

		JLabel lblProductoSubastado = new JLabel("New label");
		lblProductoSubastado.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		lblProductoSubastado.setHorizontalAlignment(SwingConstants.LEFT);
		lblProductoSubastado.setBounds(10, 66, 156, 15);
		contentPane.add(lblProductoSubastado);

		tfMensaje = new JTextField();
		tfMensaje.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		tfMensaje.addKeyListener(new KeyAdapter() {
			@Override
			// OK
			public void keyPressed(KeyEvent eve) {

				if (eve.getKeyCode() == 10) {
					enviarMensaje();
				}
			}
		});
		tfMensaje.setBounds(10, 385, 443, 31);
		contentPane.add(tfMensaje);
		tfMensaje.setColumns(10);

		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {

			//ACCION DEL EVENTO AL ENVIAR UN MENSAJE
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje();
			}
		});
		btnNewButton_1.setBounds(465, 384, 131, 31);
		contentPane.add(btnNewButton_1);

		JLabel lblNombreCliente = new JLabel("Nombre: "
				+ general.cliente.getClienteConectado().getNombre());
		lblNombreCliente.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		lblNombreCliente.setBounds(10, 12, 172, 15);
		contentPane.add(lblNombreCliente);

		JLabel lblMontoCliente = new JLabel("Monto Disponible: "
				+ general.cliente.getClienteConectado().getMonto());
		lblMontoCliente.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		lblMontoCliente.setBounds(10, 39, 443, 15);
		contentPane.add(lblMontoCliente);
		JLabel lblIpCliente;
		try {
			lblIpCliente = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress().toString());
			lblIpCliente.setBounds(465, 38, 131, 15);
			contentPane.add(lblIpCliente);

			JTextPane textPane = new JTextPane();
			textPane.setBounds(450, 92, -413, 229);
			contentPane.add(textPane);

			panelScroll.setBounds(10, 92, 445, 254);
			contentPane.add(panelScroll);
			
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"SubastaCliente / Se produjo un error en la lectura de IP "
							+ e.getMessage());
		}

	}
	
	public void enviarMensaje(){
		if (General.esNumero(tfMensaje.getText())) {
			general.cliente.enviarMensajeHilo(tfMensaje.getText());
			tfMensaje.setText("");
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
					"Por favor digite un n�mero valido", "Datos",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// OK
	public void agregarNuevo(Cliente nuevoCliente) {
		general.listadoConectados.addElement(nuevoCliente.getNombre());
	}

	// OK
	public void mensajeRecibido(String nuevoMensaje) {
		this.panelSubasta.setText(this.panelSubasta.getText() +  nuevoMensaje + "\n");
	}

	// OK
	public void borrarCliente(int posicion) {
		general.listadoConectados.remove(posicion);
	}
}
