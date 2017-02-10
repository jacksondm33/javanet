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
public class ServerTaskManager extends ServerFunction{
    
    public ServerTaskManager(ServerUtils utils){
        this.utils = utils;
        this.default_command = "task_manager";
        this.command_list = new String[]{
            "task_manager",
            "kill"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        switch(args[0]){
            case "task_manager":
                int index_after_line = 0;
                int line = 1;
                int start_index = 0;
                Process p;
                BufferedReader sr = null;
                StringBuilder sb;
                try {
                    p = Runtime.getRuntime().exec("ps -A");
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
                        if(line == 1 && c == 'C'){
                            start_index = index_after_line;
                        }
                        if(index_after_line >= start_index && line >= 2){
                            sb.append(c);
                        }
                        if(c == '\n'){
                            index_after_line = 0;
                            line += 1;
                        }
                    }
                } catch (IOException ex1) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                }
                sendResult(new String[]{"task_manager", sb.toString()});
                break;
            case "kill":
                try {
                    Runtime.getRuntime().exec("killall " + args[1]);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
        }
    }
}
