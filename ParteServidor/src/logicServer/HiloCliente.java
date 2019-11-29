package logicServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class HiloCliente extends Thread{
	
	private DataOutputStream entrada1;
	
	private DataOutputStream entrada2;
	
	private DataInputStream salida1;
	
	private int posiblesAtaques;
	
	public int getPosiblesAtaques() {
		return posiblesAtaques;
	}

	public void setPosiblesAtaques(int posiblesAtaques) {
		this.posiblesAtaques = posiblesAtaques;
	}

	private int acciones;
	
	private int crono;
	
	private Servidor server;
	
	private boolean enJuego;
	
	private Cancha cancha;
	
	private reloj reloj;
	
	public HiloCliente(DataOutputStream in1, DataInputStream out1, DataOutputStream in2, Servidor ser, Cancha pCancha) {
		entrada1 = in1;
		salida1 = out1;
		setEntrada2(in2);
		enJuego = true;
		setServer(ser);
		cancha = pCancha;
		acciones = 0;
		posiblesAtaques = 0;
		reloj = new reloj(this);
		reloj.start();
	}
	
	@Override
	public void run() {
		while(enJuego) {
			try {
				String linea = salida1.readUTF();
				if(!linea.equals("")) {
					String [] spliti = linea.split(",");
					if(spliti.length==2) {
						if(acciones<10) {
							System.out.println(linea);
							moverJuga(linea);
							entrada1.writeUTF(cancha.getJugador1().getCoordenadaX()+","+ cancha.getJugador1().getCoordenadaY()+
									","+cancha.getJugador2().getCoordenadaX()+","+cancha.getJugador2().getCoordenadaY());
							entrada2.writeUTF(cancha.getJugador1().getCoordenadaX()+","+ cancha.getJugador1().getCoordenadaY()+
									","+cancha.getJugador2().getCoordenadaX()+","+cancha.getJugador2().getCoordenadaY());
							System.out.println("envió");
							acciones++;
						}else {
							if(crono<3) {
								crono = 0;
								posiblesAtaques++;
							}else {
								acciones = 0;
								sleep(999);
							}
						}
					}else{
						if(crono<3) {
							crono = 0;
							posiblesAtaques++;
						}
					}
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	public void moverJuga(String temp) {
		String [] actu = temp.split(",");
		if (cancha.getJugador1().getNombre().equals(actu[1])) {
			if(actu[0].equals("1")) {
//				cancha.getBalon().setCoordenadaX(cancha.getBalon().getCoordenadaX() - 10);
				if(cancha.getJugador1().getCoordenadaX()-10>0) {
					cancha.getJugador1().setCoordenadaX(cancha.getJugador1().getCoordenadaX()-10);
					System.out.println("movió1"+1);
				}
			}else if(actu[0].equals("2")) {
//				cancha.getBalon().setCoordenadaY(cancha.getBalon().getCoordenadaY() - 10);
				if(cancha.getJugador1().getCoordenadaY()-10>=0) {
					cancha.getJugador1().setCoordenadaY(cancha.getJugador1().getCoordenadaY()-10);
					System.out.println("movió1"+2);
				}
			}else if(actu[0].equals("3")) {
				if(cancha.getJugador1().getCoordenadaX()+10<320) {
					cancha.getJugador1().setCoordenadaX(cancha.getJugador1().getCoordenadaX()+10);
					System.out.println("movió1"+3);
				}
			}else if(actu[0].equals("4")) {
				if(cancha.getJugador1().getCoordenadaY()+10<500) {
					cancha.getJugador1().setCoordenadaY(cancha.getJugador1().getCoordenadaY()+10);
					System.out.println("movió1"+4);
				}
			}
		}else if (cancha.getJugador2().getNombre().equals(actu[1])) {
			if(actu[0].equals("1")) {
//				cancha.getBalon().setCoordenadaX(cancha.getBalon().getCoordenadaX() - 10);
				if(cancha.getJugador2().getCoordenadaX()-10>380) {
					cancha.getJugador2().setCoordenadaX(cancha.getJugador2().getCoordenadaX()-10);
					System.out.println("movió2"+1);
				}
			}else if(actu[0].equals("2")) {
//				cancha.getBalon().setCoordenadaY(cancha.getBalon().getCoordenadaY() - 10);
				if(cancha.getJugador2().getCoordenadaY()-10>=0) {
					cancha.getJugador2().setCoordenadaY(cancha.getJugador2().getCoordenadaY()-10);
					System.out.println("movió2"+2);
				}
			}else if(actu[0].equals("3")) {
				if(cancha.getJugador2().getCoordenadaX()+10<700) {
					cancha.getJugador2().setCoordenadaX(cancha.getJugador2().getCoordenadaX()+10);
					System.out.println("movió2"+3);
				}
			}else if(actu[0].equals("4")) {
				if(cancha.getJugador2().getCoordenadaY()+10<500) {
					cancha.getJugador2().setCoordenadaY(cancha.getJugador2().getCoordenadaY()+10);
					System.out.println("movió2"+4);
				}
			}
		}
	}

	public DataOutputStream getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(DataOutputStream entrada2) {
		this.entrada2 = entrada2;
	}

	public Servidor getServer() {
		return server;
	}

	public void setServer(Servidor server) {
		this.server = server;
	}

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	public void dormir() {
		try {
			sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void terminar() {
		interrupt();
	}
	
	public class reloj extends Thread{
		private HiloCliente hilo;
		
		public reloj(HiloCliente kil) {
			hilo = kil;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				hilo.crono +=1;
				try {
					sleep(999);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
