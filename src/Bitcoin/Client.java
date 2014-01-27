package Bitcoin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main (String[] args) throws IOException{
	
		int port = 2000;
	
		InetAddress ip = InetAddress.getLocalHost();			// Je r�cup�re l'adresse IP locale
		int i;
	
		Socket sClient = new Socket (ip, port);					// J'ouvre la connexion entre client et serveur
	
		for (i = 0 ; i < 50000 ; i++){						
			
			String str = Integer.toString(i);					// Je transforme mon chiffre en chaine de caract�re
			
			sClient.getOutputStream().write(str.getBytes());	// Je l'envoie au serveur
		}
		
		sClient.close();
	
	}
	
}
