package javanet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOUtils {
    
    Socket socket;
    InputStream is;
    OutputStream os;
    
    public IOUtils(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    /**
     *
     * @return The Single byte read
     * @throws IOException
     */
    public synchronized byte read() throws IOException {
        return (byte) is.read();
    }

    /**
     *
     * @return Number of bytes available to read
     * @throws IOException
     */
    public int available() throws IOException {
        return is.available();
    }

    /**
     *
     * @param data  byte[] of data to write
     * @throws IOException
     */
    public synchronized void write(byte[] data) throws IOException {
        os.write(data);
    }
}
