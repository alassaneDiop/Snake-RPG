/** 
 * 
 */
package client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import model.Constantes;

/**
 * @author alassane
 *
 */
public class Facade {

	// une hapMash de serpent les pseudos des joueurs représentent les clés des
	// serpents
	static final Map<String, Serpent> lesSerpents = new HashMap<String, Serpent>();
	static final Grenouille grenouille = new Grenouille(0, 0);

	public Facade() {
	}

	// recréer les serpents en partant de la chaine parsée envoyée par le
	// serveur
	public static void setSerpents(String serpent) {
		String serp[] = serpent.split("\\|");

		for (String s : serp) {
			String attSerpent[] = s.split("/");
			Serpent S = new Serpent(attSerpent[0],
					parseToListCase(attSerpent[1]),
					Integer.parseInt(attSerpent[2]),
					Boolean.valueOf((attSerpent[3]).split(",")[0]),
					(attSerpent[3]).split(",")[1],
					Integer.parseInt(attSerpent[4]));

			// mise à jour d'un serpent aprés une opération avec le serveur
			if (lesSerpents.containsKey(S.nomSerpent)) {
				lesSerpents.replace(S.nomSerpent,
						lesSerpents.get(S.nomSerpent), S);
			} else { // ajout d'un nouveau serpent
				lesSerpents.put(S.nomSerpent, S);
			}
		}
	}

	public static void setGrenouilles(String grenou) {
		try {
			grenouille.xIndice = Integer.parseInt(grenou.split(",")[0]);
			grenouille.yIndice = Integer.parseInt(grenou.split(",")[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void splitMessage(String response) {
		// Map-"serpent"[alou/14,15;15,15;16,15;17,15;18,15;19,15/niveau/vie,messageVie/score],"grenouille"[]}
		// String item = null;
		String serpent = null, grenouille = null;
		try {
			// item = response.split("-")[0];
			String message = response.split("-")[1];
			message = message.substring(0, message.length());

			serpent = message.split("],")[0];
			serpent = serpent.substring(10, serpent.length());
			setSerpents(serpent);

			grenouille = message.split("],")[1];
			grenouille = grenouille.substring(13, grenouille.length() - 2);
			setGrenouilles(grenouille);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// parser la longueur en list de cases reliées pour représenter le serpent
	public static LinkedList<Case> parseToListCase(String longueur) {
		LinkedList<Case> listCase = new LinkedList<Case>();
		String cases[] = longueur.split(";");

		for (String c : cases) {
			try {
				listCase.add(new Case(Integer.parseInt(c.split(",")[0]),
						Integer.parseInt(c.split(",")[1])));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listCase;
	}

	// commencer la partie
	public static void startGame() {
		JFenetre.running = true;
		(new JFenetre()).start();
	}

	// affichage des serpents et grenouille.
	// le serpent est matérialisé par des étoiless, la grenouille par 0
	public static void affichage(String[][] tab) {
		initMap(tab);
		// affichage du serpent
		try {
			for (Entry<String, Serpent> serpent : Facade.lesSerpents.entrySet())
				serpent.getValue().affichage(tab);

			Facade.grenouille.affichage(tab);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < Constantes.NBRE_DE_LIGNES; i++) {
			for (int j = 0; j < Constantes.NBRE_DE_COLONNES; j++) {
				System.out.print(tab[i][j]);
			}
			System.out.println("\n");
		}
	}

	public static void initMap(String[][] tab) {

		for (int i = 0; i < Constantes.NBRE_DE_LIGNES; i++) {
			for (int j = 0; j < Constantes.NBRE_DE_COLONNES; j++) {
				tab[i][j] = " ";
			}
		}

		// affichage de l'entete et des bordures horizontales
		for (int i = 0; i < Constantes.NBRE_DE_COLONNES; i++) {
			tab[0][i] = "-";
			tab[2][i] = "-";
			tab[Constantes.NBRE_DE_LIGNES - 1][i] = "^";
		}

		// affichage du pseudo
		int i = 0;
		try {
			for (i = 0; i < Joueur.getInstance().pseudo.length(); i++)
				tab[1][i] = Joueur.getInstance().pseudo.substring(i, i + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		i++;

		// affichage du score du niveau et du message de vie
		for (int j = 0; j < "score = ".length(); j++) {
			tab[1][i] = "score = ".substring(j, j + 1);
			i++;
		}

		try {

			tab[1][i] = Facade.lesSerpents.get(Joueur.getInstance().pseudo).eatCount
					+ "";
			i++;

			for (int j = 0; j < " niveau = ".length(); j++) {
				tab[1][i] = " niveau = ".substring(j, j + 1);
				i++;
			}

			tab[1][i] = Facade.lesSerpents.get(Joueur.getInstance().pseudo).niveau
					+ "";

			i += 2;

			for (int j = 0; j < Facade.lesSerpents
					.get(Joueur.getInstance().pseudo).messageVie.length(); j++) {
				tab[1][i] = Facade.lesSerpents.get(Joueur.getInstance().pseudo).messageVie
						.substring(j, j + 1);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// affichage des bordures latérales du jeu
		for (i = 3; i < Constantes.NBRE_DE_LIGNES; i++) {
			for (int j = 0; j < Constantes.NBRE_DE_COLONNES; j++) {
				if (j == 0) {
					tab[i][j] = ">";
				}
				if (j == Constantes.NBRE_DE_COLONNES - 1) {
					tab[i][j] = "<";
				}
			}
		}

	}

}
