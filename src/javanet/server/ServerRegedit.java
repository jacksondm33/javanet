package javanet.server;

/**
 *
 * @author Jackson McClintock
 */
public class ServerRegedit extends ServerFunction{
    
    public ServerRegedit(ServerUtils utils){
        this.utils = utils;
        this.default_command = "regedit";
        this.command_list = new String[]{
            "regedit",
            "reg"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        
    }
}
