package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.business.Grupo;
import main.jdbc.ConnectionFactory;

public class GrupoDao {

	// a conexão com o banco de dados
	private Connection connection;
	
    public GrupoDao() {
		   this.connection = new ConnectionFactory().getConnection();
	}
	
	public void buscar(int gid, Grupo gr) {
		String sql = "select * from grupos where gid = ?";
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, gid);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
				gr.setGid(gid);
				gr.setGrupo_name(res.getString("grupo_name"));				
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	public List<Grupo> buscarTodos() {
		List<Grupo> grItens = new ArrayList<Grupo>();
		String sql = "select * from grupos";
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
            	Grupo grItem = new Grupo();
            	grItem.setGid(res.getInt("gid"));
            	grItem.setGrupo_name(res.getString("grupo_name"));
            	grItens.add(grItem);
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return grItens;
	}

	public Grupo buscarPorNome(String grName) {
		String sql = "select * from grupos where grupo_name = ?";
		Grupo gr = new Grupo();
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, grName);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
				gr.setGid(res.getInt("gid"));
				gr.setGrupo_name(res.getString("grupo_name"));				
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return gr;
	}

	
	
}
