package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.business.Grupo;
import main.business.Usuario;
import main.jdbc.ConnectionFactory;

public class UsuarioDao {

	// a conexão com o banco de dados
	private Connection connection;
	
    public UsuarioDao() {
		   this.connection = new ConnectionFactory().getConnection();
	}
    
    public void AdicionaUsuario(Usuario usuario){
    	String sql = "insert into usuarios " +
                "(" +
                "user_name," +
                "login_name," +
                "user_group_fk," +
                "user_password," +
                "user_url_pub," +
                "user_tan_list," +
                "disabled," +
                "salt," +
                "num_acessos," +
                "num_consultas," +
                "created_at," +
                "blocked_at" +
                ")" +
                " values (?,?,?,?,?,?,?,?, 0, 0, now(), now())";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);

            // seta os valores
            stmt.setString(1, usuario.getUser_name());
            stmt.setString(2, usuario.getLogin_name());
            stmt.setInt(3, usuario.getUser_group_fk());
            stmt.setString(4, usuario.getUser_pwd());
            stmt.setBytes(5, usuario.getUser_url_pub());
            stmt.setInt(6, usuario.getUser_tan_list());            
            stmt.setBoolean(7, false);
            stmt.setString(8, usuario.getSalt());

            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	public Usuario buscarPorLogin(String login_name) {
		String sql = "select * from usuarios where login_name = ?";
		Usuario usuario = new Usuario();
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, login_name);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
            	
				usuario.setId(res.getInt("id"));
				usuario.setDisabled(res.getBoolean("disabled"));
				usuario.setLogin_name(login_name);
				usuario.setUser_name(res.getString("user_name"));
				usuario.setUser_tan_list(res.getInt("user_tan_list"));
				usuario.setUser_url_pub(res.getBytes("user_url_pub"));
				usuario.setUser_pwd(res.getString("user_password"));
				usuario.setSalt(res.getString("salt"));
				usuario.setBlocked_at(res.getTimestamp("blocked_at"));
				usuario.setNum_acessos(res.getInt("num_acessos"));
				usuario.setNum_consultas(res.getInt("num_consultas"));
				
				GrupoDao grDao = new GrupoDao();
				Grupo gr = new Grupo();
				grDao.buscar(res.getInt("user_group_fk"), gr);
				
				usuario.setUser_group_fk(gr);
				
			}
            
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return usuario;
	}

	public void update(Usuario usuario) {
		
		String sql = "update usuarios " +
				"set user_name = ?" +
                " , login_name = ?" +
                " , user_group_fk = ?" +
                " , user_password = ?" +
                " , user_url_pub = ?" +
                " , user_tan_list = ?" +
                " , disabled = ?" +
                " , salt = ?" +
                " , blocked_at = ?" +
                " , num_acessos = ?" +
                " , num_consultas = ?" +
				" where id = ?";
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, usuario.getUser_name());
            stmt.setString(2, usuario.getLogin_name());
            stmt.setInt(3, usuario.getUser_group_fk());
            stmt.setString(4, usuario.getUser_pwd());
            stmt.setBytes(5, usuario.getUser_url_pub());
            stmt.setInt(6, usuario.getUser_tan_list());            
            stmt.setBoolean(7, usuario.isDisabled());
            stmt.setString(8, usuario.getSalt());
            stmt.setTimestamp(9, usuario.getBlocked_at());
            stmt.setInt(10, usuario.getNum_acessos());
            stmt.setInt(11, usuario.getNum_consultas());
            stmt.setInt(12, usuario.getId());
            
			stmt.executeUpdate();
			stmt.close();
				
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }		
	}

	public Usuario buscar(int user_fk) {
		String sql = "select * from usuarios where id = ? ";
		Usuario usuario = null;
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_fk);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
            	usuario = new Usuario();
				usuario.setId(user_fk);
				usuario.setDisabled(res.getBoolean("disabled"));
				usuario.setLogin_name(res.getString("login_name"));
				usuario.setUser_name(res.getString("user_name"));
				usuario.setUser_tan_list(res.getInt("user_tan_list"));
				usuario.setUser_url_pub(res.getBytes("user_url_pub"));
				usuario.setUser_pwd(res.getString("user_password"));
				usuario.setSalt(res.getString("salt"));
				usuario.setBlocked_at(res.getTimestamp("blocked_at"));
				usuario.setNum_acessos(res.getInt("num_acessos"));
				usuario.setNum_consultas(res.getInt("num_consultas"));
				
				GrupoDao grDao = new GrupoDao();
				Grupo gr = new Grupo();
				
				grDao.buscar(res.getInt("user_group_fk"), gr);
				usuario.setUser_group_fk(gr);
				
			}
            
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return usuario;
	}

	public int totalUsuarios() {
			
		String sql = "select count(*) as total from usuarios";
		int total_usuarios = 0;
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
            	
            	total_usuarios = res.getInt("total");
				
			}
            
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return total_usuarios;
		
	}

	public void apagarTodos() {
		
		String sql = "delete from usuarios";
		try {
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}

	public void apagarUsuario(int id) {
		String sql = "delete from usuarios where id = ?";
		try {
            
            PreparedStatement stmt = connection.prepareStatement(sql);            
            stmt.setInt(1, id);           
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
}
