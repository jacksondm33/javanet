package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientTaskManager extends ClientFunction{
    
    public ClientTaskManager(ClientUtils utils){
        this.utils = utils;
        this.default_command = "task_manager";
        this.command_list = new String[]{
            "task_manager",
            "kill"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                TaskManagerFrame taskManagerFrame = new TaskManagerFrame(utils, args);
            }
        }.start();
    }
}
