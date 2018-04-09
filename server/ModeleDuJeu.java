package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ModeleDuJeu {

	Map<String, Serpent> lesSerpents;
	boolean laPartieEstPerdue;
	Grenouille grenouille;

	// request[0] = request; request[1] = argument
	String[] request = new String[2];

	private static ModeleDuJeu instance = null;

	private ModeleDuJeu() {
		this.laPartieEstPerdue = false;
		this.grenouille = new Grenouille();

		this.lesSerpents = new HashMap<String, Serpent>();
	}

	public static ModeleDuJeu getInstance() {
		if (instance == null)
			instance = new ModeleDuJeu();

		return instance;
	}

	public void getNiveau() {
		for (Entry<String, Serpent> serpent : lesSerpents.entrySet()) {
			serpent.getValue().niveau = (serpent.getValue().eatCount / 5) + 1;
		}
	}

	public void setDirection(String keySerpent, Direction direction) {
		lesSerpents.get(keySerpent).setDemandeClavier(direction);
	}

	// le calcul du jeu
	public void calcul() {

		// if (!this.laPartieEstPerdue) {
		// // calcul de grenouille
		// this.grenouille.calcul();
		// // calcul du serpent
		// // this.serpent.calcul(this.grenouille, getNiveau());
		//
		// this.getNiveau();
		//
		// for (Entry<String, Serpent> serpent : lesSerpents.entrySet()) {
		// serpent.getValue().calcul(this.grenouille,
		// serpent.getValue().getNiveau());
		//
		// if (!serpent.getValue().getVie()) {
		// // la partie est perdue car le serpent a atteint les limites
		// // du plateau de jeu
		// this.laPartieEstPerdue = true;
		//
		// }
		// }
		// }
		for (Entry<String, Serpent> serpent : lesSerpents.entrySet()) {
			// this.getNiveau() = serpent.getValue().getEatCount() / 5) + 1;

			serpent.getValue().calcul(this.grenouille);

			// if (!serpent.getValue().getVie()) {
			// // la partie est perdue car le serpent a atteint les
			// // limites
			// // du plateau de jeu
			// this.laPartieEstPerdue = true;

			// }
		}
		// }
		// }
	}

	public void addSerpent(String nomSerpent) {
		if (lesSerpents.containsKey(nomSerpent))
			lesSerpents.replace(nomSerpent, new Serpent());
		else
			lesSerpents.put(nomSerpent, new Serpent());
	}


	public String map() {
		String chaineSerpent = "\"serpent\"[", chaineGrenouille = "\"grenouille\"[", chaineObstacle = "\"obstacle\"[";

		for (Entry<String, Serpent> serpent : lesSerpents.entrySet()) {
			// n° serpent/longueur/niveau/vie
			chaineSerpent += serpent.getKey() + "/"
					+ serpent.getValue().getPosition() + "/"
					+ serpent.getValue().niveau + "/" + serpent.getValue().vie
					+ "," + serpent.getValue().messageVie + "/"
					+ serpent.getValue().eatCount + "|";
		}

		chaineGrenouille += this.grenouille.getPosition();

		return "Map-" + chaineSerpent.substring(0, chaineSerpent.length() - 1)
				+ "]," + chaineGrenouille + "]}";
	}
}
