package directoryServer;
import java.rmi.*;

import java.rmi.registry.*;
import java.rmi.server.*;


public class Directory_Server{
	String name;
	Registry registry;
		
		
		public static void main(String args[]) throws Exception{
			
			try{//to start stubs:  rmic rmiServer.Server;
				String name = "directoryServer";
				Directory mydirectory = new Directory();
		//		Server stub = (Server)UnicastRemoteObject.exportObject(as,0);
				LocateRegistry.createRegistry(1080);
				Registry registry = LocateRegistry.getRegistry(1080);
				registry.rebind(name,mydirectory);
				System.out.println("Directory Server: Authentication bound successfully");
			
			}catch (Exception e){
				System.err.println("Directory Server: Authentication Server ERROR");
				e.printStackTrace();
			}
		
		}
	
}
