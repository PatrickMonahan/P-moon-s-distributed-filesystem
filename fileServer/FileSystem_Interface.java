package fileServer;
import java.rmi.*;

public interface FileSystem_Interface extends java.rmi.Remote{
		
		public void write() throws RemoteException;
		
		public String[] read(byte[] ticket, String filepath) throws RemoteException;
		
}
