package Piezas;

import java.util.HashSet;
import java.util.Set;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Movimiento.MOVIMIENTOS;
import Juego.Pieza;

public class Peon extends Pieza {

	private int valorMuerteOriginal;
	
	public Peon(COLOR color) {
		super(color, 2, 1, false, false, NOMBRE.PEON);
		
		Set<MOVIMIENTOS> movs = new HashSet<MOVIMIENTOS>();	
		movs.add(MOVIMIENTOS.HORIZONTAL);
		this.setMovimientos(movs);	
		
		Set<MOVIMIENTOS> matar = new HashSet<MOVIMIENTOS>();	
		matar.add(MOVIMIENTOS.DIAGONAL);
		
		valorMuerteOriginal = getValorMuerte();
		
		this.setMatar(matar);			
	}

	@Override
	public Casilla mover() {
		int numPasos = getNumPasos();

		// Despues del primer movimiento, los peones solo pueden moverse una posicion
		if(numPasos > 0) this.setDistancia(1);
		else this.setDistancia(2);
		
		// El valor de los peones se incrementa al llegar al campo contrario, ya que puede convertirse en reina.
//		this.setValorMuerte(valorMuerteOriginal + numPasos); 
		return null;
	}

}
