package vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class Principal extends JFrame {

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setTitle("Bienvenido");
		setBounds(100, 100, 364, 149);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Crear nueva Subasta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				PVendedor pvendedor = new PVendedor();
				pvendedor.setVisible(true);
			}
		});
		btnNewButton.setBounds(12, 12, 338, 42);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Entrar a la Subasta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				PCliente pcliente = new PCliente();
				pcliente.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(12, 66, 338, 42);
		getContentPane().add(btnNewButton_1);

	}
}
