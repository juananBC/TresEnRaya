package Juego;

public class Jugador {

	private int numFichas;
	private TIPO tipo;
	private int numPiezas;
	
	public Jugador(TIPO tipo) {
		this.numFichas = 16;
		this.tipo = tipo;
		this.numPiezas = 0;
	}

	public int getNumFichas() {
		return numFichas;
	}

	public void setNumFichas(int numFichas) {
		this.numFichas = numFichas;
	}

	public TIPO getTipo() {
		return tipo;
	}

	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}

	public int getNumPiezas() {
		return numPiezas;
	}

	public void setNumPiezas(int numPiezas) {
		this.numPiezas = numPiezas;
	}

	
}
