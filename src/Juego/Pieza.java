package Juego;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import Excepciones.MovimientoNoValido;
import Juego.Movimiento.MOVIMIENTOS;
import Piezas.Peon;
import Piezas.Reina;

public abstract class Pieza {
	
	// El negro sera invertido de signo en los movimientos
	public static enum NOMBRE{REY, REINA, PEON, CABALLO, ALFIL, TORRE};
	
	public static Properties prop = null;
	
	private COLOR color;
	private int distancia; 		// Cantidad de pasos que puede moverse
	private int distanciaMatar;
	private boolean libre;		// Moverse en cualquier direccion (true), o adelante (false)
	private boolean puedeSaltar;// Puede botar piezas (true), o son obstaculos (false)
	private NOMBRE nombre; 
	private int valorMuerte;
	private int numPasos;		// Cantidad de pasos que se ha movido
	
	private Set<MOVIMIENTOS> movimientos;
	private Set<MOVIMIENTOS> matar;
	
	public Pieza(COLOR color, int distancia, boolean libre, boolean puedeSaltar, NOMBRE nombre) {
		this.color = color;
		this.distancia = distancia;
		this.distanciaMatar = distancia;
		this.libre = libre;
		this.puedeSaltar = puedeSaltar;
		this.nombre = nombre;
		this.numPasos = 0;
		
		initValorMuerte();		
	}
	
	public Pieza(COLOR color, int distancia, int distanciaMatar, boolean libre, boolean puedeSaltar, NOMBRE nombre) {
		this.color = color;
		this.distancia = distancia;
		this.distanciaMatar = distanciaMatar;
		this.libre = libre;
		this.puedeSaltar = puedeSaltar;
		this.nombre = nombre;
		this.numPasos = 0;
		
		initValorMuerte();		
	}
	
	public Pieza(COLOR color, int distancia, NOMBRE nombre) { 
		
		this.color = color;
		this.distancia = distancia;
		this.distanciaMatar = distancia;
		this.libre = true;
		this.puedeSaltar = false;
		this.nombre = nombre;
		this.numPasos = 0;
		
		initValorMuerte();
	}


	private void initValorMuerte() {
		try {
			if(prop == null) {
				prop = new Properties();
				prop.load(new FileInputStream("C:\\Users\\JNBN007\\Desktop\\workspace\\JuegoDeMesa\\resources\\config"));
			}
			
			String nombrePieza = this.getClass().getSimpleName();
			this.valorMuerte = Integer.parseInt(prop.getProperty(nombrePieza, "0"));
		} catch (IOException e) {}
	}
	
	
	/**
	 * 
	 * @param origen
	 * @param destino
	 * @throws MovimientoNoValido
	 */
	public boolean isValid(Casilla origen, Casilla destino)  {
		
		Pieza pDestino = destino.getPieza();
		if(pDestino != null && this.getColor() == pDestino.getColor()) return false;
		
		int x = destino.getX() - origen.getX();
		int y = destino.getY() - origen.getY() ;

		// Obtiene el movimiento realizado
		Movimiento mov = new Movimiento(x, y);
		
		if(movimientoValido(mov, origen, destino) ||	matarValido(mov, origen, destino)) {	
			 return true;	
		}
		
		return false;
	}
	
	
	public boolean movimientoValido(Movimiento m, Casilla origen, Casilla destino) {
		
		if(destino.isOcupada()) return false;		
		
		for(MOVIMIENTOS movimiento : movimientos) {	
			
			if(m.checkMovimiento(movimiento, this)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean matarValido(Movimiento m, Casilla origen, Casilla destino) {

		if( !destino.isOcupada() || destino.getPieza().getColor() == color) 
			return false;
				
		for(MOVIMIENTOS movimiento : matar) {				
			if(m.checkMovimiento(movimiento, this)) {				
				return true;
			}
		}		
		return false;
	}
	
	public int mueveHacia() {
		return (color == COLOR.BLANCO)? 1:-1;
		
	}
	public abstract Casilla mover();

	public void avanzar() {
//		System.out.println("MOVER " + this.getClass().getName());
		numPasos++;
		mover();
	}

	public void retrasar() {
//		System.out.println("RETRASAR " + this.getClass().getName());

		numPasos--;
		mover();
	}

	/**
	 * Destransorforma una reina que antes era un peon.
	 */
	public static Pieza transformaReinaPeon(Pieza aux) {
		if(aux instanceof Reina) {
			Reina reina = (Reina) aux;

			if(reina.isFromPeon() && reina.getNumPasos() < 0) {
				Peon peon = new Peon(aux.getColor());
				peon.setNumPasos(5);
				aux = peon;
			}
		}
		
		return aux;
	}
	
	public COLOR getColor() {
		return color;
	}



	public void setColor(COLOR color) {
		this.color = color;
	}



	public Set<MOVIMIENTOS> getMovimientos() {
		return movimientos;
	}



	public void setMovimientos(Set<MOVIMIENTOS> movimientos) {
		this.movimientos = movimientos;
	}



	public Set<MOVIMIENTOS> getMatar() {
		return matar;
	}



	public void setMatar(Set<MOVIMIENTOS> matar) {
		this.matar = matar;
	}
	
	
	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	public void setLibre(boolean libre) {
		this.libre = libre;
	}
	
	public boolean isLibre() {
		return libre;
	}
	

	public void setPuedeSaltar(boolean puedeSaltar) {
		this.puedeSaltar = puedeSaltar;
	}
	
	public boolean isPuedeSaltar() {
		return puedeSaltar;
	}
	
	
	
	public NOMBRE getNombre() {
		return nombre;
	}


	public void setNombre(NOMBRE nombre) {
		this.nombre = nombre;
	}


	public int getValorMuerte() {
		return valorMuerte;
	}


	public void setValorMuerte(int valorMuerte) {
		this.valorMuerte = valorMuerte;
	}


	public int getNumPasos() {
		return numPasos;
	}


	public void setNumPasos(int numPasos) {
		this.numPasos = numPasos;
	}

	public int getDistanciaMatar() {
		return distanciaMatar;
	}

	public void setDistanciaMatar(int distanciaMatar) {
		this.distanciaMatar = distanciaMatar;
	}
	
	
}
