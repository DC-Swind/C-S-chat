import java.io.DataOutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class broadcast {
	public final static int Max_thread = 100;
	public static Lock lock = new ReentrantLock();
	private static DataOutputStream[] output = new DataOutputStream[Max_thread];
	
	
	public static void register(DataOutputStream out){
		for(int i=0; i<Max_thread; i++) if (output[i] == null){
			output[i] = out;
			break;
		}
	}
	
	public static void broad(String message,DataOutputStream send){
		System.out.println("start broad");
		for(int i=0; i<Max_thread; i++) if (output[i] != null){
			if (output[i].equals(send)) continue;
			
			try{
				System.out.println("send a broadcast");
				output[i].writeUTF(message);
			}catch(Exception e){
				output[i] = null;
				System.out.println("broadcast occur question");
			}
		}
	}
	
	
}
