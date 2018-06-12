package Juego;


import java.util.ArrayList;
import java.util.List;


public class Tablero {

	public static final int TAMANO = 3;
	public static final int TAMANO_TOTAL = TAMANO * TAMANO;
	
	private Casilla[][] casillas;
	
	public Tablero() {
		this.casillas = new Casilla[TAMANO][TAMANO];
		
			for (int x = 0; x < Tablero.TAMANO; x++) {
				for (int y = 0; y < Tablero.TAMANO; y++) {
				casillas[x][y] =  new Casilla(x, y);			
			}
		}
	}
	
	public Casilla getCasilla(int casilla) {
		if(casilla < 0 || casilla >= TAMANO_TOTAL) return null;
		
		int y = (int) Math.floor(casilla / TAMANO);
		int x = casilla % TAMANO;
		return casillas[x][y];
	}
	

	public Casilla getCasilla(int x, int y) {
		if(x < 0 || y < 0 || x >= TAMANO|| y >= TAMANO) return null;
		return this.casillas[x][y];
	}
	
	public void updateCasilla(Casilla casilla) {
		this.casillas[casilla.getX()][casilla.getY()] = casilla;
	}
		
}
