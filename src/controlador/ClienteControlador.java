package controlador;

import java.io.DataOutputStream;

import javax.swing.JOptionPane;

public class ClienteControlador {
	
	General general = General.getInstance();
	
	public void enviarMensaje(String mensaje){
		enviarTrama(2, mensaje);
    }
    
    public void enviarTrama(int nCodigo, String sTrama){
        try{
            DataOutputStream dos=new DataOutputStream(general.cliente.getOutputStream());
            dos.writeInt(nCodigo);
            dos.writeUTF(sTrama);
        }catch(Exception e){
            //JOptionPane.showMessageDialog(this, "No se pudo enviar el mensaje", nCodigo);
        }
        
    }	
	
    
}
