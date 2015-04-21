package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.business.TanList;
import main.jdbc.ConnectionFactory;

public class TanListDao {

	// a conexão com o banco de dados
		private Connection connection;
		
	    public TanListDao() {
			   this.connection = new ConnectionFactory().getConnection();
		}
	    
	    public void CriarTanListItem(TanList tanlist){
	    	String sql = "insert into tan_lists " +
	                "(" +
	                "tan_item," +
	                "id_user_fk," +
	                "created_at" +
	                ")" +
	                " values (?,?,now())";

	        try {
	            // prepared statement para inserção
	            PreparedStatement stmt = connection.prepareStatement(sql);

	            // seta os valores
	            stmt.setString(1, tanlist.getTanItem());
	            stmt.setInt(2, tanlist.getUser_fk());

	            // executa
	            stmt.execute();
	            stmt.close();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
}
