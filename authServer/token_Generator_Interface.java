package authServer;
import java.rmi.*;

public interface token_Generator_Interface extends java.rmi.Remote{

	
	//return a token of the form 
	public byte[][] getToken(byte[] ticket,byte[] authenticator, String server) throws RemoteException;

	//returns a token of the form
	public byte[][] generate_Auth_Token(String userID) throws RemoteException;
	

}
