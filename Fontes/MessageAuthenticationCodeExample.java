import java.security.*;
import javax.crypto.*;
//
// Gera o Message Authentication Code (MAC)
public class MessageAuthenticationCodeExample {

  public static void main (String[] args) throws Exception {
    //
    // verifica args e recebe o texto limpo
    if (args.length !=1) {
      System.err.println("Usage: java MessageAuthenticationCodeExample text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");
    //
    // gera uma chave para o algoritmo HmacMD5
    System.out.println( "\nStart generating key" );
    KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
    SecretKey MD5key = keyGen.generateKey();
    System.out.println( "Finish generating key" );
    //
    // define um objeto MAC e o atualiza com o texto limpo
    Mac mac = Mac.getInstance("HmacMD5");
    mac.init(MD5key);
    mac.update(plainText);
    //
    // imprime a informacao do provider utilizado
    System.out.println( "\n" + mac.getProvider().getInfo() );

    // converte o MAC para hexadecimal
    byte[] macFinal = mac.doFinal();
    StringBuffer buf = new StringBuffer();
    for(int i = 0; i < macFinal.length; i++) {
       String hex = Integer.toHexString(0x0100 + (macFinal[i] & 0x00FF)).substring(1);
       buf.append((hex.length() < 2 ? "0" : "") + hex);
    }

    // imprime o MAC em hexadecimal
    System.out.println( "\nMAC: " );
    System.out.println( buf.toString() );
  }
}










