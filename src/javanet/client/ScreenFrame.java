package javanet.client;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import javanet.Utils;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Jackson McClintock
 */
public class ScreenFrame {
    
    JFrame frame = null;
    BufferedImage image = null;
    ClientUtils utils = null;
    
    public ScreenFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        frame = new JFrame("Remote Desktop");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(utils.screenx / 3, utils.screeny / 3);
        frame.setVisible(true);
        frame.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
        frame.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
            @Override
            public void keyPressed(KeyEvent e) {
                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        new Thread(){
            @Override
            public void run(){
                for(;;){
                    updateImage();
                }
            }
        }.start();
    }
    public void updateImage(){
        utils.screen.sendRequest(new String[]{"screen"});
        String[] result = utils.screen.recieveResponse();
        image = encodeToImage(result[1]);
        frame.getContentPane().removeAll();
        frame.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.revalidate();
        frame.repaint();
    }
    public BufferedImage encodeToImage(String string) {
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
