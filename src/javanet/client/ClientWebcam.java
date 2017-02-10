package javanet.client;

/**
 *
 * @author Jackson McClintock
 */
public class ClientWebcam extends ClientFunction{
    
    public ClientWebcam(ClientUtils utils){
        this.utils = utils;
        this.default_command = "webcam";
        this.command_list = new String[]{
            "webcam"
        };
    }
    
    @Override
    public void openFrame(String[] args) {
        new Thread(){
            @Override
            public void run(){
                WebcamFrame webcamFrame = new WebcamFrame(utils, args);
            }
        }.start();
    }
}
