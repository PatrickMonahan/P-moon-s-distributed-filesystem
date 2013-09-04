package client;

import java.io.UnsupportedEncodingException;

public class tester {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "hello";
		byte[] b = s.getBytes();
		
		System.out.println("string = "+s+"  Bytes = "+b);
		
		String newstr = new String(b,"UTF-8");
		String toStr = b.toString();
		
		System.out.println("Bytes made in new string: "+newstr+"  tostring: "+toStr);
	
		byte[] bnstr = newstr.getBytes();
		System.out.println(" toString.getBytes(): "+bnstr);
	}

}
