package javanet.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class ServerCreateFile extends ServerFunction{
    
    public ServerCreateFile(ServerUtils utils){
        this.utils = utils;
        this.default_command = "create_file";
        this.command_list = new String[]{
            "create_file",
            "edit_file",
            "copy"
        };
    }
    
    @Override
    public void processCommand(String[] args) {
        FileInputStream fis;
        FileOutputStream fos;
        byte[] code;
        StringBuilder sb;
        switch(args[0]){
            case "create_file":
                fos = null;
                try {
                    fos = new FileOutputStream(args[1]);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                String write_string = args[2];
                for(int i = 0; i < write_string.length(); i++){
                    try {
                        fos.write((byte) write_string.charAt(i));
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "edit_file":
                fis = null;
                code = null;
                try {
                    fis = new FileInputStream(args[1]);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    code = new byte[fis.available()];
                    fis.read(code);
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                sb = new StringBuilder();
                for(byte b : code){
                    sb.append((char) b);
                }
                sendResult(new String[]{"edit_file", args[1], sb.toString()});
                break;
            case "copy":
                fis = null;
                fos = null;
                code = null;
                try {
                    fis = new FileInputStream(args[1]);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    code = new byte[fis.available()];
                    fis.read(code);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fos = new FileOutputStream(args[2]);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ServerCreateFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fos.write(code);
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerCreateFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
        }
    }
}
