package client;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
//import logger.Logger;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import java.io.*;



public class Encryptor{    
	KeyGenerator keygenerator;
	SecretKey myDesKey;
	Cipher desCipher,unDesCipher;
	public static final int bits = 256;

	
	
	
	
	
	public Encryptor(){
		try{
		keygenerator = KeyGenerator.getInstance("AES");
	    myDesKey = keygenerator.generateKey();
	    
	 
		
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
	}
	
	public byte[] encrypt(byte[] text, String key){
	//	System.out.println("encrypting");
		byte[] textEncrypted = null; 
		try{
			//keygenerator.init(bits); // 192 and bits bits may not be available
			byte[] keyPadded = new byte[bits / 8];
			
			for(int i = 0; i < bits / 8 && i < key.length(); i++){
				keyPadded[i] = (byte)key.charAt(i);
			}

			/* Generate the secret key specs. */
			SecretKeySpec mykey = new SecretKeySpec(keyPadded, "AES");
			
			   // Create the cipher 
		    desCipher = Cipher.getInstance("AES");

			
			// Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, mykey);
		    
		    // Encrypt the text
		    textEncrypted = desCipher.doFinal(text);

		    return (textEncrypted);

		    
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			System.out.println("error in desCipher.init");
		//} 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println();
		return textEncrypted;
	}

	
public byte[] decrypt(byte[] textEncrypted, String key){
		byte[] decrypted = null; 
		try{
			//keygenerator.init(bits); // 192 and bits bits may not be available
			byte[] keyPadded = new byte[bits / 8];
			
			for(int i = 0; i < bits / 8 && i < key.length(); i++){
				keyPadded[i] = (byte)key.charAt(i);
			}
	
			/* Generate the secret key specs. */
			SecretKeySpec mykey = new SecretKeySpec(keyPadded, "AES");
			
			   // Create the cipher 
		    desCipher = Cipher.getInstance("AES");
	
			
			// Initialize the cipher for encryption
		    desCipher.init(Cipher.DECRYPT_MODE, mykey);
		    
		    // Encrypt the text
		    decrypted = desCipher.doFinal(textEncrypted);
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			System.out.println("error in desCipher.init");
		//} 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decrypted;
	}
}

/*	public static void main(String args[]){
		Encryptor enc = new Encryptor();
		byte[] n = enc.encrypt("hello there alkjfalkjd".getBytes());
		System.out.println("returned:  "+n);
		
	}

}*/ 

