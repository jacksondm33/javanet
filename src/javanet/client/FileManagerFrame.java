package javanet.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javanet.Utils;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jackson McClintock
 */
public class FileManagerFrame {
    
    JFrame base_frame = null;
    JPanel frame = null;
    String current_path = "";
    String copy_file = "";
    String copy_mode = "";
    ClientUtils utils = null;
    
    public FileManagerFrame(ClientUtils utils, String[] args){
        this.utils = utils;
        base_frame = new JFrame("File Manager");
        base_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        base_frame.setLayout(new BorderLayout());
        current_path = "/";
        doPath(current_path);
    }
    public void doPath(String path_name){
        utils.file_manager.sendRequest(new String[]{"file_manager", current_path});
        String[] result = utils.file_manager.recieveResponse();
        String[] path_ls = new String[10000];
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < result[1].length(); i++){
            if(result[1].charAt(i) == '\n'){
                path_ls[count] = sb.toString();
                count++;
                sb = new StringBuilder();
                continue;
            }
            sb.append(result[1].charAt(i));
        }
        frame = new JPanel();
        frame.setLayout(new GridLayout(0, 1));
        JScrollPane scroll = new JScrollPane(frame);
        base_frame.add(scroll, BorderLayout.CENTER);
        JButton button;
        if(!current_path.equals("/")){
            button = new JButton("..");
            button.addActionListener((ActionEvent e) -> {
                changePath(".."); 
            });
            frame.add(button);
        }
        for(String s : path_ls){
            if(s == null){
                break;
            }
            button = new JButton(s);
            button.addActionListener((ActionEvent e) -> {
                if(s.length() >= 5 && s.substring(0,5).equals("<dir>")){
                    changePath(s);
                }else{
                    utils.file_manager.sendRequest(new String[]{"execute", current_path + s});
                } 
            });
            button.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(SwingUtilities.isRightMouseButton(e)){
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem create_file_mb = new JMenuItem("Create new file");
                        create_file_mb.addActionListener((ActionEvent ae) -> {
                            utils.create_file.openFrame(new String[]{current_path, "Create", ""});
                        });
                        JMenuItem create_dir_mb = new JMenuItem("Create new folder");
                        create_dir_mb.addActionListener((ActionEvent ae) -> {
                            utils.create_dir.openFrame(new String[]{current_path});
                        });
                        JMenuItem execute_mb = new JMenuItem("Execute");
                        execute_mb.addActionListener((ActionEvent ae) -> {
                            utils.execute.sendRequest(new String[]{utils.execute.default_command, current_path + Utils.getNameFromText(((JButton) e.getComponent()).getText())});
                        });
                        JMenuItem edit_mb = new JMenuItem("Edit");
                        edit_mb.addActionListener((ActionEvent ae) -> {
                            utils.create_file.sendRequest(new String[]{"edit_file", current_path + Utils.getNameFromText(((JButton) e.getComponent()).getText())});
                            new Thread(){
                                @Override
                                public void run(){
                                    String[] result = utils.create_file.recieveResponse();
                                    utils.create_file.openFrame(new String[]{result[1], "Edit", result[2]});
                                }
                            }.start();
                        });
                        JMenuItem copy_mb = new JMenuItem("Copy");
                        copy_mb.addActionListener((ActionEvent ae) -> {
                            copy_file = current_path + Utils.getNameFromText(((JButton) e.getComponent()).getText());
                            copy_mode = "copy";
                        });
                        JMenuItem cut_mb = new JMenuItem("Cut");
                        cut_mb.addActionListener((ActionEvent ae) -> {
                            copy_file = current_path + Utils.getNameFromText(((JButton) e.getComponent()).getText());
                            copy_mode = "cut";
                        });
                        JMenuItem paste_mb = new JMenuItem("Paste");
                        paste_mb.addActionListener((ActionEvent ae) -> {
                            utils.create_file.sendRequest(new String[]{"copy", copy_file, current_path + Utils.getNameFromFilePath(copy_file)});
                            if(copy_mode.equals("cut")){
                                utils.delete.sendRequest(new String[]{utils.delete.default_command, copy_file});
                            }
                        });
                        JMenuItem rename_mb = new JMenuItem("Rename");
                        rename_mb.addActionListener((ActionEvent ae) -> {
                            utils.rename.openFrame(new String[]{current_path + Utils.getNameFromText(((JButton) e.getComponent()).getText())});
                        });
                        JMenuItem del_mb = new JMenuItem("Delete");
                        del_mb.addActionListener((ActionEvent ae) -> {
                            utils.delete.sendRequest(new String[]{utils.delete.default_command, current_path + Utils.getNameFromText(((JButton) e.getComponent()).getText())});
                        });
                        menu.add(create_file_mb);
                        menu.add(create_dir_mb);
                        if(s.length() >= 5 && s.substring(0,5).equals("<dir>")){
                            
                        }else{
                            menu.add(execute_mb);
                            menu.add(edit_mb);
                        }
                        menu.add(copy_mb);
                        menu.add(cut_mb);
                        if(!copy_file.equals("")){
                            menu.add(paste_mb);
                        }
                        menu.add(rename_mb);
                        menu.add(del_mb);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
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
            frame.add(button);
        }
        base_frame.revalidate();
        base_frame.repaint();
        base_frame.setSize(500, 500);
        base_frame.setVisible(true);    
    }
    public void changePath(String s){
        base_frame.getContentPane().removeAll();
        switch (s) {
            case ".":
                break;
            case "..":
                current_path = current_path.substring(0, Utils.secondToLastIndexOf(current_path, '/') + 1);
                break;
            default:
                current_path = current_path + s.substring(5, s.length()) + "/";
                break;
        }
        doPath(current_path);
    }
    
}
