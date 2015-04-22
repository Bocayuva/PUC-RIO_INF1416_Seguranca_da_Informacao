package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import main.business.TanList;
import main.business.Usuario;
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
	                "order_user," +
	                "created_at" +
	                ")" +
	                " values (?,?,?,now())";

	        try {
	            // prepared statement para inserção
	            PreparedStatement stmt = connection.prepareStatement(sql);

	            // seta os valores
	            stmt.setString(1, tanlist.getTanItem());
	            stmt.setInt(2, tanlist.getUser_fk());
	            stmt.setInt(3, tanlist.getOrder_user());

	            // executa
	            stmt.execute();
	            stmt.close();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }

		public void buscarItens(List<TanList> tan_itens, int User_fk) {
			String sql = "select * from tan_lists " +
	                	 "where id_user_fk = ? " +
	                	 "order by order_user";
			
			try {
	            // prepared statement para inserção
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, User_fk);
	            ResultSet res = stmt.executeQuery();
	            
	            while (res.next()) {
	            	TanList tanItem = new TanList();
	            	tanItem.setId(res.getInt("id"));
	            	tanItem.setTanItem(res.getString("tan_item"));
	            	tanItem.setOrder_user(res.getInt("order_user"));
	            	Usuario usuario = Usuario.buscar(User_fk);
	            	tanItem.setUser_fk(usuario);
	            	tan_itens.add(tanItem);
	            }
	            stmt.close();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
			
		}

		public void apagarRegistro(int id) {
			String sql = "delete from tan_lists " +
					" where id = ? ";
			try {
	            // prepared statement para inserção
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            
	            stmt.setInt(1, id);
	            
				stmt.execute();
				stmt.close();
					
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }		
		}
	
}