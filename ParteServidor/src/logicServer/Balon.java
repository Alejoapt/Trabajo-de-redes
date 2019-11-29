package logicServer;

public class Balon {
	private int coordenadaX;
	
	private int coordenadaY;
	
	private int disfraz;
	
	private String rutaIni;
	
	public Balon() {
		coordenadaX = 250;
		
		coordenadaY = 270;
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

	public int getDisfraz() {
		return disfraz;
	}

	public void setDisfraz(int disfraz) {
		this.disfraz = disfraz;
	}

	public String getRutaIni() {
		return rutaIni;
	}

	public void setRutaIni(String rutaIni) {
		this.rutaIni = rutaIni;
	}
}
