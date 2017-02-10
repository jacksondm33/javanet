package javanet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Jackson McClintock
 */
public class IOUtils {
    
    Socket socket;
    InputStream is;
    OutputStream os;
    
    public IOUtils(Socket socket) throws IOException{
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }
    public synchronized byte read() throws IOException{
        return (byte) is.read();
    }
    public synchronized char readChar() throws IOException{
        byte[] bytes = new byte[2];
        bytes[0] = (byte) is.read();
        bytes[1] = (byte) is.read();
        return Utils.byteArrayToString(bytes).charAt(0);
    }
    public int available() throws IOException{
        return is.available();
    }
    public synchronized void write(String write_string) throws IOException{
        os.write(Utils.stringToByteArray(write_string));
    }
}
