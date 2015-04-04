import java.io.*;
import java.security.*;

public class MySignature {

    /* Our parameters */
    private PublicKey pubi;
    private PrivateKey priv;
    private MessageDigest md;


    public static MySignature getInstance(string Algo){

        Security.getProviders();
        MySignature mySig = new MySignature();
        mySig.md = MessageDigest.getInstance("SHA");        
        return mySig;
    }

    public void initSign(PrivateKey pri){
        try{
            priv = pri;
        } catch () {
            throw new Exception("Chave privada inválida");
        }
    }

    public void update(byte b[]){
        md.update(b);
    }

    public byte[] sign(){
        byte ret[] = null;
        ret = md.digest();
        return ret;
    }

    public void initVerify(PublicKey pub){
        try{
            pubi = pub;
        }catch(){
            throw new Exception("Chave pública inválida")
        }
    }

    public boolean verify(byte[] signedBytes){
        byte b[] = null;
        try {
            b = md.digest();
        } catch () {
            throw new Exception("No SHA digest found");
        }
        //byte sig[] = crypt(signedBytes, pubi);
        return MessageDigest.isEqual(1, 1);
    }

}