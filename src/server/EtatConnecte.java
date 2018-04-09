package server;

import java.util.List;

public class EtatConnecte implements Etat {

	ContexteJoueur contexteJoueur;

	public EtatConnecte(ContexteJoueur cj) {
		this.contexteJoueur = cj;
	}

	@Override
	public String connecter(String pseudo) {
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
	public void quitter() {

	}

	@Override
	public void recevoirPlanJeu() {
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
	public String creerPartie(String nomPartie) {
		if (contexteJoueur.joueur.pseudo == null)
			return "Veuillez choisir un pseudo pour continuer";

		if (nomPartie == null)
			return "Commande incorrecte. \"/newGame nomPartie\"";

		contexteJoueur.joueur.serpent = new Serpent();
		ModeleDuJeu.getInstance().addSerpent(contexteJoueur.joueur.pseudo);
		TCPServer.lesParties.add(new Partie(contexteJoueur.joueur, nomPartie));

		return "newGame:" + ModeleDuJeu.getInstance().map();
	}

	@Override
	public String rejoindrePartie(int index) {
		try {
			Partie partie = TCPServer.lesParties.get(index - 1);
			partie.joinPartie(contexteJoueur.joueur);
			contexteJoueur.joueur.serpent = new Serpent();
			ModeleDuJeu.getInstance().addSerpent(contexteJoueur.joueur.pseudo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Partie.running) {
			return "joinGame:";
		} else {
			return "newGame:" + ModeleDuJeu.getInstance().map();
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
	public String listePartie() {

		String liste = "listGame:";

		int i = 1;

		for (Partie partie : TCPServer.lesParties) {
			liste += "\n\t\tPartie " + i + " => " + partie.nomPartie + partie;
			i++;
		}

		return liste;
	}

	@Override
	public String startGame() {
		return "startGame:";
	}

	@Override
	public String endGame(String keySerpent) {
		if (!ModeleDuJeu.getInstance().lesSerpents.get(keySerpent).vie) {
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
