/** 
 * 
 */
// Thread d'envoie de message

package server;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author alassane
 *
 */

public class ServerSend extends Thread {

	private Socket client;

	PrintWriter out = null;

	String response;

	public ServerSend(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {

		try {
			// client.getOutputStream() le flux sur lequel les donnees peuvent
			// etre envoyees du server au client true pour vider le buffer
			out = new PrintWriter(client.getOutputStream(), true);
			response = "message de test";

			out.println(response); // envoyer une donnee sur le reseau

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}
}
