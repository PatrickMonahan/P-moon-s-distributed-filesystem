package fileServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileSystem extends UnicastRemoteObject implements FileSystem_Interface{
		public FileSystem() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		}

		Encryptor encryptor = new Encryptor();
		String fsKey = "191919";
		
	
	public String[] read(byte[] ticket, String filepath) throws RemoteException {
		String ticket_string = new String(encryptor.decrypt(ticket, fsKey));
		String[] parts = ticket_string.split(" ");
		System.out.println("Here's the filename sent to the FS Server: "+filepath);
		return parts;
	}

	public void write() throws RemoteException {
			
	}

}
