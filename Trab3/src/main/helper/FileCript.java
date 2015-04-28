package main.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class FileCript {

	public static void adicionaFinalArquivo(String filename, String new_line){
      try{
    	  String workingDir = System.getProperty("user.dir");
    	  PrintWriter writer = new PrintWriter(workingDir + "/src/" + filename, "UTF-8");
    	  writer.println(new_line);
    	  writer.close();
      }catch(Exception e){
    	  System.out.println("=> Erro ao gerar a lista de chaves únicas! ");
      }      

    }
	
	public static byte[] getBytesFromFile(String fileUrl){
		
		InputStream inpStream = null;
		File file = new File(fileUrl);
		if (!file.isFile()) {
			return null;
		}
		 
        byte[] bFile = new byte[(int) file.length()];
 
        try {
        	
        	inpStream = new FileInputStream(file);
		    for (int i = 0; i < bFile.length; i++) {
		    	inpStream.read(bFile);
	        }
	       	inpStream.close();

        }catch(Exception e){
        	e.printStackTrace();
        }
        
		return bFile;
		
	}
	
	public static void criptoPrivateKey(String fileUrl) throws Exception{
		byte[] plainText = "FRASE SECRETA".getBytes("UTF8");
		
		byte[] privateFileBytes = getBytesFromFile(fileUrl);
		
		KeyGenerator keyGen  = KeyGenerator.getInstance("DES");
		SecureRandom secRand = SecureRandom.getInstance("SHA1PRNG");
	    secRand.setSeed(plainText);
	    keyGen.init(56, secRand);
	    Key key              = keyGen.generateKey();
	    	    
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    
	    byte[] cipherText = cipher.doFinal(privateFileBytes);
	    
	    String workingDir = System.getProperty("user.dir");
	    File fil = new File(workingDir + "/src/Keys/userpriv_generated");
	    if (!fil.exists()) {
	    	fil.createNewFile();
		}
	    FileOutputStream fos = new FileOutputStream(workingDir + "/src/Keys/userpriv_generated");
	    fos.write(cipherText);
	    fos.close();
	}
	
	public static byte[] decriptoPrivateKey(String fileUrl) throws Exception{
		byte[] plainText = "FRASE SECRETA".getBytes("UTF8");
		
		byte[] privateFileBytes = getBytesFromFile(fileUrl);
		
		KeyGenerator keyGen  = KeyGenerator.getInstance("DES");
		SecureRandom secRand = SecureRandom.getInstance("SHA1PRNG");
	    secRand.setSeed(plainText);
	    keyGen.init(56, secRand);
	    Key key              = keyGen.generateKey();
	    	    
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    
	    byte[] cipherText = cipher.doFinal(privateFileBytes);
	    
	    return cipherText;
	}
	
}
