package View;

import Controller.ApostaController;
import Controller.CampeonatoController;
import Model.Campeonato;
import Model.Partida;
import Model.Participante;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TelaApostas extends JPanel {

    private MainFrame mainFrame;
    private ApostaController apostaController;
    private CampeonatoController campeonatoController;

    private JComboBox<String> comboCampeonato;
    private JComboBox<String> comboPartida;
    private JTextField campoGolsMandante;
    private JTextField campoGolsVisitante;
    private JButton botaoApostar;
    private JLabel labelBemVindo;
    private JLabel labelInfo;

    private static final Color VERMELHO     = new Color(0x95, 0x0E, 0x17);
    private static final Color VERMELHO_ESC = new Color(0x70, 0x0A, 0x11);
    private static final Color FUNDO        = new Color(0xFF, 0xF5, 0xF5);

    public TelaApostas(MainFrame mainFrame, ApostaController apostaController, CampeonatoController campeonatoController) {
        this.mainFrame = mainFrame;
        this.apostaController = apostaController;
        this.campeonatoController = campeonatoController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(FUNDO);

        // ===== LATERAL ESQUERDA =====
        JPanel lateral = new JPanel();
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBackground(VERMELHO);
        lateral.setPreferredSize(new Dimension(180, 0));
        lateral.setBorder(new EmptyBorder(30, 0, 20, 0));

        JLabel sistema = new JLabel("Sistema", SwingConstants.CENTER);
        sistema.setFont(new Font("Arial", Font.BOLD, 18));
        sistema.setForeground(Color.WHITE);
        sistema.setAlignmentX(Component.CENTER_ALIGNMENT);
        lateral.add(sistema);

        labelBemVindo = new JLabel("Participante", SwingConstants.CENTER);
        labelBemVindo.setFont(new Font("Arial", Font.PLAIN, 12));
        labelBemVindo.setForeground(new Color(0xFF, 0xCC, 0xCC));
        labelBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lateral.add(labelBemVindo);

        lateral.add(Box.createVerticalStrut(30));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(140, 1));
        sep.setForeground(new Color(0xAA, 0x33, 0x33));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        lateral.add(sep);

        lateral.add(Box.createVerticalStrut(20));

        JButton btnApostas = criarBotaoMenu("Minhas Apostas");
        btnApostas.addActionListener(e -> verMinhasApostas());
        lateral.add(btnApostas);
        lateral.add(Box.createVerticalStrut(4));

        JButton btnGrupos = criarBotaoMenu("Grupos");
        btnGrupos.addActionListener(e -> abrirDialogGrupos());
        lateral.add(btnGrupos);
        lateral.add(Box.createVerticalStrut(4));

        JButton btnClassificacao = criarBotaoMenu("Classificação");
        btnClassificacao.addActionListener(e -> mainFrame.trocarTela("telaClassificacao"));
        lateral.add(btnClassificacao);
        lateral.add(Box.createVerticalStrut(4));

        JButton btnResultados = criarBotaoMenu("Resultados");
        btnResultados.addActionListener(e -> mainFrame.trocarTela("telaResultados"));
        lateral.add(btnResultados);

        lateral.add(Box.createVerticalGlue());

        JButton botaoSair = criarBotaoMenu("Sair");
        botaoSair.setForeground(new Color(0xFF, 0xCC, 0xCC));
        botaoSair.addActionListener(e -> mainFrame.trocarTela("telaLogin"));
        lateral.add(botaoSair);
        lateral.add(Box.createVerticalStrut(10));

        add(lateral, BorderLayout.WEST);

        // ===== CONTEÚDO DIREITA =====
        JPanel direita = new JPanel(new GridBagLayout());
        direita.setBackground(FUNDO);
        direita.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titulo = new JLabel("Registrar Aposta");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(VERMELHO);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 24, 0);
        direita.add(titulo, gbc);

        gbc.gridwidth = 1;

        adicionarLabel(direita, gbc, "Campeonato:", 1);
        comboCampeonato = adicionarCombo(direita, gbc, 1);
        comboCampeonato.addActionListener(e -> atualizarPartidas());

        adicionarLabel(direita, gbc, "Partida:", 2);
        comboPartida = adicionarCombo(direita, gbc, 2);

        adicionarLabel(direita, gbc, "Gols mandante:", 3);
        campoGolsMandante = adicionarCampo(direita, gbc, 3);

        adicionarLabel(direita, gbc, "Gols visitante:", 4);
        campoGolsVisitante = adicionarCampo(direita, gbc, 4);

        labelInfo = new JLabel(" ");
        labelInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        labelInfo.setForeground(new Color(0x99, 0x00, 0x00));
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 0, 8, 0);
        direita.add(labelInfo, gbc);

        botaoApostar = criarBotaoAcao("Registrar Aposta");
        botaoApostar.addActionListener(e -> registrarAposta());
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        direita.add(botaoApostar, gbc);

        add(direita, BorderLayout.CENTER);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.PLAIN, 13));
        botao.setForeground(Color.WHITE);
        botao.setBackground(VERMELHO);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setMaximumSize(new Dimension(180, 36));
        botao.setPreferredSize(new Dimension(180, 36));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { botao.setBackground(VERMELHO_ESC); }
            public void mouseExited(java.awt.event.MouseEvent e)  { botao.setBackground(VERMELHO); }
        });
        return botao;
    }

    private JButton criarBotaoAcao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(VERMELHO);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(200, 36));
        return botao;
    }

    private void adicionarLabel(JPanel painel, GridBagConstraints gbc, String texto, int linha) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(0x44, 0x44, 0x44));
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.insets = new Insets(10, 0, 2, 16);
        gbc.fill = GridBagConstraints.NONE;
        painel.add(label, gbc);
    }

    private JTextField adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setPreferredSize(new Dimension(260, 34));
        gbc.gridx = 1; gbc.gridy = linha;
        gbc.insets = new Insets(10, 0, 2, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campo, gbc);
        return campo;
    }

    private JComboBox<String> adicionarCombo(JPanel painel, GridBagConstraints gbc, int linha) {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(260, 34));
        gbc.gridx = 1; gbc.gridy = linha;
        gbc.insets = new Insets(10, 0, 2, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(combo, gbc);
        return combo;
    }

    private void registrarAposta() {
        Participante participante = mainFrame.getParticipanteLogado();
        if (participante == null) return;

        String nomePartida = (String) comboPartida.getSelectedItem();
        String golsMStr    = campoGolsMandante.getText().trim();
        String golsVStr    = campoGolsVisitante.getText().trim();

        if (nomePartida == null || golsMStr.isEmpty() || golsVStr.isEmpty()) {
            labelInfo.setText("Preencha todos os campos!");
            return;
        }

        try {
            int golsM = Integer.parseInt(golsMStr);
            int golsV = Integer.parseInt(golsVStr);

            if (golsM < 0 || golsV < 0) {
                labelInfo.setText("Gols não podem ser negativos!");
                return;
            }

            Partida partida = buscarPartidaPorString(nomePartida);
            if (partida == null) {
                labelInfo.setText("Partida não encontrada!");
                return;
            }

            boolean apostou = apostaController.registrarAposta(participante, partida, golsM, golsV);

            if (apostou) {
                JOptionPane.showMessageDialog(mainFrame, "Aposta registrada! Palpite: " + golsM + " x " + golsV);
                campoGolsMandante.setText("");
                campoGolsVisitante.setText("");
                labelInfo.setText(" ");
            } else {
                labelInfo.setText("Verifique: +20min para partida ou aposta duplicada!");
            }

        } catch (NumberFormatException ex) {
            labelInfo.setText("Digite apenas números nos gols!");
        }
    }

    private void verMinhasApostas() {
        Participante participante = mainFrame.getParticipanteLogado();
        if (participante == null) return;

        List<Model.Aposta> apostas = apostaController.getApostasPorParticipante(participante);

        if (apostas.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Você ainda não fez nenhuma aposta!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Model.Aposta a : apostas) {
            sb.append("Partida: ").append(a.getPartida().toString()).append("\n");
            sb.append("Palpite: ").append(a.getGolsMandantePalpite()).append(" x ").append(a.getGolsVisitantePalpite()).append("\n");
            sb.append("Pontos:  ").append(a.getPontuacaoObtida()).append("\n");
            sb.append("─────────────────────\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(380, 260));
        JOptionPane.showMessageDialog(mainFrame, scroll, "Minhas Apostas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirDialogGrupos() {
        String[] opcoes = {"Criar novo grupo", "Entrar em grupo existente"};
        int escolha = JOptionPane.showOptionDialog(mainFrame,
                "O que deseja fazer?", "Grupos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        if (escolha == 0) criarGrupo();
        else if (escolha == 1) entrarEmGrupo();
    }

    private void criarGrupo() {
        Participante participante = mainFrame.getParticipanteLogado();
        if (participante == null) return;

        String nome = JOptionPane.showInputDialog(mainFrame,
                "Nome do novo grupo:", "Criar Grupo", JOptionPane.PLAIN_MESSAGE);

        if (nome == null || nome.trim().isEmpty()) return;

        boolean criou = mainFrame.getGrupoController().criarGrupoPorParticipante(nome.trim(), participante);
        if (criou) {
            Model.Grupo grupo = mainFrame.getGrupoController().buscarNome(nome.trim());
            mainFrame.getGrupoController().adicionarParticipante(grupo, participante);
            JOptionPane.showMessageDialog(mainFrame, "Grupo \"" + nome.trim() + "\" criado!");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Limite de 5 grupos atingido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void entrarEmGrupo() {
        Participante participante = mainFrame.getParticipanteLogado();
        if (participante == null) return;

        java.util.ArrayList<Model.Grupo> grupos = mainFrame.getGrupoController().getGrupos();
        if (grupos.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Nenhum grupo disponível ainda!");
            return;
        }

        String[] nomes = grupos.stream().map(Model.Grupo::getNome).toArray(String[]::new);
        String escolhido = (String) JOptionPane.showInputDialog(mainFrame,
                "Escolha o grupo:", "Entrar em Grupo",
                JOptionPane.PLAIN_MESSAGE, null, nomes, nomes[0]);

        if (escolhido == null) return;

        Model.Grupo grupo = mainFrame.getGrupoController().buscarNome(escolhido);
        boolean entrou = mainFrame.getGrupoController().adicionarParticipante(grupo, participante);

        if (entrou) {
            JOptionPane.showMessageDialog(mainFrame, "Você entrou no grupo \"" + escolhido + "\"!");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Grupo cheio ou você já está nele!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarPartidas() {
        comboPartida.removeAllItems();
        String nomeCampeonato = (String) comboCampeonato.getSelectedItem();
        if (nomeCampeonato == null) return;
        Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
        if (campeonato == null) return;
        for (Partida p : campeonato.getPartidasPendentes()) {
            comboPartida.addItem(p.toString());
        }
    }

    private Partida buscarPartidaPorString(String texto) {
        for (Campeonato c : campeonatoController.getCampeonatos()) {
            for (Partida p : c.getPartidas()) {
                if (p.toString().equals(texto)) return p;
            }
        }
        return null;
    }

    public void atualizar() {
        Participante participante = mainFrame.getParticipanteLogado();
        if (participante != null) {
            labelBemVindo.setText(participante.getNome());
        }
        comboCampeonato.removeAllItems();
        for (Campeonato c : campeonatoController.getCampeonatos()) {
            comboCampeonato.addItem(c.getNome());
        }
        atualizarPartidas();
        labelInfo.setText(" ");
    }
}