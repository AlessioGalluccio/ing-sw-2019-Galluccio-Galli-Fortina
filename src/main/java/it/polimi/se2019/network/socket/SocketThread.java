package it.polimi.se2019.network.socket;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.remoteView.EnemyView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketThread implements Server {
    private Socket socket;
    private ObjectOutputStream printSocket;
    private ObjectInputStream scannerSocket;

    SocketThread(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        try {
            printSocket = new ObjectOutputStream(socket.getOutputStream());
            scannerSocket =  new ObjectInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while(true) {
                        //Receive message
                        Object message = scannerSocket.readObject();
                        if (message.equals("exit")) break; //TODO Chiedere conferma e poi uscire

                        //TODO parserizzare e inooltrare alla view
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Logger.getLogger(SocketThread.class.getName()).log(Level.WARNING, "Problem receiving obj through socket", e);
                } finally {
                    closeAll(); // Closing sockets
                }
            }).start();
        } catch (IOException e) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.WARNING, "Scanner socket closed", e);
        }
    }

    public void closeAll() {
       try {
            scannerSocket.close();
            printSocket.close();
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.WARNING, "Scanner socket closed", e);
        }
    }

    @Override
    public void send(String string) {
        try {
            printSocket.writeObject(string);
            printSocket.flush();
        }catch (IOException e) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.WARNING, "Problem sending obj through socket", e);
        }
    }

    @Override
    public void send(Map map) {

    }

    @Override
    public void send(EnemyView enemy) {

    }

    @Override
    public void send(Player player) {

    }
}
