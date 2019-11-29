package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.Juego;

@SuppressWarnings("serial")
public class Principal extends JFrame implements KeyListener{

	private PanelCancha panelCancha;
		
	private Juego juego;
	
	private int marcadorJ1 = 0;
	private int marcadorJ2 = 0;
	
	public Principal(){
		panelCancha = new PanelCancha(this);
		add(panelCancha);
		setBounds(0,0,806,610);
		setResizable(false);
		addKeyListener(this);
		marcadorJ1 = 0;
		marcadorJ2 = 0;
	}


	public int getMarcadorJ1() {
		return marcadorJ1;
	}


	public void setMarcadorJ1(int marcadorJ1) {
		this.marcadorJ1 = marcadorJ1;
	}


	public int getMarcadorJ2() {
		return marcadorJ2;
	}


	public void setMarcadorJ2(int marcadorJ2) {
		this.marcadorJ2 = marcadorJ2;
	}


	public PanelCancha getPanelCancha() {
		return panelCancha;
	}

	public void setPanelCancha(PanelCancha panelCancha) {
		this.panelCancha = panelCancha;
	}

	public void activarConexion(String nombre) throws UnknownHostException, IOException {
		juego = new Juego(nombre, this);
	}

	public Juego getMundo() {
		return juego;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==37) {
			try {
				juego.moverJugador(1);
				System.out.println(1);
				repintar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getKeyCode()==38) {
			try {
				juego.moverJugador(2);
				System.out.println(2);
				repintar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getKeyCode()==39) {
			try {
				juego.moverJugador(3);
				System.out.println(3);
				repintar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}if(e.getKeyCode()==40) {
			try {
				juego.moverJugador(4);
				System.out.println(4);
				repintar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void repintar() {
		panelCancha.pintar();
		repaint();
		validate();
	}

	public void mostrarPrimera() {
		JOptionPane.showMessageDialog(this, "Fin primer Tiempo");
	}

	public void mostrarSegunda() {
//		String temp = "";
		JOptionPane.showMessageDialog(this, "Fin del partido");
		juego.darCantidadGolesPorTime();
	}


	public void esperando() {
		JOptionPane.showMessageDialog(this, "esperando Oponente");
	}

}
