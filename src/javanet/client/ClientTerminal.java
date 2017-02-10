package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientTerminal extends ClientFunction{
    
    public ClientTerminal(ClientUtils utils){
        this.utils = utils;
        this.default_command = "terminal";
        this.command_list = new String[]{
            "terminal"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                TerminalFrame terminalFrame = new TerminalFrame(utils, args);
            }
        }.start();
    }
}
