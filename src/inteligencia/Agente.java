package inteligencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import Fichero.Log;
import Gestor.Controlador;
import Gestor.Estado;
import Juego.COLOR;
import Juego.Casilla;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza;
import Juego.Tablero;
import Piezas.Rey;

public class Agente {

	private static final boolean MAX = true;
	private static final boolean MIN = false;
	
	private Arbol arbol;
	private int profundidad;
	private Controlador controlador;
	private COLOR color; 
	
	private Log log;
	
	public Agente(Controlador controlador, int profundidad, COLOR color) {
		this.profundidad = profundidad;
		this.controlador = controlador;
		this.color = color;
	
	}
	
	
	public Estado calculaMovimiento(Controlador controlador) {
			
		this.controlador = controlador;
		arbol = new Arbol(color);	
		
		log = new Log();

		generarHijos(arbol.getRaiz(), profundidad);		
							
		Nodo nodo =  minimax(arbol.getRaiz(), profundidad , MAX);
		while(nodo.getParent() != arbol.getRaiz()) {
			nodo = nodo.getParent();
		}

		Estado estado = nodo.getEstado();
//		System.out.println(estado.getIdOrigen() + " " + estado.getIdDestino());
		log.cerrar();
		
		
		return estado;

	}
	

	private Nodo minimax(Nodo nodo, int prof, boolean isMaximizador) {

		if(prof < 0 || nodo.isTerminal() ) //|| nodo.getHijos().isEmpty() )
			return nodo; 
		
		Nodo mejorValor;
		Nodo valorActual;	
		
		if(isMaximizador) {
			mejorValor = new Nodo(null, new Estado(-1, color, Integer.MIN_VALUE));
			
			for(Nodo hijo: nodo.getHijos()) {
				valorActual = minimax(hijo, prof - 1, MIN);
				mejorValor = Nodo.max(valorActual,  mejorValor);
			}	
			
			
		} else { // Minimizador
			mejorValor = new Nodo(null, new Estado(-1, color, Integer.MAX_VALUE));
			
			for(Nodo hijo: nodo.getHijos()) {
				valorActual = minimax(hijo, prof - 1, MAX);
				mejorValor = Nodo.min(valorActual,  mejorValor);				
			}
		}

		return mejorValor;
	}

	
	
	private Nodo generarHijos(Nodo parent, int prof) {
		
		if(prof == 0 || parent.isTerminal()) {
			Estado estado = parent.getEstado();
			Pieza pieza = estado.getMata();
			
			if(pieza != null) 	{		
				if(pieza.getColor() == color) estado.setPuntuacion((-1)*pieza.getValorMuerte());
				else estado.setPuntuacion(pieza.getValorMuerte());
			} else {
				estado.setPuntuacion(0);
			}

//			parent.setEstado((estado));
				
			parent.setTerminal();
			return parent;
		}
		
		
		for (int y = 0; y < Tablero.TAMANO; y++) {
			for (int x = 0; x < Tablero.TAMANO; x++) {
				
				Casilla casilla = controlador.getCasilla(x, y); 				

				if(piezaPosible(casilla)) {
					
					Set<Integer> movs = controlador.getOpcionesMover(casilla.getId());	
					for(int idDestino: movs) {	
						
//						if(!vuelveAtras(parent, idDestino)) {	
							if(controlador.mover(casilla.getId(), idDestino)) {
								
								Estado estado = controlador.getUltimoMovimiento();								
								estado.setProfundidad(profundidad - prof);
		
						
								Nodo hijo = new Nodo(parent, estado);	
								hijo = generarHijos(hijo, prof - 1);
hijo.calcPuntuacion(color == hijo.getEstado().getColor());
							writeLog(hijo.getEstado(), prof);
								
								parent.addHijo(hijo);
								controlador.revertir();
							}							
//						}						
					}
				}
			}
		}
		
		return parent;
		
	}
	
	/**
	 * Comprueba si el movimiento a realizar es el que conduce hacia el nodo padre. 
	 * @return
	 */
	private boolean vuelveAtras(Nodo parent, int idDestino) {
		return idDestino == parent.getEstado().getIdOrigen();
	}
	
	
	private boolean piezaPosible(Casilla casilla) {
		return casilla.isOcupada() && casilla.getPieza().getColor() == controlador.getJugadorActual().getColor();
	}
	
	private void writeLog(Estado estado, int prof) {
		String TAB = "";
		for(int i = profundidad - prof; i > 0; i--) TAB += "\t";
		Pieza pieza = controlador.getPieza(estado.getIdDestino());
		
		String mata ="";		
		if(estado.getMata() != null)
			mata = "["+estado.getMata().getNombre() + " " +estado.getMata().getColor()+"]";
		
		log.append(TAB + prof + " NODO " + pieza.getNombre() + " " + estado.getColor() + ": " + estado.getIdOrigen()
				+ " -> " + estado.getIdDestino() + " (" + estado.getPuntuacion()+ ") "+ mata +"\n");
	
	}
}
