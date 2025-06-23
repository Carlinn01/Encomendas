import java.sql.*;

public class EncomendaDAO {

    // Método para inserir uma encomenda no banco de dados
    public void inserir(Encomenda encomenda) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar(); // Estabelece a conexão
            String sql = "INSERT INTO encomendas (nome_cliente, telefone, produto, modelo, cor, quantidade, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql); // Prepara a query

            stmt.setString(1, encomenda.getNomeCliente());
            stmt.setString(2, encomenda.getTelefone());
            stmt.setString(3, encomenda.getProduto());
            stmt.setString(4, encomenda.getModelo());
            stmt.setString(5, encomenda.getCor());
            stmt.setInt(6, encomenda.getQuantidade());
            stmt.setString(7, encomenda.getStatus()); // novo campo status

            stmt.executeUpdate(); // Executa o comando SQL para inserir
            System.out.println("Encomenda inserida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir encomenda: " + e.getMessage());
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para listar todas as encomendas
    public void listarEncomendas() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM encomendas"; // Query para selecionar todas as encomendas
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Mostra as encomendas na tela
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome do Cliente: " + rs.getString("nome_cliente"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Produto: " + rs.getString("produto"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Cor: " + rs.getString("cor"));
                System.out.println("Quantidade: " + rs.getInt("quantidade"));
                System.out.println("Status: " + rs.getString("status")); // novo campo status
                System.out.println("----------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar as encomendas: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
