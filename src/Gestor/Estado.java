package Gestor;

import Juego.COLOR;
import Juego.Pieza;
import Juego.Pieza.NOMBRE;

public class Estado  {
	private Pieza mata;
	private int idOrigen, idDestino;
	private int turno;
	private int puntuacion;
	private COLOR color;
	
	private int profundidad;
	
	public Estado(int turno, COLOR color, int puntuacion) {
		this.mata = null;
		this.idDestino = -1;
		this.idOrigen = -1;
		this.turno = turno;
		this.color = color;
		this.puntuacion = puntuacion; //(mata == null)? 0:mata.getValorMuerte();
		

		this.profundidad = 0;
	}
	
	public Estado(Pieza mata, int idOrigen, int idDestino, int turno, COLOR color){
		this.mata = mata;
		this.idDestino = idDestino;
		this.idOrigen = idOrigen;
		this.turno = turno;
		this.color = color;
		this.puntuacion = (mata == null)? 0:mata.getValorMuerte();
		
		this.profundidad = 0;
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
	
	
	public boolean finJuego() {
		return mata != null && mata.getNombre() == NOMBRE.REY;
	}
	
	public Pieza getMata() {
		return mata;
	}

	public void setMata(Pieza mata) {
		this.mata = mata;
	}

	public int getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(int idOrigen) {
		this.idOrigen = idOrigen;
	}

	public int getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(int idDestino) {
		this.idDestino = idDestino;
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

	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
	
	
}
