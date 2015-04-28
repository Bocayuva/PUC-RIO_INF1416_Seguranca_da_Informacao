package test.dao;

import java.util.ArrayList;
import java.util.List;

import main.business.TanList;
import main.business.Usuario;
import main.helper.Utility;

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
	
	@Test
	public void criarListaUsuario(){
		Usuario usuario = Usuario.buscar(1);
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		List<TanList> tan_itens = new ArrayList<TanList>();
		tanDao.buscarItens(tan_itens, usuario.getId());
		for (int i = 0; i < tan_itens.size(); i++) {
			tanDao.apagarRegistro(tan_itens.get(i).getId());
		}
		
		tan_itens.removeAll(tan_itens);
		tanDao.buscarItens(tan_itens, usuario.getId());
		Assert.assertEquals(0, tan_itens.size());	
		
		int tam_list = 5;
		for (int i = 0; i < tam_list; i++) {
			TanList tanItem = new TanList();
			tanItem.setUser_fk(usuario);
			tanItem.setOrder_user(i + 1);
			String tanStr = i + usuario.getSalt();
			tanItem.setTanItem(Utility.geraSenha(tanStr));			
			tanDao.CriarTanListItem(tanItem);
		}
		tanDao.buscarItens(tan_itens, usuario.getId());
		Assert.assertEquals(5, tan_itens.size());
	}
	
}
