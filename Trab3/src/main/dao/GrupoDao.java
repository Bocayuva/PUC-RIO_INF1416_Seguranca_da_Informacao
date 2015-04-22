package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	
	
}