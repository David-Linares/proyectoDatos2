package vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import controlador.General;
import modelos.Producto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class PVendedor extends JFrame {

	private JPanel contentPane;
	
	General general = General.getInstance();

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeleccioneElProducto = new JLabel("Seleccione el producto a Subastar");
		lblSeleccioneElProducto.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccioneElProducto.setBounds(75, 28, 289, 42);
		contentPane.add(lblSeleccioneElProducto);
		
		final JComboBox listaProductos = new JComboBox(General.productos);
		listaProductos.setBounds(106, 90, 222, 20);
		contentPane.add(listaProductos);
		
		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				General.productoSeleccionado = (Producto) listaProductos.getSelectedItem();
				setVisible(false);
				PrincipalSubastaVendedor principalSubasta = new PrincipalSubastaVendedor();
				principalSubasta.setVisible(true);
			}
		});
		btnIniciarSubasta.setBounds(140, 171, 167, 42);
		contentPane.add(btnIniciarSubasta);	
		
	}
}
