import java.security.*;
import javax.crypto.*;
//
// criptografia assimetrica utilizando o RSA.
public class AsymmetricKeyCipherExample {

  public static void main (String[] args) throws Exception {
    //
    // verifica args e recebe o texto plano
    if (args.length !=1) {
      System.err.println("Usage: java AsymmetricKeyCipherExample text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");
    //
    // gera um par de chaves RSA
    System.out.println( "\nStart generating RSA key" );
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(1024);
    KeyPair key = keyGen.generateKeyPair();
    System.out.println( "Finish generating RSA key" );
    //
    // define o objeto de cifra RSA e imprime o provider utilizado   
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
    System.out.println( "\n" + cipher.getProvider().getInfo() );
    //
    // encripta o texto plano utilizando a chave publica
    System.out.println( "\nStart encryption" );
    cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
    byte[] cipherText = cipher.doFinal(plainText);
    System.out.println( "Finish encryption (hex output): " );

    // converte o cipherText para hexadecimal
    StringBuffer buf = new StringBuffer();
    for(int i = 0; i < cipherText.length; i++) {
       String hex = Integer.toHexString(0x0100 + (cipherText[i] & 0x00FF)).substring(1);
       buf.append((hex.length() < 2 ? "0" : "") + hex);
    }

    // imprime o ciphertext em hexadecimal
    System.out.println( buf.toString() );

    //
    // desencripta o texto cifrado utilizando a chave privada
    System.out.println( "\nStart decryption" );
    cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
    byte[] newPlainText = cipher.doFinal(cipherText);
    System.out.println( "Finish decryption: " );
    System.out.println( new String(newPlainText, "UTF8") );
  }
} 
