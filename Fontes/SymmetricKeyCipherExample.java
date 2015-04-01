import java.security.*;
import javax.crypto.*;
//
// encripta e desencripta utilizando DES
public class SymmetricKeyCipherExample {

  public static void main (String[] args) throws Exception {
    //
    // verifica args e recebe o texto plano
    if (args.length !=1) {
      System.err.println("Usage: java SymmetricKeyCipherExample text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");
    //
    // gera uma chave para o DES
    System.out.println( "\nStart generating DES key" );
    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
    keyGen.init(56);
    Key key = keyGen.generateKey();
    System.out.println( "Finish generating DES key" );
    //
    // define um objeto de cifra DES e imprime o provider utilizado
    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    System.out.println( "\n" + cipher.getProvider().getInfo() );
    //
    // encripta utilizando a chave e o texto plano
    System.out.println( "\nStart encryption" );
    cipher.init(Cipher.ENCRYPT_MODE, key);
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
    // desencripta o texto cifrado com a chave
    System.out.println( "\nStart decryption" );
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] newPlainText = cipher.doFinal(cipherText);
    System.out.println( "Finish decryption: " );
    System.out.println( new String(newPlainText, "UTF8") );
  }
}
