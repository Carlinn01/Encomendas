import java.sql.*;

public class Conexao {
    public static void criarBanco() {
        String url = "jdbc:sqlite:loja.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conectado ao banco de dados!");

                String sql = "CREATE TABLE IF NOT EXISTS encomendas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome_cliente TEXT NOT NULL," +
                        "telefone TEXT NOT NULL," +
                        "produto TEXT NOT NULL," +
                        "modelo TEXT NOT NULL," +
                        "cor TEXT NOT NULL," +
                        "quantidade INTEGER NOT NULL," +
                        "status TEXT NOT NULL)"; // <-- adicionado o campo status

                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                System.out.println("Tabela 'encomendas' criada com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }
    }

    public static Connection conectar() throws SQLException {
        String url = "jdbc:sqlite:loja.db";
        Connection conn = DriverManager.getConnection(url);
        System.out.println("Conexão estabelecida com sucesso!");
        return conn;
    }

    public static void main(String[] args) {
        criarBanco();
    }
}
