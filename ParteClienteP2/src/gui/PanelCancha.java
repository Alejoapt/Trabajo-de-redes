package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelCancha extends JPanel {

	private int posxJuga1;

	private int posxJuga2;

	private int posyJuga1;

	private int posyJuga2;

	private int posxBalon;

	private int posyBalon;

	private String time;

	private int marcador1;
	private int marcador2;

	private Principal principal;
	
	private int part;
	private int part2;

	public PanelCancha(Principal prin) {
		posxJuga1 = 180;
		posyJuga1 = 220;
		posxJuga2 = 580;
		posyJuga2 = 220;
		part2 = 0;
		time = "";
		setBounds(0, 0, 806, 610);
		posxBalon = 250;
		posyBalon = 268;
		principal = prin;
		part = 0;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (!principal.getMundo().isEnJuego() && part == 1) {
			g.setColor(Color.CYAN);
			g.drawRect(0, 0, 900, 900);
			g.setColor(Color.BLACK);
			String [] mensajes = principal.getMundo().darCantidadGolesPorTime();
			int actualX = 250;
			int actualY = 200;

			g.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
			g.drawString("Estadisticas", 290, actualY);
			actualY+=17;
			for (int i = 0; i < mensajes.length; i++) {
				g.drawString(mensajes[i], actualX, actualY);
				actualY+=17;
			}
		}else if (!principal.getMundo().isEnJuego() && part == 0) {
			g.setColor(Color.CYAN);
			g.drawRect(0, 0, 900, 900);
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 40));
			g.drawString("Fin del juego", 300, 250);
			part+=1;
		}else if (principal.getMundo().isEspera()) {
			g.setColor(Color.CYAN);
			g.drawRect(0, 0, 900, 900);
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 40));
			g.drawString("Fin primer tiempo", 300, 250);
		} else if(principal.getMundo().isReady() && part2 == 0){
			g.setColor(Color.CYAN);
			g.drawRect(0, 0, 900, 900);
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 40));
			g.drawString("ready", 300, 250);
			part2++;
		}else if(principal.getMundo().isReady() && part2 == 1){
			g.setColor(Color.CYAN);
			g.drawRect(0, 0, 900, 900);
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 40));
			g.drawString("go", 300, 250);
		}else {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image imagen = t.getImage("cancha.jpg");
			Image imagen2 = t.getImage("j1.png");
			Image imagen3 = t.getImage("j2.png");
			Image imagen4 = t.getImage("balon.png");
			g.drawImage(imagen, 0, 0, this);
			g.drawImage(imagen2, posxJuga1, posyJuga1, this);
			g.drawImage(imagen3, posxJuga2, posyJuga2, this);
			g.drawImage(imagen4, posxBalon, posyBalon, this);
			// Print el timer
//        g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 40));
			g.setColor(Color.CYAN);
			g.drawString(time, 560, 40);

			g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 40));
			g.drawString(marcador1 + "-" + marcador2, 371, 44);
		}
	}

	public void pintar() {
		posxJuga1 = principal.getMundo().getCancha().getJugador1().getCoordenadaX();
		posyJuga1 = principal.getMundo().getCancha().getJugador1().getCoordenadaY();
		posxJuga2 = principal.getMundo().getCancha().getJugador2().getCoordenadaX();
		posyJuga2 = principal.getMundo().getCancha().getJugador2().getCoordenadaY();
//		posxBalon = principal.getMundo().getCancha().getBalon().getCoordenadaX();
//		posyBalon = principal.getMundo().getCancha().getBalon().getCoordenadaY();

		time = principal.getMundo().getTime();
		marcador1 = principal.getMarcadorJ1();
		marcador2 = principal.getMarcadorJ2();

		repaint();
		validate();
	}
}
