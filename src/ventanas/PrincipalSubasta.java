package ventanas;
import general.General;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;


public class PrincipalSubasta extends JFrame {

	private JPanel contentPane;
	General general = General.getInstance();
	DefaultListModel listadoConectados = new DefaultListModel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalSubasta frame = new PrincipalSubasta();
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
	public PrincipalSubasta() {
		setTitle("Subasta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listSubasta = new JList();
		listSubasta.setBounds(10, 93, 345, 254);
		contentPane.add(listSubasta);
		
		JList listConectados = new JList();
		listConectados.setBounds(365, 93, 131, 254);
		contentPane.add(listConectados);
		
		for(int i = 0; i <= general.clientesConectados.size() - 1; i++ ){
			listadoConectados.addElement(general.clientesConectados.get(i).getNombre());
		}
		
		listConectados.setModel(listadoConectados);
		
		JButton btnNewButton = new JButton("Abandonar Subasta");
		btnNewButton.setBounds(285, 357, 211, 37);
		contentPane.add(btnNewButton);
		
		JLabel tuNombre = new JLabel("");
		tuNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tuNombre.setBounds(310, 11, 186, 23);
		contentPane.add(tuNombre);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(310, 41, 186, 23);
		contentPane.add(lblNewLabel);
		
		JLabel productoSubastado = new JLabel("New label");
		productoSubastado.setHorizontalAlignment(SwingConstants.CENTER);
		productoSubastado.setBounds(10, 11, 290, 71);
		contentPane.add(productoSubastado);
		
		productoSubastado.setText(general.productoSeleccionado);
	}
}
