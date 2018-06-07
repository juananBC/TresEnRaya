package Piezas;

import java.util.HashSet;
import java.util.Set;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza;

public class Caballo extends Pieza{

	public Caballo(COLOR color) {
		super(color, 0, true, true, NOMBRE.CABALLO);

		Set<MOVIMIENTOS> movimientos = new HashSet<MOVIMIENTOS>();		
		movimientos.add(MOVIMIENTOS.L);		
		setMovimientos(movimientos);		
		setMatar(movimientos);	
	}

	@Override
	public Casilla mover() {
		// TODO Auto-generated method stub
		return null;
	}

}
