package controlador;
import java.io.Serializable;

public class Temporizador extends Thread implements Serializable {
	
	/*ATRIBUTOS*/
	private static final long serialVersionUID = 1L;
	private String tiempo;
	private String mensaje;
	private int minutos;
	private int segundos;
	private int min;
	private int seg;
	
	public Temporizador(){		
	}
	/*CONSTRUCTOR DE LA CLASE
	 RECIBE POR PARAMETROS 2 ENTEROS QUE VAN A SER LOS MINUTOS Y SEGUNDOS*/
	public Temporizador(int minutos, int segundos){
		this.minutos = minutos;
		this.segundos = segundos;
	}
	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSeg() {
		return seg;
	}

	public void setSeg(int seg) {
		this.seg = seg;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	
	/*RELIZA EL CONTEO DEL TIEMPO, NOTIFICA AL SERVIDOR Y TODOS LOS CLIENTES CUANDO HA TRANSCURRIDO
	1, 2 Y 3 MINUTOS Y QUIEN ES EL GANADOR*/
	public void run(){
		int n = 0;
		for (min = minutos; min >= 0; min--) {
			if (n==1) segundos = 59; 
			for (seg = segundos; seg >= 0; seg--) {
				if (seg < 10) {
					tiempo = "0" + min + ":0" + seg;
					retraso();
				} else {
					tiempo = "0" + min + ":" + seg;
					retraso();
				}
				General.getVentanaServidor().getLblReloj().setText(tiempo);
				if (min == 2 && seg == 0) {
					mensaje = "MENSAJE DE SERVIDOR: A LA 1!! TE QUEDAN 2 MINUTOS PARA UN NUEVO OFRECIMIENTO.";
					General.enviarDatos(3,mensaje);
				}else if(min == 1 && seg == 0){
					mensaje = "MENSAJE DE SERVIDOR: A LAS 2!! TE QUEDA 1 MINUTO PARA UN NUEVO OFRECIMIENTO.";
					General.enviarDatos(3,mensaje);
				}else if(min == 0 && seg == 0){
					mensaje = "MENSAJE DE SERVIDOR: A LAS 3!! La subasta ha finalizado!";
					General.enviarDatos(3,mensaje);
					General.enviarDatos(6,General.getGanador().getNombre());
				}
			}
			n++;
		}
	}

	/*DUERME EL HILO PARA LLEVAR UN CONTROL DE TIEMPO*/
	public static void retraso() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
	}
	
	
}
