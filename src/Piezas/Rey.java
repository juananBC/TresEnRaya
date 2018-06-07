package Piezas;

import java.util.HashSet;
import java.util.Set;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza;

public class Rey extends Pieza {

	public Rey(COLOR color) {
		super(color, 1, NOMBRE.REY);

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
