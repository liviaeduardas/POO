package View;
import Controller.ApostaController;
import Controller.CampeonatoController;
import Model.Campeonato;
import Model.Partida;
import Model.Participante;

import javax.swing.*;
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
    private JButton botaoVerApostas;
    private JButton botaoClassificacao;
    private JButton botaoSair;
    private JLabel labelInfo;
    private JLabel labelBemVindo;

    private static final Color VERMELHO = new Color(0x95, 0x0E, 0x17);
    private static final Color FUNDO    = new Color(0xFF, 0xF5, 0xF5);

    public TelaApostas(MainFrame mainFrame, ApostaController apostaController, CampeonatoController campeonatoController) {
        this.mainFrame = mainFrame;
        this.apostaController = apostaController;
        this.campeonatoController = campeonatoController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setBackground(VERMELHO);
        setLayout(new GridBagLayout());
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(FUNDO);
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        labelBemVindo = new JLabel("Bem-vindo!", SwingConstants.CENTER);
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(labelBemVindo, gbc);

        JLabel subtitulo = new JLabel("Registre sua aposta", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridy = 1;
        card.add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(new JLabel("Campeonato:"), gbc);
        comboCampeonato = new JComboBox<>();
        comboCampeonato.setPreferredSize(new Dimension(200, 30));
        comboCampeonato.addActionListener(e -> atualizarPartidas());
        gbc.gridx = 1;
        card.add(comboCampeonato, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        card.add(new JLabel("Partida:"), gbc);
        comboPartida = new JComboBox<>();
        comboPartida.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        card.add(comboPartida, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        card.add(new JLabel("Placar previsto:", SwingConstants.CENTER), gbc);

        JPanel painelGols = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        painelGols.setBackground(FUNDO);

        campoGolsMandante = new JTextField(3);
        campoGolsMandante.setFont(new Font("Arial", Font.BOLD, 16));
        campoGolsMandante.setHorizontalAlignment(JTextField.CENTER);

        campoGolsVisitante = new JTextField(3);
        campoGolsVisitante.setFont(new Font("Arial", Font.BOLD, 16));
        campoGolsVisitante.setHorizontalAlignment(JTextField.CENTER);

        painelGols.add(new JLabel("Mandante:"));
        painelGols.add(campoGolsMandante);
        painelGols.add(new JLabel("X"));
        painelGols.add(campoGolsVisitante);
        painelGols.add(new JLabel("Visitante:"));

        gbc.gridy = 5;
        card.add(painelGols, gbc);

        labelInfo = new JLabel("", SwingConstants.CENTER);
        labelInfo.setForeground(VERMELHO);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 6;
        card.add(labelInfo, gbc);

        botaoApostar = criarBotao("Registrar Aposta");
        botaoApostar.addActionListener(e -> registrarAposta());
        gbc.gridy = 7;
        card.add(botaoApostar, gbc);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        rodape.setBackground(FUNDO);

        botaoVerApostas = criarBotao("Minhas Apostas");
        botaoVerApostas.addActionListener(e -> verMinhasApostas());

        botaoClassificacao = criarBotao("Classificação");
        botaoClassificacao.addActionListener(e -> mainFrame.trocarTela("telaClassificacao"));

        botaoSair = criarBotao("Sair");
        botaoSair.addActionListener(e -> mainFrame.trocarTela("telaLogin"));

        rodape.add(botaoVerApostas);
        rodape.add(botaoClassificacao);
        rodape.add(botaoSair);

        gbc.gridy = 8;
        card.add(rodape, gbc);

        add(card);
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        return btn;
    }

    private void registrarAposta() {
        Participante participante = mainFrame.getParticipanteLogado();

        String nomePartida = (String) comboPartida.getSelectedItem();
        if (nomePartida == null) return;

        String golsMStr = campoGolsMandante.getText().trim();
        String golsVStr = campoGolsVisitante.getText().trim();

        if (golsMStr.isEmpty() || golsVStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Preencha o placar", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int golsM = Integer.parseInt(golsMStr);
            int golsV = Integer.parseInt(golsVStr);

            Partida partida = buscarPartidaPorString(nomePartida);
            if (partida == null) return;

            boolean apostou = apostaController.registrarAposta(participante, partida, golsM, golsV);

            if (apostou) {
                JOptionPane.showMessageDialog(mainFrame, "Aposta registrada! Palpite: " + golsM + " x " + golsV);
                campoGolsMandante.setText("");
                campoGolsVisitante.setText("");
                labelInfo.setText("Aposta registrada");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Não foi possível registrar a aposta", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Digite apenas números nos campos de gols", "Erro", JOptionPane.ERROR_MESSAGE);
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

        StringBuilder sb = new StringBuilder("Suas apostas:\n\n");
        for (Model.Aposta a : apostas) {
            sb.append("Partida: ").append(a.getPartida().toString()).append("\n");
            sb.append("Palpite: ").append(a.getGolsMandantePalpite()).append(" x ").append(a.getGolsVisitantePalpite()).append("\n");
            sb.append("Pontos: ").append(a.getPontuacaoObtida()).append("\n");
            sb.append("------------------------\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(mainFrame, scroll, "Minhas Apostas", JOptionPane.INFORMATION_MESSAGE);
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
            labelBemVindo.setText("Bem-vindo, " + participante.getNome() + "!");
        }

        comboCampeonato.removeAllItems();
        for (Campeonato c : campeonatoController.getCampeonatos()) {
            comboCampeonato.addItem(c.getNome());
        }
        atualizarPartidas();
        labelInfo.setText("");
    }
}