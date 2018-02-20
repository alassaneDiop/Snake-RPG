
import java.util.Arrays;
import java.util.List;

public class Server {

	// l'ensemble des commandes reconnues par le serveur
	 List<String> listCommande = Arrays.asList("/quit", "/pseudo", "/join", "receptionPlan", "/turn H", "/turn B", "/turn G", "/turn D");

	public Server () {
	}

	public String gestionCommande (String commande) {
		
		String response = "Commande inconnue";

		// vérifier si la commande envoyée est connue par le serveur
		if (listCommande.contains(commande)) {

			switch (commande) {

			case "/quit": 
				response = "Joueur [Couleur] parti";
				break;

			case "/pseudo":
				response = "pseudo valide";
				break;
				
			case "/join":
				response = "100 [Bienvenue au jeu] / 204 [Identification échouée]";
				break;
				
			case "receptionPlan":
				response = "Commande reçue";
				break;
				
			case "/turn H":
			case "/turn B":
			case "/turn G":
			case "/turn D":
				response = "100 [Direction valide] / [Direction invalide]";
				break;
			}

		}

		return response;
	}

}
