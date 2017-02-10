package javanet.client;

import java.awt.Component;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Jackson McClintock
 */
public class IdComponent{
    
    Component component = null;
    InetAddress ip = null;
    Socket socket = null;
    
    public IdComponent(Component component){
        this.component = component;
    }
    public void setIp(InetAddress ip){
        this.ip = ip;
    }
    public InetAddress getIp(){
        return ip;
    }
    public void setComponent(Component component){
        this.component = component;
    }
    public Component getComponent(){
        return component;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    public Socket getSocket(){
        return socket;
    }
}
