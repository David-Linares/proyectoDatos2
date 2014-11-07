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
	@SuppressWarnings("rawtypes")
	public JList listConectados = new JList();
	private JTextPane panelSubasta = new JTextPane();
	public JLabel lblProductoSubastado = new JLabel();
	private JScrollPane panelScroll = new JScrollPane(panelSubasta);
	private JLabel lblReloj;
	private JScrollPane scrollLista = new JScrollPane();

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
		panelSubasta.setEditable(false);
		setResizable(false);
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 506);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollLista.setBounds(465, 119, 131, 254);
		scrollLista.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		scrollLista.setViewportView(listConectados);
		contentPane.add(scrollLista);

		JButton btnAbandonarSubasta = new JButton("Abandonar Subasta");
		btnAbandonarSubasta.setBounds(385, 428, 211, 37);
		btnAbandonarSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salirSubasta();
			}
		});
		btnAbandonarSubasta.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		contentPane.add(btnAbandonarSubasta);
		lblProductoSubastado.setBounds(10, 78, 443, 31);

		lblProductoSubastado.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		lblProductoSubastado.setHorizontalAlignment(SwingConstants.TRAILING);
		contentPane.add(lblProductoSubastado);

		tfMensaje = new JTextField();
		tfMensaje.setBounds(10, 385, 443, 31);
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
		contentPane.add(tfMensaje);
		tfMensaje.setColumns(10);

		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setBounds(465, 384, 131, 31);
		btnNewButton_1.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {

			// ACCION DEL EVENTO AL ENVIAR UN MENSAJE
			public void actionPerformed(ActionEvent arg0) {
				enviarMensaje();
			}
		});
		contentPane.add(btnNewButton_1);

		JLabel lblNombreCliente = new JLabel("Nombre: "
				+ General.getCliente().getClienteConectado().getNombre());
		lblNombreCliente.setBounds(10, 12, 221, 15);
		lblNombreCliente.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		contentPane.add(lblNombreCliente);

		JLabel lblMontoCliente = new JLabel("Monto Disponible: "
				+ General.getCliente().getClienteConectado().getMonto());
		lblMontoCliente.setBounds(10, 39, 221, 15);
		lblMontoCliente.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		contentPane.add(lblMontoCliente);
		JLabel lblIpCliente;
		
		JLabel lblId = new JLabel("ID: " + General.getCliente().getId());
		lblId.setFont(new Font("Dialog", Font.BOLD, 11));
		lblId.setBounds(10, 65, 221, 15);
		contentPane.add(lblId);

		try {
			lblIpCliente = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress().toString());
			lblIpCliente.setHorizontalAlignment(SwingConstants.CENTER);
			lblIpCliente.setBounds(465, 29, 131, 15);
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
			
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"SubastaCliente / Se produjo un error en la lectura de IP "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public void enviarMensaje() {
		if (general.validarMonto(tfMensaje.getText())) {
			if(General.getReloj() != null){
				General.getReloj().stop();
			}
			General.setReloj(new Temporizador(3,0));
			General.getReloj().start();
			General.getCliente().enviarMensajeHilo(tfMensaje.getText());
			General.getProductoSeleccionado().setValor(
					Long.parseLong(tfMensaje.getText()));
			General.getCliente().enviarProductoHilo(General
					.getProductoSeleccionado());
			tfMensaje.setText("");
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
					"Por favor digite un n\u00famero valido", "Datos",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("error"));
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
			General.getCliente().enviarDatosCliente(3, General.getCliente().getClienteConectado().getNombre());
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
		this.lblProductoSubastado.setText("Producto en Subasta: \n Nombre: "
				+ productoSubastado.getNombre() + " \n Valor Actual: "
				+ productoSubastado.getValor());
		General.setProductoSeleccionado(productoSubastado);
	}
	
	public void mostrarTiempo(Temporizador tiempo){
		if (tiempo.getSeg() < 10) {
			this.lblReloj.setText("0"+tiempo.getMin() + ":0" + tiempo.getSeg());
			if (tiempo.getMin() == 0 && tiempo.getSeg() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "La subasta ha finalizado", "Fin de la Subasta", JOptionPane.INFORMATION_MESSAGE, general.getIcon("winner"));
			}
		}else{
			this.lblReloj.setText("0"+tiempo.getMin() + ":" + tiempo.getSeg());
		}
		General.setReloj(tiempo);
	}

	// OK
	public void mensajeRecibido(String nuevoMensaje) {
		this.panelSubasta.setText(this.panelSubasta.getText() + nuevoMensaje
				+ "\n");
	}

	// OK
	public void borrarCliente(int posicion) {
		General.getListadoConectados().remove(posicion);
	}
}
