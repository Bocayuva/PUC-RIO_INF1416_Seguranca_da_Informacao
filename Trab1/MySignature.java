import java.security.*;
import javax.crypto.*;

public class MySignature {

    /* 
    * @pubi - armazena na instancia da classe a chave pública
    * @priv - armazena na instancia da classe a chave privada
    * @md   - armazena uma instancia da classe MessageDigest
    * @cipher - armazena uma instancia da classe Cipher
    */
    private PublicKey pubi;
    private PrivateKey priv;
    private MessageDigest md;
    private Cipher cipher;
    private byte[] digest;

    /*
    * Static method to initialize the MySignature class
    * params:
    * @algo - string contendo algoritmo de hash, passado como parametro para o MD
              concatenado com with
              mais o nome do metodo de criptografia
              Ex: SHA1withRSA
    */
    public static MySignature getInstance(String algo) throws NoSuchAlgorithmException {

        String[]   crypts    = algo.split("with");
        Provider[] providers = Security.getProviders();
        
        MySignature mySig = new MySignature();
        mySig.md          = MessageDigest.getInstance(crypts[0]);   

        try{
            mySig.cipher      = Cipher.getInstance(crypts[1]);
            System.out.println( "\n" + mySig.cipher.getProvider().getInfo() );    
        }catch(Exception e){
            System.out.println("Erro: ao iniciar a classe Cipher - " + e.toString());
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
        digest = md.digest();
    }

    public byte[] sign(){
        byte ret[] = null;
        ret = digest;            /* geracao do hash pelo message digest */
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
        hash_msg = digest;                  /* geracao do hash pelo message digest */
        hash_pub = cripto(signature, false); /* criptografia do hash gerado + chave privada */  

        return MessageDigest.isEqual(hash_msg, hash_pub);
    }

    public String getDigest(){
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < digest.length; i++) {
           String hex = Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1);
           buf.append((hex.length() < 2 ? "0" : "") + hex);
        }
        return buf.toString();
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