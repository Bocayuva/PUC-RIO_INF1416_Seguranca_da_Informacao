package main.business;

import main.dao.TanListDao;
import main.dao.UsuarioDao;
import main.helper.File;
import main.helper.Utility;

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
	public void criarItens(String login_name, int user_tan_list) {
		TanListDao tanDao = new TanListDao();
		Usuario usuario = Usuario.buscarPorLogin(login_name);
		String tanList = "Lista de senhas únicas: \n";
		int contador = 0;
		while (contador < user_tan_list) {
			
			TanList tanItem = new TanList();
			tanItem.setOrder_user(contador + 1);
			tanItem.setUser_fk(usuario);
			
			String tanStr = Utility.geraRandomTan();
			String tanHex = Utility.geraSenha(tanStr + usuario.getSalt());
			
			if (!tanDao.buscarPorTanItemUsuario(tanHex, usuario)) {
				tanList += tanItem.getOrder_user() + " - " + tanStr + "\n";
				tanItem.setTanItem(tanHex);
				tanDao.CriarTanListItem(tanItem);
				contador += 1;
			}			
			
		}
		File.adicionaFinalArquivo("tan/" + login_name + ".txt", tanList); 
	}
	
}
