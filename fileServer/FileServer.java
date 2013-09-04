package fileServer;
import java.rmi.*;

import java.rmi.registry.*;
import java.rmi.server.*;


public class FileServer{
	String name;
	Registry registry;
		
		
		public static void main(String args[]) throws Exception{
			
			try{//to start stubs:  rmic rmiServer.Server;
				String name = "fileServer1";
				FileSystem fs = new FileSystem();
		//		Server stub = (Server)UnicastRemoteObject.exportObject(as,0);
				LocateRegistry.createRegistry(1081);
				Registry registry = LocateRegistry.getRegistry(1081);
				registry.rebind(name,fs);
				System.out.println("FileServer: Authentication bound successfully");
			
			}catch (Exception e){
				System.err.println("FileServer: Authentication Server ERROR");
				e.printStackTrace();
			}
		
		}
	
}
