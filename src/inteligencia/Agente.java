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
import Juego.Casilla;
import Juego.TIPO;
import Juego.Ficha;
import Juego.Tablero;

public class Agente {

	private static final boolean MAX = true;
	private static final boolean MIN = false;
	
	private Arbol arbol;
	private int profundidad;
	private Controlador controlador;
	private TIPO tipo; 
	
	private Log log;
	
	public Agente(Controlador controlador, int profundidad, TIPO tipo) {
		this.profundidad = profundidad;
		this.controlador = controlador;
		this.tipo = tipo;
	
	}
	
	
	public Estado calculaMovimiento(Controlador controlador) {
			
		this.controlador = controlador;
		this.arbol = new Arbol(tipo);	
		
		log = new Log();

		Nodo raiz = generarHijos(arbol.getRaiz(), profundidad);
		arbol.setRaiz(raiz);		
							
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

		if(prof == 0 || nodo.isTerminal()) //|| nodo.getHijos().isEmpty() )
			return nodo; 
		
		Nodo mejorValor;
		Nodo valorActual;	
		
		if(isMaximizador) {
			mejorValor = new Nodo(null, new Estado(Integer.MIN_VALUE));			
			
			for(Nodo hijo: nodo.getHijos()) {				
				valorActual = minimax(hijo, prof - 1, MIN);
				mejorValor = Nodo.max(valorActual,  mejorValor);
			}	
			
			
		} else { // Minimizador
			mejorValor = new Nodo(null, new Estado(Integer.MAX_VALUE));
			
			for(Nodo hijo: nodo.getHijos()) {
				valorActual = minimax(hijo, prof - 1, MAX);
				mejorValor = Nodo.min(valorActual,  mejorValor);	

			}
		}
	
		nodo.setEstado(mejorValor.getEstado());
		return mejorValor;
	}

	
	
	private Nodo generarHijos(Nodo parent, int prof) {
		
		if( parent.isTerminal()) {			
			return parent;
		}
		
		
		for (int y = 0; y < Tablero.TAMANO; y++) {
			for (int x = 0; x < Tablero.TAMANO; x++) {

				Casilla casilla = controlador.getCasilla(x, y);

				if (!casilla.isOcupada()) {

					if (controlador.ponerPieza(casilla.getId())) {

						Estado estado = controlador.getUltimoMovimiento();
						estado.setProfundidad(profundidad - prof);
						writeLog(estado, prof );

						Nodo hijo = new Nodo(parent, estado);
						hijo = generarHijos(hijo, prof - 1);


						parent.addHijo(hijo);
						controlador.revertir();
					}
				}
			}
		}

		return parent;
		
	}
	
	
	
	private void writeLog(Estado estado, int prof) {
		String TAB = "";
		for(int i = profundidad - prof; i > 0; i--) TAB += "\t";
		
		String ganador = (estado.getGanador() != null)? "GANA " +estado.getGanador().getTipo() : "EMPATE";
		log.append(TAB + (profundidad - prof)+ " NODO " + controlador.getCasilla(estado.getIdCasilla()).getPieza().getTipo() + 
				" " + estado.getIdCasilla() + ": " + ganador + "(" + estado.getPuntuacion()
				+ ")"
		
				 + " \n");
	
	}
}
