package javanet.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class ServerExecute extends ServerFunction{
    
    public ServerExecute(ServerUtils utils){
        this.utils = utils;
        this.default_command = "execute";
        this.command_list = new String[]{
            "execute"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        try {
            Runtime.getRuntime().exec(args[1]);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
