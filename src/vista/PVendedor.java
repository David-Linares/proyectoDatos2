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


@SuppressWarnings("serial")
public class PVendedor extends JFrame {

	private JPanel contentPane;
	
	private CServidor servidor = null;
	
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeleccioneElProducto = new JLabel("Seleccione el producto a Subastar");
		lblSeleccioneElProducto.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccioneElProducto.setBounds(10, 41, 233, 42);
		contentPane.add(lblSeleccioneElProducto);
		
		final JComboBox listaProductos = new JComboBox(General.productos);
		listaProductos.setBounds(226, 52, 222, 20);
		contentPane.add(listaProductos);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setBounds(89, 125, 46, 14);
		contentPane.add(lblPuerto);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setText("8090");
		textFieldPuerto.setBounds(226, 122, 86, 20);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.setBounds(178, 193, 167, 42);
		contentPane.add(btnIniciarSubasta);	
		
		btnIniciarSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (servidor==null){
					int puerto =Integer.parseInt(textFieldPuerto.getText());
					servidor = new CServidor(puerto);
					servidor.start();
				}
				
				/*try {
					General.ipServidor = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				General.productoSeleccionado = (Producto) listaProductos.getSelectedItem();
				setVisible(false);
				PrincipalSubastaVendedor principalSubasta = new PrincipalSubastaVendedor();
				principalSubasta.setVisible(true);
			}
		});
		
	}
}
