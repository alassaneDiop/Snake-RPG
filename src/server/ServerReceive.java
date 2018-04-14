/** 
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author alassane
 */
public class ServerReceive extends Thread {

	Socket client;
	BufferedReader entree;
	ContexteJoueur contexte;
	String response;
	static String pseudo = null;
	TCPServer server;
	PrintWriter out;

	public ServerReceive() {

	}

	public ServerReceive(Socket client, ContexteJoueur contexte,
			TCPServer server) throws IOException {
		this.client = client;
		this.contexte = contexte;
		this.server = server;
	}

	public void gestionCommande(String requete) {
		String commande = null, argument = null;

		if (requete.contains(" ")) {
			try {
				commande = requete.split(" ")[0];
				argument = requete.split(" ")[1];
			} catch (Exception e) {
				// e.printStackTrace();
				argument = null;
			}

		} else {
			commande = requete;
		}

		// separer le pseudo du message du joueur
		if (commande.contains(":")) {
			pseudo = commande.split(":")[0]; // renseigner le pseudo du
												// joueur qui envoie le
												// message
			commande = commande.split(":")[1]; // renseigner le message du
												// joueur
		}

		switch (commande) {

		case "/join":
			String password = null;
			String pse = argument;
			if (argument != null && argument.contains("-")) {
				password = argument.split("-")[1];
				pse = argument.split("-")[0];
			}

			response = contexte.connecter(pse, password);
			break;

		case "/listJoueur":
			response = contexte.listeJoueur();
			break;

		case "/joinGame":
			response = contexte.rejoindreGame();
			break;

		case "/startGame":
			response = contexte.startGame(pseudo);
			if (!Partie.isRunning) {
				Partie.isRunning = true;
				try {
					(Partie.getInstance()).start(); // lancer le thread de la
													// partie
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			break;

		case "/restartGame":
			response = contexte.restartGame(pseudo);
			break;

		case "/endGame":
			response = contexte.endGame(pseudo);
			break;

		case "/turn":
			response = contexte.deplacer(pseudo, argument);
			response = null;
			break;

		case "/disconnect":
			contexte.deconnecter();
			break;

		case "/quit":
			contexte.quitter(pseudo);
			break;

		default:
			response = "Commande inconnue";
			break;
		}

		System.out.println("Requete du joueur " + requete);

		System.out.println("Réponse du server " + response);

		// Aprés chaque traitement de message de joueur, si réponse est
		// renseignée, transmettre la réponse au joueur
		if (response != null) {
			// if (pseudo == null)
			TCPServer.sendResponse(contexte.so, response);
			// else
			// TCPServer.sendToUnJoueur(pseudo, response);

			response = null;
		}

	}

	@Override
	public void run() {
		try {
			// initialise le buffer pour les flux entrant du reseau
			entree = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			while (true) {
				String commande = entree.readLine(); // lire la commande du
														// joueur

				if (commande != null) {
					gestionCommande(commande);
				}
				// else
				// break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(pseudo + " est parti");
		}
		// finally {
		// try {
		// this.buffer.close();
		// this.client.close();
		// } catch (Exception e2) {
		// e2.printStackTrace();
		// }
		// }
	}
}
