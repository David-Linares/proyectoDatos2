package controlador;

public class Temporizador extends Thread {
	
	private int minutos;
	private int segundos;
	private String tiempo;
		
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
		for (int min = minutos; min >= 0; min--) {
			if (n==1) segundos = 59; 
			for (int seg = segundos; seg >= 0; seg--) {
				if (seg < 10) {
					tiempo = "0" + min + ":0" + seg;
					retraso();
				} else {
					tiempo = "0" + min + ":" + seg;
					retraso();
				}
				System.out.println(tiempo);
			/*General.setTiempo(tiempo);
			General.getCliente().enviarReloj(tiempo);*/
			}
			n++;
		}
	}

}
