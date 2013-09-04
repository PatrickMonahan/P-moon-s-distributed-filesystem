package directoryServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Directory extends UnicastRemoteObject implements Directory_Interface {
			private HashMap<String,String[]> mapping;
			Encryptor encryptor;
			String directoryKey = "2001111";
			
			public Directory() throws RemoteException{
				mapping = new HashMap<String, String[]>();
				mapping.put("/paddy/file.txt",new String[]{"fileServer1","1098"});
				encryptor = new Encryptor();
				
			}
	
			
		
			public String[] getFilePath(String filename, byte[] ticket) throws RemoteException {
				byte[] decrypted_ticket = encryptor.decrypt(ticket, directoryKey);
				System.out.println("Ticket decrypted (directory server) "+ decrypted_ticket);
				
				String ticket_s = new String(decrypted_ticket);
				String [] parts = ticket_s.split(" ");
				String user = parts[0];
				String timestamp = parts[1];
				String sessionkey = parts[2];
				System.out.println("   user: "+user+ "   timestamp: " +timestamp+"   key: "+sessionkey);
				return mapping.get(filename);
				
				
	
	
			}



}
