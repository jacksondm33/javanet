package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientExecute extends ClientFunction{
    
    public ClientExecute(ClientUtils utils){
        this.utils = utils;
        this.default_command = "execute";
        this.command_list = new String[]{
            "execute"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                ExecuteFrame executeFrame = new ExecuteFrame(utils, args);
            }
        }.start();
    }
}
