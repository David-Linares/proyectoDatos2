package ventanas;
import general.General;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.net.*;
import java.io.*;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PVendedor extends JFrame {

	private JPanel contentPane;
	
	final General general = General.getInstance();

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
		lblSeleccioneElProducto.setBounds(96, 28, 232, 29);
		contentPane.add(lblSeleccioneElProducto);
		
		final JComboBox listaProductos = new JComboBox();
		listaProductos.setBounds(106, 90, 222, 20);
		contentPane.add(listaProductos);
		
		JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
		btnIniciarSubasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				general.productoSeleccionado = (String) listaProductos.getSelectedItem();
				setVisible(false);
				PrincipalSubasta principalSubasta = new PrincipalSubasta();
				principalSubasta.setVisible(true);
			}
		});
		btnIniciarSubasta.setBounds(140, 171, 167, 42);
		contentPane.add(btnIniciarSubasta);
		
		DefaultComboBoxModel listadoProductos = new DefaultComboBoxModel(); 
		
		for(int i = 0; i <= general.productos.size() -1; i++ ){
			listadoProductos.addElement(general.productos.get(i).getNombre());
		}
		
		listaProductos.setModel(listadoProductos);
		
		
		
	}
}
