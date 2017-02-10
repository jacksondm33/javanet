package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientKeylogger extends ClientFunction{
    
    public ClientKeylogger(ClientUtils utils){
        this.utils = utils;
        this.default_command = "keylogger";
        this.command_list = new String[]{
            "keylogger"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                KeyloggerFrame keyloggerFrame = new KeyloggerFrame(utils, args);
            }
        }.start();
    }
}
