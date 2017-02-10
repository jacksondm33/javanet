package javanet.server;

import java.io.File;

/**
 *
 * @author Jackson McClintock
 */
public class ServerRename extends ServerFunction{
    
    public ServerRename(ServerUtils utils){
        this.utils = utils;
        this.default_command = "rename";
        this.command_list = new String[]{
            "rename"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        File old_file = new File(args[1]);
        File new_file = new File(args[2]);
        old_file.renameTo(new_file);
    }
}
