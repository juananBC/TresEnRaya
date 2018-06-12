package inteligencia;

import java.util.ArrayList;
import java.util.List;

import Gestor.Estado;
import Juego.Ficha;
import Juego.TIPO;

public class Nodo {

	
	private Estado estado;
	private List<Nodo> hijos;
	private Nodo parent;
	 
	
	
	public Nodo() {
		this.parent = null;
		this.estado = new Estado(-1, null, 0, null);
	}
	
	public Nodo(Nodo parent, Estado estado) {
		this.parent = parent;
		this.estado = estado;
		this.hijos = new ArrayList<Nodo>();		
	}
	
	
//	public Nodo addHijo(Estado estado) {
//		Nodo hijo = new Nodo(this, estado);		
//		hijos.add(hijo);
//		
//		return hijo;
//	}
	

	public static Nodo max(Nodo n1, Nodo n2) {
		if(iguales(n1, n2)) 
			return getFromIguales(n1, n2);
			
		if(n1.getEstado().getPuntuacion() > n2.getEstado().getPuntuacion())
			return n1;
		return n2;
	}
	
	public static Nodo min(Nodo n1, Nodo n2) {
		if(iguales(n1, n2)) 
			return getFromIguales(n1, n2);
		
		
		if(n1.getEstado().getPuntuacion() < n2.getEstado().getPuntuacion())
			return n1;
		return n2;
	}
	
	private static boolean iguales(Nodo n1, Nodo n2) {
		return (n1.getEstado().getPuntuacion() == n2.getEstado().getPuntuacion());
	}
	
	private static Nodo random(Nodo n1, Nodo n2) {
		double rand = Math.random();		
		if(rand < 0.5) return n1;
		return n2;
	}
	
	private static Nodo getFromIguales(Nodo n1,Nodo n2) {
		Estado e1 = n1.getEstado();
		Estado e2 = n2.getEstado();
		
		if(e1.getProfundidad() > e2.getProfundidad()) return n2;
		else if(e1.getProfundidad() < e2.getProfundidad()) return n1;		

		return random(n1, n2);
	}
	
	public void addHijo(Nodo hijo) {	
		if(hijo != null)
			hijos.add(hijo);		
	}

	public int getValor() {
		return estado.getPuntuacion();
	}
	
	public void setTerminal() {
		this.hijos = null;
	}
	
	public boolean isTerminal() {
		return (estado != null  && estado.finJuego());
	}
	
	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public Nodo getHijo(int hijo){
		if(isTerminal()) return null;		
		return hijos.get(hijo);
	}
	
	public List<Nodo> getHijos() {
		return hijos;
	}


	public void setHijos(List<Nodo> hijos) {
		this.hijos = hijos;
	}


	public Nodo getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}


	public void setParent(Nodo parent) {
		this.parent = parent;
	}

	
	
}
