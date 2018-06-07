package Gestor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import Juego.*;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza.NOMBRE;
import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Peon;
import Piezas.Reina;
import Piezas.Rey;
import Piezas.Torre;
import inteligencia.Agente;

public class Controlador {

	private Jugador jugador, rival;
	private Jugador ganador;
	private Tablero tablero;
	private int turno;
	private Properties prop;
	private Stack<Estado> pila;
	
	private Agente agente;
	
	
	public Controlador() {
		prop = new Properties();
		
		int profundidad = 0;
		try {
			prop.load(new FileInputStream("C:\\Users\\JNBN007\\Desktop\\workspace\\JuegoDeMesa\\resources\\config"));
			profundidad = Integer.parseInt(prop.getProperty("profundidad", "0"));
		} catch (Exception e) {}
		
		pila = new Stack<Estado>();
		turno = 0;
		jugador = new Jugador(COLOR.BLANCO);
		rival = new Jugador(COLOR.NEGRO);		
		ganador = null;		
		tablero = new Tablero();
		colocarPiezas();

		agente = new Agente(this, profundidad , rival.getColor());
	}
	

	/**
	 * Vuelve atras el ultimo movimiento realizado.
	 */
	public void revertir() {		
		if(pila.empty()) return;
		
		Estado estado = pila.pop();
		
		Casilla destino = getCasilla(estado.getIdDestino());
		Casilla origen = getCasilla(estado.getIdOrigen());
		
		Pieza aux = destino.getPieza();
		aux.retrasar();
		
		aux = Pieza.transformaReinaPeon(aux);
		
		destino.setPieza(estado.getMata());
		origen.setPieza(aux);
		
		turno = estado.getTurno();
		ganador = null;
	}
	
	
	public Jugador getContrincante() {		
		if(turno % 2 == 1) return jugador;
		return rival;
	}
	
	public Jugador getJugadorActual() {
		if(turno % 2 == 0) return jugador;
		return rival;
		
	}
	
	
	public Set<Integer> getOpcionesMover(int idCasilla) {		
		Casilla casilla = tablero.getCasilla(idCasilla);

		Pieza pieza = casilla.getPieza();		
		if(pieza == null) return null;
		
		Set<Integer> casillas = new HashSet<Integer>();
		Set<MOVIMIENTOS> movs = new HashSet<MOVIMIENTOS>();
		movs.addAll(pieza.getMovimientos()); 
		movs.addAll(pieza.getMatar());	
		
		for(MOVIMIENTOS mov: movs) {
			switch (mov) {
			case HORIZONTAL:
				casillas.addAll(getHorizontales(casilla));
				break;
			case DIAGONAL:
				casillas.addAll(getDiagonales(casilla));
			case L:
				casillas.addAll(getLs(casilla));
			default:
				break;
			}
		}
		
		return casillas;	
	}
	
	
	public Set<Integer> getHorizontales(Casilla origen){
		Set<Integer> casillas = new HashSet<Integer>();

		int x = origen.getX();
		int y = origen.getY();
		
		int px = 1;
		int py = 0;
		for(int n = 0; n < 2; n++) {
			for (int i = 0; i < Tablero.TAMANO; i++) {
				int X = px*i + py*x;
				int Y = py*i + px*y;
				Casilla destino = tablero.getCasilla(X, Y);
				if(puedeMover(origen, destino))
					casillas.add(destino.getId());
			}
			
			px = 0;
			py = 1;
		}
		
		return casillas;
	}
	
	public Set<Integer> getDiagonales(Casilla origen){
		Set<Integer> casillas = new HashSet<Integer>();

		int x = origen.getX();
		int y = origen.getY();
		
		int dir = 1;
		for(int n = 0; n < 2; n++) {
			for (int i = 0; i < Tablero.TAMANO; i++) {
				int X = (i + x) % Tablero.TAMANO;
				int Y = (Tablero.TAMANO + dir*i + y) % Tablero.TAMANO;
				Casilla destino = tablero.getCasilla(X, Y);
				if(puedeMover(origen, destino) )
					casillas.add(destino.getId());
			}
			
			dir = -1;
		}
		
		return casillas;
	}
	
	public Set<Integer> getLs(Casilla origen){
		Set<Integer> casillas = new HashSet<Integer>();

		String mask;
		int x = origen.getX();
		int y = origen.getY();
		int j;
		
		for (int i = 1; i < 3; i++) {
			j = (i == 1)? 2:1;

			for (int n = 0; n < 4; n++) {
				mask = "0"+Integer.toBinaryString(n);
				
				// Coje los 2 ultimos bits
				mask = mask.substring(mask.length() -2, mask.length());
				int X = (mask.charAt(1) == '0' )? 1:-1;
				int Y = (mask.charAt(0) == '0' )? 1:-1;
				X = X*i + x;
				Y = Y*j + y;
				
				Casilla destino = tablero.getCasilla(X, Y);
				
				if (puedeMover(origen, destino))
					casillas.add(destino.getId());
			}

		}
		
		
		return casillas;
	}
	
	public Estado moverAgente() {
		if(getJugadorActual() == rival) {		
			Estado estado = agente.calculaMovimiento(this);
			if(estado != null) {
				boolean mover;
				mover = mover(estado.getIdOrigen(), estado.getIdDestino());
				if(mover)
				return estado;
			}
		}
		return null;
	}
	
