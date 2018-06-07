package interfaz;

import Juego.COLOR;
import Juego.Casilla;
import Juego.Pieza;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Gestor.Controlador;
import Gestor.Estado;
import Juego.Tablero;

public class Juego {

	public static enum DIRECCION{ARRIBA, DERECHA, ABAJO, IZQUIERDA};
	
	private static final Color NEGRO = new Color(169, 169, 169);
	private static final Color BLANCO = new Color(229, 229, 229);
	private static final Color SELECCIONADA =  new Color(72, 209, 204);
	private static final Color POSIBLE =  new Color(200, 251, 200);

	private  int TAM_CASILLA, PADDING_X, PADDING_Y;
	private DIRECCION orientacion;
	private JLabel casillaSeleccionada;
	private Controlador controlador;
	private JPanel jpTablero;
	private Set<Integer> casillasPosible;
	private List<JLabel> casillas;
	
	public Juego(Controlador controlador, JPanel jpTablero) {
		this.controlador = controlador;
		this.jpTablero = jpTablero;		
		this.casillaSeleccionada = null;		
		this.casillas = new ArrayList<JLabel>();
		this.casillasPosible = new HashSet<Integer>();
		this.orientacion = DIRECCION.ARRIBA;		
		pintarTablero();		
	}

	private void calcPadding() {
		int size = Math.min(jpTablero.getWidth(), jpTablero.getHeight());
		TAM_CASILLA = (int)( size / Tablero.TAMANO);
		PADDING_X = jpTablero.getWidth() -  jpTablero.getHeight();
		PADDING_X = (PADDING_X < 0)? 0: PADDING_X / 2;

		PADDING_Y = jpTablero.getHeight() - jpTablero.getWidth();
		PADDING_Y = (PADDING_Y < 0)? 0: PADDING_Y / 2;
	}
	
	
	public void pintarTablero() {
				
		casillas.clear();// = new ArrayList<JLabel>();
		jpTablero.removeAll();

		calcPadding();

		for (int y = 0; y < Tablero.TAMANO; y++) {
			for (int x = 0; x< Tablero.TAMANO; x++) {
				JLabel jCasilla = pintarCasilla(x, y);					
				jpTablero.add(jCasilla);	
				casillas.add(jCasilla);
			}
		}
		
		if(casillaSeleccionada != null) 			
			posiblesMovimiento(Integer.parseInt(casillaSeleccionada.getName()));
		
		jpTablero.validate();
		jpTablero.repaint();

	}
	
	
	private JLabel pintarCasilla(int x, int y) {
		COLOR color = controlador.getColorCasilla(x, y);
		String casillaId = controlador.getCasilla(x, y).getId()+"";

		JLabel jCasilla = new JLabel();
		
		jCasilla.setText(casillaId);
		jCasilla.setHorizontalTextPosition(SwingConstants.CENTER);
		jCasilla.setVerticalTextPosition(SwingConstants.CENTER);
		jCasilla.setForeground(Color.DARK_GRAY);
		
		jCasilla.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));		
		jCasilla.setIconTextGap(4);
		jCasilla.setOpaque(true);
		jCasilla.setName(casillaId);		
		jCasilla.addMouseListener(eventClickCasilla());
		setBounds(x, y, jCasilla);
		
		// Pone la pieza, si tiene.
		Pieza pieza = controlador.getPieza(x, y);
		if(pieza != null) ponerFicha(jCasilla, getNombrePieza(pieza), 0);
				
		// Setea el color de la casilla
		Color c = (color == COLOR.BLANCO)?  BLANCO: NEGRO;
		if(casillaSeleccionada != null && casillaSeleccionada.getName().equals(casillaId)) {
			c = SELECCIONADA;
			casillaSeleccionada = jCasilla;
		}
		jCasilla.setBackground(c);

		
		return jCasilla;
	}
	
