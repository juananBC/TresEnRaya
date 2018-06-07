package Juego;

/**
 * Indica cuantas posiciones en X e Y se puede mover una pieza
 */
public class Movimiento {

	private int x, y;

	public static enum MOVIMIENTOS {
		DIAGONAL, HORIZONTAL, L
	};

	public Movimiento(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Movimiento mover(int xInicio, int yInicio) {
		int xFinal = xInicio + x;
		int yFinal = yInicio + y;

		Movimiento m = new Movimiento(xFinal, yFinal);
		return m;
	}

	
	public boolean checkMovimiento(MOVIMIENTOS M, Pieza pieza) {
		switch (M) {
			case L:
				return isL(pieza);
				
			case DIAGONAL:
				return isDiagonal(pieza);
				
			case HORIZONTAL:
				return isHorizontal(pieza);
				
			default:
				return false;
		}
	}

	public boolean isEqual(Movimiento mov) {
		return mov.x == this.x && mov.y == this.y;
	}

	public static Movimiento getMovimiento(Casilla origen, Casilla destino) {
		int x = origen.getX() - destino.getX();
		int y = origen.getY() - destino.getY();

		return new Movimiento(x, y);
	}

	
	public boolean isDiagonal(Pieza p) {

		if(limitarDireccion(p)) {
			return false;
		}

		int x = Math.abs(this.x);
		int y = Math.abs(this.y);

		final int MAX = Math.min(p.getDistancia(), p.getDistanciaMatar());
		if (x > MAX || y > MAX || x != y) return false;		
		
		return true;
	}

	public boolean isHorizontal(Pieza p) {
		if(limitarDireccion(p)) {
			return false;
		}

		int x = Math.abs(this.x);
		int y = Math.abs(this.y);

		final int MAX = Math.max(p.getDistancia(), p.getDistanciaMatar());
		if (x > MAX || y > MAX ) return false;
		return (x != 0 && y == 0) || (x == 0 && y != 0);
	}

	public boolean isL(Pieza p) {		
		int x = Math.abs(this.x);
		int y = Math.abs(this.y);

		return ((x == 2 && y == 1) || (x == 1 && y == 2));
	}
	
	
	private boolean limitarDireccion(Pieza p) {
		// Limitacion para los peones: mira que vaya hacia adelante
		return (!p.isLibre() && Integer.signum(p.mueveHacia()) != Integer.signum(this.y) );
	}

}
