package javanet.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Jackson McClintock
 */
public class ProxyFrame {
    
    JFrame frame = null;
    ClientUtils utils = null;
    
    public ProxyFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        frame = new JFrame("Proxy setup");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3,2));
        JTextField ip_field = new JTextField();
        JTextField port_field = new JTextField();
        JButton proxy_bt = new JButton("Start proxy");
        frame.add(new JLabel("Enter proxy ip:", JLabel.CENTER));
        frame.add(ip_field);
        frame.add(new JLabel("Enter proxy port:", JLabel.CENTER));
        frame.add(port_field);
        frame.add(new JLabel(""));
        frame.add(proxy_bt);
        frame.setBounds(utils.screenx / 3, utils.screeny / 3 , 500, 150);
        frame.setVisible(true);
        proxy_bt.addActionListener((ActionEvent ae) -> {
            utils.proxy.sendRequest(new String[]{utils.proxy.default_command, ip_field.getText(), port_field.getText()});
            frame.dispose();
        });
    }
}
