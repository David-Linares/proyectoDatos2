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
import modelo.Producto;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextPane;

import controlador.General;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ClienteSubasta extends JFrame {

	private JPanel contentPane;
	General general = General.getInstance();
	private JTextField tfMensaje;
	@SuppressWarnings("rawtypes")
	private JList listConectados = new JList();
	private JTextPane panelSubasta = new JTextPane();
	public JLabel lblProductoSubastado = new JLabel();
	private JScrollPane panelScroll = new JScrollPane(panelSubasta);
	private JLabel lblReloj;
	private JScrollPane scrollLista = new JScrollPane();
	private JTextArea tAProductoDescripcion = new JTextArea();
	private JScrollPane scrollProductoDescripcion= new JScrollPane(tAProductoDescripcion);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteSubasta frame = new ClienteSubasta();
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
	public ClienteSubasta() {
		setFont(new Font("Calibri", Font.BOLD, 12));
		panelSubasta.setForeground(new Color(0, 153, 255));
		panelSubasta.setFont(new Font("SansSerif", Font.BOLD, 12));
		panelSubasta.setEditable(false);
		setResizable(false);
	
		setTitle("Subasta Cliente: " + General.getCliente().getClienteConectado().getNombre().toLowerCase().toLowerCase().trim());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 506);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(100,149,237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollLista.setBounds(465, 119, 131, 254);
		scrollLista.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		listConectados.setFont(new Font("SansSerif", Font.BOLD, 12));
		listConectados.setForeground(new Color(0, 153, 255));
		scrollLista.setViewportView(listConectados);
		contentPane.add(scrollLista);

		JButton btnAbandonarSubasta = new JButton("Abandonar Subasta");
		btnAbandonarSubasta.setBackground(new Color(255, 255, 255));
		btnAbandonarSubasta.setForeground(new Color(0, 153, 255));
		btnAbandonarSubasta.setBounds(385, 428, 211, 37);
		btnAbandonarSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salirSubasta();
			}
		});
		btnAbandonarSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		contentPane.add(btnAbandonarSubasta);
		lblProductoSubastado.setForeground(new Color(255, 255, 255));
		lblProductoSubastado.setBounds(10, 38, 586, 31);

		lblProductoSubastado.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
		lblProductoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblProductoSubastado);

		lblProductoSubastado.setText("Producto en Subasta: \n Nombre: "
				+ 	General.getProductoSeleccionado().getNombre() + " \n Valor Actual: "
				+ General.getProductoSeleccionado().getValor() );
		
		tfMensaje = new JTextField();
		tfMensaje.setForeground(new Color(0, 153, 255));
		tfMensaje.setBounds(10, 385, 443, 31);
		tfMensaje.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfMensaje.addKeyListener(new KeyAdapter() {
			@Override
			// OK
			public void keyPressed(KeyEvent eve) {

				if (eve.getKeyCode() == 10) {
					enviarMensaje();
				}
			}
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if (tfMensaje.getText().length() >= 18)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
		});
		contentPane.add(tfMensaje);
		tfMensaje.setColumns(10);

		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setForeground(new Color(0, 153, 255));
		btnNewButton_1.setBounds(465, 384, 131, 31);
		btnNewButton_1.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {

			// ACCION DEL EVENTO AL ENVIAR UN MENSAJE
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje();
			}
		});
		contentPane.add(btnNewButton_1);

		JLabel lblMontoCliente = new JLabel("Monto Disponible: "
				+ General.getCliente().getClienteConectado().getMonto());
		lblMontoCliente.setForeground(new Color(255, 255, 255));
		lblMontoCliente.setBounds(10, 13, 443, 21);
		lblMontoCliente.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
		contentPane.add(lblMontoCliente);
		JLabel lblIpCliente;

		try {
			lblIpCliente = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress().toString());
			lblIpCliente.setHorizontalAlignment(SwingConstants.TRAILING);
			lblIpCliente.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 14));
			lblIpCliente.setForeground(new Color(255, 255, 255));
			lblIpCliente.setBounds(465, 8, 131, 31);
			contentPane.add(lblIpCliente);

			JTextPane textPane = new JTextPane();
			textPane.setBounds(450, 92, -413, 229);
			contentPane.add(textPane);
			panelScroll.setBounds(10, 120, 445, 254);
			contentPane.add(panelScroll);
			
			lblReloj = new JLabel("");
			lblReloj.setFont(new Font("DejaVu Sans", Font.BOLD, 22));
			lblReloj.setHorizontalAlignment(SwingConstants.CENTER);
			lblReloj.setBounds(241, 12, 212, 43);
			contentPane.add(lblReloj);
			tAProductoDescripcion.setBackground(new Color(100,149,237));
			
			
			tAProductoDescripcion.setWrapStyleWord(true);
			tAProductoDescripcion.setText((String) null);
			tAProductoDescripcion.setLineWrap(true);
			tAProductoDescripcion.setForeground(new Color(255, 255, 255));
			tAProductoDescripcion.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
			tAProductoDescripcion.setEditable(false);
			tAProductoDescripcion.setBorder(null);
			scrollProductoDescripcion.setBackground(new Color(100, 149, 237));
			scrollProductoDescripcion.setBounds(10, 66, 586, 46);
			contentPane.add(scrollProductoDescripcion);
			tAProductoDescripcion.setText("Descripci\u00f3n de Producto: " + General.getProductoSeleccionado().getDescripcion());
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"SubastaCliente / Se produjo un error en la lectura de IP "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public void enviarMensaje() {
		if (general.validarMonto(tfMensaje.getText())) {
			General.getCliente().enviarMensajeHilo(tfMensaje.getText());
			General.getProductoSeleccionado().setValor(
					Long.parseLong(tfMensaje.getText()));
			General.getCliente().enviarProductoHilo(General
					.getProductoSeleccionado());
			tfMensaje.setText("");
		}else {			
			tfMensaje.setText("");
			tfMensaje.requestFocus();
		} 
	}

	public void salirSubasta() {
		Object[] opciones = { "Si, Deseo Salir.", "No, Deseo Quedarme." };
		int respuesta = JOptionPane
				.showOptionDialog(
						new JFrame(),
						"Realmente deseas salir de la subasta? \n Una vez sales tienes que esperar\n a una pr\u00f3xima subasta para poder ingresar.",
						"Salir?", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						general.getIcon("sure"), opciones, opciones[1]);
		if (respuesta == JOptionPane.NO_OPTION) {
			return;
		}

		if (General.getCliente() != null) {
			General.getCliente().enviarDatosCliente(4, General.getCliente().getClienteConectado().getNombre());
			General.getCliente().interrupt();
		}
		General.setCliente(null);
		setVisible(false);

		General.getListadoConectados().removeAllElements();
		panelSubasta.setText("");
		Principal regreso = new Principal();
		regreso.setVisible(true);
		}
	

	// OK
	@SuppressWarnings("unchecked")
	public void agregarNuevo(Cliente nuevoCliente) {
		General.getListadoConectados().addElement(nuevoCliente.getNombre());
	}

	public void agregarProductoEnSubasta(Producto productoSubastado) {
		this.tAProductoDescripcion.setText("Producto en Subasta: \n Nombre: "
				+ productoSubastado.getNombre() + " \n Valor Actual: "
				+ productoSubastado.getValor());
		General.setProductoSeleccionado(productoSubastado);
	}
	
	public void mensajeRecibido(String nuevoMensaje) {
		this.panelSubasta.setText(this.panelSubasta.getText() + nuevoMensaje
				+ "\n");
	}

	public void borrarCliente(int posicion) {
		General.getListadoConectados().remove(posicion);
	}
	
	public void finSubasta(String ganador){
		JOptionPane.showMessageDialog(this, "La subasta ha finalizado \n El ganador es "+ganador, "Ganador", JOptionPane.INFORMATION_MESSAGE, general.getIcon("winner"));
	}
	
	public JList getListConectados() {
		return listConectados;
	}

	public void setListConectados(JList listConectados) {
		this.listConectados = listConectados;
	}	
}
