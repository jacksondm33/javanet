package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientProxy extends ClientFunction{
    
    public ClientProxy(ClientUtils utils){
        this.utils = utils;
        this.default_command = "proxy";
        this.command_list = new String[]{
            "proxy"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                ProxyFrame proxyFrame = new ProxyFrame(utils, args);
            }
        }.start();
    }
}
