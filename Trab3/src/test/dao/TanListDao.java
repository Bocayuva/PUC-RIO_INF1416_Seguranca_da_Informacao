package test.dao;

import main.business.TanList;
import main.business.Usuario;

import org.junit.Test;

public class TanListDao {

	@Test
	public void CriarTanListItem(){
		TanList tanlist = new TanList();
		tanlist.setTanItem("md53175bce1d3201d16594cebf9d7eb3");
		Usuario user = new Usuario();
		user.setId(1);
		tanlist.setUser_fk(user);
		
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		tanDao.CriarTanListItem(tanlist);
	}
	
}
