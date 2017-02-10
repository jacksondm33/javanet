package javanet.server;

import com.github.sarxos.webcam.Webcam;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javanet.Utils;

/**
 *
 * @author Jackson McClintock
 */
public class ServerScreen extends ServerFunction{
    
    public ServerScreen(ServerUtils utils){
        this.utils = utils;
        this.default_command = "screen";
        this.command_list = new String[]{
            "screen",
            "mouse_move",
            "mouse_click",
            "keyboard"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        Robot robot = null;
        BufferedImage capture;
        switch(args[0]){
            case "screen":
                try {
                    robot = new Robot();
                } catch (AWTException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                capture = robot.createScreenCapture(new Rectangle(utils.screenx, utils.screeny));
                IntBuffer buffer = IntBuffer.allocate(capture.getWidth() * capture.getHeight() + 2);
                buffer.put(capture.getWidth());
                buffer.put(capture.getHeight());
                for(int i = capture.getMinX(); i <  capture.getMinX() + capture.getWidth(); i++){
                    for(int j = capture.getMinY(); j < capture.getMinY() + capture.getHeight(); j++){
                        buffer.put(capture.getRGB(i, j));
                    }
                }
                sendResult(new String[]{"screen", Utils.intArrayToString(buffer.array())});
                break;
            case "mouse_move":
                try {
                    robot = new Robot();
                } catch (AWTException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                robot.mouseMove(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
                break;
            case "mouse_click":
                try {
                    robot = new Robot();
                } catch (AWTException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                robot.mousePress(Integer.valueOf(args[1]));
                robot.mouseRelease(Integer.valueOf(args[1]));
                break;
            case "keyboard":
                break;
            default:
                break;
        }
    }
}
