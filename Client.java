/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSH_HK
 */
public final class Client implements Runnable {

    private String name;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter out;
    private Server server;

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Enter your name: ");
            setName(input.readLine());

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(String message, Client sender) {
        out.println(sender.name + " : " + message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isClosed(){
    return this.socket.isClosed();
    }

    public void closeConnection() {
        try {
            server.remove(this);
            input.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        String message = "";

        while (!socket.isClosed()) {
            try {
                message = input.readLine();
                if(message == null){
                closeConnection();
                System.out.println("some one has left");
                break;
                }
                this.server.sendAll(message, this);
                
            } catch (IOException ex) {
                
                closeConnection();
                System.out.println("some one is out");
                break;
            }

        }
        
        

    }

}
