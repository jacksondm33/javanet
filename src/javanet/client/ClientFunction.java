package javanet.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class ClientFunction {
    
    ClientUtils utils = null;
    String[] result = null;
    String default_command = "";
    String[] command_list = new String[]{};
    
    public void openFrame(String[] args){
        
    }
    public void sendRequest(String[] args){
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
                    Logger.getLogger(ClientFunction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    public synchronized String[] recieveResponse(){
        try {
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public synchronized void notifyRecieved(String[] args){
        result = args;
        notify();
    }
}
