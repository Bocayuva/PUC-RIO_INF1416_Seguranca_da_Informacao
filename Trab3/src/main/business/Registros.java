package main.business;

public class Registros {

	private int Id;
	private Mensagem msg_fk;
	private Usuario user_fk;
	private String arq_name;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getMsg_fk() {
		return msg_fk.getId();
	}
	public void setMsg_fk(Mensagem msg_fk) {
		this.msg_fk = msg_fk;
	}
	public int getUser_fk() {
		return user_fk.getId();
	}
	public void setUser_fk(Usuario user_fk) {
		this.user_fk = user_fk;
	}
	public String getArq_name() {
		return arq_name;
	}
	public void setArq_name(String arq_name) {
		this.arq_name = arq_name;
	}
	
}