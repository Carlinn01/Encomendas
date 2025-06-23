import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroEncomenda extends JFrame {

    private JTextField nomeClienteField;
    private JTextField telefoneField;
    private JTextField produtoField;
    private JTextField modeloField;
    private JTextField corField;
    private JTextField quantidadeField;
    private JComboBox<String> statusComboBox;
    private JButton salvarButton;

    public CadastroEncomenda() {
        setTitle("Cadastro de Encomenda");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(9, 2, 10, 10)); // 9 linhas, 2 colunas, espaço 10px

        add(new JLabel("Nome do Cliente:"));
        nomeClienteField = new JTextField();
        add(nomeClienteField);

        add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        add(telefoneField);

        add(new JLabel("Produto:"));
        produtoField = new JTextField();
        add(produtoField);

        add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        add(modeloField);

        add(new JLabel("Cor:"));
        corField = new JTextField();
        add(corField);

        add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        add(quantidadeField);

        add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(
                new String[] { "Não Encomendado", "A Caminho", "Em Estoque", "Não Disponível", "Encomendado" });
        add(statusComboBox);

        salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarEncomenda();
            }
        });
        add(salvarButton);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaInicial().setVisible(true);
                dispose();
            }
        });
        add(voltarButton);
    }

    private void salvarEncomenda() {
        try {
            String nomeCliente = nomeClienteField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String produto = produtoField.getText().trim();
            String modelo = modeloField.getText().trim();
            String cor = corField.getText().trim();
            String quantidadeTexto = quantidadeField.getText().trim();
            String status = (String) statusComboBox.getSelectedItem(); // Pega o status selecionado

            // Verifica se algum campo obrigatório está vazio
            if (nomeCliente.isEmpty() || telefone.isEmpty() || produto.isEmpty() ||
                    modelo.isEmpty() || cor.isEmpty() || quantidadeTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int quantidade = Integer.parseInt(quantidadeTexto);

            Encomenda encomenda = new Encomenda(nomeCliente, telefone, produto, modelo, cor, quantidade, status);

            EncomendaDAO dao = new EncomendaDAO();
            dao.inserir(encomenda);

            JOptionPane.showMessageDialog(this, "Encomenda salva com sucesso!");

            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar encomenda: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeClienteField.setText("");
        telefoneField.setText("");
        produtoField.setText("");
        modeloField.setText("");
        corField.setText("");
        quantidadeField.setText("");
        statusComboBox.setSelectedIndex(0);
    }
}
