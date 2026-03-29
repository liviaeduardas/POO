package View;

import Controller.CampeonatoController;
import Controller.PartidaController;
import Model.Campeonato;
import Model.Clube;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TelaCadastro extends JPanel {

    private MainFrame mainFrame;
    private CampeonatoController campeonatoController;
    private PartidaController partidaController;

    private JTabbedPane abas;

    // aba campeonato
    private JTextField campoCampeonatoNome;
    private JTextField campoCampeonatoAno;
    private JButton botaoCriarCampeonato;

    // aba clube
    private JTextField campoClubeNome;
    private JTextField campoClubeSigla;
    private JComboBox<String> comboCampeonatoClube;
    private JButton botaoCadastrarClube;

    // aba partida
    private JComboBox<String> comboCampeonatoPartida;
    private JComboBox<String> comboMandante;
    private JComboBox<String> comboVisitante;
    private JTextField campoData;
    private JTextField campoHora;
    private JButton botaoCadastrarPartida;

    // aba resultado
    private JComboBox<String> comboCampeonatoResultado;
    private JComboBox<String> comboPartidaResultado;
    private JTextField campoGolsMandante;
    private JTextField campoGolsVisitante;
    private JButton botaoRegistrarResultado;

    private JButton botaoSair;

    public TelaCadastro(MainFrame mainFrame, CampeonatoController campeonatoController, PartidaController partidaController) {
        this.mainFrame = mainFrame;
        this.campeonatoController = campeonatoController;
        this.partidaController = partidaController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // título
        JLabel titulo = new JLabel("Painel do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // abas
        abas = new JTabbedPane();
        abas.addTab("Campeonato", criarAbaCampeonato());
        abas.addTab("Clube",      criarAbaClube());
        abas.addTab("Partida",    criarAbaPartida());
        abas.addTab("Resultado",  criarAbaResultado());
        add(abas, BorderLayout.CENTER);

        // botão sair
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoSair = new JButton("Sair");
        botaoSair.setFont(new Font("Arial", Font.BOLD, 13));
        botaoSair.addActionListener(e -> mainFrame.trocarTela("telaLogin"));
        painelRodape.add(botaoSair);
        add(painelRodape, BorderLayout.SOUTH);
    }

    // ===== ABA CAMPEONATO =====
    private JPanel criarAbaCampeonato() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome do campeonato:"), gbc);
        campoCampeonatoNome = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoCampeonatoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Ano:"), gbc);
        campoCampeonatoAno = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoCampeonatoAno, gbc);

        botaoCriarCampeonato = new JButton("Criar Campeonato");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        painel.add(botaoCriarCampeonato, gbc);

        botaoCriarCampeonato.addActionListener(e -> criarCampeonato());

        return painel;
    }

    // ===== ABA CLUBE =====
    private JPanel criarAbaClube() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome do clube:"), gbc);
        campoClubeNome = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoClubeNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Sigla:"), gbc);
        campoClubeSigla = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoClubeSigla, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Campeonato:"), gbc);
        comboCampeonatoClube = new JComboBox<>();
        gbc.gridx = 1;
        painel.add(comboCampeonatoClube, gbc);

        botaoCadastrarClube = new JButton("Cadastrar Clube");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        painel.add(botaoCadastrarClube, gbc);

        botaoCadastrarClube.addActionListener(e -> cadastrarClube());

        // atualiza combo quando trocar para essa aba
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 1) {
                atualizarComboCampeonatos(comboCampeonatoClube);
            }
        });

        return painel;
    }

    // ===== ABA PARTIDA =====
    private JPanel criarAbaPartida() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Campeonato:"), gbc);
        comboCampeonatoPartida = new JComboBox<>();
        gbc.gridx = 1;
        painel.add(comboCampeonatoPartida, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Mandante:"), gbc);
        comboMandante = new JComboBox<>();
        gbc.gridx = 1;
        painel.add(comboMandante, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Visitante:"), gbc);
        comboVisitante = new JComboBox<>();
        gbc.gridx = 1;
        painel.add(comboVisitante, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        campoData = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoData, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(new JLabel("Hora (HH:mm):"), gbc);
        campoHora = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoHora, gbc);

        botaoCadastrarPartida = new JButton("Cadastrar Partida");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        painel.add(botaoCadastrarPartida, gbc);

        botaoCadastrarPartida.addActionListener(e -> cadastrarPartida());

        // atualiza combos quando trocar para essa aba
        comboCampeonatoPartida.addActionListener(e -> atualizarClubesPartida());
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 2) {
                atualizarComboCampeonatos(comboCampeonatoPartida);
            }
        });

        return painel;
    }

    // ===== ABA RESULTADO =====
    private JPanel criarAbaResultado() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Campeonato:"), gbc);
        comboCampeonatoResultado = new JComboBox<>();
        gbc.gridx = 1;
        painel.add(comboCampeonatoResultado, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Partida:"), gbc);
        comboPartidaResultado = new JComboBox<>();
        gbc.gridx = 1;
        painel.add(comboPartidaResultado, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Gols mandante:"), gbc);
        campoGolsMandante = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoGolsMandante, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Gols visitante:"), gbc);
        campoGolsVisitante = new JTextField(20);
        gbc.gridx = 1;
        painel.add(campoGolsVisitante, gbc);

        botaoRegistrarResultado = new JButton("Registrar Resultado");
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        painel.add(botaoRegistrarResultado, gbc);

        botaoRegistrarResultado.addActionListener(e -> registrarResultado());

        comboCampeonatoResultado.addActionListener(e -> atualizarPartidasResultado());
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 3) {
                atualizarComboCampeonatos(comboCampeonatoResultado);
            }
        });

        return painel;
    }

    // ===== AÇÕES =====
    private void criarCampeonato() {
        String nome = campoCampeonatoNome.getText().trim();
        String anoStr = campoCampeonatoAno.getText().trim();

        if (nome.isEmpty() || anoStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int ano = Integer.parseInt(anoStr);
            boolean criou = campeonatoController.criarCampeonato(nome, ano);
            if (criou) {
                JOptionPane.showMessageDialog(mainFrame, "Campeonato criado com sucesso!");
                campoCampeonatoNome.setText("");
                campoCampeonatoAno.setText("");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Erro ao criar campeonato!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Ano inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarClube() {
        String nome  = campoClubeNome.getText().trim();
        String sigla = campoClubeSigla.getText().trim();
        String nomeCampeonato = (String) comboCampeonatoClube.getSelectedItem();

        if (nome.isEmpty() || sigla.isEmpty() || nomeCampeonato == null) {
            JOptionPane.showMessageDialog(mainFrame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
        if (campeonato == null) {
            JOptionPane.showMessageDialog(mainFrame, "Campeonato não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean cadastrou = campeonatoController.cadastrarClube(nome, sigla);
        if (!cadastrou) {
            JOptionPane.showMessageDialog(mainFrame, "Erro ao cadastrar clube!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Clube clube = campeonatoController.buscarClubeSigla(sigla);
        boolean adicionou = campeonatoController.adicionarClubeCampeonato(campeonato, clube);

        if (adicionou) {
            JOptionPane.showMessageDialog(mainFrame, "Clube cadastrado com sucesso!");
            campoClubeNome.setText("");
            campoClubeSigla.setText("");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Limite de 8 clubes atingido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarPartida() {
        String nomeCampeonato = (String) comboCampeonatoPartida.getSelectedItem();
        String nomeMandante   = (String) comboMandante.getSelectedItem();
        String nomeVisitante  = (String) comboVisitante.getSelectedItem();
        String dataStr        = campoData.getText().trim();
        String horaStr        = campoHora.getText().trim();

        if (nomeCampeonato == null || nomeMandante == null || nomeVisitante == null
                || dataStr.isEmpty() || horaStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nomeMandante.equals(nomeVisitante)) {
            JOptionPane.showMessageDialog(mainFrame, "Mandante e visitante não podem ser iguais!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String[] partesData = dataStr.split("/");
            LocalDate data = LocalDate.of(
                    Integer.parseInt(partesData[2]),
                    Integer.parseInt(partesData[1]),
                    Integer.parseInt(partesData[0])
            );

            String[] partesHora = horaStr.split(":");
            LocalTime hora = LocalTime.of(
                    Integer.parseInt(partesHora[0]),
                    Integer.parseInt(partesHora[1])
            );

            Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
            Clube mandante  = campeonatoController.buscarClubeSigla(nomeMandante);
            Clube visitante = campeonatoController.buscarClubeSigla(nomeVisitante);

            boolean cadastrou = partidaController.cadastrarPartida(campeonato, mandante, visitante, data, hora);

            if (cadastrou) {
                JOptionPane.showMessageDialog(mainFrame, "Partida cadastrada com sucesso!");
                campoData.setText("");
                campoHora.setText("");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Erro ao cadastrar partida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Data ou hora inválida!\nUse o formato dd/MM/yyyy e HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarResultado() {
        String nomePartida    = (String) comboPartidaResultado.getSelectedItem();
        String golsMStr       = campoGolsMandante.getText().trim();
        String golsVStr       = campoGolsVisitante.getText().trim();
        String nomeCampeonato = (String) comboCampeonatoResultado.getSelectedItem();

        if (nomePartida == null || golsMStr.isEmpty() || golsVStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int golsM = Integer.parseInt(golsMStr);
            int golsV = Integer.parseInt(golsVStr);

            Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
            if (campeonato == null) return;

            // busca a partida pelo toString
            Model.Partida partida = null;
            for (Model.Partida p : campeonato.getPartidas()) {
                if (p.toString().equals(nomePartida)) {
                    partida = p;
                    break;
                }
            }

            if (partida == null) {
                JOptionPane.showMessageDialog(mainFrame, "Partida não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean registrou = partidaController.registrarResultado(partida, golsM, golsV);

            if (registrou) {
                // calcula pontuações após registrar resultado
                mainFrame.getApostaController().calcularPontuacoes(campeonato);
                JOptionPane.showMessageDialog(mainFrame, "Resultado registrado! Pontuações calculadas.");
                campoGolsMandante.setText("");
                campoGolsVisitante.setText("");
                atualizarPartidasResultado();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Partida já encerrada ou erro!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Gols inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== AUXILIARES =====
    private void atualizarComboCampeonatos(JComboBox<String> combo) {
        combo.removeAllItems();
        for (Campeonato c : campeonatoController.getCampeonatos()) {
            combo.addItem(c.getNome());
        }
    }

    private void atualizarClubesPartida() {
        comboMandante.removeAllItems();
        comboVisitante.removeAllItems();
        String nomeCampeonato = (String) comboCampeonatoPartida.getSelectedItem();
        if (nomeCampeonato == null) return;

        Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
        if (campeonato == null) return;

        for (Clube c : campeonato.getClubes()) {
            comboMandante.addItem(c.getSigla());
            comboVisitante.addItem(c.getSigla());
        }
    }

    private void atualizarPartidasResultado() {
        comboPartidaResultado.removeAllItems();
        String nomeCampeonato = (String) comboCampeonatoResultado.getSelectedItem();
        if (nomeCampeonato == null) return;

        Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
        if (campeonato == null) return;

        List<Model.Partida> pendentes = partidaController.getPartidasPendentes(campeonato);
        for (Model.Partida p : pendentes) {
            comboPartidaResultado.addItem(p.toString());
        }
    }
}