package test.dao;

import java.util.ArrayList;
import java.util.List;

import main.business.Grupo;
import main.business.TanList;
import main.business.Usuario;
import main.helper.Utility;

import org.junit.Assert;
import org.junit.Test;

public class TanListDao {
	
	@Test
	public void BCriarTanListItem(){
		TanList tanlist = new TanList();
		
		Usuario user = Usuario.buscarPorLogin("joaosilva");
		tanlist.setUser_fk(user);
		
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		
		tanlist.setTanItem(Utility.geraSenha("ABCDE"));
		tanlist.setOrder_user(1);
		
		tanDao.CriarTanListItem(tanlist);
		
		tanlist.setTanItem(Utility.geraSenha("EDCBA"));
		tanlist.setOrder_user(1);
		
		tanDao.CriarTanListItem(tanlist);
		
		boolean retorno = tanDao.buscarPorTanItemUsuario(Utility.geraSenha("ABCDE"), user);
		Assert.assertTrue(retorno);
		
		retorno = tanDao.buscarPorTanItemUsuario(Utility.geraSenha("EDCBA"), user);
		Assert.assertTrue(retorno);
		
		retorno = tanDao.buscarPorTanItemUsuario(Utility.geraSenha("AAAAA"), user);
		Assert.assertFalse(retorno);
		
	}
	
	@Test
	public void CBuscarTanListItensUsuario(){
		List<TanList> tan_itens = new ArrayList<TanList>();
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		
		Usuario user = Usuario.buscarPorLogin("joaosilva");
		tanDao.buscarItens(tan_itens, user.getId());
		Assert.assertEquals(2, tan_itens.size());
		Assert.assertNotEquals(0, tan_itens.size());
		
		for (int i = 0; i < tan_itens.size(); i++) {
			Assert.assertEquals(user.getId(), tan_itens.get(i).getUser_fk());	
		}
	}
	
	@Test
	public void DApagarRegistro(){
		
		List<TanList> tan_itens = new ArrayList<TanList>();
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		
		Usuario user = Usuario.buscarPorLogin("joaosilva");
		tanDao.buscarItens(tan_itens, user.getId());
		
		for (int i = 0; i < tan_itens.size(); i++) {
			tanDao.apagarRegistro(tan_itens.get(i).getId());	
		}
		
		tan_itens.removeAll(tan_itens);
		
		tanDao.buscarItens(tan_itens, user.getId());
		Assert.assertEquals(0, tan_itens.size());
		
	}
	
	@Test
	public void EcriarListaUsuario(){
		Usuario usuario            = Usuario.buscarPorLogin("joaosilva");
		main.dao.TanListDao tanDao = new main.dao.TanListDao();
		List<TanList> tan_itens = new ArrayList<TanList>();
		tanDao.buscarItens(tan_itens, usuario.getId());
		Assert.assertEquals(0, tan_itens.size());
				
		int tam_list = 5;
		String[] itens = new String[]{
				"AAAAA",
				"BBBBB",
				"CCCCC",
				"DDDDD",
				"EEEEE"
		};
		String new_line = "Tan List inicial \n"+
						  "1 - AAAAA \n"+
						  "2 - BBBBB \n"+
						  "3 - CCCCC \n"+
						  "4 - DDDDD \n"+
						  "5 - EEEEE \n";
		Utility.adicionaFinalArquivo("tan/" + usuario.getLogin_name() + ".tan", new_line);
		for (int i = 0; i < tam_list; i++) {
			TanList tanItem = new TanList();
			tanItem.setUser_fk(usuario);
			tanItem.setOrder_user(i + 1);
			String tanStr = itens[i] + usuario.getSalt();
			tanItem.setTanItem(Utility.geraSenha(tanStr));			
			tanDao.CriarTanListItem(tanItem);
		}
		tanDao.buscarItens(tan_itens, usuario.getId());
		Assert.assertEquals(5, tan_itens.size());
	}
	
}
