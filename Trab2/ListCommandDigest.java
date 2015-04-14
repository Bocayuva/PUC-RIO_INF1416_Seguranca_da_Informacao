/*
* INF1416 - Segurança da Informação
* GRUPO 1
* Autores - 1320614 - Alexandre Werneck
*           1111060 - Carlos Fernando Bocayuva
*
*/


public class ListCommandDigest {


	private String nome;
	private String digest;
	private String tipo_digest;
	
	public static ListCommandDigest getInstance(String linha) throws Exception {

		ListCommandDigest lis = new ListCommandDigest();
		String[]   itens  = linha.split(" ");

		lis.setNome(itens[0]);		
		lis.setDigest(itens[1]);
		lis.setTipoDigest(itens[2]);

		return lis;
        
    }

	public String getNome(){
		return this.nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	public String getDigest(){
		return this.digest;
	}
	public void setDigest(String digest){
		this.digest = digest;
	}
	public String getTipoDigest(){
		return this.tipo_digest;
	}
	public void setTipoDigest(String tipo_digest){
		this.tipo_digest = tipo_digest;
	}
	
	


}