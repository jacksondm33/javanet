package javanet.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class Server {
    
    Socket socket = null;
    ServerUtils utils = null;
    final int SLEEP_INTERVAL = 5000;
    
    public Server(String ip, int port){
        InetSocketAddress inetBind;
        SocketAddress bind;
        for(;;){
            socket = new Socket();
            inetBind = new InetSocketAddress(ip, port);
            bind = (SocketAddress) inetBind;
            try {
                socket.connect(bind);
            } catch (IOException ex) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                }
                continue;
            }
            utils = new ServerUtils(socket);
            utils.commandListener();
        }
    }
    
    public static void main(String[] args){
        Server server = new Server("127.0.1.1", 1567);
    }
}
