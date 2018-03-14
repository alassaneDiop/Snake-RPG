
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author ALASSANE
 */
public class Server_Send extends Thread {

	private Socket client;

	public Server_Send(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		// objet pour envoyer un flux d'ecriture sur le reseau
		PrintWriter out = null;
		String reponse = null;
		BufferedReader buffer = null;
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			// client.getOutputStream() le flux sur lequel les donnees peuvent etre envoyees
			// du server au client
			// true pour vider le buffer
			// initialise le buffer pour les flux entrant du reseau
			buffer = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				reponse = buffer.readLine(); // on recupere ce que l'utilisateur tape
				if (reponse.equalsIgnoreCase("fin")) {
					break;
				}
				out.println(reponse); // envoyer une donnee sur le reseau
				System.out.println("réponse");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		try {
			out.close();
			buffer.close();
			client.close(); // l'objet client doit etre le dernier a etre ferme
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
