package Piezas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza;
import Juego.Tablero;

public class Dama  extends Pieza{

	public Dama(COLOR color) {
		super(color, Tablero.TAMANO - 1, NOMBRE.PEON);		
		
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

}