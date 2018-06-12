package interfaz;

import Juego.Casilla;
import Juego.Ficha;
import Juego.Jugador;
import Juego.TIPO;

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

	private static final Color X = new Color(0, 255, 0);
	private static final Color O = new Color(255, 0, 0);

	private  int TAM_CASILLA, PADDING_X, PADDING_Y;
	private Controlador controlador;
	private JPanel jpTablero;
	private List<JLabel> casillas;
	
	public Juego(Controlador controlador, JPanel jpTablero) {
		this.controlador = controlador;
		this.jpTablero = jpTablero;		
		this.casillas = new ArrayList<JLabel>();


		controlador.moverAgente();
		
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
			for (int x = 0; x < Tablero.TAMANO; x++) {
				JLabel jCasilla = pintarCasilla(x, y);					
				jpTablero.add(jCasilla);	
				casillas.add(jCasilla);
			}
		}
				
		jpTablero.validate();
		jpTablero.repaint();

	}
	
	
	private JLabel pintarCasilla(int x, int y) {
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
		Color c =  NEGRO;	
		Ficha pieza = controlador.getPieza(x, y);
		if(pieza != null){
			if(pieza.getTipo() == TIPO.O) c = O;
			else c = X;
		}
			
		jCasilla.setBackground(c);
		jCasilla.setBorder(BorderFactory.createMatteBorder(5,5,5,5,  BLANCO));
		
		return jCasilla;
	}
	
	
	private void setBounds(int x, int y, JLabel jCasilla) {		
		int xPos = PADDING_X + x * TAM_CASILLA;
		int yPos = PADDING_Y + y * TAM_CASILLA; 
		jCasilla.setBounds(xPos, yPos, TAM_CASILLA, TAM_CASILLA);
	}
	
//	private ImageIcon ponerFicha(JLabel jLabel, String pieza, int borde){
//
//		ImageIcon imageIcon = new ImageIcon("C:\\Users\\JNBN007\\Desktop\\workspace\\JuegoDeMesa\\resources\\img\\"+pieza+".png"); // load the image to a imageIcon
//		Image image = imageIcon.getImage(); // transform it 
//	    Image newimg = image.getScaledInstance(jLabel.getWidth() + borde, jLabel.getHeight() + borde,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
//	    imageIcon = new ImageIcon(newimg);  // transform it back
//	    
//	    jLabel.setIcon(imageIcon);
//	    
//	    return imageIcon;
//	}


	
	public void revertir() {	
		Jugador player = controlador.getJugadorActual();
		controlador.revertir();
		
		if(player == controlador.getJugador1())
			controlador.revertir();
		
//		if(controlador.getTurno() <= 0 && controlador.isEmpiezaCPU()) 
//			controlador.moverAgente();
		
		pintarTablero();
	}
	
	
	private MouseAdapter eventClickCasilla() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				// Casilla en la que se ha hecho click
				JLabel casillaActual = (JLabel) arg0.getComponent();
				int idCasilla = Integer.parseInt(casillaActual.getName());
				
				controlador.ponerPieza(idCasilla);	
				if(controlador.quedanCasillas())
					controlador.moverAgente();				
				pintarTablero();

			}
		};
	}
	
}
