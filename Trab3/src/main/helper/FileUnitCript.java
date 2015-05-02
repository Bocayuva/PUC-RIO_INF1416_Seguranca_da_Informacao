package main.helper;

import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class FileUnitCript {

	private String NomeSecreto;
	private String NomeCodigo;
	private String Status;
	private PrivateKey privKey;
	private PublicKey  pubKey;
	private Key symKey;
	private String folderUrl;
	private String fileDecript;
	private String keyphrase;
	
	public FileUnitCript(){
		
	}

	public String getNomeSecreto() {
		return NomeSecreto;
	}

	public void setNomeSecreto(String nomeSecreto) {
		NomeSecreto = nomeSecreto;
	}

	public String getNomeCodigo() {
		return NomeCodigo;
	}

	public void setNomeCodigo(String nomeCodigo) {
		NomeCodigo = nomeCodigo;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public PrivateKey getPrivKey() {
		return privKey;
	}

	public void setPrivKey(PrivateKey privKey) {
		this.privKey = privKey;
	}

	public PublicKey getPubKey() {
		return pubKey;
	}

	public void setPubKey(PublicKey pubKey) {
		this.pubKey = pubKey;
	}

	public Key getSymKey() {
		return symKey;
	}

	public void setSymKey() {
		byte[] seed = null;
		byte[] indexEnv = Utility.getBytesFromFile(this.getFolderUrl() + "/" +  this.getNomeCodigo() + ".env");
		
		try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		    cipher.init(Cipher.DECRYPT_MODE, this.getPrivKey());
		    seed = cipher.doFinal(indexEnv);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.symKey = Utility.getKeyFromRandom(seed);
	}

	public String getFolderUrl() {
		return folderUrl;
	}

	public void setFolderUrl(String folderUrl) {
		this.folderUrl = folderUrl;
	}

	public String getFileDecript() {
		return fileDecript;
	}

	public void setFileDecript(String fileDecript) {
		this.fileDecript = fileDecript;
	}

	public String getKeyphrase() {
		return keyphrase;
	}

	public void setKeyphrase(String keyphrase) {
		this.keyphrase = keyphrase;
	}
	
	public boolean checarAutenticidadeIntegridade(){
		byte[] indexAsd = Utility.getBytesFromFile(this.getFolderUrl() + "/" +  this.getNomeCodigo() + ".asd");
		byte[] bSignature = null;
		try{
			Signature signature = Signature.getInstance("MD5WithRSA");
		    signature.initSign(this.getPrivKey());
		    signature.update(indexAsd);		    
		    bSignature = signature.sign();
		    
		    signature.initVerify(this.getPubKey());
		    signature.update(indexAsd);
		    
		    if(signature.verify(bSignature)) {
				return true;
		    }
		    return false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public void decriptoFile() throws Exception {
		byte[] plainText = null;
		byte[] indexEnc = Utility.getBytesFromFile(this.getFolderUrl() + "/" + this.getNomeCodigo()  + ".enc");
		try{
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, this.getSymKey());
		    plainText = cipher.doFinal(indexEnc);
		}catch(Exception e){
			e.printStackTrace();
		}
		String outputFile = this.getFolderUrl() + "/decripto/" + this.getNomeSecreto();
		FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(plainText);
        outputStream.close();
        this.setFileDecript(outputFile);
	}
	
}
