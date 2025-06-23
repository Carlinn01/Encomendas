import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class DeletarEncomenda extends JFrame {
    private JComboBox<String> encomendasComboBox;

    public DeletarEncomenda() {
        setTitle("Deletar Encomenda");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Criando o painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Criando o ComboBox para listar as encomendas
        encomendasComboBox = new JComboBox<>();
        loadEncomendas();

        // Criando o botão para deletar a encomenda
        JButton deletarButton = new JButton("Deletar Encomenda");

        // Adicionando os componentes ao painel
        panel.add(new JLabel("Selecione uma encomenda para deletar:"));
        panel.add(encomendasComboBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(deletarButton);

        // Adicionando o painel à tela
        add(panel, BorderLayout.CENTER);

        // Ação do botão deletar
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encomendaSelecionada = (String) encomendasComboBox.getSelectedItem();
                if (encomendaSelecionada != null) {
                    int idEncomenda = Integer.parseInt(encomendaSelecionada.split(" - ")[0]); // Extrair o ID da
                                                                                              // encomenda
                    deleteEncomenda(idEncomenda); // Deletar a encomenda
                    JOptionPane.showMessageDialog(DeletarEncomenda.this, "Encomenda deletada com sucesso!");
                    loadEncomendas(); // Atualizar o ComboBox após a exclusão
                }
            }
        });
    }

    // Método para carregar as encomendas no ComboBox
    private void loadEncomendas() {
        encomendasComboBox.removeAllItems(); // Limpar o ComboBox antes de adicionar as encomendas
        ArrayList<String> encomendas = getEncomendas();
        for (String encomenda : encomendas) {
            encomendasComboBox.addItem(encomenda); // Adicionar cada encomenda ao ComboBox
        }
    }

    // Método para pegar as encomendas do banco de dados
    private ArrayList<String> getEncomendas() {
        ArrayList<String> encomendas = new ArrayList<>();
        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT id, nome_cliente, produto FROM encomendas";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeCliente = rs.getString("nome_cliente");
                String produto = rs.getString("produto");
                encomendas.add(id + " - " + nomeCliente + " - " + produto); // Adiciona a encomenda no formato "ID -
                                                                            // Nome - Produto"
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar as encomendas: " + e.getMessage());
        }
        return encomendas;
    }

    // Método para deletar a encomenda no banco de dados
    private void deleteEncomenda(int idEncomenda) {
        try (Connection conn = Conexao.conectar()) {
            String sql = "DELETE FROM encomendas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEncomenda);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar encomenda: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeletarEncomenda().setVisible(true);
            }
        });
    }
}
