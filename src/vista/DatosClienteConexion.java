package vista;

import java.awt.EventQueue;
import java.awt.Toolkit;

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

	/*ATRIBUTOS*/
	SubastaCliente psubasta;
	General general = General.getInstance();
	
	private JPanel contentPane;
	private JTextField tfIpDatosConexion;
	private JTextField tfPuertoDatosConexion;

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

	/*CREAR EL MARCO*/
	public DatosClienteConexion() {
		setResizable(false);
		setFont(new Font("Calibri", Font.BOLD, 12));
		setTitle("Datos de Conexi\u00F3n Cliente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatosClienteConexion.class.getResource("/images/customer.png")));
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

		JLabel lblIpDatosConexion = new JLabel("Indique la Ip del Servidor");
		lblIpDatosConexion.setForeground(new Color(255, 255, 255));
		lblIpDatosConexion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIpDatosConexion.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblIpDatosConexion.setBounds(28, 57, 154, 23);
		contentPane.add(lblIpDatosConexion);

		JLabel lblPuertoDatosConexion = new JLabel("Indique el Puerto");
		lblPuertoDatosConexion.setForeground(new Color(255, 255, 255));
		lblPuertoDatosConexion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuertoDatosConexion.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblPuertoDatosConexion.setBounds(38, 92, 144, 23);
		contentPane.add(lblPuertoDatosConexion);
		
		tfIpDatosConexion = new JTextField("127.0.0.1");
		tfIpDatosConexion.setForeground(new Color(51, 102, 255));
		tfIpDatosConexion.addKeyListener(new KeyAdapter() {
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
		tfIpDatosConexion.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfIpDatosConexion.setColumns(10);
		tfIpDatosConexion.setBounds(200, 57, 209, 23);
		contentPane.add(tfIpDatosConexion);
	
		tfPuertoDatosConexion = new JTextField("8090");
		tfPuertoDatosConexion.setForeground(new Color(51, 102, 255));
		tfPuertoDatosConexion.addKeyListener(new KeyAdapter() {
			/* SE VALIDA QUE EL PUERTO NO EXCEDA LOS 5 CARACTERES Y 
			 ESTE SEAN SOLO NÚMEROS*/
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char car = evt.getKeyChar();
				if (tfPuertoDatosConexion.getText().length() >= 5)
					evt.consume();
				if ((car < '0' || car > '9')) {
					evt.consume();
				}
			}
		});
		tfPuertoDatosConexion.setFont(new Font("SansSerif", Font.BOLD, 12));
		tfPuertoDatosConexion.setColumns(10);
		tfPuertoDatosConexion.setBounds(200, 92, 209, 23);
		contentPane.add(tfPuertoDatosConexion);
		
		JButton btnConectarmeDatosConexion = new JButton("Conectarme");
		btnConectarmeDatosConexion.setBackground(new Color(255, 255, 255));
		btnConectarmeDatosConexion.setForeground(new Color(51,102,255));
		btnConectarmeDatosConexion.setFont(new Font("Kristen ITC", Font.BOLD, 15));
		btnConectarmeDatosConexion.addActionListener(new ActionListener() {
			/*BOTON DE CONECTARME CREA EL CLIENTE E INICIA LA SUBASTA PARA ESE CLIENTE*/
			public void actionPerformed(ActionEvent arg0) {
				iniciarConexion();
			}
			
		});
		btnConectarmeDatosConexion.setBounds(102, 136, 224, 34);
		contentPane.add(btnConectarmeDatosConexion);
	}
	/*CREA EL CLIENTE CON LA CONEXION QUE SE CREO EN DATOS CLIENTE CONEXION*/
	private void iniciarConexion() {
		if (validacion()) {
			try {
				//JOptionPane.showMessageDialog(new JFrame(), "PConexion / Entrï¿½ a Iniciar Conexiï¿½n");
				int puerto = Integer.parseInt(tfPuertoDatosConexion.getText());
				String ip = tfIpDatosConexion.getText();
				if (General.getConexCliente() == null) {
					General.setConexCliente(new ConexionCliente(puerto, ip));
					DatosCliente pcliente = new DatosCliente();
					General.setVentanaDatosCliente(pcliente);
					System.out.println("DCC / "+General.getConexCliente());
					General.getConexCliente().start();
					setVisible(false);
					pcliente.setVisible(true);
				}
				//JOptionPane.showMessageDialog(new JFrame(), "PConexion / Pasï¿½ el Run de Cliente y va a crear la nueva ventana");
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(new JFrame(),
						//"PConexion / Se produjo un error " + e.getMessage());
				//General.setCliente(null);
						e.printStackTrace();
			}
		}
	}

	/*VALIDA SI LOS DATOS INGRESADOS SE HAN DIGITADO */
	private boolean validacion() {
		String ipV = this.tfIpDatosConexion.getText();
		String puertoV = this.tfPuertoDatosConexion.getText();

		String mensajeV = "";

		if (ipV.equals("")) {
			mensajeV = "\u00A1Debe escribir la IP!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfIpDatosConexion.requestFocus();
			return false;
		} else if (puertoV.equals("")) {
			mensajeV = "\u00A1Debe escribir el n\u00famero de puerto!\n";
			JOptionPane.showMessageDialog(null, mensajeV, "\u00A1Advertencia!",
					JOptionPane.INFORMATION_MESSAGE, general.getIcon("alarm"));
			tfPuertoDatosConexion.requestFocus();
			return false;
		} else {
			return true;
		}

	}
}
