package View;
import Controller.CampeonatoController;
import Controller.PartidaController;
import Model.Campeonato;
import Model.Clube;
import Model.Administrador;

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

    private JTextField campoGrupoNome;
    private JComboBox<String> comboParticipantesGrupo;
    private JButton botaoCriarGrupo;
    private JButton botaoAdicionarParticipante;

    private JButton botaoSair;

    private static final Color VERMELHO = new Color(0x95, 0x0E, 0x17);
    private static final Color FUNDO = new Color(0xFF, 0xF5, 0xF5);

    public TelaCadastro(MainFrame mainFrame, CampeonatoController campeonatoController, PartidaController partidaController) {
        this.mainFrame = mainFrame;
        this.campeonatoController = campeonatoController;
        this.partidaController = partidaController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(0, 0));
        setBackground(VERMELHO);

        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.setBackground(VERMELHO);
        painelNorte.setBorder(new EmptyBorder(18, 10, 12, 10));

        JLabel titulo = new JLabel("Painel do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        painelNorte.add(titulo, BorderLayout.CENTER);

        add(painelNorte, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(0, 0));
        painelCentral.setBackground(VERMELHO);
        painelCentral.setBorder(new EmptyBorder(0, 12, 0, 12));

        abas = new JTabbedPane();
        abas.setBackground(FUNDO);
        abas.setFont(new Font("Arial", Font.PLAIN, 13));

        abas.addTab("Campeonato", criarAbaCampeonato());
        abas.addTab("Clube",      criarAbaClube());
        abas.addTab("Partida",    criarAbaPartida());
        abas.addTab("Resultado",  criarAbaResultado());
        abas.addTab("Grupo",      criarAbaGrupo());

        painelCentral.add(abas, BorderLayout.CENTER);
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelSul.setBackground(VERMELHO);
        painelSul.setBorder(new EmptyBorder(8, 10, 10, 10));

        botaoSair = criarBotao("Sair");
        botaoSair.addActionListener(e -> mainFrame.trocarTela("telaLogin"));
        painelSul.add(botaoSair);

        add(painelSul, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.PLAIN, 13));
        botao.setBackground(Color.LIGHT_GRAY);
        botao.setForeground(Color.BLACK);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private JPanel criarPainelAba() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return painel;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(0x33, 0x33, 0x33));
        return label;
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        return campo;
    }

    private JComboBox<String> criarCombo() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JPanel criarAbaCampeonato() {
        JPanel painel = criarPainelAba();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(criarLabel("Nome do campeonato:"), gbc);
        campoCampeonatoNome = criarCampo();
        gbc.gridx = 1;
        painel.add(campoCampeonatoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(criarLabel("Ano:"), gbc);
        campoCampeonatoAno = criarCampo();
        gbc.gridx = 1;
        painel.add(campoCampeonatoAno, gbc);

        botaoCriarCampeonato = criarBotao("Criar Campeonato");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        painel.add(botaoCriarCampeonato, gbc);

        botaoCriarCampeonato.addActionListener(e -> criarCampeonato());

        return painel;
    }

    private JPanel criarAbaClube() {
        JPanel painel = criarPainelAba();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(criarLabel("Nome do clube:"), gbc);
        campoClubeNome = criarCampo();
        gbc.gridx = 1;
        painel.add(campoClubeNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(criarLabel("Sigla:"), gbc);
        campoClubeSigla = criarCampo();
        gbc.gridx = 1;
        painel.add(campoClubeSigla, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(criarLabel("Campeonato:"), gbc);
        comboCampeonatoClube = criarCombo();
        gbc.gridx = 1;
        painel.add(comboCampeonatoClube, gbc);

        botaoCadastrarClube = criarBotao("Cadastrar Clube");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        painel.add(botaoCadastrarClube, gbc);

        botaoCadastrarClube.addActionListener(e -> cadastrarClube());

        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 1) {
                atualizarComboCampeonatos(comboCampeonatoClube);
            }
        });

        return painel;
    }

    private JPanel criarAbaPartida() {
        JPanel painel = criarPainelAba();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(criarLabel("Campeonato:"), gbc);
        comboCampeonatoPartida = criarCombo();
        gbc.gridx = 1;
        painel.add(comboCampeonatoPartida, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(criarLabel("Mandante:"), gbc);
        comboMandante = criarCombo();
        gbc.gridx = 1;
        painel.add(comboMandante, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(criarLabel("Visitante:"), gbc);
        comboVisitante = criarCombo();
        gbc.gridx = 1;
        painel.add(comboVisitante, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(criarLabel("Data (dd/MM/yyyy):"), gbc);
        campoData = criarCampo();
        gbc.gridx = 1;
        painel.add(campoData, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(criarLabel("Hora (HH:mm):"), gbc);
        campoHora = criarCampo();
        gbc.gridx = 1;
        painel.add(campoHora, gbc);

        botaoCadastrarPartida = criarBotao("Cadastrar Partida");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        painel.add(botaoCadastrarPartida, gbc);

        botaoCadastrarPartida.addActionListener(e -> cadastrarPartida());

        comboCampeonatoPartida.addActionListener(e -> atualizarClubesPartida());
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 2) {
                atualizarComboCampeonatos(comboCampeonatoPartida);
            }
        });

        return painel;
    }

    private JPanel criarAbaResultado() {
        JPanel painel = criarPainelAba();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(criarLabel("Campeonato:"), gbc);
        comboCampeonatoResultado = criarCombo();
        gbc.gridx = 1;
        painel.add(comboCampeonatoResultado, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(criarLabel("Partida:"), gbc);
        comboPartidaResultado = criarCombo();
        gbc.gridx = 1;
        painel.add(comboPartidaResultado, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(criarLabel("Gols mandante:"), gbc);
        campoGolsMandante = criarCampo();
        gbc.gridx = 1;
        painel.add(campoGolsMandante, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(criarLabel("Gols visitante:"), gbc);
        campoGolsVisitante = criarCampo();
        gbc.gridx = 1;
        painel.add(campoGolsVisitante, gbc);

        botaoRegistrarResultado = criarBotao("Registrar Resultado");
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

    private JPanel criarAbaGrupo() {
        JPanel painel = criarPainelAba();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(criarLabel("Nome do grupo:"), gbc);
        campoGrupoNome = criarCampo();
        gbc.gridx = 1;
        painel.add(campoGrupoNome, gbc);

        botaoCriarGrupo = criarBotao("Criar Grupo");
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        painel.add(botaoCriarGrupo, gbc);

        botaoCriarGrupo.addActionListener(e -> criarGrupo());

        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 4) {
                atualizarComboParticipantes();
            }
        });

        return painel;
    }

    private void criarCampeonato() {
        String nome = campoCampeonatoNome.getText().trim();
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

    private void criarGrupo() {
        String nome = campoGrupoNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Digite o nome do grupo!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Administrador admin = mainFrame.getAdminLogado();
        boolean criou = mainFrame.getGrupoController().criarGrupo(nome, admin);

        if (criou) {
            JOptionPane.showMessageDialog(mainFrame, "Grupo criado com sucesso!");
            campoGrupoNome.setText("");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Limite de 5 grupos atingido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarParticipanteGrupo() {
        String nomeParticipante = (String) comboParticipantesGrupo.getSelectedItem();
        if (nomeParticipante == null) {
            JOptionPane.showMessageDialog(mainFrame, "Nenhum participante disponível!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.ArrayList<Model.Grupo> grupos = mainFrame.getGrupoController().getGrupos();
        if (grupos.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Crie um grupo primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Model.Grupo grupo = grupos.get(0);
        Model.Participante participante = mainFrame.getLoginController().buscarNome(nomeParticipante);

        if (participante == null) {
            JOptionPane.showMessageDialog(mainFrame, "Participante não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean adicionou = mainFrame.getGrupoController().adicionarParticipante(grupo, participante);

        if (adicionou) {
            JOptionPane.showMessageDialog(mainFrame, nomeParticipante + " adicionado ao grupo " + grupo.getNome() + "!");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Grupo cheio ou participante já está no grupo!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarComboParticipantes() {
        comboParticipantesGrupo.removeAllItems();
        for (Model.Participante p : mainFrame.getLoginController().getParticipantes()) {
            comboParticipantesGrupo.addItem(p.getNome());
        }
    }

    private void cadastrarClube() {
        String nome  = campoClubeNome.getText().trim();
        String sigla = campoClubeSigla.getText().trim();
        String nomeCampeonato = (String) comboCampeonatoClube.getSelectedItem();

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

        try {
            int golsM = Integer.parseInt(golsMStr);
            int golsV = Integer.parseInt(golsVStr);

            Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
            if (campeonato == null) return;

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
