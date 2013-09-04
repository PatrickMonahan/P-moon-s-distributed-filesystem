package authServer;
import java.rmi.*;

import java.rmi.registry.*;
import java.rmi.server.*;


public class authServer{
	String name;
	Registry registry;
		
		
		public static void main(String args[]) throws Exception{
			
			try{//to start stubs:  rmic rmiServer.Server;
				String name = "authServer";
				token_Generator blanktoken = new token_Generator();
		//		Server stub = (Server)UnicastRemoteObject.exportObject(as,0);
				LocateRegistry.createRegistry(1085);
				Registry registry = LocateRegistry.getRegistry(1085);
				registry.rebind(name,blanktoken);
				System.out.println("Authentication bound successfully");
			
			}catch (Exception e){
				System.err.println("Authentication Server ERROR");
				e.printStackTrace();
			}
		
		}
	
}
