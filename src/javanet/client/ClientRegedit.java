package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientRegedit extends ClientFunction{
    
    public ClientRegedit(ClientUtils utils){
        this.utils = utils;
        this.default_command = "regedit";
        this.command_list = new String[]{
            "regedit",
            "reg"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                RegeditFrame regeditFrame = new RegeditFrame(utils, args);
            }
        }.start();
    }
}
