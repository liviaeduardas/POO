package Controller;

import Model.Campeonato;
import Model.Clube;
import Model.Partida;

import java.util.ArrayList;
import java.util.List;

/**
 * Regras :
 * - Nome não pode ser vazio
 * - Máximo de 8 clubes por campeonato
 * - Clube não pode ser duplicado
 * - Partida só pode ser adicionada se os dois clubes pertencem ao campeonato
 */
public class CampeonatoController {

    private static final int MAX_CLUBES = 8;

    private ArrayList<Campeonato> campeonatos;
    private ArrayList<Clube> clubes;

    public CampeonatoController() {
        this.campeonatos = new ArrayList<>();
        this.clubes = new ArrayList<>();
    }

    public boolean criarCampeonato(String nome, int ano) {
        if (nome == null || nome.trim().isEmpty()) return false;
        campeonatos.add(new Campeonato(nome.trim(), ano));
        return true;
    }

    public boolean cadastrarClube(String nome, String sigla) {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (sigla == null || sigla.trim().isEmpty()) return false;
        clubes.add(new Clube(nome.trim(), sigla.trim()));
        return true;
    }

    /**
     * Adiciona clube ao campeonato.
     * Regras: máximo 8 clubes, sem duplicatas.
     */
    public boolean adicionarClubeCampeonato(Campeonato campeonato, Clube clube) {
        if (campeonato == null || clube == null) return false;
        if (campeonato.getClubes().size() >= MAX_CLUBES) return false;
        if (campeonato.getClubes().contains(clube)) return false;
        campeonato.adicionarClube(clube);
        return true;
    }

    /**
     * Adiciona partida ao campeonato.
     * Regra: os dois clubes precisam pertencer ao campeonato.
     */
    public boolean adicionarPartidaCampeonato(Campeonato campeonato, Partida partida) {
        if (campeonato == null || partida == null) return false;
        boolean mandanteNoCampeonato = campeonato.getClubes().contains(partida.getClubeMandante());
        boolean visitanteNoCampeonato = campeonato.getClubes().contains(partida.getClubeVisitante());
        if (!mandanteNoCampeonato || !visitanteNoCampeonato) return false;
        campeonato.adicionarPartida(partida);
        return true;
    }

    /**
     * Retorna partidas ainda não encerradas.
     */
    public List<Partida> getPartidasPendentes(Campeonato campeonato) {
        List<Partida> pendentes = new ArrayList<>();
        for (Partida p : campeonato.getPartidas()) {
            if (!p.isEncerrada()) pendentes.add(p);
        }
        return pendentes;
    }

    /**
     * Retorna partidas já encerradas.
     */
    public List<Partida> getPartidasFinalizadas(Campeonato campeonato) {
        List<Partida> finalizadas = new ArrayList<>();
        for (Partida p : campeonato.getPartidas()) {
            if (p.isEncerrada()) finalizadas.add(p);
        }
        return finalizadas;
    }

    public Campeonato buscarNome(String nome) {
        for (Campeonato c : campeonatos) {
            if (c.getNome().equalsIgnoreCase(nome.trim())) return c;
        }
        return null;
    }

    public Clube buscarClubeSigla(String sigla) {
        for (Clube c : clubes) {
            if (c.getSigla().equalsIgnoreCase(sigla.trim())) return c;
        }
        return null;
    }

    public ArrayList<Campeonato> getCampeonatos() { return campeonatos; }
    public ArrayList<Clube> getClubes() { return clubes; }
}