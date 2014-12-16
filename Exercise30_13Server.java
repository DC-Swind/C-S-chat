import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
public class Exercise30_13Server extends JFrame{
    private JTextArea show = new JTextArea();
    
    private static final long serialVersionUID = 1L;
    public void startServer(){
        try{
            ServerSocket serverSocket = new ServerSocket(1888);
            writetoshow(show,"Server started @ " + new Date() + '\n');
            while (true){
                Socket socket = serverSocket.accept();
                writetoshow(show,"New connection accepted " +socket.getInetAddress() + ":" +socket.getPort() +"\n");
                
                serverthread runthread = new serverthread(socket,show);
                Thread thread = new Thread(runthread);
                thread.start();
            }
            
        } catch (IOException ex){
            System.err.println(ex);
            System.out.println("socket connect shut down\n");
        }    
    }

        
    public static void writetoshow(JTextArea show, String string) {
    	show.setCaretPosition(show.getText().length()); 
    	show.append(string);
	}


	Exercise30_13Server(){  
        setLayout(new BorderLayout());
        add(new JScrollPane(show),BorderLayout.CENTER);
        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        startServer();

    }
    public static void main(String[] args) throws Exception {
        Exercise30_13Server server = new Exercise30_13Server();
    }
}
