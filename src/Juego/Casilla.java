package Juego;


public class Casilla {

	// El negro sera invertido de signo en los movimientos
	
	private boolean ocupada;
	private Pieza pieza;
	private int id;
	private int x,y;
	private COLOR color;


	public Casilla(Pieza pieza, int x, int y) {
		this.ocupada = true;
		this.pieza = pieza;
		this.id = calcId(x,  y);
		this.x = x;
		this.y = y;
		this.color = ((this.x + this.y)%2 == 0)? COLOR.BLANCO: COLOR.NEGRO;;

	}

	public Casilla(int x, int y) {
		this.ocupada = false;
		this.pieza = null;
		this.id = calcId(x, y);
		this.x = x;
		this.y = y;
		this.color = ((this.x + this.y)%2 == 0)? COLOR.BLANCO: COLOR.NEGRO;;
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
		ocupada = false;
		pieza = null;
	}
	

	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}
	
	public Pieza ocupar(Pieza pieza) {		
		Pieza p = null;
		if(ocupada == true) {
			p = getPieza();
		}
		
		ocupada = true;
		this.pieza = pieza;
		
		return p;
	}

	 
	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public Pieza getPieza() {
		return pieza;
	}

	public void setPieza(Pieza pieza) {
		this.ocupada = (pieza != null);		
		this.pieza = pieza;
	}
	
	
	private int calcId(int x, int y) {
		int id = x + Tablero.TAMANO * y;	
		return id;
	}
	
}
