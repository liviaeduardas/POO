package View;

import Controller.ApostaController;
import Controller.CampeonatoController;
import Controller.PartidaController;
import Model.Campeonato;
import Model.Partida;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaResultados extends JPanel {

    private MainFrame mainFrame;
    private PartidaController partidaController;
    private CampeonatoController campeonatoController;
    private ApostaController apostaController;

    private JComboBox<String> comboCampeonato;
    private JTabbedPane abas;

    private javax.swing.table.DefaultTableModel modeloPendentes;
    private javax.swing.table.DefaultTableModel modeloFinalizadas;
    private JTable tabelaPendentes;
    private JTable tabelaFinalizadas;

    private JButton botaoAtualizar;
    private JButton botaoVoltar;

    public TelaResultados(MainFrame mainFrame, PartidaController partidaController,
                          CampeonatoController campeonatoController, ApostaController apostaController) {
        this.mainFrame = mainFrame;
        this.partidaController = partidaController;
        this.campeonatoController = campeonatoController;
        this.apostaController = apostaController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Resultados das Partidas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelTopo.add(titulo, BorderLayout.NORTH);

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelFiltro.add(new JLabel("Campeonato:"));

        comboCampeonato = new JComboBox<>();
        comboCampeonato.setPreferredSize(new Dimension(200, 30));
        comboCampeonato.addActionListener(e -> atualizarTabelas());
        painelFiltro.add(comboCampeonato);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.addActionListener(e -> atualizar());
        painelFiltro.add(botaoAtualizar);

        painelTopo.add(painelFiltro, BorderLayout.SOUTH);
        add(painelTopo, BorderLayout.NORTH);

        // ===== ABAS =====
        abas = new JTabbedPane();

        // tabela partidas pendentes
        String[] colunasPendentes = {"Mandante", "Visitante", "Data", "Hora"};
        modeloPendentes = new javax.swing.table.DefaultTableModel(colunasPendentes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPendentes = new JTable(modeloPendentes);
        tabelaPendentes.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaPendentes.setRowHeight(28);
        tabelaPendentes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        centralizarColunas(tabelaPendentes, colunasPendentes.length);
        abas.addTab("Aguardando resultado", new JScrollPane(tabelaPendentes));

        // tabela partidas finalizadas
        String[] colunasFinalizadas = {"Mandante", "Visitante", "Placar", "Resultado"};
        modeloFinalizadas = new javax.swing.table.DefaultTableModel(colunasFinalizadas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaFinalizadas = new JTable(modeloFinalizadas);
        tabelaFinalizadas.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaFinalizadas.setRowHeight(28);
        tabelaFinalizadas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        centralizarColunas(tabelaFinalizadas, colunasFinalizadas.length);
        abas.addTab("Finalizadas", new JScrollPane(tabelaFinalizadas));

        add(abas, BorderLayout.CENTER);

        // ===== RODAPÉ =====
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        botaoVoltar.addActionListener(e -> mainFrame.trocarTela("telaApostas"));
        painelRodape.add(botaoVoltar);

        add(painelRodape, BorderLayout.SOUTH);
    }

    private void atualizarTabelas() {
        modeloPendentes.setRowCount(0);
        modeloFinalizadas.setRowCount(0);

        String nomeCampeonato = (String) comboCampeonato.getSelectedItem();
        if (nomeCampeonato == null) return;

        Campeonato campeonato = campeonatoController.buscarNome(nomeCampeonato);
        if (campeonato == null) return;

        // pendentes
        List<Partida> pendentes = partidaController.getPartidasPendentes(campeonato);
        for (Partida p : pendentes) {
            modeloPendentes.addRow(new Object[]{
                    p.getClubeMandante().getNome(),
                    p.getClubeVisitante().getNome(),
                    p.getData().toString(),
                    p.getHora().toString()
            });
        }

        // finalizadas
        List<Partida> finalizadas = partidaController.getPartidasFinalizadas(campeonato);
        for (Partida p : finalizadas) {
            String placar = p.getGolMandante() + " x " + p.getGolVisitante();
            modeloFinalizadas.addRow(new Object[]{
                    p.getClubeMandante().getNome(),
                    p.getClubeVisitante().getNome(),
                    placar,
                    p.getResultado()
            });
        }
    }

    private void centralizarColunas(JTable tabela, int totalColunas) {
        javax.swing.table.DefaultTableCellRenderer centralizador =
                new javax.swing.table.DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < totalColunas; i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }
    }

    public void atualizar() {
        comboCampeonato.removeAllItems();
        for (Campeonato c : campeonatoController.getCampeonatos()) {
            comboCampeonato.addItem(c.getNome());
        }
        atualizarTabelas();
    }
}