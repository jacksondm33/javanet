package javanet.server;

/**
 *
 * @author Jackson McClintock
 */
public class ServerKeylogger extends ServerFunction{
    
    public ServerKeylogger(ServerUtils utils){
        this.utils = utils;
        this.default_command = "keylogger";
        this.command_list = new String[]{
            "keylogger"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        
    }
}
