import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VisualizarEncomendas extends JFrame {

    private JTable encomendasTable;
    private DefaultTableModel tableModel;

    public VisualizarEncomendas() {
        setTitle("Visualizar Encomendas");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Modelo da tabela
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nome Cliente", "Telefone", "Produto", "Modelo", "Cor", "Quantidade", "Status" },
                0) {
            // Deixa todas as células não editáveis diretamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        encomendasTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(encomendasTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel dos botões
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton editarButton = new JButton("Editar Encomenda");
        JButton excluirButton = new JButton("Excluir Encomenda");
        JButton voltarButton = new JButton("Voltar");

        panel.add(editarButton);
        panel.add(excluirButton);
        panel.add(voltarButton);

        add(panel, BorderLayout.SOUTH);

        // Carrega os dados na tabela
        loadEncomendas();

        // Ação Editar
        editarButton.addActionListener(e -> editarEncomenda());

        // Ação Excluir
        excluirButton.addActionListener(e -> excluirEncomenda());

        // Ação Voltar
        voltarButton.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });
    }

    private void editarEncomenda() {
        int selectedRow = encomendasTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nomeCliente = (String) tableModel.getValueAt(selectedRow, 1);
            String telefone = (String) tableModel.getValueAt(selectedRow, 2);
            String produto = (String) tableModel.getValueAt(selectedRow, 3);
            String modelo = (String) tableModel.getValueAt(selectedRow, 4);
            String cor = (String) tableModel.getValueAt(selectedRow, 5);
            int quantidade = (int) tableModel.getValueAt(selectedRow, 6);
            String status = (String) tableModel.getValueAt(selectedRow, 7);

            EditarEncomenda editar = new EditarEncomenda(this, id, nomeCliente, telefone, produto, modelo, cor,
                    quantidade, status);
            editar.setVisible(true);

            // Recarregar a tabela depois de editar
            loadEncomendas();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma encomenda para editar.");
        }
    }

    private void excluirEncomenda() {
        int selectedRow = encomendasTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta encomenda?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = Conexao.conectar()) {
                    String sql = "DELETE FROM encomendas WHERE id=?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Encomenda excluída com sucesso!");
                    loadEncomendas(); // Atualizar a tabela
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir encomenda: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma encomenda para excluir.");
        }
    }

    private void loadEncomendas() {
        tableModel.setRowCount(0); // Limpa a tabela

        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT id, nome_cliente, telefone, produto, modelo, cor, quantidade, status FROM encomendas";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("nome_cliente"),
                        rs.getString("telefone"),
                        rs.getString("produto"),
                        rs.getString("modelo"),
                        rs.getString("cor"),
                        rs.getInt("quantidade"),
                        rs.getString("status")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar encomendas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualizarEncomendas().setVisible(true));
    }
}
