package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import Gestor.Controlador;
import Juego.Tablero;
import interfaz.Juego.DIRECCION;

import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;

public class GUI {

	private JFrame frmAjedrez;

	private static Juego juego = null;
	private static JPanel jpTablero;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Inicio del ajedrez");
					GUI window = new GUI();
					window.frmAjedrez.setVisible(true);
					
					 Controlador gestor = new Controlador();
					juego = new Juego(gestor, jpTablero);
					
					JPanel panel = new JPanel();
					panel.setBackground(new Color(102, 205, 170));
					window.frmAjedrez.getContentPane().add(panel, BorderLayout.EAST);
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		frmAjedrez = new JFrame();
		frmAjedrez.setTitle("Ajedrez");
		frmAjedrez.setBounds(100, 100, 521, 516);
		frmAjedrez.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAjedrez.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		panel.setForeground(Color.YELLOW);
		frmAjedrez.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel jpControles = new JPanel();
		jpControles.setBackground(Color.RED);
		frmAjedrez.getContentPane().add(jpControles, BorderLayout.SOUTH);
		
		lblNewLabel = new JLabel("Revertir");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				juego.revertir();	// CPU
				juego.revertir();	// Jugador
				jpTablero.repaint();
				
			}
		});
		
		lblNewLabel.setForeground(new Color(152, 251, 152));
		jpControles.add(lblNewLabel);
		frmAjedrez.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(juego != null) {
					switch(e.getKeyCode()) {
					case 37: // LEFT
						juego.setOrientacion(DIRECCION.IZQUIERDA);
						break;
					case 38: // UP
						juego.setOrientacion(DIRECCION.ARRIBA);
						break;
					case 39: // RIGHT
						juego.setOrientacion(DIRECCION.DERECHA);
						break;
					case 40: // DOWN
						juego.setOrientacion(DIRECCION.ABAJO);
						break;
					}
					
					juego.pintarTablero();
					jpTablero.repaint();
				}
			}
		});
		
		jpTablero = new JPanel();
		jpTablero.setBackground(new Color(255, 255, 255));
		frmAjedrez.getContentPane().add(jpTablero, BorderLayout.CENTER);
		jpTablero.setLayout(null);
		jpTablero.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				juego.pintarTablero();
			}
		});
		

		
		
		
		
//		JLabel lblNewLabel = new JLabel("");
//		lblNewLabel.setSize(new Dimension(3, 0));
//		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//		lblNewLabel.setBounds(53, 60, 242, 230);
//		lblNewLabel.setIconTextGap(1);
//		lblNewLabel.setOpaque(true);
//		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\JNBN007\\Desktop\\workspace\\JuegoDeMesa\\resources\\img\\pieza.png"));
//		lblNewLabel.setBackground(new Color(220, 220, 220));
//		jpTablero.add(lblNewLabel);
		
		

	}
}
