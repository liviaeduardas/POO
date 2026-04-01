package View;

import Controller.CampeonatoController;
import Controller.PartidaController;
import Model.Campeonato;
import Model.Clube;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TelaCadastro extends JPanel {

    private MainFrame mainFrame;
    private CampeonatoController campeonatoController;
    private PartidaController partidaController;

    private JTabbedPane abas;

    private JTextField campoCampeonatoNome;
    private JTextField campoCampeonatoAno;
    private JButton botaoCriarCampeonato;

    private JTextField campoClubeNome;
    private JTextField campoClubeSigla;
    private JComboBox<String> comboCampeonatoClube;
    private JButton botaoCadastrarClube;

    private JComboBox<String> comboCampeonatoPartida;
    private JComboBox<String> comboMandante;
    private JComboBox<String> comboVisitante;
    private JTextField campoData;
    private JTextField campoHora;
    private JButton botaoCadastrarPartida;

    private JComboBox<String> comboCampeonatoResultado;
    private JComboBox<String> comboPartidaResultado;
    private JTextField campoGolsMandante;
    private JTextField campoGolsVisitante;
    private JButton botaoRegistrarResultado;

    private JButton botaoSair;

    private static final Color VERMELHO     = new Color(0x95, 0x0E, 0x17);
    private static final Color VERMELHO_ESC = new Color(0x70, 0x0A, 0x11);
    private static final Color FUNDO        = new Color(0xFF, 0xF5, 0xF5);

    public TelaCadastro(MainFrame mainFrame, CampeonatoController campeonatoController, PartidaController partidaController) {
        this.mainFrame = mainFrame;
        this.campeonatoController = campeonatoController;
        this.partidaController = partidaController;
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

        JLabel admin = new JLabel("Administrador", SwingConstants.CENTER);
        admin.setFont(new Font("Arial", Font.PLAIN, 12));
        admin.setForeground(new Color(0xFF, 0xCC, 0xCC));
        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lateral.add(admin);

        lateral.add(Box.createVerticalStrut(30));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(140, 1));
        sep.setForeground(new Color(0xAA, 0x33, 0x33));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        lateral.add(sep);

        lateral.add(Box.createVerticalStrut(20));

        String[] secoes = {"Campeonato", "Clube", "Partida", "Resultado"};
        for (int i = 0; i < secoes.length; i++) {
            final int index = i;
            JButton btnMenu = criarBotaoMenu(secoes[i]);
            btnMenu.addActionListener(e -> abas.setSelectedIndex(index));
            lateral.add(btnMenu);
            lateral.add(Box.createVerticalStrut(4));
        }

        lateral.add(Box.createVerticalGlue());

        botaoSair = criarBotaoMenu("Sair");
        botaoSair.setForeground(new Color(0xFF, 0xCC, 0xCC));
        botaoSair.addActionListener(e -> mainFrame.trocarTela("telaLogin"));
        lateral.add(botaoSair);
        lateral.add(Box.createVerticalStrut(10));

        add(lateral, BorderLayout.WEST);

        // ===== CONTEÚDO DIREITA =====
        JPanel direita = new JPanel(new BorderLayout());
        direita.setBackground(FUNDO);

        JLabel titulo = new JLabel("Painel do Administrador", SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(VERMELHO);
        titulo.setBorder(new EmptyBorder(24, 30, 16, 20));
        direita.add(titulo, BorderLayout.NORTH);

        abas = new JTabbedPane();
        abas.setBackground(FUNDO);
        abas.setFont(new Font("Arial", Font.PLAIN, 13));
        abas.setBorder(new EmptyBorder(0, 20, 20, 20));

        abas.addTab("Campeonato", criarAbaCampeonato());
        abas.addTab("Clube",      criarAbaClube());
        abas.addTab("Partida",    criarAbaPartida());
        abas.addTab("Resultado",  criarAbaResultado());

        direita.add(abas, BorderLayout.CENTER);
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
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(VERMELHO_ESC);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(VERMELHO);
            }
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

    private JPanel criarAba() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return painel;
    }

    private void adicionarLabel(JPanel painel, GridBagConstraints gbc, String texto, int linha) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(0x44, 0x44, 0x44));
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 2, 16);
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

    private JPanel criarAbaCampeonato() {
        JPanel painel = criarAba();
        GridBagConstraints gbc = new GridBagConstraints();

        adicionarLabel(painel, gbc, "Nome do campeonato:", 0);
        campoCampeonatoNome = adicionarCampo(painel, gbc, 0);

        adicionarLabel(painel, gbc, "Ano:", 1);
        campoCampeonatoAno = adicionarCampo(painel, gbc, 1);

        botaoCriarCampeonato = criarBotaoAcao("Criar Campeonato");
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(botaoCriarCampeonato, gbc);
        botaoCriarCampeonato.addActionListener(e -> criarCampeonato());

        return painel;
    }

    private JPanel criarAbaClube() {
        JPanel painel = criarAba();
        GridBagConstraints gbc = new GridBagConstraints();

        adicionarLabel(painel, gbc, "Nome do clube:", 0);
        campoClubeNome = adicionarCampo(painel, gbc, 0);

        adicionarLabel(painel, gbc, "Sigla:", 1);
        campoClubeSigla = adicionarCampo(painel, gbc, 1);

        adicionarLabel(painel, gbc, "Campeonato:", 2);
        comboCampeonatoClube = adicionarCombo(painel, gbc, 2);

        botaoCadastrarClube = criarBotaoAcao("Cadastrar Clube");
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(botaoCadastrarClube, gbc);
        botaoCadastrarClube.addActionListener(e -> cadastrarClube());

        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 1) atualizarComboCampeonatos(comboCampeonatoClube);
        });

        return painel;
    }

    private JPanel criarAbaPartida() {
        JPanel painel = criarAba();
        GridBagConstraints gbc = new GridBagConstraints();

        adicionarLabel(painel, gbc, "Campeonato:", 0);
        comboCampeonatoPartida = adicionarCombo(painel, gbc, 0);

        adicionarLabel(painel, gbc, "Mandante:", 1);
        comboMandante = adicionarCombo(painel, gbc, 1);

        adicionarLabel(painel, gbc, "Visitante:", 2);
        comboVisitante = adicionarCombo(painel, gbc, 2);

        adicionarLabel(painel, gbc, "Data (dd/MM/yyyy):", 3);
        campoData = adicionarCampo(painel, gbc, 3);

        adicionarLabel(painel, gbc, "Hora (HH:mm):", 4);
        campoHora = adicionarCampo(painel, gbc, 4);

        botaoCadastrarPartida = criarBotaoAcao("Cadastrar Partida");
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(botaoCadastrarPartida, gbc);
        botaoCadastrarPartida.addActionListener(e -> cadastrarPartida());

        comboCampeonatoPartida.addActionListener(e -> atualizarClubesPartida());
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 2) atualizarComboCampeonatos(comboCampeonatoPartida);
        });

        return painel;
    }

    private JPanel criarAbaResultado() {
        JPanel painel = criarAba();
        GridBagConstraints gbc = new GridBagConstraints();

        adicionarLabel(painel, gbc, "Campeonato:", 0);
        comboCampeonatoResultado = adicionarCombo(painel, gbc, 0);

        adicionarLabel(painel, gbc, "Partida:", 1);
        comboPartidaResultado = adicionarCombo(painel, gbc, 1);

        adicionarLabel(painel, gbc, "Gols mandante:", 2);
        campoGolsMandante = adicionarCampo(painel, gbc, 2);

        adicionarLabel(painel, gbc, "Gols visitante:", 3);
        campoGolsVisitante = adicionarCampo(painel, gbc, 3);

        botaoRegistrarResultado = criarBotaoAcao("Registrar Resultado");
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(botaoRegistrarResultado, gbc);
        botaoRegistrarResultado.addActionListener(e -> registrarResultado());

        comboCampeonatoResultado.addActionListener(e -> atualizarPartidasResultado());
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 3) atualizarComboCampeonatos(comboCampeonatoResultado);
        });

        return painel;
    }

    // ===== AÇÕES =====

    private void criarCampeonato() {
        String nome   = campoCampeonatoNome.getText().trim();
        String anoStr = campoCampeonatoAno.getText().trim();
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
        String nome   = campoClubeNome.getText().trim();
        String sigla  = campoClubeSigla.getText().trim();
        String nomeCampeonato = (String) comboCampeonatoClube.getSelectedItem();
        Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
        if (campeonato == null) {
            JOptionPane.showMessageDialog(mainFrame, "Selecione um campeonato!", "Erro", JOptionPane.ERROR_MESSAGE);
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
        if (nomeMandante == null || nomeVisitante == null || nomeMandante.equals(nomeVisitante)) {
            JOptionPane.showMessageDialog(mainFrame, "Selecione times diferentes!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String[] pd = dataStr.split("/");
            LocalDate data = LocalDate.of(Integer.parseInt(pd[2]), Integer.parseInt(pd[1]), Integer.parseInt(pd[0]));
            String[] ph = horaStr.split(":");
            LocalTime hora = LocalTime.of(Integer.parseInt(ph[0]), Integer.parseInt(ph[1]));
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
            JOptionPane.showMessageDialog(mainFrame, "Data ou hora inválida!\nUse dd/MM/yyyy e HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarResultado() {
        String nomePartida    = (String) comboPartidaResultado.getSelectedItem();
        String nomeCampeonato = (String) comboCampeonatoResultado.getSelectedItem();
        String golsMStr       = campoGolsMandante.getText().trim();
        String golsVStr       = campoGolsVisitante.getText().trim();
        try {
            int golsM = Integer.parseInt(golsMStr);
            int golsV = Integer.parseInt(golsVStr);
            Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
            if (campeonato == null) return;
            Model.Partida partida = null;
            for (Model.Partida p : campeonato.getPartidas()) {
                if (p.toString().equals(nomePartida)) { partida = p; break; }
            }
            if (partida == null) {
                JOptionPane.showMessageDialog(mainFrame, "Partida não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean registrou = partidaController.registrarResultado(partida, golsM, golsV);
            if (registrou) {
                mainFrame.getApostaController().calcularPontuacoes(campeonato);
                JOptionPane.showMessageDialog(mainFrame, "Resultado registrado! Pontuações calculadas.");
                campoGolsMandante.setText("");
                campoGolsVisitante.setText("");
                atualizarPartidasResultado();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Partida já encerrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Gols inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== AUXILIARES =====

    private void atualizarComboCampeonatos(JComboBox<String> combo) {
        combo.removeAllItems();
        for (Campeonato c : campeonatoController.getCampeonatos()) combo.addItem(c.getNome());
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
        for (Model.Partida p : pendentes) comboPartidaResultado.addItem(p.toString());
    }
}