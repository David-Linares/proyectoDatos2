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
		String tiempo = "";
		for (int minutos = 2; minutos >= 0; minutos--) {
			for (int segundos = 59; segundos >= 0; segundos--) {
				if (segundos < 10) {
					tiempo =  "0" + minutos + ":0" + segundos;
					System.out.println(tiempo);
					retraso();
				} else {
					tiempo = "0" + minutos + ":" + segundos;
					retraso();
				}
				General.setTiempo(tiempo);
			}
			

		}
	}

}
