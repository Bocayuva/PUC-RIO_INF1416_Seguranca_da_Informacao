/*
* INF1416 - Segurança da Informação
* GRUPO 1
* Autores - 1320614 - Alexandre Werneck
*           1111060 - Carlos Fernando Bocayuva
*
*/
import java.io.*;
import java.security.*;

class OurUseSignature {

    public static void main(String[] args) {

        /* Geração e verificação da assinatura */

        if (args.length != 1) {
            System.out.println("Falta de argumento: É necessário fornecer um argumento para a assinatura!");
            System.exit(1);
        }
        else try {
            byte[] plainText = args[0].getBytes("UTF8");

            System.out.println("Instanciando a classe geradora das chaves RSA");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");  

            System.out.println("Definindo o tamanho da chave em 1024 bits");
            keyGen.initialize(1024);    

            System.out.println("Iniciando geração das chaves");
            KeyPair    pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey  pub  = pair.getPublic(); 

            System.out.println("\n\nIniciando o uso da classe - MySignature");
            System.out.println("\nUsamos o algoritmo SHA1 de hash + RSA para criptografia\n");
            MySignature sign = MySignature.getInstance("SHA1withRSA");
            sign.initSign(priv);    
            sign.update(plainText); 

            /* Imprimindo o digest */
            System.out.println( "\nDigest: " + sign.getDigest() );
            byte[] signature = sign.sign();

            System.out.println( "\nAssinatura:" );
            StringBuffer buf = new StringBuffer();
            for(int i = 0; i < signature.length; i++) {
               String hex = Integer.toHexString(0x0100 + (signature[i] & 0x00FF)).substring(1);
               buf.append((hex.length() < 2 ? "0" : "") + hex);
            }
            System.out.println( buf.toString() );

            System.out.println( "\n\nInicia verificação da assinatura: \n" );
            sign.initVerify(pub);
            sign.update(plainText);

            /* Imprimindo o digest */
            System.out.println( "\nDigest: " + sign.getDigest() );

            /* Verificação da assinatura */
            try {
              if (sign.verify(signature)) {
                System.out.println( "\nAssinatura verificada" );
              } else{
                System.out.println( "Assinatura falhou" );
              } 
            } catch (Exception se) {
              System.out.println( "Assinatura falhou" + se.toString() );
            }


        } catch (Exception e) {

            System.err.println("Exceção: " + e.toString());

        }
    }
}

