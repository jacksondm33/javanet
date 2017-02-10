package javanet.server;

/**
 *
 * @author Jackson McClintock
 */
public class ServerProxy extends ServerFunction{
    
    public ServerProxy(ServerUtils utils){
        this.utils = utils;
        this.default_command = "proxy";
        this.command_list = new String[]{
            "proxy"
        };
    }
    
    @Override
    public void processCommand(String[] args) {

    }
}
