package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientDelete extends ClientFunction{
    
    public ClientDelete(ClientUtils utils){
        this.utils = utils;
        this.default_command = "delete";
        this.command_list = new String[]{
            "delete"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                DeleteFrame deleteFrame = new DeleteFrame(utils, args);
            }
        }.start();
    }
}
