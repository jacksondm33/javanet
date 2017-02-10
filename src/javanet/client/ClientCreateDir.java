package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientCreateDir extends ClientFunction{
    
    public ClientCreateDir(ClientUtils utils){
        this.utils = utils;
        this.default_command = "create_dir";
        this.command_list = new String[]{
            "create_dir"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                CreateDirFrame createDirFrame = new CreateDirFrame(utils, args);
            }
        }.start();
    }
}
