package inteligencia;

import Gestor.Estado;
import Juego.COLOR;

public class Arbol {

	private Nodo raiz;
	
	public Arbol(COLOR color) {		
		raiz = new Nodo(null, new Estado(null, -1, -1, 0, color));
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}	
		
}
