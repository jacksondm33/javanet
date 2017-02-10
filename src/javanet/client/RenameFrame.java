package javanet.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javanet.Utils;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Jackson McClintock
 */
public class RenameFrame {
    
    JFrame frame = null;
    ClientUtils utils = null;
    
    public RenameFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        frame = new JFrame("Rename File");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3,2));
        JTextField file_field = new JTextField(args[0]);
        JTextField name_field = new JTextField(Utils.getNameFromFilePath(args[0]));
        JButton rename_bt = new JButton("Rename");
        frame.add(new JLabel("Enter file to rename:", JLabel.CENTER));
        frame.add(file_field);
        frame.add(new JLabel("Enter new name:", JLabel.CENTER));
        frame.add(name_field);
        frame.add(new JLabel(""));
        frame.add(rename_bt);
        frame.pack();
        frame.setLocation(utils.screenx / 3, utils.screeny / 3);
        frame.setVisible(true);
        rename_bt.addActionListener((ActionEvent ae) -> {
            utils.rename.sendRequest(new String[]{utils.rename.default_command, file_field.getText(), Utils.getPathFromFilePath(file_field.getText()) + name_field.getText()});
            frame.dispose();
        });
    }
}