/** 
 * 
 */
package server;

import java.io.IOException;
import java.util.List;

import model.Direction;
import model.Serpent;

/**
 * @author alassane
 *
 */
public class EtatConnecte implements Etat {

	ContexteJoueur contexteJoueur;

	public EtatConnecte(ContexteJoueur cj) {
		this.contexteJoueur = cj;
	}

	@Override
	public String connecter(String pseudo, String password) {
		return "Vous êtes déjà connectés";
	}

	// déconnecter le joueur et passer à l'état EtatDeconnecte
	@Override
	public void deconnecter() {
		TCPServer.lesJoueurs.remove(contexteJoueur);

		contexteJoueur.setEtat(contexteJoueur.getEtatDeconnecte());
		contexteJoueur.joueur.pseudo = null;
	}

	// quitter le jeu
	@Override
	public void quitter(String pseudo) {
		ModeleDuJeu.getInstance().lesSerpents.remove(pseudo);
		try {
			TCPServer.sendToUnJoueur(pseudo, "quit");
			contexteJoueur.joueur.so.close();
			TCPServer.lesJoueurs.remove(contexteJoueur);
			TCPServer.sendToAll(pseudo + " est parti");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String deplacer(String keySerpent, String direction) {
		if (contexteJoueur.joueur.pseudo == null)
			return "Veuillez choisir un pseudo pour continuer";

		System.out.println(keySerpent);

		if (ModeleDuJeu.getInstance().lesSerpents.get(keySerpent).vie) {
			switch (direction) {
			case "LEFT":
				ModeleDuJeu.getInstance().setDirection(keySerpent,
						Direction.LEFT);
				break;
			case "UP":
				ModeleDuJeu.getInstance()
						.setDirection(keySerpent, Direction.UP);
				break;
			case "RIGHT":
				ModeleDuJeu.getInstance().setDirection(keySerpent,
						Direction.RIGHT);
				break;
			case "DOWN":
				ModeleDuJeu.getInstance().setDirection(keySerpent,
						Direction.DOWN);
				break;
			}

			return "turn:" + ModeleDuJeu.getInstance().map();
		}
		return "";
	}

	@Override
	public String rejoindreGame() {
		try {
			Partie.getInstance().joinPartie(contexteJoueur.joueur);
			contexteJoueur.joueur.serpent = new Serpent();
			ModeleDuJeu.getInstance().addSerpent(contexteJoueur.joueur.pseudo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Partie.isRunning) {
			return "joinGame:" + ModeleDuJeu.getInstance().map();
		} else {
			return "Veuiller démarrer la partie \"startGame\"";
		}
	}

	@Override
	public String listeJoueur() {
		List<ContexteJoueur> lesJoueurs = TCPServer.lesJoueurs;

		String liste = "listJoueur:";

		int i = 1;
		for (ContexteJoueur cj : lesJoueurs) {
			liste += "\n\t\tJoueur " + i + " => ";
			liste += (cj.joueur.pseudo != null) ? cj.joueur.pseudo
					: "pas encore de pseudo";
			i++;
		}

		return liste;
	}

	@Override
	public String startGame() {
		if (contexteJoueur.joueur.pseudo == null)
			return "Veuillez choisir un pseudo pour continuer";

		if (Partie.isRunning) {
			rejoindreGame();
		} else {
			contexteJoueur.joueur.serpent = new Serpent();
			ModeleDuJeu.getInstance().addSerpent(contexteJoueur.joueur.pseudo);
		}

		Partie.running = true;
		return "startGame:" + ModeleDuJeu.getInstance().map();
	}

	@Override
	public String endGame(String keySerpent) {
		if (!ModeleDuJeu.getInstance().lesSerpents.get(keySerpent).vie) {

			// mettre à jour le serpent dans son joueur
			contexteJoueur.joueur.serpent = ModeleDuJeu.getInstance().lesSerpents
					.get(keySerpent);
			TCPServer.updateJoueur(contexteJoueur);
			ModeleDuJeu.getInstance().lesSerpents.remove(keySerpent);
			return "endGame:";
		}
		return "";
	}

	@Override
	public String restartGame(String keySerpent) {
		if (!ModeleDuJeu.getInstance().lesSerpents.get(keySerpent).vie) {
			try {
				ModeleDuJeu.getInstance().lesSerpents.replace(keySerpent,
						new Serpent());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "restartGame:";
		}
		return "";
	}

}
