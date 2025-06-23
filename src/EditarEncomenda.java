import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditarEncomenda extends JDialog {
    private JTextField nomeField, telefoneField, produtoField, modeloField, corField, quantidadeField;
    private JComboBox<String> statusComboBox;
    private int idEncomenda;

    public EditarEncomenda(Frame parent, int id, String nomeCliente, String telefone, String produto, String modelo,
            String cor, int quantidade, String statusAtual) {
        super(parent, "Editar Encomenda", true);
        this.idEncomenda = id;

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(9, 2, 5, 5));

        add(new JLabel("Nome Cliente:"));
        nomeField = new JTextField(nomeCliente);
        add(nomeField);

        add(new JLabel("Telefone:"));
        telefoneField = new JTextField(telefone);
        add(telefoneField);

        add(new JLabel("Produto:"));
        produtoField = new JTextField(produto);
        add(produtoField);

        add(new JLabel("Modelo:"));
        modeloField = new JTextField(modelo);
        add(modeloField);

        add(new JLabel("Cor:"));
        corField = new JTextField(cor);
        add(corField);

        add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField(String.valueOf(quantidade));
        add(quantidadeField);

        add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(
                new String[] { "Não Encomendado", "Não Dísponivel", "Entregue", "Encomendado", "Aguardando Cliente" });
        statusComboBox.setSelectedItem(statusAtual); // Deixa o status atual selecionado
        add(statusComboBox);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarEdicao());
        add(salvarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dispose());
        add(cancelarButton);
    }

    private void salvarEdicao() {
        try (Connection conn = Conexao.conectar()) {
            String sql = "UPDATE encomendas SET nome_cliente=?, telefone=?, produto=?, modelo=?, cor=?, quantidade=?, status=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeField.getText());
            stmt.setString(2, telefoneField.getText());
            stmt.setString(3, produtoField.getText());
            stmt.setString(4, modeloField.getText());
            stmt.setString(5, corField.getText());
            stmt.setInt(6, Integer.parseInt(quantidadeField.getText()));
            stmt.setString(7, (String) statusComboBox.getSelectedItem());
            stmt.setInt(8, idEncomenda);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Encomenda atualizada com sucesso!");
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar encomenda: " + e.getMessage());
        }
    }
}
