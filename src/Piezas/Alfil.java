package Piezas;

import java.util.HashSet;
import java.util.Set;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza;
import Juego.Tablero;

public class Alfil extends Pieza{
	
	public Alfil(COLOR color) {
		super(color, Tablero.TAMANO - 1, NOMBRE.ALFIL);		
		
		Set<MOVIMIENTOS> movimientos = new HashSet<MOVIMIENTOS>();		
		movimientos.add(MOVIMIENTOS.DIAGONAL);		
		setMovimientos(movimientos);		
		setMatar(movimientos);			
	}


	@Override
	public Casilla mover() {
		// TODO Auto-generated method stub
		return null;
	}

}