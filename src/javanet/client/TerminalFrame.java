package javanet.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Jackson McClintock
 */
public class TerminalFrame {
    
    JFrame frame = null;
    String current_path = "~";
    String os = "";
    ClientUtils utils = null;
    GridBagConstraints gc = new GridBagConstraints();
    
    public TerminalFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        this.os = args[0];
        frame = new JFrame("Terminal Emulator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.ipadx = 150;
        gc.ipady = 10;
        gc.anchor = GridBagConstraints.NORTH;
        JLabel prefix = new JLabel("user@" + os + ":" + current_path + "# ", JLabel.RIGHT);
        JTextField command_field = new JTextField();
        JTextArea result_area = new JTextArea();
        result_area.setEditable(false);
        JButton exit_bt = new JButton("Exit");
        JButton send_command_bt = new JButton("Send command");
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(result_area);
        frame.add(prefix, gc);
        gc.gridx = 1;
        frame.add(command_field, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        frame.add(new JLabel("stdout:", JLabel.CENTER), gc);
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
        frame.add(send_command_bt, gc);
        gc.gridx = 0;
        frame.add(exit_bt, gc);
        frame.pack();
        frame.setLocation(utils.screenx / 3, utils.screeny / 3);
        frame.setVisible(true);
        send_command_bt.addActionListener((ActionEvent ae) -> {
            utils.terminal.sendRequest(new String[]{utils.terminal.default_command, command_field.getText()});
            result_area.setText(utils.terminal.recieveResponse()[1]);
        });
        exit_bt.addActionListener((ActionEvent ae) -> {
            frame.dispose();
        });
    }
}
