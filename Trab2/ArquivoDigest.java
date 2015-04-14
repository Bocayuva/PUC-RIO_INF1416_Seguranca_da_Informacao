/*
* @Classe ArquivoDigest
			Contém informações dos digests presentes no arquivo da lista
* INF1416 - Segurança da Informação
* GRUPO 1
* Autores - 1320614 - Alexandre Werneck
*           1111060 - Carlos Fernando Bocayuva
*
*/


public class ArquivoDigest {


	private String nome;
	private String digest;
	private String tipo_digest;
	private String digest_2;
	private String tipo_digest_2;
	
	public static ArquivoDigest getInstance(String linha) throws Exception {

		ArquivoDigest dig = new ArquivoDigest();
		String[]   itens  = linha.split(" ");

		dig.setNome(itens[0]);
		dig.setTipoDigest(itens[1]);
		dig.setDigest(itens[2]);

		if (itens.length > 4) {
			dig.setTipoDigest2(itens[3]);
			dig.setDigest2(itens[4]);
		}else{
			dig.setTipoDigest2("");
			dig.setDigest2("");
		}

		return dig;
        
    }

	public String getNome(){
		return this.nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	/* Digest 1 */
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
	/* Digest 2 */
	public String getDigest2(){
		return this.digest_2;
	}
	public void setDigest2(String digest){
		this.digest_2 = digest;
	}
	public String getTipoDigest2(){
		return this.tipo_digest_2;
	}
	public void setTipoDigest2(String tipo_digest){
		this.tipo_digest_2 = tipo_digest;
	}
	
	


}