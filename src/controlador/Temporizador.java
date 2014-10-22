package controlador;

public class Temporizador {

	public static void main(String[] args) {

		int minutos = 3;
		int segundos = 0;
		for(minutos=2;minutos>= 0; minutos--){
			for(segundos=59;segundos>=0;segundos--){
				if(segundos<10){
					System.out.println("0"+minutos+":0"+segundos);
					retraso();
				}else{
					System.out.println("0"+minutos+":"+segundos);
					retraso();
				}
				
			}
		}
		
	}

	private static void retraso() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
