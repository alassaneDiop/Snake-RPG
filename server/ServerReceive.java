package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author ALASSANE
 */
public class ServerReceive extends Thread {

	Socket client;
	BufferedReader entree;
	ContexteJoueur contexte;
	String response, pseudo;
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
		String commande, argument = null;

		if (requete.contains(" ")) {
			commande = requete.split(" ")[0];
			argument = requete.split(" ")[1];
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

		// System.out.println("Commande : " + commande + " argument : " +
		// argument+ " Pseudo " + pseudo);

		switch (commande) {

		case "/join":
			response = contexte.connecter(argument);
			break;

		case "/listJoueur":
			response = contexte.listeJoueur();
			break;

		case "/listGame":
			response = contexte.listePartie();
			break;

		case "/joinGame":
			response = contexte.rejoindrePartie(Integer.parseInt(argument));
			break;

		case "/newGame":
			response = contexte.creerPartie(argument);
			// ModeleDuJeu modele = new ModeleDuJeu();
			// modele.setMessage("Message de test");
			// try {
			// ObjectOutputStream outputStream = new ObjectOutputStream(
			// client.getOutputStream());
			// outputStream.writeObject(modele);
			// } catch (IOException e1) {
			// e1.printStackTrace();
			// }

			//
			break;

		case "/startGame":
			response = contexte.startGame(pseudo);
			break;

		case "/restartGame":
			response = contexte.restartGame(pseudo);
			break;

		case "/endGame":
			response = contexte.endGame(pseudo);
			break;

		case "/turn":
			response = contexte.deplacer(pseudo, argument);
			// TCPServer
			// .sendToPartieManager(TCPServer.lesParties.get(0), response);
			// TCPServer.sendToPartie(TCPServer.lesParties.get(0), response);
			response = null;
			break;

		case "/disconnect":
			contexte.deconnecter();
			break;

		case "/quit":
			try {
				contexte.quitter();
				entree.close();
				client.close();
				// this.interrupt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			response = "Commande inconnue";
			break;
		}

		System.out.println("Requete du joueur " + requete);

		if (response != null) {
			try {
				out = new PrintWriter(client.getOutputStream(), true);
				out.println(response); // envoyer une donnee sur le reseau
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		try {
			// initialise le buffer pour les flux entrant du reseau
			entree = new BufferedReader(new InputStreamReader(
					client.getInputStream())); // client.getInputStream() le
			while (true) {

				String commande = entree.readLine(); // lire le pseudo du client

				if (commande != null) {

					gestionCommande(commande);
					// while (true) {
					// ServerResponse answer = new ServerResponse(this, client);
					// answer.start();

					// response = message;
					// setResponse();

				} else
					break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
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
