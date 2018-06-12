package Gestor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import Juego.*;
import inteligencia.Agente;

public class Controlador {

	private Jugador jugador, rival;
	private Jugador ganador;
	private Tablero tablero;
	private int turno;
	private Properties prop;
	private Stack<Estado> pila;
	
	private Agente agente;
	
	private boolean empiezaCPU;
	
	public Controlador(boolean empiezaCPU, TIPO tipoJugador) {
		prop = new Properties();
		
		int profundidad = 0;
		try {
			prop.load(new FileInputStream("C:\\Users\\JNBN007\\Desktop\\workspace\\TresEnRaya\\TresEnRaya\\config"));
			profundidad = Integer.parseInt(prop.getProperty("profundidad", "-1"));
		} catch (Exception e) {
			profundidad = Integer.MAX_VALUE;
		}
		
		this.pila = new Stack<Estado>();
		this.turno = 0;
		this.jugador = new Jugador(tipoJugador);
		this.rival = new Jugador( (tipoJugador == TIPO.X)? TIPO.O: TIPO.X );		
		this.ganador = null;		
		this.tablero = new Tablero();
		
		this.empiezaCPU = empiezaCPU;

		this.agente = new Agente(this, profundidad , rival.getTipo());
	}
	
	
	public boolean ponerPieza(int idCasilla) {
		if(ganador != null) return false;
		
		Jugador player = getJugadorActual();
		Casilla casilla = tablero.getCasilla(idCasilla);
				
		Ficha pieza = new Ficha(player.getTipo());		
		if(casilla.ocupar(pieza)) {			
			guardarMovimiento(casilla);
			return true;
		} 
		
		return false;
		
	}
	
	public boolean quedanCasillas() {
		for (int id = 0; id < Tablero.TAMANO_TOTAL; id++) {
			if (!tablero.getCasilla(id).isOcupada())
				return true;
		}
		
		return false;
	}
	
	public Estado moverAgente() {
		if(getJugadorActual() == rival && ganador == null  ) {	
			
			Estado estado = agente.calculaMovimiento(this);
			if(estado != null) {
				ponerPieza(estado.getIdCasilla());
				return estado;
			}
		}
		return null;
	}
	


	
	
	private void guardarMovimiento(Casilla casilla) {
		tablero.updateCasilla(casilla);
		
		int puntuacion = calculaPuntuacion();
		Estado estado = new Estado(turno, casilla, puntuacion, ganador);

		pila.push(estado);		
		turno++;
	}
	
	/**
	 * Vuelve atras el ultimo movimiento realizado.
	 */
	public void revertir() {		
		if(pila.empty()) return;
		
		Estado estado = pila.pop();		
		Casilla casilla = getCasilla(estado.getIdCasilla());
		casilla.liberar(); 
		
		turno = estado.getTurno();
		ganador = null;
		
	}
	
	public int calculaPuntuacion() {
		int diagonal = calcDiagonal();
		int horizontal = calcHorizontal();
		int vertical =  calcVertical();

		int puntuacion =  diagonal + horizontal + vertical;
		
		setGanador(puntuacion);		
		
		return puntuacion;
	}
	
	
	
	private int calcDiagonal() {
		TIPO tipo = null;
		
		for(int i = 0; i < Tablero.TAMANO; i++) {							
			Casilla c = tablero.getCasilla(i, i);
			if (!c.isOcupada() || (tipo != null && tipo != c.getPieza().getTipo())) {
				tipo = null;
				break;
			}

			tipo = c.getPieza().getTipo();
		}		
		
		if(tipo != null) 
			return getPuntuacion(tipo);


		for(int i = 0; i < Tablero.TAMANO; i++) {							
			Casilla c = tablero.getCasilla(Tablero.TAMANO - 1 - i, i);
			if (!c.isOcupada() || (tipo != null && tipo != c.getPieza().getTipo())) {
				tipo = null;
				break;
			}
			tipo = c.getPieza().getTipo();
		}
		
		return getPuntuacion(tipo);
	}
	
	private int calcVertical() {		
		TIPO tipo = null;

		for (int x = 0; x < Tablero.TAMANO; x++) {
			
			if(tipo != null) break;
			
			for (int y = 0; y < Tablero.TAMANO; y++) {

				Casilla c = tablero.getCasilla(x, y);
				if (!c.isOcupada() || (tipo != null && tipo != c.getPieza().getTipo())) {
					tipo = null;
					break;
				}

				tipo = c.getPieza().getTipo();
			}
		}
		
		return getPuntuacion(tipo);
	}
	
	private int calcHorizontal() {
		TIPO tipo = null;

		for (int y = 0; y < Tablero.TAMANO; y++) {
			if(tipo != null) break; // Ha encontrado una fila ganadora
			for (int x = 0; x < Tablero.TAMANO; x++) {

				Casilla c = tablero.getCasilla(x, y);
				if (!c.isOcupada() || (tipo != null && tipo != c.getPieza().getTipo())) {
					tipo = null;
					break;
				}

				tipo = c.getPieza().getTipo();
			}
		}
		
		return getPuntuacion(tipo);
	}
	
	
	private void setGanador(int puntuacion) {
		if(puntuacion < 0) ganador = jugador;		// GANA JUGADOR
		else if(puntuacion > 0) ganador = rival;	// GANA RIVAL
		else ganador = null;	//EMPATE
	}
	
	private int getPuntuacion(TIPO tipo) {
		if(tipo == null) return 0;		
		return (tipo == rival.getTipo())? 1:-1;
	}

	public Estado getUltimoMovimiento() {
		if(pila.empty()) return null;
		
		return pila.peek();
	}
	
	public Casilla getCasilla(int x, int y) {
		return tablero.getCasilla(x, y);
	}
	
	public Ficha getPieza(int x, int y) {
		return tablero.getCasilla(x, y).getPieza();
	}
	
	public Ficha getPieza(int id) {
		return tablero.getCasilla(id).getPieza();
	}
	
	public boolean isCasillaOcupada(int id) {
		return tablero.getCasilla(id).isOcupada();
	}
	
	
	public Casilla getCasilla(int id) {
		return tablero.getCasilla(id);
	}
	
	public Jugador getJugador1() {
		return jugador;
	}


	public void setJugador1(Jugador jugador1) {
		this.jugador = jugador1;
	}


	public Jugador getRival() {
		return rival;
	}


	public void setRival(Jugador jugador2) {
		this.rival = jugador2;
	}


	public Tablero getTablero() {
		return tablero;
	}


	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}


	public int getTurno() {
		return turno;
	}


	public void setTurno(int turno) {
		this.turno = turno;
	}
	
	
	public Jugador getContrincante() {		
		int valorJugador = (empiezaCPU)? 1:0;
		if(turno % 2 == valorJugador) return rival;
		return jugador;
	}
	
	public Jugador getJugadorActual() {
		int valorJugador = (empiezaCPU)? 1:0;
		if(turno % 2 == valorJugador ) return jugador;
		return rival;
		
	}
	
}
