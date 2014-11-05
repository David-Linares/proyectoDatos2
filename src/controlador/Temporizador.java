package controlador;

public class Temporizador extends Thread {

	public Temporizador(){
		
	}
	
	public static void retraso() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
	
	public void run(){
		int minutos = 3;
		int segundos = 1;
		
		for (minutos = 2; minutos >= 0; minutos--) {
			String tiempo;
			for (segundos = 59; segundos >= 0; segundos--) {
				if (segundos < 10) {
					tiempo = "0" + minutos + ":0" + segundos;
					retraso();
				} else {
					tiempo = "0" + minutos + ":" + segundos;
					retraso();
				}
				
			General.setTiempo(tiempo);
			General.getCliente().enviarReloj(tiempo);
			}
			

		}
	}

}
