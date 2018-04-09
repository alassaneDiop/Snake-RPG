package client;

// TCPClient.java  
// A client program implementing TCP socket
import java.net.*;
import java.io.*;

class TCPClient {

	public static void main(String args[]) { // arguments supply message and
												// hostname of destination
		Socket so = null;
		String serveur; // le serveur
		int port; // le port de connexion

		if (args.length == 2) {
			serveur = args[0];
			port = Integer.parseInt(args[1]);

			try {
				so = new Socket(serveur, port);

				Joueur.getInstance(so); // instancier le joueur
				ClientSend sending = new ClientSend(so);
				ClientReceive receiving = new ClientReceive(so);

				sending.start();
				receiving.start();

			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("Sock:" + e.getMessage());
			} catch (EOFException e) {
				e.printStackTrace();
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IO:" + e.getMessage());
			}
		}
	}

	public static void sendToServer(String message) {
		try {

			// System.out.println("Message à envoyer");
			PrintWriter sortie = new PrintWriter(
					Joueur.getInstance().so.getOutputStream(), true);
			sortie.println(Joueur.getInstance().pseudo + ":" + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
