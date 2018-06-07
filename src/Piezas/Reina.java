package Piezas;

import java.util.HashSet;
import java.util.Set;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Pieza;
import Juego.Tablero;
import Juego.Movimiento.MOVIMIENTOS;

public class Reina  extends Pieza{

	private boolean fromPeon;
	
	public Reina(COLOR color) {
		super(color, Tablero.TAMANO - 1, NOMBRE.REINA);	
		
		fromPeon = false;
		
		// Movimientos en diagonal
		Set<MOVIMIENTOS> movimientos = new HashSet<MOVIMIENTOS>();		
		movimientos.add(MOVIMIENTOS.HORIZONTAL);	
		movimientos.add(MOVIMIENTOS.DIAGONAL);
		
		setMovimientos(movimientos);		
		setMatar(movimientos);	
	}

	@Override
	public Casilla mover() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isFromPeon() {
		return fromPeon;
	}

	public void setFromPeon(boolean fromPeon) {
		this.fromPeon = fromPeon;
	}

	
}