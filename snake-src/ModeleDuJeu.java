
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class ModeleDuJeu {

	private Serpent serpent;
	private boolean laPartieEstPerdue;
	private Grenouille grenouille;

	public ModeleDuJeu() {
		this.serpent = new Serpent();
		this.laPartieEstPerdue = false;
		this.grenouille = new Grenouille();
	}

	// Gestion des touches du clavier
	public void gestionDuClavier(KeyEvent event) {

		switch (event.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			this.serpent.setDemandeClavier(Direction.RIGHT);
			break;

		case KeyEvent.VK_LEFT:
			this.serpent.setDemandeClavier(Direction.LEFT);
			break;

		case KeyEvent.VK_UP:
			this.serpent.setDemandeClavier(Direction.UP);
			break;

		case KeyEvent.VK_DOWN:
			this.serpent.setDemandeClavier(Direction.DOWN);
			break;

		default:
			break;
		}

	}

	private int getNiveau() {
		return (this.serpent.getEatCount() / 5) + 1;
	}

	// le calcul du jeu
	public void calcul() {

		if (!this.laPartieEstPerdue) {
			// calcul de la grenouille
			this.grenouille.calcul();
			// calcul du serpent
			this.serpent.calcul(this.grenouille, getNiveau());

			if (this.serpent.estMort()) {
				// la partie est perdue car le serpent a atteint les limites du plateau de jeu
				this.laPartieEstPerdue = true;
			}
		}
	}

	// le dessin graphique du jeu
	public void affichage(Graphics g) {
	
		// affichage du serpent
		this.serpent.affichage(g);
		// affichage de la grenouille
		this.grenouille.affichage(g);
		
		if (this.laPartieEstPerdue) {
			String str = "game over";
			g.setColor(Color.RED);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
			FontMetrics fm = g.getFontMetrics();
			int x = (g.getClipBounds().width - fm.stringWidth(str)) / 2;
			int y = (g.getClipBounds().height / 2) + fm.getMaxDescent();
			g.drawString(str, x, y);
		}
		
		// affichage du niveau
		g.setColor(Color.BLUE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString(String.valueOf(getNiveau()), 5, 25);
	}

}
