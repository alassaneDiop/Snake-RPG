/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author ALASSANE
 */
public class Client_Receive extends Thread {

    private Socket client;

    public Client_Receive(Socket client) {
        this.client = client;
        this.start();
    }

    @Override
    public void run() {
        String reponse = null;
        BufferedReader buffer = null;
        try {
            //initialise le buffer pour les flux entrant du reseau
            buffer = new BufferedReader(new InputStreamReader(client.getInputStream())); //client.getInputStream() le flux venant du client
            while (true) {
                reponse = buffer.readLine(); //on recupere ce que l'utilisateur tape
                if (reponse == null) {
                    break;
                }
                System.out.println("MESSAGE : " + reponse);
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
}
