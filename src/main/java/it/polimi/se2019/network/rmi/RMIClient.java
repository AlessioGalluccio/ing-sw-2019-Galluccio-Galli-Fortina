package it.polimi.se2019.network.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    private static ClientView clientView;

    public RMIClient() {
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ServerInterface stub = (ServerInterface) registry.lookup("Server");
            String response = clientView.send();
            switch (response){

                case "character":
                    stub.chooseCharacter(response);
                    break;

                case "action":
                    stub.chooseAction(response);

                    /* TODO
                     * implementare tutti i possibili case
                     *
                    */
            }

            //String response = stub.sayHello();
            //System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


    }

}

