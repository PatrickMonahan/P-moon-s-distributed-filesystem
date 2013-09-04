package client;

import java.rmi.*;
import java.rmi.registry.*;

import directoryServer.Directory_Interface;
import fileServer.FileSystem_Interface;

import authServer.token_Generator_Interface;



public class Client {
	static String authSessKey;
	static int authTicTs;
	static String authToken;

	public static void  extractStuff(String s){
		String[] elements = s.split(" ");
		System.out.println("Length of string: " +elements.length);
		authTicTs = Integer.parseInt(elements[1]);
		authSessKey = elements[0];
	}
	
	public static void main(String[] args) {//[B@68e6ff0d

	//	if(System.getSecurityManager() == null){
	//		System.setSecurityManager(new SecurityManager());			
	//	}
		String userID = "paddy";
		Encryptor encryptor = new Encryptor();
		try{
			String name = "authServer";
			
			//***************
			//AUTHENTICATE USER
			
			//get registry
			Registry authentication_Registry = LocateRegistry.getRegistry(1085);
			//retrieve token interface from registry to allow call of token_generator methods
			token_Generator_Interface token = (token_Generator_Interface) authentication_Registry.lookup(name);
			byte[][] output = token.generate_Auth_Token("paddy");
			byte[] ticket = output[0];
			
			String info = new String(encryptor.decrypt(output[1],"hello"));
			//get session key and timestamp from token
			extractStuff(info);
		
			String timestamp = makeTimeStamp();
			
			
			
			//******************
			//Get ticket for directory server
			//make authenticator to send to retrieve server token
			byte[] authenticator = (encryptor.encrypt((timestamp+userID).getBytes(),authSessKey));

			//get a token for a specific server(directory)
			byte[][] serverTicket = token.getToken(ticket, authenticator, "directory server");
			
			
			
			//*********************
			//Contact directory server
			Registry directory_registry = LocateRegistry.getRegistry(1080);
			Directory_Interface directory = (Directory_Interface) directory_registry.lookup("directoryServer");
			String[] fileInfo = directory.getFilePath("/paddy/file.txt", serverTicket[1]);
			System.out.println(fileInfo[0]+" "+fileInfo[1]);
		
			
			
			//*********************
			//get authentication for file server
			//make authenticator to send with request for fileSystem ticket
			String timestamp1 = makeTimeStamp();
			//make authenticator to send to retrieve server token
			byte[] faauthenticator = (encryptor.encrypt((timestamp1+userID).getBytes(),authSessKey));

			//get a token for a specific server(directory)
			byte[][] fsserverTicket = token.getToken(ticket, faauthenticator, "fileServer1");
			//Connect to the file system registry
			Registry file_registry = LocateRegistry.getRegistry(1081);
			FileSystem_Interface fs = (FileSystem_Interface) file_registry.lookup("fileServer1");
			
					
			String[] ticketparts = fs.read(fsserverTicket[1], "I am a file path");
		
			
		}catch(Exception e){
			System.err.println("problem in rmi client");
			e.printStackTrace();
		}

	}
	
	public static String makeTimeStamp(){
		return (Integer.toString((int)System.currentTimeMillis()/1000));
	}

}
