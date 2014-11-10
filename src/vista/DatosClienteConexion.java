package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import controlador.ConexionCliente;
import controlador.General;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

@SuppressWarnings("serial")
public class DatosClienteConexion extends JFrame {

	
	ClienteSubasta psubasta;
	private JPanel contentPane;
	private JTextField tfIp;
	private JTextField tfPuerto;
	General general = General.getInstance();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatosClienteConexion frame = new DatosClienteConexion();
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
	public DatosClienteConexion() {
		setResizable(false);
		setFont(new Font("Calibri", Font.BOLD, 12));
		setTitle("Datos de Conexi\u00F3n Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 447, 220);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100,149,237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Datos de Conexi\u00F3n");
		label.setForeground(new Color(255, 255, 255));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Kristen ITC", Font.BOLD, 18));
		label.setBounds(90, 11, 247, 34);
		contentPane.add(label);

		JLabel lblIpPConexion = new JLabel("Indique la Ip del Servidor");
		lblIpPConexion.setForeground(new Color(255, 255, 255));
		lblIpPConexion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIpPConexion.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblIpPConexion.setBounds(28, 57, 154, 23);
		contentPane.add(lblIpPConexion);

		tfIp = new JTextField("127.0.0.1");
		tfIp.setForeground(new Color(51, 102, 255));
		tfIp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					iniciarConexion();
				}
			}
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if ((car < '0' || car > '9')) {
					if((car != '.')){
					evt.consume();}
				}
				
			}
		});
		tfIp.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfIp.setColumns(10);
		tfIp.setBounds(200, 57, 209, 23);
		contentPane.add(tfIp);

		JLabel lblPuertoConexion = new JLabel("Indique el Puerto");
		lblPuertoConexion.setForeground(new Color(255, 255, 255));
		lblPuertoConexion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuertoConexion.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblPuertoConexion.setBounds(38, 92, 144, 23);
		contentPane.add(lblPuertoConexion);

		tfPuerto = new JTextField("8090");
		tfPuerto.setForeground(new Color(51, 102, 255));
		tfPuerto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if (tfPuerto.getText().length() >= 5)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
		});
		tfPuerto.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfPuerto.setColumns(10);
		tfPuerto.setBounds(200, 92, 209, 23);
		contentPane.add(tfPuerto);
		

		JButton btnConectarme = new JButton("Conectarme");
		btnConectarme.setBackground(new Color(255, 255, 255));
		btnConectarme.setForeground(new Color(51,102,255));
		btnConectarme.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnConectarme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarConexion();
			}
			
		});
		btnConectarme.setBounds(102, 136, 224, 34);
		contentPane.add(btnConectarme);
	}

	private void iniciarConexion() {
		if (validacion()) {
			try {
				//JOptionPane.showMessageDialog(new JFrame(), "PConexion / Entr� a Iniciar Conexi�n");
				int puerto = Integer.parseInt(tfPuerto.getText());
				String ip = tfIp.getText();
				System.out.println("PConexion / " + General.getCliente());
				if (General.getCliente() == null) {
					General.setCliente(new ConexionCliente(puerto, ip));
					DatosCliente pcliente = new DatosCliente();
					General.setVentanaDatosCliente(pcliente);
					General.getCliente().start();
					setVisible(false);
					pcliente.setVisible(true);
				}
				//JOptionPane.showMessageDialog(new JFrame(), "PConexion / Pas� el Run de Cliente y va a crear la nueva ventana");
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(new JFrame(),
						//"PConexion / Se produjo un error " + e.getMessage());
				//General.setCliente(null);
						e.printStackTrace();
			}
		}
	}

	private boolean validacion() {
		String ipV = this.tfIp.getText();
		String puertoV = this.tfPuerto.getText();

		String mensajeV = "";

		if (ipV.equals("")) {
			mensajeV = "\u00A1Debe escribir la IP!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfIp.requestFocus();
			return false;
		} else if (puertoV.equals("")) {
			mensajeV = "\u00A1Debe escribir el n\u00famero de puerto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfPuerto.requestFocus();
			return false;
		} else {
			return true;
		}

	}
}
