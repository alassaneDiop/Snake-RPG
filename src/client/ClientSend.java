/** 
 * 
 */
package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author alassane
 *
 */
// Thread pour envoyer
class ClientSend extends Thread {

	BufferedReader buffer = null;
	Socket so = null;
	PrintWriter sortie = null;
	String chaine;

	public ClientSend(Socket so) {
		this.so = so;
	}

	public void run() {
		try {
			buffer = new BufferedReader(new InputStreamReader(System.in));
			sortie = new PrintWriter(so.getOutputStream(), true);

			while (!so.isClosed()) {

				// System.out.println(Joueur.getInstance(so).pseudo);

				chaine = buffer.readLine();
				sortie.println(Joueur.getInstance(so).pseudo + ":" + chaine);
				// on écrit la chaine dans le canal de sortie

				if (chaine.equals("/quit")) {
					buffer.close();
					sortie.close();
					so.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally {
		// try {
		// buffer.close();
		// sortie.close();
		// so.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
	}
}
