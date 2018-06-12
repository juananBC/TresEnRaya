package inteligencia;

import Gestor.Estado;
import Juego.TIPO;

public class Arbol {

	private Nodo raiz;
	
	public Arbol(TIPO tipo) {		
		raiz = new Nodo(null, null);
		
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}	
		
}
