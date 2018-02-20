
public interface Etat {

	public void connecter ();
	public void deconnecter ();
	public void quitter();
	public void choisirPseudo();
	public void rejoindreJeu();
	public void recevoirPlanJeu();
	public void deplacer(String deplacement);

}
