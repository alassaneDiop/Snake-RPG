/** 
 * 
 */
package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Direction;
import model.Grenouille;
import model.Serpent;

/**
 * @author alassane
 *
 */

public class ModeleDuJeu {

	Map<String, Serpent> lesSerpents;
	boolean laPartieEstPerdue;
	Grenouille grenouille;

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

	public void setDirection(String keySerpent, Direction direction) {
		lesSerpents.get(keySerpent).setDemandeClavier(direction);
	}

	// le calcul du jeu
	public void calcul() {
		for (Entry<String, Serpent> serpent : lesSerpents.entrySet()) {
			serpent.getValue().calcul(this.grenouille);
		}
	}

	public void addSerpent(String nomSerpent) {
		if (lesSerpents.containsKey(nomSerpent))
			lesSerpents.replace(nomSerpent, new Serpent());
		else
			lesSerpents.put(nomSerpent, new Serpent());
	}

	// renvoie une chaine de caractére qui parse les nouvelles positions de tous
	// les serpents
	public String map() {
		String chaineSerpent = "\"serpent\"[", chaineGrenouille = "\"grenouille\"[";

		for (Entry<String, Serpent> serpent : lesSerpents.entrySet()) {
			// n° serpent/longueur/niveau/vie messagevie/score
			chaineSerpent += serpent.getKey() + "/"
					+ serpent.getValue().getAllPosition() + "/"
					+ serpent.getValue().niveau + "/" + serpent.getValue().vie
					+ "," + serpent.getValue().messageVie + "/"
					+ serpent.getValue().eatCount + "|";
		}

		chaineGrenouille += this.grenouille.getPosition();

		return "Map-" + chaineSerpent.substring(0, chaineSerpent.length() - 1)
				+ "]," + chaineGrenouille + "]}";
	}
}
