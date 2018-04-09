package server;

import java.net.*;
import java.util.*;
import java.io.*;

public class TCPServer {

	static List<ContexteJoueur> lesJoueurs = new ArrayList<ContexteJoueur>();
	static List<Partie> lesParties = new ArrayList<Partie>();

	static int nbJoueur = 0;

	public static void main(String args[]) {
		int port;
		ServerSocket ecoute;
		Socket so = null;

		ContexteJoueur contexteJoueur;

		TCPServer server = new TCPServer();

		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);

				ecoute = new ServerSocket(port);
				System.out.println("Serveur en attente de connection ...");

				while (true) {
					so = ecoute.accept(); // accepter la connexion d'un client

					// contexteJoueur = new ContexteJoueur(server, so);
					contexteJoueur = new ContexteJoueur(so);

					ThreadEtat threadEtat = new ThreadEtat(so, contexteJoueur,
							server);
					threadEtat.start();

				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Listen :" + e.getMessage());
			}
		}
	}

	public static void addJoueur(ContexteJoueur cj) {
		lesJoueurs.add(cj);
		nbJoueur++;
	}

	public static void deleteJoueur(Socket so) {
		lesJoueurs.remove(so);
	}

	// tester si le pseudo est déja utilisé. si déjà utilisé renvoie true
	public static boolean checkPseudo(String pseudo) {
		for (ContexteJoueur cj : lesJoueurs) {
			try {
				if (cj.joueur.pseudo != null && cj.joueur.pseudo.equals(pseudo)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	// envoi d'un message
	public static void sendResponse(Socket so, String response) {
		try {
			PrintWriter out = new PrintWriter(so.getOutputStream(), true);
			out.println(response); // envoyer une donnee sur le reseau
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Envoyer à tous les joueurs d'une partie
	public static void sendToPartie(Partie partie, String response) {
		for (Joueur joueur : partie.lesJoueurs) {
			sendResponse(joueur.so, response);
		}
	}

	// Envoyer au patron (createur) d'une partie
	public static void sendToPartieManager(Partie partie, String response) {
		sendResponse(partie.lesJoueurs.get(0).so, response);
	}

	// Répondre à un joueur particulier
	public static void sendToUnJoueur(String pseudo, String response) {
		sendResponse(findJoueur(pseudo).so, response);
	}

	// trouver un joueur à partir d'un pseudo
	public static Joueur findJoueur(String pseudo) {

		for (ContexteJoueur j : lesJoueurs)
			if (j.joueur.pseudo.equals(pseudo))
				return j.joueur;

		return null;
	}

}
