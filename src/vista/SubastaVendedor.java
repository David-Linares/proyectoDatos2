package vista;

import java.awt.EventQueue;
import java.awt.Toolkit;

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

import java.awt.Color;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SubastaVendedor extends JFrame {

	/* ATRIBUTOS */
	General general = General.getInstance();

	private Principal regreso;

	private JPanel contentPane;
	private JLabel labelIp;
	private JLabel lblProductoSubastado;
	private JLabel lblReloj;
	private JTextPane tpMensajesSubastaVendedor = new JTextPane();
	private JScrollPane scrollPanel = new JScrollPane(tpMensajesSubastaVendedor);
	private JTextArea tAProductoDescripcion = new JTextArea();
	private JScrollPane scrollProductoDescripcion = new JScrollPane(
			tAProductoDescripcion);
	@SuppressWarnings("rawtypes")
	private JList listConectados = new JList();
	private JScrollPane scrollLista = new JScrollPane(listConectados);

	/* SE CREA EL MARCO */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubastaVendedor frame = new SubastaVendedor();
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

	public JTextPane getTpMensajesSubastaVendedor() {
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
	public SubastaVendedor() {
		setFont(new Font("Calibri", Font.BOLD, 12));
		setResizable(false);
		tpMensajesSubastaVendedor.setForeground(new Color(51, 102, 255));
		tpMensajesSubastaVendedor.setFont(new Font("SansSerif", Font.BOLD, 12));
		tpMensajesSubastaVendedor.setEditable(false);
		setTitle("Subasta Vendedor");
		setDefaultCloseOperation(0);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SubastaVendedor.class.getResource("/images/martillo.png")));
		setBounds(100, 100, 563, 452);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100, 149, 237));
		contentPane.setForeground(new Color(100, 149, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollLista.setAutoscrolls(true);
		scrollLista.setFont(new Font("Dialog", Font.BOLD, 11));
		scrollLista.setBounds(418, 116, 131, 254);
		listConectados.setForeground(new Color(51, 102, 255));
		listConectados.setFont(new Font("SansSerif", Font.BOLD, 12));
		scrollLista.setViewportView(listConectados);
		contentPane.add(scrollLista);

		listConectados.setModel(General.getListadoConectados());

		JButton btnFinalizarSubasta = new JButton("Finalizar Subasta");
		btnFinalizarSubasta.setBackground(new Color(255, 255, 255));
		btnFinalizarSubasta.setForeground(new Color(51, 102, 255));
		btnFinalizarSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnFinalizarSubasta.addActionListener(new ActionListener() {
			/*
			 * FINALIZA LA SUBASTA CUANDO EL VENDEDOR LO DESEE O SE ACABE EL
			 * TIEMPO Y LO DEVUELVE A LA VENTANA PRINCIPAL PARA UNA NUEVA
			 * SUBASTA
			 */
			public void actionPerformed(ActionEvent arg0) {
				salirSubasta();
			}
		});
		btnFinalizarSubasta.setBounds(352, 382, 197, 29);
		contentPane.add(btnFinalizarSubasta);

		lblProductoSubastado = new JLabel();
		lblProductoSubastado.setForeground(new Color(255, 255, 255));
		lblProductoSubastado.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		lblProductoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductoSubastado.setBounds(10, 11, 396, 42);
		lblProductoSubastado.setText(General.getProductoSeleccionado()
				.getNombre()
				+ " = "
				+ General.getProductoSeleccionado().getValor());
		contentPane.add(lblProductoSubastado);

		try {
			labelIp = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress());
			labelIp.setForeground(new Color(255, 255, 255));
			labelIp.setHorizontalAlignment(SwingConstants.CENTER);
			labelIp.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
			labelIp.setBounds(418, 54, 121, 56);
			contentPane.add(labelIp);
			scrollPanel.setAutoscrolls(true);
			scrollPanel.setBounds(10, 116, 396, 254);
			contentPane.add(scrollPanel);

			lblReloj = new JLabel();
			lblReloj.setForeground(new Color(255, 255, 255));
			lblReloj.setHorizontalAlignment(SwingConstants.CENTER);
			lblReloj.setFont(new Font("SansSerif", Font.BOLD, 18));
			lblReloj.setBounds(418, 11, 121, 42);
			contentPane.add(lblReloj);

			scrollProductoDescripcion.setFont(new Font("Berlin Sans FB Demi",
					Font.PLAIN, 14));
			scrollProductoDescripcion.setBackground(new Color(100, 149, 237));
			tAProductoDescripcion.setBorder(null);
			tAProductoDescripcion.setBackground(new Color(100, 149, 237));
			tAProductoDescripcion.setFont(new Font("Berlin Sans FB Demi",
					Font.PLAIN, 14));
			tAProductoDescripcion.setWrapStyleWord(true);
			tAProductoDescripcion.setLineWrap(true);
			tAProductoDescripcion.setForeground(new Color(255, 255, 255));
			tAProductoDescripcion.setEditable(false);
			scrollProductoDescripcion.setBounds(10, 57, 396, 56);
			contentPane.add(scrollProductoDescripcion);
			tAProductoDescripcion.setText("Descripción de Producto: "
					+ General.getProductoSeleccionado().getDescripcion());

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"SubastaVendedor / Se produjo un error " + e.getMessage());
		}
	}

	/* IMPRIME AL CLIENTE EL MENSAJE QUE RECIBE */
	public void mensajeRecibido(String nuevoMensaje) {
		tpMensajesSubastaVendedor.setText(tpMensajesSubastaVendedor.getText()
				+ nuevoMensaje + "\n");
	}

	/*
	 * RECIBE UN NOMBRE DE UN CLIENTE, TRAE LA POSICION DEL MISMO Y LO ELIMINA
	 * DEL LISTADO DE CONECTADOS
	 */
	public void borrarCliente(String nombre) {
		int pos = General.getListadoConectados().indexOf(nombre);
		General.getListadoConectados().remove(pos);
	}

	public void salirSubasta() {
		Object[] opciones = { "Si, Deseo Salir.", "No, Deseo Quedarme." };
		int respuesta = JOptionPane
				.showOptionDialog(
						new JFrame(),
						"Hay una subasta en proceso. Realmente desea salir?",
						"Salir?", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						General.getIcon("sure"), opciones, opciones[1]);
		if (respuesta == JOptionPane.NO_OPTION) {
			return;
		}
		/* SE ELIMINA EL CLIENTE DE LA CONEXION Y DEL LISTADO DE CONECTADOS */
		try {
			General.getServidor().interrupt();
			regreso = new Principal();
			setVisible(false);
			regreso.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		General.setServidor(null);
		setVisible(false);
		General.getListadoConectados().removeAllElements();
		tpMensajesSubastaVendedor.setText("");
		System.exit(0);
	}
}
