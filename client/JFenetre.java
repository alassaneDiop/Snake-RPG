package client;

// pour un meilleur affichage la console doit être en 80 x 41 (dimensions) 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFenetre extends Thread {

	JFrame frame;
	JPanel content;

	volatile static String[][] tab;

	volatile static String request = "";
	volatile static boolean gameOver = false;
	volatile static boolean running = true;

	@SuppressWarnings("serial")
	public JFenetre() {
		tab = new String[Constantes.NBRE_DE_LIGNES][Constantes.NBRE_DE_COLONNES];

		frame = new JFrame();
		frame.setUndecorated(true);

		// this.modele = modele; // créer le modèle du jeu

		// créer un conteneur qui affichera le jeu
		content = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// JFenetre.this.modele.affichage(g); // affichage du modèle du
				// jeu
				// Facade.affichage(g);
				// (new Serpent()).affichage(g);
			}
		};

		// le listener gérant les entrées au clavier
		content.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Serpent serpent = Facade.lesSerpents.get(Joueur.getInstance().pseudo);
				serpent.gestionDuClavier(e);
			}
		});

		content.setPreferredSize(new Dimension(450, 450));
		setContent();
	}

	public void setContent() {

		content.setFocusable(true);
		content.setBackground(new Color(0, 0, 0, 0));
		frame.setContentPane(content);// ajouter le conteneur à la fenêtre
		frame.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setFocusable(false); // s'assurer du focus pour le listener
									// clavier
		// fermeture de l'application lorsque la fenêtre est fermée
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // pas de redimensionnement possible de la
									// fenêtre
		frame.pack();
		frame.setLocationRelativeTo(null); // centrage sur l'écran
		frame.setVisible(true); // affichage
	}

	// public void affichage() {
	//
	// Facade.affichage(tab);
	//
	//
	// }

	public static void affichage() {
		Facade.affichage(tab);
	}

	@Override
	public void run() {
		// création de la fenêtre
		// JFenetre fenetre = new JFenetre();
		// dimension de ce conteneur
		// fenetre.content.setPreferredSize(new Dimension(450, 450));
		// fenetre.setContent();

		// fenetre.paintContent();
		// fenetre.start();

		/*
		 * à chaque fois que la boucle est exécutée, la méthode de calcul du jeu
		 * est appelée. Comme la boucle est infinie, la méthode d e calcul sera
		 * appelée en cycle perpétuel.
		 */
		while (running) {

			// System.out.println("Request : " + request);

			// if (request != null) {
			// System.out.println("Thread interrupted"+Thread.interrupted());

			// affichage();
			switch (request) {
			case "turn":
				Facade.affichage(tab);
				try {
					Thread.sleep(200);
					// vider la console
					// System.out.print("\033[H\033[2J");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				break;

			case "gameOver":
				affichage();
				gameOver = true;
				break;

			case "endGame":
				this.frame.setVisible(false);
				running = false;
				break;

			// default:
			// try {
			// Thread.sleep(200);
			// // vider la console
			// // System.out.print("\033[H\033[2J");
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// break;
			// }

			}

			// request = null;
		}

	}
}
