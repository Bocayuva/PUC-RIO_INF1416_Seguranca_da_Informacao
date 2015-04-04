/*
* INF1416 - Segurança da Informação
* GRUPO 1
* Autores - 1320614 - Alexandre Werneck
*           1111060 - Carlos Fernando Bocayuva
*
*/
import java.io.*;
import java.security.*;
import javax.crypto.*;

class OurUseSignature {

    public static void main(String[] args) {

        /* Generate a OUR signature */

        if (args.length != 1) {
            System.out.println("Usage: GenSig nameOfFileToSign");
        }
        else try {
            byte[] plainText = args[0].getBytes("UTF8");

            /* Now we gonna use a keypargenerator to generate our public and private keys */
            System.out.println("Instanciando a classe geradora das chaves RSA");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");  

            /* Define the lenght of our key - 1024 bits */
            System.out.println("Definindo o tamanho da chave em 1024 bits");
            keyGen.initialize(1024);    

            /* Generated both keys */
            System.out.println("Iniciando geração das chaves");
            KeyPair    pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey  pub  = pair.getPublic(); 


            /* Now we start to use our class MySignature */
            System.out.println("Iniciando o uso da classe criada pelo grupo");
            MySignature sign = MySignature.getInstance("SHA1withRSA");
            sign.initSign(priv);
            sign.update(plainText);

            byte[] signature = sign.sign();
            //System.out.println( sign.getProvider().getInfo() );

            System.out.println( "\nAssinatura:" );

            
            StringBuffer buf = new StringBuffer();
            for(int i = 0; i < signature.length; i++) {
               String hex = Integer.toHexString(0x0100 + (signature[i] & 0x00FF)).substring(1);
               buf.append((hex.length() < 2 ? "0" : "") + hex);
            }

            System.out.println( buf.toString() );

            System.out.println( "\nInicia verificação da assinatura" );
            sign.initVerify(pub);
            sign.update(plainText);

            /* Final verification */
            try {
              if (sign.verify(signature)) {
                System.out.println( "Assinatura verificada" );
              } else{
                System.out.println( "Assinatura falhou" );
              } 
            } catch (Exception se) {
              System.out.println( "Assinatura falhou" + se.toString() );
            }


        } catch (Exception e) {

            System.err.println("Caught exception " + e.toString());

        }
    }
}

