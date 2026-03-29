package View;

import Controller.ApostaController;
import Controller.CampeonatoController;
import Model.Campeonato;
import Model.Clube;
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

    public TelaApostas(MainFrame mainFrame, ApostaController apostaController, CampeonatoController campeonatoController) {
        this.mainFrame = mainFrame;
        this.apostaController = apostaController;
        this.campeonatoController = campeonatoController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());

        labelBemVindo = new JLabel("Bem-vindo!", SwingConstants.CENTER);
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        painelTopo.add(labelBemVindo, BorderLayout.NORTH);

        JLabel subtitulo = new JLabel("Registre sua aposta", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        painelTopo.add(subtitulo, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);

        // ===== CENTRO =====
        JPanel painelCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // campeonato
        gbc.gridx = 0; gbc.gridy = 0;
        painelCentro.add(new JLabel("Campeonato:"), gbc);
        comboCampeonato = new JComboBox<>();
        comboCampeonato.setPreferredSize(new Dimension(200, 30));
        comboCampeonato.addActionListener(e -> atualizarPartidas());
        gbc.gridx = 1;
        painelCentro.add(comboCampeonato, gbc);

        // partida
        gbc.gridx = 0; gbc.gridy = 1;
        painelCentro.add(new JLabel("Partida:"), gbc);
        comboPartida = new JComboBox<>();
        comboPartida.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        painelCentro.add(comboPartida, gbc);

        // placar previsto
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel labelPlacar = new JLabel("Placar previsto:", SwingConstants.CENTER);
        labelPlacar.setFont(new Font("Arial", Font.BOLD, 13));
        painelCentro.add(labelPlacar, gbc);

        // campos de gols
        JPanel painelGols = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        campoGolsMandante = new JTextField(3);
        campoGolsMandante.setFont(new Font("Arial", Font.BOLD, 16));
        campoGolsMandante.setHorizontalAlignment(JTextField.CENTER);

        JLabel labelX = new JLabel("X");
        labelX.setFont(new Font("Arial", Font.BOLD, 16));

        campoGolsVisitante = new JTextField(3);
        campoGolsVisitante.setFont(new Font("Arial", Font.BOLD, 16));
        campoGolsVisitante.setHorizontalAlignment(JTextField.CENTER);

        painelGols.add(new JLabel("Mandante:"));
        painelGols.add(campoGolsMandante);
        painelGols.add(labelX);
        painelGols.add(campoGolsVisitante);
        painelGols.add(new JLabel(":Visitante"));

        gbc.gridy = 3;
        painelCentro.add(painelGols, gbc);

        // label info
        labelInfo = new JLabel("", SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        labelInfo.setForeground(Color.BLUE);
        gbc.gridy = 4;
        painelCentro.add(labelInfo, gbc);

        // botão apostar
        botaoApostar = new JButton("Registrar Aposta");
        botaoApostar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoApostar.addActionListener(e -> registrarAposta());
        gbc.gridy = 5;
        painelCentro.add(botaoApostar, gbc);

        add(painelCentro, BorderLayout.CENTER);

        // ===== RODAPÉ =====
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        botaoVerApostas = new JButton("Minhas Apostas");
        botaoVerApostas.setFont(new Font("Arial", Font.PLAIN, 13));
        botaoVerApostas.addActionListener(e -> verMinhasApostas());
        painelRodape.add(botaoVerApostas);

        botaoClassificacao = new JButton("Classificação");
        botaoClassificacao.setFont(new Font("Arial", Font.PLAIN, 13));
        botaoClassificacao.addActionListener(e -> mainFrame.trocarTela("telaClassificacao"));
        painelRodape.add(botaoClassificacao);

        botaoSair = new JButton("Sair");
        botaoSair.setFont(new Font("Arial", Font.PLAIN, 13));
        botaoSair.addActionListener(e -> mainFrame.trocarTela("telaLogin"));
        painelRodape.add(botaoSair);

        add(painelRodape, BorderLayout.SOUTH);
    }

    private void registrarAposta() {
        Participante participante = mainFrame.getParticipanteLogado();
        if (participante == null) {
            JOptionPane.showMessageDialog(mainFrame, "Nenhum participante logado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomePartida = (String) comboPartida.getSelectedItem();
        if (nomePartida == null || nomePartida.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Selecione uma partida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String golsMStr = campoGolsMandante.getText().trim();
        String golsVStr = campoGolsVisitante.getText().trim();

        if (golsMStr.isEmpty() || golsVStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Preencha o placar previsto!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int golsM = Integer.parseInt(golsMStr);
            int golsV = Integer.parseInt(golsVStr);

            if (golsM < 0 || golsV < 0) {
                JOptionPane.showMessageDialog(mainFrame, "Gols não podem ser negativos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // busca a partida pelo toString
            Partida partida = buscarPartidaPorString(nomePartida);
            if (partida == null) {
                JOptionPane.showMessageDialog(mainFrame, "Partida não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean apostou = apostaController.registrarAposta(participante, partida, golsM, golsV);

            if (apostou) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Aposta registrada com sucesso!\nSeu palpite: " + golsM + " x " + golsV);
                campoGolsMandante.setText("");
                campoGolsVisitante.setText("");
                labelInfo.setText("Aposta registrada!");
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Não foi possível registrar a aposta!\nVerifique se:\n- Faltam mais de 20 min para a partida\n- Você ainda não apostou nessa partida",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Digite apenas números nos campos de gols!", "Erro", JOptionPane.ERROR_MESSAGE);
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

        List<Partida> pendentes = campeonato.getPartidasPendentes();
        if (pendentes.isEmpty()) {
            comboPartida.addItem("Nenhuma partida disponível");
            return;
        }

        for (Partida p : pendentes) {
            comboPartida.addItem(p.toString());
        }
    }

    private Partida buscarPartidaPorString(String texto) {
        for (Campeonato c : campeonatoController.getCampeonatos()) {
            for (Partida p : c.getPartidas()) {
                if (p.toString().equals(texto)) {
                    return p;
                }
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