import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;

import javax.swing.*;

public class Exercise30_13Client extends JFrame{
    private static final long serialVersionUID = 1L;
    private JTextField jtf = new JTextField();
    private JTextField jtf2 = new JTextField();
    private JTextArea jta = new JTextArea();
 
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private Socket socket;
 
    Exercise30_13Client(){
        jta.setEditable(false);//设置不可编辑;
        jta.setCaretPosition(jta.getText().length());   
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        
        panel.setLayout(new BorderLayout());
        panel2.setLayout(new BorderLayout());
        panel.add(new JLabel("Enter name: "), BorderLayout.WEST);
        panel.add(jtf, BorderLayout.CENTER);
        panel2.add(new JLabel("Enter message: "), BorderLayout.WEST);
        panel2.add(jtf2, BorderLayout.CENTER);
        
        jtf.setHorizontalAlignment(JTextField.LEFT);// 文字方向;
        
        jtf2.setHorizontalAlignment(JTextField.LEFT);// 文字方向;
        jtf2.addActionListener(new ButtonListener());
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.add(panel,BorderLayout.NORTH);
        panel3.add(panel2,BorderLayout.CENTER);
        
        setLayout(new BorderLayout());
        add(panel3, BorderLayout.NORTH); 
        add(new JScrollPane(jta), BorderLayout.CENTER);
 
        setTitle("Client");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        try {
            socket = new Socket("localhost", 1888);
            Exercise30_13Server.writetoshow(jta,"connect server successful.\n");
        } catch (Exception ex){
        	Exercise30_13Server.writetoshow(jta,ex.toString() + "\n");
        }
        
        try{
        	toServer = new DataOutputStream(socket.getOutputStream());
        	fromServer = new DataInputStream(socket.getInputStream());
        }catch(Exception e){
        	Exercise30_13Server.writetoshow(jta,"create io stream failed!\n");
        }
        
        listenthread runthread = new listenthread(fromServer,jta);
        Thread thread = new Thread(runthread);
        thread.start();
    }
 
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){   
            try{
                String message = jtf.getText() +" : " +  jtf2.getText();
                toServer.writeUTF(message);
 
                jtf2.setText("");// 清空;
 
                Exercise30_13Server.writetoshow(jta,message + "\n");
          
 
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
 
    public static void main(String[] args){
        new Exercise30_13Client();
    }
}

class listenthread implements Runnable{
	private JTextArea jta = null;
    private DataInputStream input = null;
    
	listenthread(DataInputStream input,JTextArea jta){
		this.input = input;
		this.jta = jta;
	}
	@Override
	public void run() {
		while(true){
			try{
				String message = input.readUTF();
				Exercise30_13Server.writetoshow(jta,message + "\n");
			}catch(Exception e){
				Exercise30_13Server.writetoshow(jta,e.toString() + "\n");
			}
		}
		
	}
	
}
