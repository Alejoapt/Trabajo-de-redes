package logic;

public class Jugador {

	private int coordenadaX;
	
	private int coordenadaY;
	
	private String rutaIni;
	
	private String nombre;
	
	public Jugador(int x, int y, String pNombre) {
		coordenadaX = x;
		
		coordenadaY = y;
		
		nombre = pNombre;
	}

	public Jugador(String pNombre) {
		setNombre(pNombre);

		coordenadaX = 0;
		
		coordenadaY = 0;
		
		rutaIni = "";
	}

	public int getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(int coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public int getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public String getRutaIni() {
		return rutaIni;
	}

	public void setRutaIni(String rutaIni) {
		this.rutaIni = rutaIni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
