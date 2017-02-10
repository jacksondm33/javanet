package javanet.client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javanet.Utils;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Jackson McClintock
 */
public class WebcamFrame {
    
    ClientUtils utils = null;
    JFrame frame = null;
    BufferedImage image = null;
    
    public WebcamFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        frame = new JFrame("Webcam View");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(utils.screenx / 3, utils.screeny / 3);
        frame.setVisible(true);
        new Thread(){
            @Override
            public void run(){
                utils.webcam.sendRequest(new String[]{"webcam"});
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WebcamFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(;;){
                    utils.webcam.sendRequest(new String[]{"webcam"});
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(WebcamFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run(){
                for(;;){
                    String[] result = utils.webcam.recieveResponse();
                    new Thread(){
                        @Override
                        public void run(){
                            updateImage(result);
                        }
                    }.start();
                }
            }
        }.start();
    }
    public void updateImage(String[] result){
        image = decodeToImage(result[1]);
        frame.getContentPane().removeAll();
        frame.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.revalidate();
        frame.repaint();
    }
    public BufferedImage decodeToImage(String string) {
        int width = ByteBuffer.wrap(Utils.stringToByteArray(string.substring(0, 2))).getInt();
        int height = ByteBuffer.wrap(Utils.stringToByteArray(string.substring(2, 4))).getInt();
        int[] ints = Utils.stringToIntArray(string.substring(4));
        BufferedImage temp_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                temp_image.setRGB(i, j, ints[i * height + j]);
            }
        }
        return temp_image;
    }
}
