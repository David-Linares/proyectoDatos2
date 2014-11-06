package controlador;

import java.io.Serializable;

public class Temporizador extends Thread implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int minutos;
	private int segundos;
	private String tiempo;
	private int min;
	private int seg;
		
	public Temporizador(){
		
	}
	
	public Temporizador(int minutos, int segundos){
		this.minutos = minutos;
		this.segundos = segundos;
	}
	
	public static void retraso() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
	}
	
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
				General.getReloj().setMin(min);
				General.getReloj().setSeg(seg);
				General.getCliente().enviarReloj(General.getReloj());
			}
			n++;
		}
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
	

}
