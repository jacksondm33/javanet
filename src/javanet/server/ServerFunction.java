package javanet.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class ServerFunction {
    
    ServerUtils utils = null;
    String[] result = null;
    String default_command = "";
    String[] command_list = new String[]{};
    
    public void processCommand(String[] args){
        
    }
    public void sendResult(String[] args){
        new Thread(){
            @Override
            public void run(){
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < args.length; i++){
                    sb.append(args[i]);
                    if(i < (args.length - 1)){
                        sb.append(":_:_:");
                    }else{
                        sb.append(":^:^:");
                    }
                }
                try {
                    utils.io.write(sb.toString());
                } catch (IOException ex) {
                    Logger.getLogger(ServerFunction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
}
