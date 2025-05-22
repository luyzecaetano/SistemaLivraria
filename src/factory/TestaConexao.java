package factory;

import java.sql.SQLException;

public class TestaConexao {
    public static void main(String[] args) throws SQLException{
        java.sql.Connection conn = new ConnectionFactory().getConnection();
        System.out.println("Conexão estabelecida!");
        conn.close();
    }
}
