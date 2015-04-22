package main.business;

import main.dao.TanListDao;
import main.dao.UsuarioDao;

public class TanList {

	private int Id;
	private int Order_user;
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
	public int getOrder_user() {
		return Order_user;
	}
	public void setOrder_user(int Order_user) {
		this.Order_user = Order_user;
	}
	public static void apagarRegistro(TanList tanList) {
		TanListDao tanDao = new TanListDao();
		tanDao.apagarRegistro(tanList.getId());	
	}
	
}