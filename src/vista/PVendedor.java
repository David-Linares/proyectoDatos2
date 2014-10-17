package vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import controlador.CServidor;
import controlador.General;
import modelo.Producto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JTextField;
import java.awt.Font;


@SuppressWarnings("serial")
public class PVendedor extends JFrame {

	private JPanel contentPane;
	
	General general = General.getInstance();
	private JTextField textFieldPuerto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PVendedor frame = new PVendedor();
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
	public PVendedor() {
		setTitle("Producto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeleccioneElProducto = new JLabel("Seleccione el producto a Subastar");
		lblSeleccioneElProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccioneElProducto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblSeleccioneElProducto.setBounds(12, 41, 198, 20);
		contentPane.add(lblSeleccioneElProducto);
		
		final JComboBox listaProductos = new JComboBox(General.productos);
		listaProductos.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblSeleccioneElProducto.setLabelFor(listaProductos);
		listaProductos.setBounds(228, 40, 222, 20);
		contentPane.add(listaProductos);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		lblPuerto.setBounds(133, 86, 77, 14);
		contentPane.add(lblPuerto);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		textFieldPuerto.setText("8090");
		textFieldPuerto.setBounds(228, 83, 222, 20);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.setBounds(98, 128, 264, 42);
		contentPane.add(btnIniciarSubasta);	
		
		btnIniciarSubasta.addActionListener(new ActionListener() {
			//OK
			public void actionPerformed(ActionEvent arg0) {
				if (general.servidor == null){
					int puerto =Integer.parseInt(textFieldPuerto.getText());
					general.servidor = new CServidor(puerto);
					general.servidor.start();
				}
								
				General.productoSeleccionado = (Producto) listaProductos.getSelectedItem();
				setVisible(false);
				PrincipalSubastaVendedor principalSubasta = new PrincipalSubastaVendedor();
				principalSubasta.setVisible(true);
			}
		});
		
	}
}
