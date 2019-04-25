package it.polimi.se2019.network.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketThread implements Runnable {
    private Socket socket;
    PrintWriter printSocket;

    SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner scannerSocket =  new Scanner(socket.getInputStream())) {

            printSocket = new PrintWriter(socket.getOutputStream());

            while(true) {
                //Receive message
                String message = scannerSocket.nextLine();
                if (message.equals("exit")) break; //TODO Chiedere conferma e poi uscire

                //TODO inoltrare alla view (parser & Co.)
            }
            // Closing sockets
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.WARNING, "Scanner socket closed", e);

        }
        finally {
            printSocket.close();
        }
    }


}
