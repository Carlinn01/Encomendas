import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        setTitle("Tela Inicial");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton cadastrarButton = new JButton("Cadastrar Encomenda");
        JButton visualizarButton = new JButton("Visualizar Encomendas");

        cadastrarButton.setPreferredSize(new Dimension(200, 50)); // Largura 200, altura 50
        visualizarButton.setPreferredSize(new Dimension(200, 50)); // Largura 200, altura 50

        add(cadastrarButton, gbc);

        gbc.gridy = 1;
        add(visualizarButton, gbc);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroEncomenda().setVisible(true);
                dispose();
            }
        });

        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizarEncomendas().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaInicial().setVisible(true);
            }
        });
    }
}
