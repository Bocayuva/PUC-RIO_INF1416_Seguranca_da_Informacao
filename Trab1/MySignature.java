import java.io.*;
import java.security.*;
import javax.crypto.*;

public class MySignature {

    /* Our parameters */
    private PublicKey pubi;
    private PrivateKey priv;
    private MessageDigest md;
    private String crypto;
    private Cipher cipher;


    public static MySignature getInstance(String algo) throws NoSuchAlgorithmException {

        String[]   crypts    = algo.split("with");
        Provider[] providers = Security.getProviders();
        
        MySignature mySig = new MySignature();
        mySig.md          = MessageDigest.getInstance(crypts[0]);        
        mySig.crypto      = crypts[1];

        try{
            mySig.cipher      = Cipher.getInstance(crypts[1]);
            System.out.println( "\n" + mySig.cipher.getProvider().getInfo() );    
        }catch(Exception e){
            System.out.println("Erro: ao iniciar a classe Cipher" + e.toString());
        }
        

        return mySig;
    }

    public void initSign(PrivateKey pri){
        /* seto a chave privada na classe */
        priv = pri;
    }

    public void update(byte b[]){
        /* texto fornecido para o message digest */
        md.update(b);
    }

    public byte[] sign(){
        byte ret[] = null;
        ret = md.digest(); /* geracao do hash pelo message digest */
        ret = cripto(ret, true); /* criptografia do hash gerado + chave privada */      
        return ret;
    }

    public void initVerify(PublicKey pub){
        /* seto a chave publica na classe para verificação */
        pubi = pub;
    }

    public boolean verify(byte[] signature){

        byte hash_msg[] = null;
        byte hash_pub[] = null;
        hash_msg = md.digest();              /* geracao do hash pelo message digest */
        hash_pub = cripto(signature, false); /* criptografia do hash gerado + chave privada */  

        return MessageDigest.isEqual(hash_msg, hash_pub);
    }

    private byte[] cripto(byte[] b, boolean sign){
        byte[] ret       = null;
        try{
            if (sign == true) {
                cipher.init(cipher.ENCRYPT_MODE, priv);
            }else{
                cipher.init(cipher.DECRYPT_MODE, pubi);
            }            
            cipher.update(b);
            ret = cipher.doFinal();
        }catch(Exception e){
            System.out.println("Erro: no processo de criptografia do hash - " + e.toString());
        }        
        return ret;
    }

}