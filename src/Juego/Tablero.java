package Juego;


import java.util.ArrayList;
import java.util.List;


public class Tablero {

	public static final int TAMANO = 8;
	public static final int TAMANO_TOTAL = TAMANO * TAMANO;
	
	private Casilla[][] casillas;
	
	
	public Tablero() {
		this.casillas = new Casilla[TAMANO][TAMANO];
		
		for (int y = 0; y < Tablero.TAMANO; y++) {
			for (int x = 0; x < Tablero.TAMANO; x++) {
				casillas[x][y] =  new Casilla(x, y);			
			}
		}
	}
	
	
	public List<Casilla> getMovimientos(Casilla c) {
		List<Casilla> lista = new ArrayList<Casilla>();
		
//		Pieza p = c.getPieza();
//		for(Movimiento mov: p.getMovimientos()) {
//			
//		}
//		
		
		return lista;
	}

	/**
	 * Obtiene la casilla a partir del identificador
	 * @param casilla
	 * @return
	 */
	public Casilla getCasilla(int casilla) {
		if(casilla < 0 || casilla >= TAMANO_TOTAL) return null;
		
		int y = (int) Math.floor(casilla / TAMANO);
		int x = casilla % TAMANO;
		return casillas[x][y];
	}
	
	/**
	 * Obtiene la casilla a partir de la posicion X e Y
	 * @param casilla
	 * @return
	 */
	public Casilla getCasilla(int x, int y) {
		if(x < 0 || y < 0 || x >= TAMANO|| y >= TAMANO) return null;
		return this.casillas[x][y];
	}
	
	public void updateCasilla(Casilla casilla) {
		this.casillas[casilla.getX()][casilla.getY()] = casilla;
	}
		
}
