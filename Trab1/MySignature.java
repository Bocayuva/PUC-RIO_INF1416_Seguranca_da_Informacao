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
    private Key secKey;


    public static MySignature getInstance(String algo) throws NoSuchAlgorithmException {

        String[]   crypts    = algo.split("with");

        Provider[] providers = Security.getProviders();

        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        
        MySignature mySig = new MySignature();
        mySig.md          = MessageDigest.getInstance(crypts[0]);        
        mySig.crypto      = crypts[1];
        mySig.secKey      = keyGen.generateKey();

        try{
            mySig.cipher      = Cipher.getInstance("DES/ECB/PKCS5Padding");    
        }catch(Exception e){
            System.out.println("Erro: ao iniciar a classe Cipher" + e.toString());
        }
        

        return mySig;
    }

    public void initSign(PrivateKey pri){
        priv = pri;
    }

    public void update(byte b[]){
        md.update(b);
    }

    public byte[] sign(){
        byte ret[] = null;
        ret = cripto(md.digest());        
        return ret;
    }

    public void initVerify(PublicKey pub){
        pubi = pub;
    }

    public boolean verify(byte[] signedBytes){
        byte b[] = null;
        b = cripto(md.digest());
        return MessageDigest.isEqual(signedBytes, b);
    }

    private byte[] cripto(byte[] b){
        byte[] ret       = null;
        try{
            cipher.init(cipher.ENCRYPT_MODE, secKey);
            cipher.update(b);
            ret = cipher.doFinal(b);
        }catch(Exception e){
            System.out.println("Erro: ao gerar chave" + e.toString());
        }        
        return ret;
    }

}