package javanet.server;

/**
 *
 * @author Jackson McClintock
 */
public class ServerMic extends ServerFunction{
    
    public ServerMic(ServerUtils utils){
        this.utils = utils;
        this.default_command = "mic";
        this.command_list = new String[]{
            "mic"
        };
    }
    
    @Override
    public void processCommand(String[] args) {

    }
}
