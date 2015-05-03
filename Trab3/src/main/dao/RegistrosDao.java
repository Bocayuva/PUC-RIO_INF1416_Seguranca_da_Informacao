package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.business.Grupo;
import main.business.Mensagem;
import main.business.Registros;
import main.business.Usuario;
import main.jdbc.ConnectionFactory;

public class RegistrosDao {

	// a conexão com o banco de dados
	private Connection connection;
	
    public RegistrosDao() {
		   this.connection = new ConnectionFactory().getConnection();
	}
    
    public void CriaRegistro(Registros registro){
    	String sql = "insert into registros " +
                "(" +
                "id_msg_fk," +
                "id_user_fk," +
                "arq_name," +
                "num_controle," +
                "created_at" +
                ")" +
                " values (?,?,?,?,now())";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);

            // seta os valores
            stmt.setInt(1, registro.getMsg_fk());
            if (registro.getUser_fk() != 0) {
            	stmt.setInt(2, registro.getUser_fk());
			}else{
				stmt.setObject(2, null);
			}
            stmt.setString(3, registro.getArq_name());
            stmt.setInt(4, registro.getNum_controle());

            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	public Registros buscar(int id) {
		String sql = "select * from registros where id = ? ";
		Registros registro = null;
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
            	registro = new Registros();
				registro.setArq_name(res.getString("arq_name"));
				
				Mensagem msg = new Mensagem();				
				msg.setId(res.getInt("id_msg_fk"));
				registro.setMsg_fk(msg);
				
				Usuario user = Usuario.buscar(res.getInt("id_user_fk"));
				registro.setUser_fk(user);
				
				registro.setNum_controle(res.getInt("num_controle"));
			}
            
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return registro;
	}

	public void apagarTodos() {
		
		String sql = "delete from registros";
		try {
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}

	public int buscarUltimoRegistro() {
		String sql = "select num_controle "+
						"from registros "+
						"order by num_controle desc "+
						"limit 1 ";
		int controle = 0;
		try {
            // prepared statement para inserção
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
            	controle = res.getInt("num_controle");
			}
            
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return controle;
	}
	
}


