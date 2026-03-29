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
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Classificação do Grupo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelTopo.add(titulo, BorderLayout.NORTH);

        // seletor de grupo
        JPanel painelGrupo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelGrupo.add(new JLabel("Grupo:"));

        comboGrupos = new JComboBox<>();
        comboGrupos.setPreferredSize(new Dimension(200, 30));
        comboGrupos.addActionListener(e -> atualizarRanking());
        painelGrupo.add(comboGrupos);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.addActionListener(e -> carregarGrupos());
        painelGrupo.add(botaoAtualizar);

        painelTopo.add(painelGrupo, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        // ===== TABELA =====
        String[] colunas = {"Posição", "Participante", "Pontos"};
        modeloTabela = new javax.swing.table.DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição das células
            }
        };

        tabelaRanking = new JTable(modeloTabela);
        tabelaRanking.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaRanking.setRowHeight(30);
        tabelaRanking.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaRanking.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // centraliza as colunas
        javax.swing.table.DefaultTableCellRenderer centralizador =
                new javax.swing.table.DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < colunas.length; i++) {
            tabelaRanking.getColumnModel().getColumn(i).setCellRenderer(centralizador);
        }

        JScrollPane scroll = new JScrollPane(tabelaRanking);
        add(scroll, BorderLayout.CENTER);

        // ===== RODAPÉ =====
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));

        labelGrupo = new JLabel("");
        labelGrupo.setFont(new Font("Arial", Font.ITALIC, 12));
        painelRodape.add(labelGrupo);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        botaoVoltar.addActionListener(e -> mainFrame.trocarTela("telaApostas"));
        painelRodape.add(botaoVoltar);

        add(painelRodape, BorderLayout.SOUTH);
    }

    public void carregarGrupos() {
        comboGrupos.removeAllItems();
        ArrayList<Grupo> grupos = grupoController.getGrupos();

        if (grupos.isEmpty()) {
            comboGrupos.addItem("Nenhum grupo cadastrado");
            modeloTabela.setRowCount(0);
            labelGrupo.setText("Nenhum grupo encontrado");
            return;
        }

        for (Grupo g : grupos) {
            comboGrupos.addItem(g.getNome());
        }
    }

    private void atualizarRanking() {
        modeloTabela.setRowCount(0);

        String nomeGrupo = (String) comboGrupos.getSelectedItem();
        if (nomeGrupo == null || nomeGrupo.equals("Nenhum grupo cadastrado")) {
            return;
        }

        Grupo grupo = grupoController.buscarNome(nomeGrupo);
        if (grupo == null) {
            return;
        }

        ArrayList<Participante> ranking = grupoController.getRanking(grupo);

        if (ranking.isEmpty()) {
            labelGrupo.setText("Nenhum participante no grupo");
            return;
        }

        for (int i = 0; i < ranking.size(); i++) {
            Participante p = ranking.get(i);
            String posicao = (i + 1) + "º";
            modeloTabela.addRow(new Object[]{posicao, p.getNome(), p.getPontosTotal() + " pts"});
        }

        labelGrupo.setText("Total de participantes: " + ranking.size());
    }

    // chamado pelo MainFrame sempre que essa tela for exibida
    public void atualizar() {
        carregarGrupos();
        atualizarRanking();
    }
}