package main.business;

public class TanList {

	private int Id;
	private String TanItem;
	private Usuario user_fk;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTanItem() {
		return TanItem;
	}
	public void setTanItem(String tanItem) {
		TanItem = tanItem;
	}
	public int getUser_fk() {
		return user_fk.getId();
	}
	public void setUser_fk(Usuario user_fk) {
		this.user_fk = user_fk;
	}
	
}
