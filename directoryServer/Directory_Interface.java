package directoryServer;

import java.rmi.*;

public interface Directory_Interface extends java.rmi.Remote {
	
	public String[] getFilePath(String filename,byte[] token) throws RemoteException;

}
