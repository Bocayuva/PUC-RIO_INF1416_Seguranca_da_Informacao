package test.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionFactory {

	@Test
	public void testConnection() throws SQLException {
		Connection connection = new main.jdbc.ConnectionFactory().getConnection();
		System.out.println("Conexão aberta!");
		connection.close();
	}
	
}