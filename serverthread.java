import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;


public class serverthread implements Runnable{
	private Socket socket;
	private DataInputStream inputFromClient = null;
	private DataOutputStream outputToClient = null;
	private JTextArea show = null;
	serverthread(Socket socket,JTextArea show){
		this.socket = socket;
		this.show = show;
		
		try{
			inputFromClient = new DataInputStream(socket.getInputStream());
			outputToClient = new DataOutputStream(socket.getOutputStream());
		}catch(Exception e){
			Exercise30_13Server.writetoshow(show,"create io stream failed!\n");
		}
		
		broadcast.register(outputToClient);

        
	}
	public void run() {
		while(true){
			try{
				String message = inputFromClient.readUTF();
				Exercise30_13Server.writetoshow(show,message + "\n");
				broadcast.lock.lock();
				broadcast.broad(message,outputToClient);
				broadcast.lock.unlock();
			}catch(Exception e){
				Exercise30_13Server.writetoshow(show,"a connect disconnect!\n");
        		break;
			}
			
		}
		
	}

}
