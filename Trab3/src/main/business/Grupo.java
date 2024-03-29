package main.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public static List<Grupo> buscarTodos(){
		GrupoDao grDao = new GrupoDao();
		List<Grupo> grItens = grDao.buscarTodos();
		return grItens;
		
	}
	public static Grupo buscarPorNome(String grName) {
		GrupoDao grDao = new GrupoDao();
		Grupo gr = grDao.buscarPorNome(grName);
		return gr;
	}
	
}
