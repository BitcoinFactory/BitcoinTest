package Bitcoin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main (String[] args) throws IOException{
	
		int port = 2000;
		InetAddress ip = InetAddress.getLocalHost();
		
		try{
			Socket sClient = new Socket(ip, port);						// On ouvre une connexion du client au serveur
			
			System.out.println("ip");
			
			int i;
			
			for (i = 0 ; i < 10000 ; i++){								// Le client envoie des valeurs en continu au serveur
				String str = Integer.toString(i);
				
				sClient.getOutputStream().write(str.getBytes());  		// On envoie le nombre sous forme de char
			}
			
			sClient.close();
		}
		catch(IOException e){
			System.out.println("Vous ne pouvez pas vous connecter");
		}
		
		
		
	}
	

	
	
}
