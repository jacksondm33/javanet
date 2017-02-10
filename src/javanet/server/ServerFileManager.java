package javanet.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class ServerFileManager extends ServerFunction{
    
    public ServerFileManager(ServerUtils utils){
        this.utils = utils;
        this.default_command = "file_manager";
        this.command_list = new String[]{
            "file_manager"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        int index_after_line = 0;
        int line = 1;
        int start_index = 0;
        Process p;
        BufferedReader sr = null;
        StringBuilder sb;
        try {
            p = Runtime.getRuntime().exec("ls -l -a " + args[1]);
            sr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb = new StringBuilder();
        try {
            for(;;){
                index_after_line += 1;
                int int_c = sr.read();
                if(int_c == -1){
                    break;
                }
                char c = (char) int_c;
                if(line == 2 && c == '.'){
                    start_index = index_after_line;
                }
                if(index_after_line >= start_index && line >= 4){
                    sb.append(c);
                }
                if(c == '\n'){
                    index_after_line = 0;
                    line += 1;
                }
                if(index_after_line == 1 && c == 'd' && line >= 4){
                    sb.append("<dir>");
                }
            }
        } catch (IOException ex1) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
        }
        sendResult(new String[]{"file_manager", sb.toString()});
    }

}