//	private HashMap<String,ImageIcon> cargarImagenes(){
//		HashMap<String,ImageIcon> mapa = new HashMap<String,ImageIcon>();
//)
//		for(Pieza.NOMBRE pieza: Pieza.NOMBRE.values()) {
//			mapa.put(pieza+COLOR.BLANCO.name(), pieza+"-N");
//		}
//		return mapa;
//	}
	
	
	private void setBounds(int x, int y, JLabel jCasilla) {
		int xPos = x;
		int yPos = y;

		switch(orientacion) {
		case ARRIBA:
			xPos = x;
			yPos = y; 
			break;
		case ABAJO:
			xPos = 7 - x;
			yPos = 7 - y; 
			break;
		case IZQUIERDA:
			xPos = y;
			yPos = x; 
			break;
		case DERECHA:
			xPos =  7 - y;
			yPos =  7 - x; 
			break;
			
		}
		
		xPos = PADDING_X + xPos * TAM_CASILLA;
		yPos = PADDING_Y + yPos * TAM_CASILLA; 
		jCasilla.setBounds(xPos, yPos, TAM_CASILLA, TAM_CASILLA);
	}
	
	private ImageIcon ponerFicha(JLabel jLabel, String pieza, int borde){

		ImageIcon imageIcon = new ImageIcon("C:\\Users\\JNBN007\\Desktop\\workspace\\JuegoDeMesa\\resources\\img\\"+pieza+".png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
	    Image newimg = image.getScaledInstance(jLabel.getWidth() + borde, jLabel.getHeight() + borde,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    imageIcon = new ImageIcon(newimg);  // transform it back
	    
	    jLabel.setIcon(imageIcon);
	    
	    return imageIcon;
	}

	private String getNombrePieza(Pieza pieza) {
		return  pieza.getNombre() + "-" + pieza.getColor().toString().charAt(0);
	}
	
	public void revertir() {		
		controlador.revertir();
		pintarTablero();
	}
	
	
	private MouseAdapter eventClickCasilla() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				int idOrigen, idDestino;
				Pieza piezaDestino, piezaOrigen = null;
				
				// Casilla en la que se ha hecho click
				JLabel casillaActual = (JLabel) arg0.getComponent();
				idDestino = Integer.parseInt(casillaActual.getName());

				// Es un movimiento de una pieza
				if (casillaSeleccionada != null) {
					idOrigen = Integer.parseInt(casillaSeleccionada.getName());
					piezaOrigen = controlador.getPieza(idOrigen);
					
					if (controlador.getColorCasilla(idOrigen) == COLOR.BLANCO) {
						casillaSeleccionada.setBackground(BLANCO);
					} else {
						casillaSeleccionada.setBackground(NEGRO);
					}	
					
					piezaDestino = controlador.getPieza(idDestino);							
					if(piezaDestino != null && piezaOrigen != null && piezaDestino.getColor() == piezaOrigen.getColor()) {
						
						if(posiblesMovimiento(idDestino) > 0 ) {
							casillaSeleccionada = casillaActual;
							casillaSeleccionada.setBackground(SELECCIONADA);
						}else {
							casillaSeleccionada = null;
						}
						
					} else {

						// Mover pieza
						if (controlador.mover(idOrigen, idDestino)) {
							casillaSeleccionada.setIcon(null);
							piezaDestino = controlador.getPieza(idDestino);
							ponerFicha(casillaActual, getNombrePieza(piezaDestino), 0);

							Estado estado = null;
							try {
							estado = controlador.moverAgente();
							
//							casillaActual = casillas.get(estado.getIdDestino());
//							piezaDestino = controlador.getPieza(estado.getIdDestino());
//							ponerFicha(casillaActual, getNombrePieza(piezaDestino), 0);
							
							}catch(Exception e) {
								e.printStackTrace();
								System.out.println();
							}	
							
//							if(estado != null) {
//								casillaActual = casillas.get(estado.getIdOrigen());
//								piezaDestino = controlador.getPieza(estado.getIdDestino());
//								ponerFicha(casillaActual, getNombrePieza(piezaDestino), 0);
//								
//							}

						}

						casillaSeleccionada = null;
//						eliminarCasillasPosibles(idDestino);
						
						pintarTablero();
					}
					
					
				} else {
					// Seleccion de una pieza
					if (controlador.isCasillaOcupada(idDestino)) {
						if (posiblesMovimiento(idDestino) > 0) {
							casillaSeleccionada = casillaActual;
							casillaSeleccionada.setBackground(SELECCIONADA);
						}
						
					}
				}

				
				
			}
		};
	}
	
	
	
	
	private int posiblesMovimiento(int idCasilla) {
		final int T = 255;
		
		eliminarCasillasPosibles(idCasilla);
		
		casillasPosible = controlador.getOpcionesMover(idCasilla);
		for(int id: casillasPosible) {
			JLabel casilla = casillas.get(id);
			Color color = (controlador.getColorCasilla(id) == COLOR.BLANCO)? BLANCO:NEGRO;
//			color = new Color(color.getRed() * POSIBLE.getRed() / T,
//					color.getGreen() * POSIBLE.getGreen() / T,
//					color.getBlue() * POSIBLE.getBlue() / T
//					);
			color = new Color(BLANCO.getRed() * POSIBLE.getRed() / T,
					BLANCO.getGreen() * POSIBLE.getGreen() / T,
					BLANCO.getBlue() * POSIBLE.getBlue() / T
					);
			casilla.setBackground((controlador.getPieza(idCasilla).getColor() == COLOR.BLANCO)? Color.WHITE:Color.BLACK);
			casilla.setBorder(BorderFactory.createMatteBorder(
                    5,5,5,5, (controlador.getColorCasilla(id) == COLOR.BLANCO)? BLANCO:NEGRO));
			

			


		}
		
		return casillasPosible.size();
	}

	private void eliminarCasillasPosibles(int idCasilla) {
		JLabel casilla;
		if(!casillasPosible.isEmpty()) {
			for(int id: casillasPosible) {
				casilla = casillas.get(id);
				Color color = (controlador.getColorCasilla(id) == COLOR.BLANCO)? BLANCO:NEGRO;
				casilla.setBackground(color);
				casilla.setBorder(null);
			}
		}
		
		casillasPosible.clear();
	}
	
	
	public DIRECCION getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(DIRECCION orientacion) {
		this.orientacion = orientacion;
	}
	
	
	
}
