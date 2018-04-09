package server;

import java.util.ArrayList;

public class Partie extends Thread {

	// Le joueur à la position 0 est celui qui crée la partie
	ArrayList<Joueur> lesJoueurs;
	String nomPartie;

	volatile static boolean running = true;

	public Partie(Joueur joueur, String nom) {
		lesJoueurs = new ArrayList<Joueur>();
		lesJoueurs.add(joueur);
		this.nomPartie = nom;
	}

	public Partie() {

	}

	public void joinPartie(Joueur joueur) {
		if (!lesJoueurs.contains(joueur))
			lesJoueurs.add(joueur);
	}

	public void quitPartie(Joueur joueur) {
		lesJoueurs.remove(joueur);
	}

	public void setNomPartie(String nom) {
		this.nomPartie = nom;
	}

	public String toString() {
		String liste = "Liste des joueurs de la partie " + nomPartie;

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
			} else {
//				System.out.println("La partie est en cours");
				ModeleDuJeu.getInstance().calcul();
				TCPServer.sendToPartie(TCPServer.lesParties.get(0), "turn:"
						+ ModeleDuJeu.getInstance().map());

				// for (Entry<String, Serpent> serpent :
				// ModeleDuJeu.getInstance().lesSerpents
				// .entrySet()) {
				//
				// //
				// serpent.getValue().calcul(ModeleDuJeu.getInstance().grenouille,
				// // serpent.getValue().getNiveau());
				//
				// System.out.println("position à envoyer : "
				// + serpent.getValue().getAllPosition());
				//
				// TCPServer.sendToUnJoueur(serpent.getKey(), "position "
				// + serpent.getValue().getAllPosition());
				//
				// }

				// temporisation
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
