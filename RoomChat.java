/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSH_HK
 */
public class RoomChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        try {
            // TODO code application logic here
            ServerSocket serverSocket = new ServerSocket(999);
            while(!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
                
            Client client = new Client(socket, server);
            server.add(client);
            Thread thread = new Thread(client);  
            thread.start();  
            }
        
        
        } catch (IOException ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
    
}
