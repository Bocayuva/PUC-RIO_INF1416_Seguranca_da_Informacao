package main.helper;

import java.security.MessageDigest;

public class DigestHelp {

	public static String getDigest(String tipo, byte[] key) throws Exception {
		
	    MessageDigest messageDigest = MessageDigest.getInstance(tipo);
	    messageDigest.update(key);
	    byte [] digest = messageDigest.digest();
	    
	    StringBuffer buf = new StringBuffer();
	    for(int i = 0; i < digest.length; i++) {
	       String hex = Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1);
	       buf.append((hex.length() < 2 ? "0" : "") + hex);
	    }

	    return buf.toString();
	    
	  }
	
}
