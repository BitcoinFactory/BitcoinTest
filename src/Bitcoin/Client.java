package Bitcoin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
 
	public static void main (String[] args) throws IOException{
	
		int port = 62002;
	
		InetAddress ip = InetAddress.getByName("127.0.0.1");	// Je récupère l'adresse IP du serveur
		int i = 1000;
	
		Socket sClient = new Socket (ip, port);					// J'ouvre la connexion entre client et serveur
	
		for (i = 0 ; i <= 10 ; i++){						
			
			String str = Integer.toString(i);					// Je transforme mon chiffre en chaine de caractère
			
			sClient.getOutputStream().write((str+'\n').getBytes());	// Je l'envoie au serveur
		}
		
		sClient.close();
	
	}
	
}
