package server;

public interface Etat {

	public String connecter(String pseudo);

	public void deconnecter();

	public void quitter();

	// public String choisirPseudo(String pseudo);

	public void recevoirPlanJeu();

	public String deplacer(String keySerpent, String direction);

	public String listePartie();

	public String creerPartie(String nomPartie);

	public String rejoindrePartie(int partie);

	public String listeJoueur();

	public String startGame();
	
	public String endGame(String keySerpent);
	
	public String restartGame(String keySerpent);

}
