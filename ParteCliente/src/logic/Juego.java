package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import gui.Principal;

public class Juego {
	
	private Cancha cancha;
	
	private Socket socket;
	
	private String userName;
	
	private String time;
	
	private int golesJ1;
	
	private int golesJ2;
	
	private DataInputStream imputStream;
	
	private DataOutputStream OutputStream;
	
	private HiloCliente hilo;
	
	private String[] golesTiempo;
	
	private boolean enJuego;

	private boolean espera;

	private boolean ready;

	public Juego(String nombre, Principal prin) throws UnknownHostException, IOException {
		cancha = new Cancha(nombre,"");
		enJuego = true;
		setUserName(nombre);
		golesTiempo = new String[2];
		@SuppressWarnings("resource")
		Socket actu = new Socket("localhost", 7000);
		DataInputStream tempi = new DataInputStream(actu.getInputStream());
		String temp = tempi.readUTF();
		String [] puertoyresto = temp.split(",");
		int port = Integer.parseInt(puertoyresto[0]);
		System.out.println(port);
		int rest = Integer.parseInt(puertoyresto[1]);
		System.out.println(rest);
		System.out.println(port);
		socket = new Socket("localhost", port);
		System.out.println("nuevo socket");
		OutputStream = new DataOutputStream(socket.getOutputStream());
		OutputStream.writeUTF(nombre);
		imputStream = new DataInputStream(socket.getInputStream());
		time = "";
		ready = false;
		hilo = new HiloCliente(prin, this, imputStream, port, (port-rest)*5, rest);
		System.out.println("juego creado");
	}

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	public void moverJugador(int dire) throws IOException {
		if(enJuego && !espera)
			OutputStream.writeUTF(dire+","+userName);
	}
	
	public void moverBalon(int dire, int angle) {
		try {
			OutputStream.writeUTF(dire + "," + angle);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataInputStream getSocket() throws IOException {
		return new DataInputStream(socket.getInputStream());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DataInputStream getImputStream() {
		return imputStream;
	}

	public void inicialHilo() {
		hilo.start();
	}
	
	public void setImputStream(DataInputStream imputStream) {
		this.imputStream = imputStream;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public DataOutputStream getOutputStream() {
		return OutputStream;
	}

	public void setOutputStream(DataOutputStream outputStream) {
		OutputStream = outputStream;
	}
	
	public boolean isEnJuego() {
		return enJuego;
	}

	public void setEnJuego(boolean enJuego) {
		this.enJuego = enJuego;
	}

	public String[] darCantidadGolesPorTime() {
		String [] temp1 = golesTiempo[0].split(",");
		String [] temp2 = golesTiempo[1].split(",");
		String [] mensaje = new String[7];
		mensaje [0] = "goles Primer Tiempo j1: "+ temp1[0];
		mensaje [1] = "goles Primer Tiempo j2: "+ temp1[1];
		mensaje [2] = "goles Segundo Tiempo j1: "+ temp2[0];
		mensaje [3] = "goles Segundo Tiempo j2: "+ temp2[1];
		mensaje [4] = "total goles j1: "+golesJ1;
		mensaje [5] = "total goles j2: "+golesJ2;
		if(golesJ1 > golesJ2) {
			mensaje[6] = "el ganador es el jugador 1";
		}
		if(golesJ1 < golesJ2) {
			mensaje[6] = "el ganador es el jugador 1";
		}
		if(golesJ1 == golesJ2) {
			mensaje[6] = "hubo un empate";
		}
		return mensaje;
	}

	public String[] getGolesTiempo() {
		return golesTiempo;
	}

	public void setGolesTiempo(String[] golesTiempo) {
		this.golesTiempo = golesTiempo;
	}

	public void setGolesTiempo(int i) {
		if(i==0) {
			golesTiempo[i]=golesJ1+","+golesJ2;
		}else {
			String [] tempi =  golesTiempo[0].split(",");
			golesTiempo[i]=(golesJ1-Integer.parseInt(tempi[0]))+","+(golesJ2-Integer.parseInt(tempi[1]));
		}
				
	}
	
	public boolean isEspera() {
		return espera;
	}

	public void setEspera(boolean espera) {
		this.espera = espera;
	}

	public void setReady(boolean espera) {
		this.ready = espera;
	}
	
	public boolean isReady() {
		return ready;
	}
}
