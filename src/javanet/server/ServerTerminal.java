package javanet.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class ServerTerminal extends ServerFunction{
    
    public ServerTerminal(ServerUtils utils){
        this.utils = utils;
        this.default_command = "terminal";
        this.command_list = new String[]{
            "terminal"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        StringBuilder sb = null;
        try {
            Process p = Runtime.getRuntime().exec(args[1]);
            BufferedReader sr = new BufferedReader(new InputStreamReader(p.getInputStream()));
            sb = new StringBuilder();
            int i = 0;
            for(;;){
                try {
                    i = sr.read();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(i == -1){
                    break;
                }
                sb.append((char) i);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendResult(new String[]{"terminal", sb.toString()});
    }
}
