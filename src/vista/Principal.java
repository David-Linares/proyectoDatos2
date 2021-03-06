package vista;
import java.awt.EventQueue;



import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;


@SuppressWarnings("serial")
public class Principal extends JFrame {
	
	/*ATRIBUTOS*/
	private JButton btnNuevaSubasta;
	
	
	public JButton getBtnNuevaSubasta() {
		return btnNuevaSubasta;
	}

	public void setBtnNuevaSubasta(JButton btnNuevaSubasta) {
		this.btnNuevaSubasta = btnNuevaSubasta;
	}	
	
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
	/*SE CREA EL MARCO*/
	public Principal() {
		setForeground(new Color(30, 144, 255));
		setFont(new Font("Calibri", Font.BOLD, 12));

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContentPane().setBackground(new Color(100, 149, 237));
		getContentPane().setForeground(new Color(0, 0, 0));
		setResizable(false);
		
		setTitle("Bienvenido");
		setBounds(100, 100, 376, 158);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/martillo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		btnNuevaSubasta = new JButton("Crear nueva Subasta");
		btnNuevaSubasta.setForeground(new Color(51, 102, 255));
		btnNuevaSubasta.setBackground(new Color(255, 255, 255));
		btnNuevaSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnNuevaSubasta.addActionListener(new ActionListener() {
			/*EVENTO QUE CREA LA VENTANA DE DATOS VENDEDOR */
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				DatosVendedor datosVendedor = new DatosVendedor();
				datosVendedor.setVisible(true);
			}
		});
		btnNuevaSubasta.setBounds(12, 12, 338, 42);
		getContentPane().add(btnNuevaSubasta);
		
		JButton btnEntrarSubasta = new JButton("Entrar a la Subasta");
		btnEntrarSubasta.setForeground(new Color(51, 102, 255));
		btnEntrarSubasta.setBackground(new Color(255, 255, 255));
		btnEntrarSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnEntrarSubasta.addActionListener(new ActionListener() {
			/*EVENTO QUE CREA LA VENTANA DE DATOS CLIENTE CONEXION*/
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				DatosClienteConexion datosClienteConexion = new DatosClienteConexion();
				datosClienteConexion.setVisible(true);
			}
		});
		btnEntrarSubasta.setBounds(12, 66, 338, 42);
		getContentPane().add(btnEntrarSubasta);
	}

	

	
}
