package logicServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Servidor {

	// private ArrayList<Cancha> canchas;
	private Cancha cancha;
	private ArrayList<Socket> sockets;
	private ArrayList<ServerSocket> ssockets;
	private int actualPort;
	private ArrayList<ArrayList<HiloCliente>> hiloCliente;
	private ArrayList<HiloTimer> hiloTimer;
	private HiloDetinaSockets hiloSocj;
	private HiloPendiente hiloPendiente;
	private HiloServidorWeb serverWeb;
	
	public Servidor() throws IOException, InterruptedException {
		actualPort = 4001;
		sockets = new ArrayList<Socket>();
		hiloSocj = new HiloDetinaSockets();
		hiloSocj.start();
		ssockets = new ArrayList<ServerSocket>();
		hiloCliente = new ArrayList<ArrayList<HiloCliente>>();
		hiloTimer = new ArrayList<HiloTimer>();
		serverWeb = new HiloServidorWeb();
		serverWeb.start();
		hiloPendiente = new HiloPendiente(this);
		hiloPendiente.start();
	}

	public void serverRun() {
		while (true) {
			try {
				ServerSocket ss1 = new ServerSocket(actualPort);
				ServerSocket ss2 = new ServerSocket(actualPort+1);
				Socket acepte1;
				Socket acepte2;
				acepte1 = ss1.accept();
				actualPort++;
				DataInputStream temp = new DataInputStream(acepte1.getInputStream());
				String juga1 = temp.readUTF();
				System.out.println(juga1);
				acepte2 = ss2.accept();
				actualPort++;
				DataInputStream temp2 = new DataInputStream(acepte2.getInputStream());
				String juga2 = temp2.readUTF();
				System.out.println(juga2);
				DataOutputStream tempi = new DataOutputStream(acepte1.getOutputStream());
				tempi.writeUTF("es hora");
				DataOutputStream tempi2 = new DataOutputStream(acepte2.getOutputStream());
				tempi2.writeUTF("es hora");
				cancha = new Cancha("", juga1, juga2);
				HiloCliente hilo1 = new HiloCliente(tempi, temp, tempi2, this, cancha);
				HiloCliente hilo2 = new HiloCliente(tempi, temp2, tempi2, this, cancha);
				System.out.println(actualPort);
				HiloTimer hilo3 = new HiloTimer(tempi, tempi2, hilo1, hilo2, (actualPort-3)*5);
//				HiloTimer hilo4 = new HiloTimer(tempi, tempi2, hilo1, hilo2, actualPort--);
				hilo1.start();
				hilo2.start();
				hilo3.start();
//				hilo4.start();
				ArrayList<HiloCliente> tempito = new ArrayList<HiloCliente>();
				tempito.add(hilo1);
				tempito.add(hilo2);
				sockets.add(acepte1);
				sockets.add(acepte2);
				ssockets.add(ss1);
				ssockets.add(ss2);
				hiloCliente.add(tempito);
				hiloTimer.add(hilo3);
			} catch (Exception e) {

			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		try {
			Servidor server = new Servidor();
			server.serverRun();
		} catch (Exception e) {
			
		}
		
	}
	
	public class HiloPendiente extends Thread{
	
		private int ataques;
		private Servidor serv;
		
		public HiloPendiente(Servidor ser) {
			ataques = 0;
			serv = ser;
		}
		@Override
		public void run() {
			while(true) {
				ataques = serv.getAtaques();
				System.out.println(ataques);
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public class HiloServidorWeb extends Thread{
		JavaHTTPServer myServer;		
		
		@Override
		public void run() {
			try {
				ServerSocket serverConnect = new ServerSocket(8080);
				System.out.println("Server started.\nListening for connections on port : " + 8080 + " ...\n");
				
				while (true) {
					myServer = new JavaHTTPServer(serverConnect.accept());
					
					if (myServer.isVerbose()) {
						System.out.println("Connecton opened. (" + new Date() + ")");
					}
					
					Thread thread = new Thread(myServer);
					thread.start();
				}
				
			} catch (IOException e) {
				System.err.println("Server Connection error : " + e.getMessage());
			}
		}
	}
	
	public class HiloDetinaSockets extends Thread{
		private ServerSocket ss;
		private Socket s;
		
		public HiloDetinaSockets() {
			
		}
		
		@Override
		public void run() {
			while(true) {

				try {
					ss = new ServerSocket(7000);
					s = ss.accept();
					DataOutputStream data = new DataOutputStream(s.getOutputStream());
					String temp = "";
					temp+=actualPort+",";
					if (actualPort%2 == 0) {
						temp+=2;
						System.out.println(2);
					}else {
						System.out.println(1);
						temp+=1;
					}
					data.writeUTF(temp);
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getAtaques() {
		int retorno = 0;
		for (int i = 0; i < hiloCliente.size(); i++) {
			retorno += hiloCliente.get(i).get(0).getPosiblesAtaques();
			retorno += hiloCliente.get(i).get(1).getPosiblesAtaques();
		}
		return retorno;
	}
}
