package client;

public class Grenouille extends Case {

	public Grenouille(int x, int y) {
		super(x, y);
	}

	public void affichage(String[][] tab) {
		tab[yIndice][xIndice] = "O";
	}

}
