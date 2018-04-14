/** 
 * 
 */
package server;

import java.net.*;
import java.util.*;
import java.io.*;

import model.Joueur;

/**
 * @author alassane
 *
 */

public class TCPServer {

	// liste des joueurs connectés au serveur
	static List<ContexteJoueur> lesJoueurs = new ArrayList<ContexteJoueur>();

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

	// ajouter un joueur à la liste des joueurs connectés au serveur
	public static void addJoueur(ContexteJoueur cj) {
		lesJoueurs.add(cj);
		nbJoueur++;
	}

	public static void deleteJoueur(Socket so) {
		lesJoueurs.remove(so);
	}

	public static void updateJoueur(ContexteJoueur cj) {
		for (ContexteJoueur contexte : lesJoueurs) {
			if (contexte.equals(cj)) {
				contexte.replace(cj);
				break;
			}
		}
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

	// Envoyer à tous les joueurs
	public static void sendToAll(String response) {
		for (ContexteJoueur joueur : lesJoueurs) {
			sendResponse(joueur.joueur.so, response);
		}
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
