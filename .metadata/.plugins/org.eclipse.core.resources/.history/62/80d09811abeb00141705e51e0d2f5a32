package main.business;

import java.sql.Date;
import java.sql.Timestamp;

import main.dao.UsuarioDao;

public class Usuario {
	
	private int Id;
	private String user_name;
	private String login_name;
	private Grupo user_group_fk;
	private String user_pwd;
	private String user_url_pub;
	private int user_tan_list;
	private boolean disabled;
	private int salt;
	private Timestamp blocked_at;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public int getUser_group_fk() {
		return user_group_fk.getGid();
	}
	public void setUser_group_fk(Grupo user_group_fk) {
		this.user_group_fk = user_group_fk;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_url_pub() {
		return user_url_pub;
	}
	public void setUser_url_pub(String user_url_pub) {
		this.user_url_pub = user_url_pub;
	}
	public int getUser_tan_list() {
		return user_tan_list;
	}
	public void setUser_tan_list(int user_tan_list) {
		this.user_tan_list = user_tan_list;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public int getSalt() {
		return salt;
	}
	public void setSalt(int salt) {
		this.salt = salt;
	}
	public Timestamp getBlocked_at() {
		return blocked_at;
	}
	public void setBlocked_at(Timestamp timestamp) {
		this.blocked_at = timestamp;
	}
	public static Usuario buscarPorLogin(String login_name) {
		UsuarioDao usuDao = new UsuarioDao();
		Usuario usuario = usuDao.buscarPorLogin(login_name);
		return usuario;
	}
	public static void update(Usuario usuario) {
		UsuarioDao usuDao = new UsuarioDao();
		usuDao.update(usuario);		
	}
	public static Usuario buscar(int user_fk) {
		UsuarioDao usuDao = new UsuarioDao();
		Usuario usuario = usuDao.buscar(user_fk);
		return usuario;
	}	
	

}
