package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientCreateFile extends ClientFunction{
    
    public ClientCreateFile(ClientUtils utils){
        this.utils = utils;
        this.default_command = "create_file";
        this.command_list = new String[]{
            "create_file",
            "edit_file",
            "copy"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                CreateFileFrame createFileFrame = new CreateFileFrame(utils, args);
            }
        }.start();
    }
}
