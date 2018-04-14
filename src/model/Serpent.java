/**  
 * 
 */
package model;

import java.util.LinkedList;

/**
 * @author alassane
 *
 */
public class Serpent {

	public LinkedList<Case> list; // représente le serpent en mode visuel
	public Direction direction;
	public boolean vie;
	public String messageVie = "Mangez toutes les grenouilles";
	public Direction demande;
	public int eatCount;
	public int moveCounter;
	public int niveau;

	public Serpent() {
		this.list = new LinkedList<Case>();
		this.list.add(new Case(14, 15));
		this.list.add(new Case(15, 15));
		this.list.add(new Case(16, 15));
		this.list.add(new Case(17, 15));
		this.list.add(new Case(18, 15));
		this.list.add(new Case(19, 15));
		this.direction = Direction.LEFT; // attribuer une direction par défaut
											// au serpent
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

	// renvoie true, si la case qui se trouve devant la tête du serpent est
	// valide, c'est à dire pas un mur, pas le corp du serpent lui meme
	private boolean peutAvancer() {
		Case nextCase = getNextcase();
		return nextCase.estValide() && !this.list.contains(nextCase);
	}

	// renvoie true si la case devant la tete du serpent est une grenouille
	private boolean peutManger(Grenouille grenouille) {
		return grenouille.equals(getNextcase());
	}

	private void mange() {
		// ajoute en tête de liste la case sur laquelle
		// le serpent doit se déplacer
		this.list.addFirst(getNextcase());

		// comptabiliser les grenouilles "mangées"
		this.eatCount++;

		// à chaque multiple de 3 on incrémente le niveau
		if (eatCount > 0 && eatCount % 3 == 0) {
			niveau++;
		}
	}

	// calculer et mettre à jour les données du serpent
	public void calcul(Grenouille grenouille) {

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
	}

	// parser la position du serpent, ie la position de chaque case formant le
	// serpent
	public synchronized String getAllPosition() {
		String chaine = "";

		for (Case c : list) {
			chaine += c.xIndice + "," + c.yIndice + ";";
		}

		return chaine.substring(0, chaine.length() - 1);
	}
}
