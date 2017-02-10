package javanet.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 *
 * @author Jackson McClintock
 */
public class CreateFileFrame {
    
    JFrame frame = null;
    GridBagConstraints gc = new GridBagConstraints();
    ClientUtils utils = null;
    
    public CreateFileFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        frame = new JFrame(args[1] + " File");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.ipadx = 150;
        gc.ipady = 10;
        gc.anchor = GridBagConstraints.NORTH;
        JTextField file_field = new JTextField();
        file_field.setText(args[0]);
        JTextArea code_area = new JTextArea();
        code_area.setText(args[2]);
        JButton create_bt = new JButton("Save");
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(code_area);
        frame.add(new JLabel("Enter file to " + args[1] + ":", JLabel.CENTER), gc);
        gc.gridx = 1;
        frame.add(file_field, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        frame.add(new JLabel("Enter file content:", JLabel.CENTER), gc);
        gc.gridy = 2;
        gc.gridheight = 3;
        gc.gridwidth = 2;
        gc.ipady = 300;
        frame.add(scroll, gc);
        gc.gridx = 1;
        gc.gridy = 5;
        gc.gridheight = 1;
        gc.gridwidth = 1;
        gc.ipady = 10;
        frame.add(create_bt, gc);
        frame.pack();
        frame.setLocation(utils.screenx / 3, utils.screeny / 3);
        frame.setVisible(true);
        create_bt.addActionListener((ActionEvent ae) -> {
            utils.create_file.sendRequest(new String[]{utils.create_file.default_command, file_field.getText(), code_area.getText()});
            frame.dispose();
        });
    }
}
