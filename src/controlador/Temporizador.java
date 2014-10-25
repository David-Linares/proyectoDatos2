package controlador;

public class Temporizador {

	public static void main(String[] args) {

		
		reloj(3, 0);
		
	}

	public static void retraso() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
	
	public static String reloj(int minutos, int segundos){
		
		String restante = null;
		for(minutos=0;minutos>= 0; minutos--){
			for(segundos=59;segundos>=0;segundos--){
				if(segundos<10){
					restante = "0"+minutos+":0"+segundos;
					retraso();
				}else{
					restante = "0"+minutos+":"+segundos;
					retraso();
				}
				
			}
		}
		
		return restante;
	}
}
