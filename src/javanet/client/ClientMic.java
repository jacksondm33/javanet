package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientMic extends ClientFunction{
    
    public ClientMic(ClientUtils utils){
        this.utils = utils;
        this.default_command = "mic";
        this.command_list = new String[]{
            "mic"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                MicFrame micFrame = new MicFrame(utils, args);
            }
        }.start();
    }
}
