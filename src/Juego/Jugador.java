package Juego;

import Excepciones.MovimientoNoValido;

public class Jugador {

	private int numFichas;
	private COLOR color;
	
	public Jugador(COLOR color) {
		this.numFichas = 16;
		this.color = color;
	}

	public void mover(Tablero tablero, int origen, int destino) throws MovimientoNoValido {

		// if(!origen.isOcupada()) throw new MovimientoNoValido("Movimiento fuera de
		// tablero");

	}
	
	
	public void matar() {
		if(numFichas > 0) numFichas--;
	}

	public int getNumFichas() {
		return numFichas;
	}

	public void setNumFichas(int numFichas) {
		this.numFichas = numFichas;
	}

	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}
	
	

}
