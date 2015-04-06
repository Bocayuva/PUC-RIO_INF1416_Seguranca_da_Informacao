/*
* INF1416 - Segurança da Informação
* GRUPO 1
* Autores - 1320614 - Alexandre Werneck
*           1111060 - Carlos Fernando Bocayuva
*
*/
import java.security.*;
import javax.crypto.*;

public class MySignature {

    /* 
    * @pubi - armazena na instancia da classe a chave pública
    * @priv - armazena na instancia da classe a chave privada
    * @md   - armazena uma instancia da classe MessageDigest
    * @cipher - armazena uma instancia da classe Cipher - criptografia
    */
    private PublicKey pubi;
    private PrivateKey priv;
    private MessageDigest md;
    private Cipher cipher;
    private byte[] digest;

    /*
    * Metodo estatico que inicializa classe MySignature
    * params:
    * @algo - string contendo algoritmo de hash, passado como parametro para o MD
              concatenado com with
              mais o nome do metodo de criptografia
              Ex: SHA1withRSA
    */
    public static MySignature getInstance(String algo) throws NoSuchAlgorithmException {

        String[]   crypts    = algo.split("with");
        Provider[] providers = Security.getProviders();
        
        MySignature mySig    = new MySignature();
        mySig.md             = MessageDigest.getInstance(crypts[0]);   

        try{
            mySig.cipher      = Cipher.getInstance(crypts[1]);
            System.out.println( "\n" + mySig.cipher.getProvider().getInfo() );    
        }catch(Exception e){
            System.out.println("Erro: ao iniciar a classe Cipher - " + e.toString());
        }
        

        return mySig;
    }

    /*
    * Metodo para setar a chave privada no objeto de MySignature
    * params:
    * @pri  - Privatekey - gerado pelo generateparkey
    */
    public void initSign(PrivateKey pri){
        try{
            priv = pri;
        }catch(Exception e){
            System.out.println("Erro ao atribuir chave privada: " + e.toString());
        }        
    }

    /*
    * Metodo para inputar o texto no messagedigest
    * para geração do hash
    * Armazena no atributo digest no objeto de MySignature
    * params:
    * @b  - Array de bytes da palavra fornecida
    */
    public void update(byte b[]){
        md.update(b);
        digest = md.digest(); /* geracao do hash pelo message digest */
    }

    /*
    * Metodo para geração da assinatura
    * no params
    * Retorna arrray de bytes da assinatura gerada
    */
    public byte[] sign(){
        byte ret[] = null;
        ret = digest;            
        ret = cripto(ret, true); /* criptografia do hash gerado + chave privada */      
        return ret;
    }

    /*
    * Metodo para setar a chave publica no objeto de MySignature
    * params:
    * @pub  - PublicKey - gerado pelo generateparkey
    */
    public void initVerify(PublicKey pub){
        try{
            pubi = pub;
        }catch(Exception e){
            System.out.println("Erro ao atribuir chave publica: " + e.toString());
        }
        
    }

    /*
    * Metodo que verifica a autenticidade da assinatura
    * São comparados hash da mensagem com a assinatura
    * decriptografada com a chave publica
    * params:
    * @signature  - array de bytes da assinatura
    * return:
    * true - assinatura verificada
    * false - assinatura não confere
    */
    public boolean verify(byte[] signature){

        byte hash_msg[] = null;
        byte hash_pub[] = null;
        hash_msg = digest;                  
        hash_pub = cripto(signature, false); /* criptografia do hash gerado + chave privada */  

        return MessageDigest.isEqual(hash_msg, hash_pub);
    }

    /*
    * Metodo para retornar o digest como string
    * no params
    * Retorna o digest como string
    */
    public String getDigest(){
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < digest.length; i++) {
           String hex = Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1);
           buf.append((hex.length() < 2 ? "0" : "") + hex);
        }
        return buf.toString();
    }

    /*
    * Metodo para criptografar e decriptografar
    * params:
    * @b - array de bytes
    * @sign - true para chave privada + criptografia
    *         false para chave publica + decriptografia
    * Retorna o array de bytes criptografado ou decriptografado
    */
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