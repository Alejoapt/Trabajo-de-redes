package logicAtaque;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Ataque {
	private Socket socket;
	private DataOutputStream OutputStream;
	public Ataque()  throws UnknownHostException, IOException{
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
		OutputStream.writeUTF("soyUnAtacante");
		System.out.println("juego creado");
		
		atacar();
	}
	private void atacar() {
		for(int i = 0; i< 100; i++) {
			Thread temp = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							OutputStream.writeUTF("toma"+","+"pum");
							System.out.println("ataqué");
						} catch (IOException e) {
							
						}
					}
				}
			};
			temp.start();
		}
//		Thread temp = new Thread() {
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						OutputStream.writeUTF("toma"+","+"pum");
//						System.out.println("ataqué");
//					} catch (IOException e) {
//						
//					}
//				}
//			}
//		};
//		temp.start();
	}
	public static void main(String[] args) {
		try {
			Ataque ata = new Ataque();
		} catch (IOException e) {
		}
		
	}
}
