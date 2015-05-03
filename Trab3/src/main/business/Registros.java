package main.business;

import java.awt.Window;

import main.dao.RegistrosDao;
import main.dao.UsuarioDao;
import main.java.Login;

public class Registros {

	private int Id;
	private Mensagem msg_fk;
	private Usuario user_fk;
	private String arq_name;
	private int num_controle;
	
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
		if (user_fk == null) {
			return 0;
		}else{
			return user_fk.getId();
		}		
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
	public int getNum_controle() {
		return num_controle;
	}
	public void setNum_controle(int num_controle) {
		this.num_controle = num_controle;
	}	
	public static void incluir(Registros registro) {
		RegistrosDao regDao = new RegistrosDao();
		regDao.CriaRegistro(registro);
	}
	public static void adicionarRegistro(int[] id_msg_fk, Usuario[] usuario, String[] file_name) {
		for (int i = 0; i < id_msg_fk.length; i++) {
		
			Registros regIncluir = new Registros();
			regIncluir.setArq_name(file_name[i]);
			
			Mensagem msgItem = new Mensagem();
			msgItem.setId(id_msg_fk[i]);
			regIncluir.setMsg_fk(msgItem);
			msgItem = null;
			
			regIncluir.setNum_controle(Login.getSession());
			regIncluir.setUser_fk(usuario[i]);
			incluir(regIncluir);
			
		}
	}
	public static int buscarUltimoRegistro() {
		RegistrosDao regDao = new RegistrosDao();
		return regDao.buscarUltimoRegistro();
	}
	
}
