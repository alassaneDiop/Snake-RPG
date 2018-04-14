/** 
 * 
 */
package client;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

import model.Direction;

/**
 * @author alassane
 *
 */
public class Serpent {

	LinkedList<Case> list;
	Direction direction;
	boolean vie;
	String messageVie;
	Direction demande;
	int eatCount;
	int moveCounter;

	String nomSerpent;
	int niveau;

	public Serpent() {
		this.list = new LinkedList<Case>();
		this.list.add(new Case(14, 15));
		this.list.add(new Case(15, 15));
		this.list.add(new Case(16, 15));
		this.direction = Direction.LEFT;
	}

	public Serpent(String nom, LinkedList<Case> lCase, int niveau, boolean vie,
			String messageVie, int eatCount) {
		this.nomSerpent = nom;
		this.niveau = niveau;
		this.vie = vie;
		this.messageVie = messageVie;
		this.list = lCase;
		this.direction = Direction.LEFT;
		this.eatCount = eatCount;
	}

	public void setDemandeClavier(Direction demande) {
		this.demande = demande;
	}

	// Gestion des touches du clavier
	public void gestionDuClavier(KeyEvent event) {

		switch (event.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			TCPClient.sendToServer("/turn RIGHT");
			break;

		case KeyEvent.VK_LEFT:
			TCPClient.sendToServer("/turn LEFT");
			break;

		case KeyEvent.VK_UP:
			TCPClient.sendToServer("/turn UP");
			break;

		case KeyEvent.VK_DOWN:
			TCPClient.sendToServer("/turn DOWN");
			break;

		case KeyEvent.VK_Y:
			TCPClient.sendToServer("/restartGame");
			break;

		case KeyEvent.VK_N:
			TCPClient.sendToServer("/endGame");
			break;
		}
	}

	public void affichage(String[][] tab) {

		for (Case c : list) {
			tab[c.yIndice][c.xIndice] = "*";
		}

		if (!vie)
			JFenetre.request = "gameOver";
	}

}
