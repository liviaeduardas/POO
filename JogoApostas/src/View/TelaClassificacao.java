package View;
import Controller.GrupoController;
import Model.Grupo;
import Model.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaClassificacao extends JPanel {
    private MainFrame mainFrame;
    private GrupoController grupoController;
    private JComboBox<String> comboGrupos;
    private JTable tabelaRanking;
    private javax.swing.table.DefaultTableModel modeloTabela;
    private JLabel labelGrupo;
    private JButton botaoAtualizar;
    private JButton botaoVoltar;

    public TelaClassificacao(MainFrame mainFrame, GrupoController grupoController) {
        this.mainFrame = mainFrame;
        this.grupoController = grupoController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0x95, 0x0E, 0x17));

        Color vermelho = new Color(0x95, 0x0E, 0x17);
        Color fundoLista = new Color(0xFF, 0xF5, 0xF5);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(vermelho);

        JLabel titulo = new JLabel("Classificação do Grupo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        painelTopo.add(titulo, BorderLayout.NORTH);

        JPanel painelGrupo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelGrupo.setBackground(vermelho);

        JLabel labelCombo = new JLabel("Grupo:");
        labelCombo.setForeground(Color.WHITE);
        painelGrupo.add(labelCombo);

        comboGrupos = new JComboBox<>();
        comboGrupos.setPreferredSize(new Dimension(200, 30));
        comboGrupos.addActionListener(e -> atualizarRanking());
        painelGrupo.add(comboGrupos);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBackground(Color.LIGHT_GRAY);
        botaoAtualizar.setForeground(Color.BLACK);
        botaoAtualizar.setFocusPainted(false);
        botaoAtualizar.addActionListener(e -> carregarGrupos());
        painelGrupo.add(botaoAtualizar);

        painelTopo.add(painelGrupo, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        String[] colunas = {"Posição", "Participante", "Pontos"};
        modeloTabela = new javax.swing.table.DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaRanking = new JTable(modeloTabela);
        tabelaRanking.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaRanking.setRowHeight(30);
        tabelaRanking.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaRanking.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaRanking.setBackground(fundoLista);
        tabelaRanking.getTableHeader().setBackground(fundoLista);

        javax.swing.table.DefaultTableCellRenderer centralizador = new javax.swing.table.DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);
        centralizador.setBackground(fundoLista);
        for (int i = 0; i < colunas.length; i++) {
            tabelaRanking.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        JScrollPane scroll = new JScrollPane(tabelaRanking);
        scroll.getViewport().setBackground(fundoLista);
        scroll.setBackground(fundoLista);
        add(scroll, BorderLayout.CENTER);


        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodape.setBackground(vermelho);

        labelGrupo = new JLabel("");
        labelGrupo.setFont(new Font("Arial", Font.ITALIC, 12));
        labelGrupo.setForeground(Color.WHITE);
        painelRodape.add(labelGrupo);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        botaoVoltar.setBackground(Color.LIGHT_GRAY);
        botaoVoltar.setForeground(Color.BLACK);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> mainFrame.trocarTela("telaApostas"));
        painelRodape.add(botaoVoltar);

        add(painelRodape, BorderLayout.SOUTH);
    }

    public void carregarGrupos() {
        comboGrupos.removeAllItems();
        ArrayList<Grupo> grupos = grupoController.getGrupos();

        for (Grupo g : grupos) {
            comboGrupos.addItem(g.getNome());
        }
    }

    private void atualizarRanking() {
        modeloTabela.setRowCount(0);

        String nomeGrupo = (String) comboGrupos.getSelectedItem();
        if (nomeGrupo == null) return;

        Grupo grupo = grupoController.buscarNome(nomeGrupo);
        if (grupo == null) return;

        ArrayList<Participante> ranking = grupoController.getRanking(grupo);

        for (int i = 0; i < ranking.size(); i++) {
            Participante p = ranking.get(i);
            String posicao = (i + 1) + "º";
            modeloTabela.addRow(new Object[]{posicao, p.getNome(), p.getPontosTotal() + " pts"});
        }

        labelGrupo.setText("Total de participantes: " + ranking.size());
    }

    public void atualizar() {
        carregarGrupos();
        atualizarRanking();
    }
}