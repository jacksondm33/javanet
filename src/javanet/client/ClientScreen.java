package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientScreen extends ClientFunction{
    
    public ClientScreen(ClientUtils utils){
        this.utils = utils;
        this.default_command = "screen";
        this.command_list = new String[]{
            "screen",
            "mouse_move",
            "mouse_click",
            "keyboard"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                ScreenFrame screenFrame = new ScreenFrame(utils, args);
            }
        }.start();
    }
}
