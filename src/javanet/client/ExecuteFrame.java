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
public class ExecuteFrame {
    
    JFrame frame = null;
    ClientUtils utils = null;
    
    public ExecuteFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        frame = new JFrame("Execute file");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2,2));
        JTextField file_field = new JTextField();
        JButton execute_bt = new JButton("Execute");
        frame.add(new JLabel("Enter file to execute:", JLabel.CENTER));
        frame.add(file_field);
        frame.add(new JLabel(""));
        frame.add(execute_bt);
        frame.setBounds(utils.screenx / 3, utils.screeny / 3 , 500, 100);
        frame.setVisible(true);
        execute_bt.addActionListener((ActionEvent ae) -> {
            utils.execute.sendRequest(new String[]{utils.execute.default_command, file_field.getText()});
            frame.dispose();
        });
    }
}
