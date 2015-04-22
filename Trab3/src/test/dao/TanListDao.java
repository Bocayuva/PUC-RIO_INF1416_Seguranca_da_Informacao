package test.dao;

import java.util.ArrayList;
import java.util.List;

import main.business.TanList;
import main.business.Usuario;

import org.junit.Assert;
import org.junit.Test;

public class TanListDao {

	@Test
	public void CriarTanListItem(){
		TanList tanlist = new TanList();
		tanlist.setTanItem("md53175bce1d3201d16594cebf9d7eb3");
		tanlist.setOrder_user(1);
		Usuario user = new Usuario();
		user.setId(47);
		tanlist.setUser_fk(user);
		
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		tanDao.CriarTanListItem(tanlist);
	}
	
	@Test
	public void BuscarTanListItensUsuario(){
		List<TanList> tan_itens = new ArrayList<TanList>();
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		tanDao.buscarItens(tan_itens, 1);
		Assert.assertNotEquals(0, tan_itens.size());
		for (int i = 0; i < tan_itens.size(); i++) {
			Assert.assertEquals(1, tan_itens.get(i).getUser_fk());	
		}
	}
	
	@Test
	public void ApagarRegistro(){
		
	}
	
}