package javanet.server;

import java.io.File;

/**
 *
 * @author Jackson McClintock
 */
public class ServerCreateDir extends ServerFunction{
    
    public ServerCreateDir(ServerUtils utils){
        this.utils = utils;
        this.default_command = "create_dir";
        this.command_list = new String[]{
            "create_dir"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        File dir = new File(args[1]);
        dir.mkdir();
    }
}
