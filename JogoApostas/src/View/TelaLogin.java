package View;

import Controller.LoginController;
import Model.Administrador;
import Model.Participante;
import Model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {

    private MainFrame mainFrame;
    private LoginController loginController;

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JLabel labelErro;

    public TelaLogin(MainFrame mainFrame, LoginController loginController) {
        this.mainFrame = mainFrame;
        this.loginController = loginController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // título
        JLabel titulo = new JLabel("Sistema de Apostas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        // subtítulo
        JLabel subtitulo = new JLabel("Faça login para continuar", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(subtitulo, gbc);

        // label email
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        // campo email
        campoEmail = new JTextField(20);
        gbc.gridx = 1;
        add(campoEmail, gbc);

        // label senha
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Senha:"), gbc);

        // campo senha
        campoSenha = new JPasswordField(20);
        gbc.gridx = 1;
        add(campoSenha, gbc);

        // label erro
        labelErro = new JLabel("", SwingConstants.CENTER);
        labelErro.setForeground(Color.RED);
        labelErro.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(labelErro, gbc);

        // botão entrar
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 5;
        add(botaoEntrar, gbc);

        // ação do botão
        botaoEntrar.addActionListener(e -> realizarLogin());

        // pressionar Enter no campo senha também faz login
        campoSenha.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            labelErro.setText("Preencha todos os campos!");
            return;
        }

        Usuario usuario = loginController.autenticar(email, senha);

        if (usuario == null) {
            labelErro.setText("Email ou senha incorretos!");
            campoSenha.setText("");
            return;
        }

        labelErro.setText("");
        campoEmail.setText("");
        campoSenha.setText("");

        if (usuario instanceof Administrador) {
            mainFrame.setAdminLogado((Administrador) usuario);
            mainFrame.trocarTela("telaCadastro");

        } else if (usuario instanceof Participante) {
            Participante participante = (Participante) usuario;

            if (participante.getNome() == null || participante.getNome().isEmpty()) {
                pedirNome(participante);
            } else {
                mainFrame.setParticipanteLogado(participante);
                mainFrame.trocarTela("telaApostas");
            }
        }
    }

    private void pedirNome(Participante participante) {
        String nome = JOptionPane.showInputDialog(
                mainFrame,
                "Bem-vindo! É seu primeiro acesso.\nDigite seu nome:",
                "Cadastro",
                JOptionPane.PLAIN_MESSAGE
        );

        if (nome == null || nome.trim().isEmpty()) {
            labelErro.setText("Nome obrigatório para continuar!");
            return;
        }

        boolean cadastrou = loginController.cadastrar(participante, nome);

        if (!cadastrou) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Limite de 5 participantes atingido!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        mainFrame.setParticipanteLogado(participante);
        mainFrame.trocarTela("telaApostas");
    }
}