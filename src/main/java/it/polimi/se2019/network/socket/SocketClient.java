package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SocketClient implements Client {
    private int port;
    private String IP;
    private boolean open;
    private PrintWriter printSocket;

    public SocketClient(int port, String IP) {
        this.port = port;
        this.IP = IP;
        this.open = true;
    }

    public void start() {
        try (Socket socket = new Socket(IP, port); /*Connection established*/
             Scanner scannerSocket = new Scanner(socket.getInputStream())) {

            printSocket = new PrintWriter(socket.getOutputStream());

            //TODO inviare il mesaggio al server
            while(open) { // Fake condition: it's always true
                String messageSocket = scannerSocket.nextLine();
                //TODO parse the messagge
            }

        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Client socket close", e);
        } catch (NoSuchElementException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.INFO, "Connection client close", e);
        } finally {
            printSocket.close();
        }
    }

    @Override
    public void send(ViewControllerMessage message) {
        printSocket.println(message);
        printSocket.flush();
    }
}
