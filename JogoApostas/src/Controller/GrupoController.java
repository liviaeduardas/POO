package Controller;

import Model.Grupo;
import Model.Participante;
import Model.Administrador;

import java.util.ArrayList;

/**
 * Gerencia todas as regras relacionadas a grupos.
 *
 * Regras que vivem aqui:
 * - Nome não pode ser vazio
 * - Máximo de 5 grupos no sistema
 * - Máximo de 5 participantes por grupo
 * - Ordenação do ranking por pontuação
 */
public class GrupoController {

    private static final int MAX_GRUPOS = 5;
    private static final int MAX_PARTICIPANTES = 5;

    private ArrayList<Grupo> grupos;
    private int proximoId;

    public GrupoController() {
        this.grupos = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Cria um novo grupo no sistema.
     */
    public boolean criarGrupo(String nome, Administrador criador) {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (grupos.size() >= MAX_GRUPOS) return false;

        String nomeCriador = criador != null ? criador.getNome() : null;
        grupos.add(new Grupo(proximoId++, nome.trim(), nomeCriador));
        return true;
    }

    /**
     * Adiciona um participante a um grupo.
     * Regra: máximo de 5 participantes por grupo.
     */
    public boolean adicionarParticipante(Grupo grupo, Participante participante) {
        if (grupo == null || participante == null) return false;
        if (grupo.getParticipantes().size() >= MAX_PARTICIPANTES) return false;
        grupo.addParticipante(participante);
        return true;
    }

    /**
     * Retorna os participantes do grupo ordenados por pontuação (maior primeiro).
     */
    public ArrayList<Participante> getRanking(Grupo grupo) {
        if (grupo == null) return new ArrayList<>();
        ArrayList<Participante> ranking = new ArrayList<>(grupo.getParticipantes());
        ranking.sort((p1, p2) -> p2.getPontosTotal() - p1.getPontosTotal());
        return ranking;
    }

    public Grupo buscarNome(String nome) {
        for (Grupo g : grupos) {
            if (g.getNome().equalsIgnoreCase(nome.trim())) return g;
        }
        return null;
    }

    public ArrayList<Grupo> getGrupos() { return grupos; }
}