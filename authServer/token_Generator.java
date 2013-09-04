package authServer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;

public class token_Generator extends UnicastRemoteObject implements token_Generator_Interface{
	//private String tokenval;
//	private int i;
	String authServKey;
	HashMap<String, String> publicServerKeys,serverKeys;
	AuthenticationToken authToken;
	Encryptor encryptor;
		
	public token_Generator() throws RemoteException {
		authServKey = "hello";//Integer.toString((int)(Math.random() * 1000));
		System.out.println("Token Generator Initialised!");
		encryptor = new Encryptor();
		publicServerKeys = new HashMap<String, String>();
		
		serverKeys = new HashMap<String, String>();
		serverKeys.put("directory server",new String("2001111"));
		serverKeys.put("fileServer1",new String("191919"));
	}
	

	public byte[][] generate_Auth_Token(String userID) throws RemoteException {
		authToken = new AuthenticationToken(userID);
		String ClientSessionKey;
	
		String password = getPassword(userID);
		
		ClientSessionKey = authToken.getSessKey();
		// Ticket containing [user ID, client n/w address, expiry info, Client-TGS session key] encrypted with TGS 
		String ticket = (userID + " " + authToken.getTimeStamp() + " " +ClientSessionKey);
		byte[] encrTicket = (encryptor.encrypt(ticket.getBytes(), authServKey));
		
		String token = ( ClientSessionKey + " " +authToken.getTimeStamp());
		
		
		byte[][] encrToken = {encrTicket,encryptor.encrypt(token.getBytes(), password)};

		return encrToken;

	}

	public byte[][] getToken(byte[] ticket,byte[] authenticator, String server) throws RemoteException{
		String ClientSessionKey;
		
		byte[] Mashticket = encryptor.decrypt(ticket, authServKey);
		String receivedTicket = new String(Mashticket);
		String rcvTicket[] = receivedTicket.split(" ");
		String user = rcvTicket[0];
		String sessionKey = rcvTicket[2];
		String timestamp = rcvTicket[1];
		
		System.out.println("user: "+user+"  sessionKey: "+sessionKey+ " Time Stamp: "+timestamp);
			
		//ticket containing userID, timestamp, and client<--->Service server key
		String ts = authToken.makeTimeStamp();
		String ServClientKey = authToken.makeSessionKey();
		String srvticket = (user +" "+ authToken.makeTimeStamp()+" " + ServClientKey);
		byte[] incr_Server_Ticket = encryptor.encrypt(srvticket.getBytes(), serverKeys.get(server));
		
		String token = (ServClientKey+" "+ts);
		byte[] incr_token = encryptor.encrypt(token.getBytes(),sessionKey);
		
		byte[][] fullToken = {incr_token,incr_Server_Ticket};
		return fullToken;
		/*
		 * Ticket containing [user ID, client n/w addr, expiry info, client-SS session key] encrypted with SS key.
		 [Client-SS session key, expiry info] encrypted with Client-TGS session key
		 *///String username, String server
		// Ticket containing [user ID, client n/w address, expiry info, Client-TGS session key] encrypted with TGS 
	/*
		//username, timestamp, client-ss-key
		String clientServerKey = authToken.makeSessionKey();
		String ticket1 = (username + " " +authToken.makeTimeStamp() + " "+clientServerKey);
		String encrTicket = new String(encryptor.encrypt(ticket1, serverKeys.get(server)));
		
		String token = (encrTicket+ " " +clientServerKey + " " +authToken.makeTimeStamp());
		
		byte[] encrToken = encryptor.encrypt(token, ClientSessionKey);
		
		
		return encrToken;
	*/
	}
		

	//Method to return the password of the user....IF!
	private	String getPassword(String userID) throws RemoteException{
		

		try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream("users.txt");
			  
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null )   {
			  // Print the content on the console
				  String[] info = strLine.split(" ");
				  
		//		  System.out.println ("username: "+info[0]+"  password: "+info[1]);
				  if(userID.equals(info[0])){
					  return new String(info[1]);
				  }
				  
			  }
			  //Close the input stream
			  	in.close();
				}catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
		return " ";	 
		
	}

}
