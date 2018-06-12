package Juego;

import java.util.Properties;

public class Ficha {
	
	public static Properties prop = null;
	
	private TIPO tipo; 
	
	
	public Ficha(TIPO tipo) {	
		this.tipo = tipo;
	}
	
	
	public TIPO getTipo() {
		return tipo;
	}


	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}

	
}
