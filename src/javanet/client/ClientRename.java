package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientRename extends ClientFunction{
    
    public ClientRename(ClientUtils utils){
        this.utils = utils;
        this.default_command = "rename";
        this.command_list = new String[]{
            "rename"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                RenameFrame renameFrame = new RenameFrame(utils, args);
            }
        }.start();
    }
}
