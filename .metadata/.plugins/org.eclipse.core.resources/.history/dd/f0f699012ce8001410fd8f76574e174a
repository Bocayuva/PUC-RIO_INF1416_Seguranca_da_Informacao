package test.dao;

import main.business.Grupo;
import main.business.Usuario;
import main.helper.Utility;

import org.junit.Assert;
import org.junit.Test;

public class UsuarioDao {
	
	@Test
	public void AdicionaUsuario(){
		Usuario user = new Usuario();
		user.setUser_name("João da Silva");
		user.setLogin_name("joaosilva");
		Grupo gr = new Grupo();
		gr.setGid(1);
		user.setUser_group_fk(gr);
		user.setUser_tan_list(10);
		user.setUser_url_pub("aa");
		user.setSalt(100);
		user.setUser_pwd(Utility.geraSenha("123456" + user.getSalt()));		
		
		main.dao.UsuarioDao udao = new main.dao.UsuarioDao();
		udao.AdicionaUsuario(user);
	}
	
	@Test
	public void buscarUsuario(){
		Usuario usuario = Usuario.buscar("joaoteste");
		Assert.assertEquals(1, usuario.getUser_group_fk());
		Assert.assertEquals(1, usuario.getId());		
	}
	
	@Test
	public void updateUsuario(){
		Usuario usuario = Usuario.buscar("joaosilva");
		int id          = usuario.getId();
		Assert.assertNotEquals("Update Joao", usuario.getUser_name());
		usuario.setUser_name("Update Joao");
		Usuario.update(usuario);
		Usuario usuario_update = Usuario.buscar("joaosilva");
		Assert.assertEquals(id, usuario_update.getId());
		Assert.assertEquals("Update Joao", usuario_update.getUser_name());
	}

}
