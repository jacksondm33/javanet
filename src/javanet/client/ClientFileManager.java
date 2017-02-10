package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientFileManager extends ClientFunction{
    
    public ClientFileManager(ClientUtils utils){
        this.utils = utils;
        this.default_command = "file_manager";
        this.command_list = new String[]{
            "file_manager"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                FileManagerFrame fileManagerFrame = new FileManagerFrame(utils, args);
            }
        }.start();
    }

}