	public boolean mover(int idOrigen, int idDestino) {		
		if(idOrigen < 0 ||  idOrigen > Tablero.TAMANO_TOTAL || 
			idDestino < 0 || idDestino > Tablero.TAMANO_TOTAL) return false;
		
		Casilla origen = tablero.getCasilla(idOrigen);
		Casilla destino = tablero.getCasilla(idDestino);
		
		if(puedeMover(origen, destino)) {

			Pieza pieza = origen.getPieza();
			origen.liberar();
			
			Pieza mata = destino.ocupar(pieza);
			if(mata != null) {
				matar(mata, getContrincante());
			}
			
			pieza.avanzar();
//			pieza = convertirPeon(destino);
			guardarMovimiento(mata, origen, destino);	
			
			return true;
		} 
		
		return false;
	}
	
	private void matar(Pieza pieza, Jugador jugador) {
		jugador.matar();
		
		if(pieza.getNombre() == Pieza.NOMBRE.REY)
			ganador = getJugadorActual();
	}
	
	/**
	 * Realiza el movimiento desde la casilla de origen a la casilla de destino.
	 * Comprueba que es el movimiento valido de la pieza, si puede botar obstaculos
	 * o que no haya obstaculos en caso de no poder botar
	 */
	private boolean puedeMover(Casilla origen, Casilla destino) { 
	
		if(ganador != null) 
			return false;
		
		if(origen == null || destino == null || !origen.isOcupada()) return false;		
		
		Pieza pieza = origen.getPieza();	
		if(pieza.getColor() != getJugadorActual().getColor())
			return false;
		
		return (pieza.isValid(origen, destino) && (pieza.isPuedeSaltar() || !hayObstaculos(origen, destino)));
	}
	
	
	/**
	 * Comprueba si en las casillas que separan 'origen' y 'destino' hay alguna
	 * ficha que impida hacer el movimiento.
	 */
	private boolean hayObstaculos(Casilla origen, Casilla destino) {
		Casilla tmp;
		
		// Origen y destino
		int xo = origen.getX();
		int yo = origen.getY();		
		int xd = destino.getX();
		int yd = destino.getY();
		
		int deltaX = xd - xo;
		int deltaY = yd - yo;		

		// Dirección de la separación de las casillas.
		int dirX = (deltaX == 0)? 0 : (deltaX < 0)? -1:1;
		int dirY = (deltaY == 0)? 0 : (deltaY < 0)? -1:1;		
		int limitMax = Math.max(Math.abs(deltaX), Math.abs(deltaY));

		for (int i = 1; i < limitMax; i++) {
			tmp = tablero.getCasilla(xo + i*dirX, yo + i*dirY);
			if (tmp.isOcupada()) return true;
		}
		return false;			
	}
	
	
	private Pieza convertirPeon(Casilla casilla) {
		Pieza p = casilla.getPieza();
		
		if(p  instanceof Peon) {

			if (casilla.getY() == 0 || casilla.getY() == Tablero.TAMANO - 1) {
				System.out.println(casilla.getX() + " , " + casilla.getY() + ", " + casilla.getId());
				
				// p.setNombre(NOMBRE.REINA);
				Reina reina = new Reina(p.getColor());
				reina.setFromPeon(true);
				
				casilla.setPieza(reina);

				p = reina;
			}
			
		}
		
		return p;
		
	}
	
	
	private void guardarMovimiento(Pieza mata, Casilla origen, Casilla destino) {
		tablero.updateCasilla(origen);
		tablero.updateCasilla(destino);
		
//		Pieza pieza = destino.getPieza();
		Estado estado = new Estado(mata, origen.getId(), destino.getId(), turno, getJugadorActual().getColor());
		pila.push(estado);
		
		turno++;
	}
	
	
	private void colocarPiezas() {		
		for (int x = 0; x < Tablero.TAMANO; x++) {
			for (int y = 0; y < Tablero.TAMANO; y++) {
				ponerPieza(x ,y);					
			}
		}	
	}
	
	
	private boolean ponerPieza(int x, int y) {

		if(y > 1 && y < 6 ) return false;
		
		Casilla casilla = tablero.getCasilla(x, y);	
		Pieza pieza = null;	
		COLOR color;
		
			color = (y <= 1) ?  COLOR.BLANCO : COLOR.NEGRO;

		if (y == 1 || y == 6) {
			pieza = new Peon(color);
			
		} else {
			NOMBRE nombreCase = NOMBRE.valueOf(prop.getProperty(x + ""));
			switch (nombreCase) {
			case CABALLO:
				pieza = new Caballo(color);
				break;
			case TORRE:
				pieza = new Torre(color);
				break;
			case REY:
				pieza = new Rey(color);
				break;
			case REINA:
				pieza = new Reina(color);
				break;
			case ALFIL:
				pieza = new Alfil(color);
				break;
			default:
				break;
			}
		}
		if(pieza == null ) return false;
		
		casilla.ocupar(pieza);
		tablero.updateCasilla(casilla);
		return true;
	}


	public Estado getUltimoMovimiento() {
		if(pila.empty()) return null;
		
		return pila.peek();
	}
	
	public Casilla getCasilla(int x, int y) {
		return tablero.getCasilla(x, y);
	}
	
	public Pieza getPieza(int x, int y) {
		return tablero.getCasilla(x, y).getPieza();
	}
	
	public Pieza getPieza(int id) {
		return tablero.getCasilla(id).getPieza();
	}
	
	public boolean isCasillaOcupada(int id) {
		return tablero.getCasilla(id).isOcupada();
	}
	
	public COLOR getColorCasilla(int id) {
		return tablero.getCasilla(id).getColor();
	}
	
	public COLOR getColorCasilla(int x, int y) {
		return tablero.getCasilla(x, y).getColor();
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
	
	
}
