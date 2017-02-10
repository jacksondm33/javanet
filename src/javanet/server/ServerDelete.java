package javanet.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jackson McClintock
 */
public class ServerDelete extends ServerFunction{
    
    public ServerDelete(ServerUtils utils){
        this.utils = utils;
        this.default_command = "delete";
        this.command_list = new String[]{
            "delete"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        File del_file = new File(args[1]);
        if(del_file.isDirectory()){
            try {
                FileUtils.deleteDirectory(del_file);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            del_file.delete();
        }
    }
}
