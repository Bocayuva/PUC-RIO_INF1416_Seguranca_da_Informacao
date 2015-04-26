package main.business;

import main.dao.GrupoDao;

public class Grupo {

	private int gid;
	private String grupo_name;
	
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGrupo_name() {
		return grupo_name;
	}
	public void setGrupo_name(String grupo_name) {
		this.grupo_name = grupo_name;
	}
	public static Grupo buscar(int user_group_fk) {
		GrupoDao grDao = new GrupoDao();
		Grupo gr = new Grupo();
		grDao.buscar(user_group_fk, gr);
		return gr;
	}
	
}
