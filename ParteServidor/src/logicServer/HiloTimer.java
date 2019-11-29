package logicServer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class HiloTimer extends Thread {
	private int nuMin = 0; 
	private int nuSeg=0; 
	private  int nuHora=0; 
	public static boolean ejecutable = false;
	private String tiempo = "";
	private DataOutputStream in1;
	private DataOutputStream in2;
	private mandarMen mandar;
	private int asignable;
    public HiloTimer(DataOutputStream pIn1, DataOutputStream pIn2, HiloCliente hili, HiloCliente hili2, int port){ 
    	in1 = pIn1;
    	in2 = pIn2;
    	asignable = port;
    	mandar = new mandarMen(asignable);
    }
    public void run() {
        try {
            for (int i = 0; i < 1000; i++){           
               if(nuSeg!=59) {
                   nuSeg++;                                 
                }else{
                    if(nuMin!=59){
                        nuSeg=0;
                        nuMin++;
                    }else{
                            nuHora++;
                            nuMin=0;
                            nuSeg=0;          
                    }
                }  
               
               if((nuMin == 1  && nuSeg!=0)) {
                 	System.out.println("Fin Del Primer Tiempo");
                    in1.writeUTF("ayuda1");
                    in2.writeUTF("ayuda1");
                	mandar.start();
                 	sleep(10000);
                 	break;
                 	
               } else {
            	   tiempo = nuMin+":"+nuSeg+":"+ejecutable; 
                   in1.writeUTF(tiempo);
                   in2.writeUTF(tiempo);
                   System.out.println("llego1");
                   System.out.println("Tiempo Del Partido: " +nuHora+":"+nuMin+":"+nuSeg);//Muestro en pantalla el cronometro
                   System.out.println("funciona");
                   sleep(999);//Duermo el hilo durante 999 milisegundos(casi un segundo, quintandole el tiempo de proceso)
               }              
        }
            
            ejecutable = false;
            nuMin = 0;
            nuSeg = 0;
            //System.out.println("Tiempo Del Partido: " +nuHora+":"+nuMin+":"+nuSeg);//Muestro en pantalla el cronometro
            
            sleep(999);//Duermo el hilo durante 999 milisegundos(casi un segundo, quintandole el tiempo de proceso)
            for (int i = 0; i < 1000; i++){           
                if(nuSeg!=59) {
                    nuSeg++;                                 
                 }else{
                     if(nuMin!=59){
                         nuSeg=0;
                         nuMin++;
                     }else{
                             nuHora++;
                             nuMin=0;
                             nuSeg=0;          
                     }
                 }  
                
                if((nuMin == 1  &&nuSeg!=0)) {
                  	System.out.println("Fin Del Partido");
                  	
                    in1.writeUTF("ayuda2");
                    in2.writeUTF("ayuda2");
//                  	ejecutable = true;
//                    hilito.terminar();
//                    hilito2.terminar();
                  	break;
//                  	sleep(10000);
                } else {
             	   tiempo = nuMin+":"+nuSeg+":"+ejecutable; 
                    in1.writeUTF(tiempo);
                    in2.writeUTF(tiempo);
                    System.out.println("llego2");
                    System.out.println("Tiempo Del Partido: " +nuHora+":"+nuMin+":"+nuSeg);//Muestro en pantalla el cronometro
                    System.out.println("funciona");
                    sleep(999);//Duermo el hilo durante 999 milisegundos(casi un segundo, quintandole el tiempo de proceso)
                }
            }
//            tiempo = nuMin+":"+nuSeg+":"+ejecutable; 
//            in1.writeUTF(tiempo);
//            in2.writeUTF(tiempo);
        } catch (Exception ex) {
             System.out.println(ex.getMessage());
        } 
        
    }
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public DataOutputStream getIn1() {
		return in1;
	}
	public void setIn1(DataOutputStream in1) {
		this.in1 = in1;
	}
	
	public class mandarMen extends Thread{
		UdpClient udp;
		public mandarMen(int asig) {
			udp = new UdpClient(asig);
		}
		public void run() {
			udp.mandarMensajes();
		}
	}
	
	public class UdpClient {
		private DatagramSocket dg;
		private int asig;
		public UdpClient(int asignable){
			try {
				dg = new DatagramSocket();
				asig = asignable;
			} catch (SocketException e) {
				e.printStackTrace();
			}
			
		}
		
		public void mandarMensajes(){
			File file = new File("video.mp4");
			byte[] bytes = new byte[(int)file.length()];
			try {
				FileInputStream is = new FileInputStream(file);
				is.read(bytes);
				is.close();
				//System.out.println(bytes.length/20);
				for (int y = 0; y < 25; y++) {
					byte [] actu = new byte[bytes.length/25];
					for (int i = 0; i < actu.length; i++) {
						actu[i] = bytes[36830*y+i];
					}
					dg.send(new DatagramPacket(actu, actu.length, InetAddress.getByName("localhost"), asig));
					System.out.println(asig);
					dg.send(new DatagramPacket(actu, actu.length, InetAddress.getByName("localhost"), asig+1));
					//System.out.println("p"+y);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}
