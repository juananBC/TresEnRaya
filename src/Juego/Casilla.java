package Juego;


public class Casilla {

	// El negro sera invertido de signo en los movimientos
	
	private Ficha pieza;
	private int id;
	private int x,y;


	public Casilla(Ficha pieza, int x, int y) {
		this.pieza = pieza;
		this.id = calcId(x,  y);
		this.x = x;
		this.y = y;
	}

	public Casilla(int x, int y) {
		this.pieza = null;
		this.id = calcId(x, y);
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void liberar() {
		pieza = null;
	}
	
	public boolean ocupar(Ficha pieza) {
		if(isOcupada()) return false;		
		
		this.pieza = pieza;
		
		return true;
	}

	 
	public boolean isOcupada() {
		return pieza != null;
	}


	public Ficha getPieza() {
		return pieza;
	}

	public void setPieza(Ficha pieza) {
		this.pieza = pieza;
	}
	
	
	private int calcId(int x, int y) {
		int id = x + Tablero.TAMANO * y;	
		return id;
	}
	
}
