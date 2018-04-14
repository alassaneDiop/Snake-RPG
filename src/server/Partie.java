/**  
 * 
 */
package server;

import java.util.ArrayList;

import model.Joueur;
import dao.DAOFactory;
import dao.JoueurDaoImpl;

/**
 * @author alassane
 *
 */
public class Partie extends Thread {

	// liste des joueurs qui sont déjà dans la partie de jeu
	ArrayList<Joueur> lesJoueurs;

	private static volatile Partie instance;

	volatile static boolean isRunning = false;
	volatile static boolean running = true;

	private Partie() {
		lesJoueurs = new ArrayList<Joueur>();
	}

	public static Partie getInstance() {
		if (instance == null) {
			instance = new Partie();
		}
		return instance;
	}

	public void joinPartie(Joueur joueur) {
		if (!lesJoueurs.contains(joueur))
			lesJoueurs.add(joueur);
	}

	public void quitPartie(Joueur joueur) {
		lesJoueurs.remove(joueur);
	}

	public String toString() {
		String liste = "Liste des joueurs de la partie ";

		int i = 1;
		for (Joueur joueur : lesJoueurs) {
			liste += "\n\t\tJoueur " + i + " => " + joueur.pseudo;
			i++;
		}

		return liste;
	}

	@Override
	public void run() {
		/*
		 * à chaque fois que la boucle est exécutée, la méthode de calcul du jeu
		 * est appelée. Comme la boucle est infinie, la méthode de calcul sera
		 * appelée en cycle perpétuel.
		 */
		while (running) {

			if (ModeleDuJeu.getInstance().lesSerpents.isEmpty()) {
				running = false;
				isRunning = false;
			} else {
				// System.out.println("La partie est en cours");
				ModeleDuJeu.getInstance().calcul();
				// System.out.println("Message à envoyer : "
				// + ModeleDuJeu.getInstance().map());

				// envoyer la nouvelle map, les nouvelles positions parsées de
				// tous les joueurs
				TCPServer.sendToAll("turn:" + ModeleDuJeu.getInstance().map());

				// temporisation
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		running = true;

		// enregistrer le bilan de la partie dans la bdd
		(new JoueurDaoImpl(DAOFactory.getInstance()))
				.process(TCPServer.lesJoueurs);
	}
}
