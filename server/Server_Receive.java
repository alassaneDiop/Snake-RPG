/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ALASSANE
 */
public class Server_Receive extends Thread {

    private Socket client;
    private Map<String, Socket> lesClients;
    BufferedReader buffer = null;

    //public Server_Receive(Socket client, Map<String, Socket> lesClients) throws IOException {
	public Server_Receive(Socket client) throws IOException {
        this.client = client;
//        this.lesClients = lesClients;
        
        //recupere le pseudo envoye par le client qui vient de se connecter
        buffer = new BufferedReader(new InputStreamReader(client.getInputStream())); //client.getInputStream() le flux venant du client
        String pseudo = buffer.readLine(); //lire le pseudo du client
  //      lesClients.put(pseudo, client);
        System.out.println("Peuso : "+ pseudo);
		this.start();
    }

    @Override
    public void run() {
        String msgClient = null;
        try {
            //initialise le buffer pour les flux entrant du reseau
            buffer = new BufferedReader(new InputStreamReader(client.getInputStream())); //client.getInputStream() le flux venant du client
            while (true) {
                msgClient = buffer.readLine(); //on recupere ce que l'utilisateur tape
                if (msgClient == null) {
                    break;
                }
                //on recupere les pseudos des clients
    //            Set<String> cles = lesClients.keySet();
                //pour separer les messages et les pseudos
                // String[] tab = msgClient.split("::");
                // PrintgWriter out = null;
                //for (String cle : cles) {
                    //if (!tab[0].equals(cle)) {
                        // out = new PrintWriter(client.getOutputStream(), true);
                        // out.println(tab[0] + ":" + tab[1]); //envoyer une donnee sur le reseau
                        //tab[0] est le pseudo tab[1] est le message
                        System.out.println(msgClient);
                    //}
                //}
                //System.out.println("MESSAGE : " + reponse);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            buffer.close();
            client.close(); //l'objet client doit etre le dernier a etre ferme
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
   /* public void run() {
        String reponse = null;
        try {
            //initialise le buffer pour les flux entrant du reseau
            buffer = new BufferedReader(new InputStreamReader(client.getInputStream())); //client.getInputStream() le flux venant du client
            while (true) {
                reponse = buffer.readLine(); //on recupere ce que l'utilisateur tape
                if (reponse == null) {
                    break;
                }
                //on recupere les pseudos des clients
    //            Set<String> cles = lesClients.keySet();
                //pour separer les messages et les pseudos
                String[] tab = reponse.split("::");
                PrintWriter out = null;
                for (String cle : cles) {
                    if (!tab[0].equals(cle)) {
                        out = new PrintWriter(lesClients.get(cle).getOutputStream(), true);
                        out.println(tab[0] + ":" + tab[1]); //envoyer une donnee sur le reseau
                        //tab[0] est le pseudo tab[1] est le message
                    }
                }
                //System.out.println("MESSAGE : " + reponse);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            buffer.close();
            client.close(); //l'objet client doit etre le dernier a etre ferme
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    */
}
