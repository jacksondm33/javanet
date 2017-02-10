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
public class CreateDirFrame {
    
    JFrame frame = null;
    String current_path = "";
    ClientUtils utils = null;
    
    public CreateDirFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        this.current_path = args[0];
        frame = new JFrame("Create Directory");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2,2));
        JTextField directory_field = new JTextField(current_path);
        JButton create_bt = new JButton("Create Directory");
        frame.add(new JLabel("Enter directory to create:", JLabel.CENTER));
        frame.add(directory_field);
        frame.add(new JLabel(""));
        frame.add(create_bt);
        frame.pack();
        frame.setLocation(utils.screenx / 3, utils.screeny / 3);
        frame.setVisible(true);
        create_bt.addActionListener((ActionEvent ae) -> {
            utils.create_dir.sendRequest(new String[]{utils.create_dir.default_command, directory_field.getText()});
            frame.dispose();
        });
    }
}

