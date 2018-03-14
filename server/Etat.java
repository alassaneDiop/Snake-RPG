
public interface Etat {

	public void connecter(String response);

	public void deconnecter();

	public void quitter();

	public void choisirPseudo();

	public void rejoindreJeu();

	public void recevoirPlanJeu();

	public void deplacer(String deplacement);

}
