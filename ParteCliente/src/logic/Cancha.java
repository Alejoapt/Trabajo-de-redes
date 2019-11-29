package logic;

public class Cancha {
	
	private Jugador jugador1;
	
	private Jugador jugador2;
	
	private boolean balonAtrapado;
	
	private boolean tiempoAcabado;
	
//	private Time tiempoInicio;
	
	private Balon balon;
	
	public Cancha(String juga1, String juga2) {
		jugador1 = new Jugador(180, 220, juga1);
		jugador2 = new Jugador(580, 220, juga2);
		balonAtrapado = true;
		tiempoAcabado = false;
		balon = new Balon();
	}
	
	public Cancha() {
		
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	public boolean isBalonAtrapado() {
		return balonAtrapado;
	}

	public void setBalonAtrapado(boolean balonAtrapado) {
		this.balonAtrapado = balonAtrapado;
	}

	public boolean isTiempoAcabado() {
		return tiempoAcabado;
	}

	public void setTiempoAcabado(boolean tiempoAcabado) {
		this.tiempoAcabado = tiempoAcabado;
	}

//	public Time getTiempoInicio() {
//		return tiempoInicio;
//	}
//
//	public void setTiempoInicio(Time tiempoInicio) {
//		this.tiempoInicio = tiempoInicio;
//	}

	public Balon getBalon() {
		return balon;
	}

	public void setBalon(Balon balon) {
		this.balon = balon;
	}

}
