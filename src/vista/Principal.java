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


public class Principal extends JFrame {
	
	private JButton btnNuevaSubasta;
	
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
		//Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("confirm.png"));
		//setIconImage(icon);
		
		setTitle("Bienvenido");
		setBounds(100, 100, 376, 158);
		//setIconImage(Toolkit.getDefaultToolkit().createImage("images/martillo.png")); 
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/martillo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		btnNuevaSubasta = new JButton("Crear nueva Subasta");
		btnNuevaSubasta.setForeground(new Color(51, 102, 255));
		btnNuevaSubasta.setBackground(new Color(255, 255, 255));
		btnNuevaSubasta.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnNuevaSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				DatosVendedor pvendedor = new DatosVendedor();
				pvendedor.setVisible(true);
			}
		});
		btnNuevaSubasta.setBounds(12, 12, 338, 42);
		getContentPane().add(btnNuevaSubasta);
		
		JButton btnNewButton_1 = new JButton("Entrar a la Subasta");
		btnNewButton_1.setForeground(new Color(51, 102, 255));
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				DatosClienteConexion pconexion = new DatosClienteConexion();
				pconexion.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(12, 66, 338, 42);
		getContentPane().add(btnNewButton_1);
	}

	

	public JButton getBtnNuevaSubasta() {
		return btnNuevaSubasta;
	}

	public void setBtnNuevaSubasta(JButton btnNuevaSubasta) {
		this.btnNuevaSubasta = btnNuevaSubasta;
	}	
}
