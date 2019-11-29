package logic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import gui.Principal;
import logic.HiloCliente.HiloMensajes;

public class HiloCliente extends Thread{
	private Principal interfaz;
	
	private Juego juego;
	
	private DataInputStream out;
	
	private HiloMensajes mensajes;
	
	//private Reproduce reproduce;
	
	private int asignable;
	
	public HiloCliente(Principal inter, Juego jueg, DataInputStream outi, int port, int asignabl, int sen) {
		interfaz = inter;
		juego = jueg;
		out = outi;
		asignable = asignabl;
		if(sen == 1) {
			mensajes = new HiloMensajes(asignable);
			System.out.println(asignable);
		}else {
			mensajes = new HiloMensajes(asignable+1);
			System.out.println(asignable+1);
		}
		mensajes.start();
	}
	
	@Override
	public void run() {
		int temp1 = 0;
		while(true) {
			try {
				
			} catch (Exception e) {
			}
			String line = "";
			try {
				line = out.readUTF();
			} catch (Exception e) {
				
			}
			if(!line.equals("")) {
				try {
					String [] temp = line.split(",");
						if(temp.length==4) {
							System.out.println(temp[0]+","+temp[1]+","+temp[2]+","+temp[3]);
							juego.getCancha().getJugador1().setCoordenadaX(Integer.parseInt(temp[0]));
							juego.getCancha().getJugador1().setCoordenadaY(Integer.parseInt(temp[1]));
							juego.getCancha().getJugador2().setCoordenadaX(Integer.parseInt(temp[2]));
							juego.getCancha().getJugador2().setCoordenadaY(Integer.parseInt(temp[3]));
//							juego.getCancha().getBalon().setCoordenadaX(Integer.parseInt(temp[4]));
//							juego.getCancha().getBalon().setCoordenadaX(Integer.parseInt(temp[5]));

							interfaz.getMarcadorJ1();
							interfaz.getMarcadorJ2();
							interfaz.repintar();
						}else {
							temp = line.split(":");
							if(temp.length==3) {
								if(temp[2].equals("false")) {
									if(Integer.parseInt(temp[0])<1) {
										if(Integer.parseInt(temp[1])<10) {
											juego.setTime("TIME 00:0"+ temp[1]);
											interfaz.repintar();
										}else {
											juego.setTime("TIME 00:"+temp[1]);
											interfaz.repintar();
										}
									}else {
										juego.setTime("TIME 0"+ temp[0]+":0" +temp[1]);
										interfaz.repintar();
									}
								}
							}
							
						}
						if(line.equals("ayuda1")) {
							interfaz.repintar();
//							interfaz.mostrarPrimera();
							juego.setTime("TIME 00:00");
							juego.setGolesTiempo(0);
							juego.setEspera(true);
							interfaz.repintar();
							sleep(8900);
							juego.setEspera(false);
							juego.setReady(true);
							interfaz.repintar();
							sleep(550);
							juego.setReady(true);
							interfaz.repintar();
							sleep(550);
							juego.setReady(false);
							interfaz.repintar();
						}
						if(line.equals("ayuda2")) {
							juego.setTime("TIME 00:00");
							juego.setGolesTiempo(1);
							interfaz.repintar();
							//interfaz.mostrarSegunda();
							juego.setTime("TIME 00:00");
							juego.setEnJuego(false);
							interfaz.repintar();
							System.out.println("listo");
							interfaz.repintar();
							//Reproduce.launch();
							interfaz.repintar();
							sleep(4000);
							ProcessBuilder p=new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe","video.mp4");
							p.start();
							sleep(3000);
						}
						
						//Prueba de la puntuacion jajjaja saludos
						interfaz.setMarcadorJ1(temp1);	
						System.out.println("pintó");
					
				}catch (Exception e) {
					
				}
			}
		}
	}
	
	public class UdpServer {
		
		private DatagramSocket dg;
		private ArrayList<byte[]> video;
		private int actu;

		private byte[] videolisto;
		public UdpServer(int port){
			try {
				video = new ArrayList<byte[]>(); 
				dg = new DatagramSocket(port);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
		public void mandarMensajes() throws IOException{
			while (actu<25) {
				byte[] bytes = new byte[36830];
//				System.out.println("esperando....");
				dg.receive(new DatagramPacket(bytes, 36830));
				System.out.println(bytes[bytes.length-1]);
				video.add(bytes);
//				System.out.println(bytes.length);
				//System.out.println(bytes[bytes.length-1]);
				actu++;
//				System.out.println("p"+actu+" terminada");
			}
			System.out.println(actu);
			System.out.println("armado");
			videolisto = new byte[36830*25];
			for (int i = 0; i < video.size(); i++) {
				for (int j = 0; j < video.get(i).length; j++) {
					videolisto[36830*i+j] = video.get(i)[j];
				}
			}
			File file = new File("video.mp4");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(videolisto);
			fos.close();
//			System.out.println(videolisto.length+"="+46037*20);
		}
		
		public int getActu() {
			return actu;
		}

		public void setActu(int actu) {
			this.actu = actu;
		}
		
	}
	
	public class HiloMensajes extends Thread{
	
		private UdpServer recibir;
		
		public HiloMensajes(int port) {
			recibir = new UdpServer(port);
		}
	
		@Override
		public void run() {
			try {
				recibir.mandarMensajes();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
