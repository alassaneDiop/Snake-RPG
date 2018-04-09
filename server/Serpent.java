package server;

import java.util.LinkedList;

public class Serpent {

	LinkedList<Case> list;
	Direction direction;
	boolean vie;
	String messageVie = "Mangez toutes les grenouilles";
	Direction demande;
	int eatCount;
	int moveCounter;
	int niveau;

	public Serpent() {
		this.list = new LinkedList<Case>();
		this.list.add(new Case(14, 15));
		this.list.add(new Case(15, 15));
		this.list.add(new Case(16, 15));
		this.list.add(new Case(17, 15));
		this.list.add(new Case(18, 15));
		this.list.add(new Case(19, 15));
		this.direction = Direction.LEFT;
		this.vie = true; // le serpent est vivant
	}

	public void setDemandeClavier(Direction demande) {
		this.demande = demande;
	}

	private void tourner() {
		if (this.demande != null) { // une touche à été pressée

			// le serpent va vers le haut ou le bas
			if (this.direction == Direction.UP
					|| this.direction == Direction.DOWN) {
				if (this.demande == Direction.RIGHT) { // la touche droite à été
														// pressée
					// le serpent tourne à droite
					this.direction = Direction.RIGHT;
				} else if (this.demande == Direction.LEFT) { // la touche gauche
																// à été pressée
					// le serpent tourne à gauche
					this.direction = Direction.LEFT;
				}
			} else { // le serpent va vers la droite ou la gauche
				if (this.demande == Direction.UP) { // la touche haut à été
													// pressée
					// le serpent tourne vers le haut
					this.direction = Direction.UP;
				} else if (this.demande == Direction.DOWN) { // la touche bas à
																// été pressée
					// le serpent tourne vers le bas
					this.direction = Direction.DOWN;
				}
			}

			// nous avons tenu compte du clavier, nous le vidons afin de
			// forcer le joueur à rappuyer sur une touche pour demander
			// une autre direction
			this.demande = null;
		}
	}

	private void avance() {
		// ajoute en tête de liste la case sur laquelle
		// le serpent doit se déplacer
		this.list.addFirst(getNextcase());
		// supprime le dernier élément de la liste
		this.list.removeLast();
	}

	private Case getNextcase() {
		Case tete = this.list.getFirst();
		switch (this.direction) {
		case UP:
			return new Case(tete.xIndice, tete.yIndice - 1);
		case RIGHT:
			return new Case(tete.xIndice + 1, tete.yIndice);
		case DOWN:
			return new Case(tete.xIndice, tete.yIndice + 1);
		case LEFT:
			return new Case(tete.xIndice - 1, tete.yIndice);
		}
		return null;
	}

	private boolean peutAvancer() {
		Case nextCase = getNextcase();
		return nextCase.estValide() && !this.list.contains(nextCase);
	}

	private boolean peutManger(Grenouille grenouille) {
		return grenouille.equals(getNextcase());
	}

	private void mange() {
		// ajoute en tête de liste la case sur laquelle
		// le serpent doit se déplacer
		this.list.addFirst(getNextcase());
		// comptabiliser les grenouilles "mangées"
		this.eatCount++;
	}

	private int getThresholdCounter(int niveau) {
		switch (niveau) {
		case 1:
			return 20;
		case 2:
			return 16;
		case 3:
			return 14;
		case 4:
			return 12;
		case 5:
			return 10;
		case 6:
			return 8;
		case 7:
			return 6;
		case 8:
			return 4;
		case 9:
			return 3;
		default:
			return 2;
		}
	}

	public void calcul(Grenouille grenouille) {

		// incrémenter le compteur
		// this.moveCounter++;
		// vérifier qu'il est temps d'animer le serpent
		// if (this.moveCounter >= getThresholdCounter(niveau)) {
		// remettre le compteur à zéro
		// this.moveCounter = 0;
		// calcul du serpent
		tourner();
		if (peutManger(grenouille)) {
			mange();
			grenouille.nouvelleGrenouille();
		} else if (peutAvancer()) {
			avance();
		} else {
			// la partie est perdue car le serpent
			// a atteint les limites du plateau de jeu
			this.vie = false;
			this.messageVie = "Aie la tete. Game Over. Rejouer la partie ? Y ou N";
		}
		// }
	}

	public String getPosition() {
		String chaine = "";

		for (Case c : this.list) {
			chaine += c.xIndice + "," + c.yIndice + ";";
		}

		return chaine.substring(0, chaine.length() - 1); // supprimer le dernier
															// ';'
	}

	public String getAllPosition() {
		String chaine = "";

		for (Case c : list) {
			chaine += c.xIndice + "," + c.yIndice + ";";
		}

		return chaine.substring(0, chaine.length() - 1);
	}
}
