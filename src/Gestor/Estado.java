package Gestor;

import Juego.Casilla;
import Juego.Ficha;
import Juego.Jugador;
import Juego.TIPO;
import Juego.Tablero;

public class Estado  {
	private Ficha mata;
	private Casilla casilla;
	private int turno;
	private int puntuacion;
	private TIPO tipo;
	private Jugador ganador;
	
	private int profundidad;
	
	public Estado(int turno, Casilla casilla, int puntuacion, Jugador ganador) {
		this.turno = turno;
		this.casilla = casilla;
		this.puntuacion = puntuacion;
		this.ganador = ganador; 
		
		this.profundidad = 0;
	}
	
	public Estado(int puntuacion) {
		this.turno = -1;
		this.casilla = null;
		this.puntuacion = puntuacion;
		this.ganador = null; 
		
		this.profundidad = -1;
	}
	

	public static Estado max(Estado e1, Estado e2) {
		if(e1.getPuntuacion() >= e2.getPuntuacion()){
			return e1;
		}		
		return e2;
	}
	
	public static Estado min(Estado e1, Estado e2) {
		if(e1.getPuntuacion() <= e2.getPuntuacion()){
			return e1;
		}		
		return e2;
	}
	
	public void actualizaPuntos(int puntos) {
		this.puntuacion += puntos;
	}	
		
	public Ficha getMata() {
		return mata;
	}

	public void setMata(Ficha mata) {
		this.mata = mata;
	}

	

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	
	public boolean finJuego() {
		return ganador != null || turno >= Tablero.TAMANO_TOTAL;
	}
	
	public int getIdCasilla() {
		return casilla.getId();
	}

	public TIPO getTipo() {
		return tipo;
	}


	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}


	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}


	public Casilla getCasilla() {
		return casilla;
	}


	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}


	public Jugador getGanador() {
		return ganador;
	}


	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}
	
	
	
}
