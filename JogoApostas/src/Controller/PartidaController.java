package Controller;

import Model.Campeonato;
import Model.Clube;
import Model.Partida;
import Model.PartidaRegular;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Regras:
 * - Clube não pode jogar contra si mesmo
 * - Data e hora são obrigatórios
 * - Gols não podem ser negativos
 * - Partida já encerrada não pode receber resultado
 */
public class PartidaController {

    /**
     * Cria e cadastra uma nova partida no campeonato.
     */
    public boolean cadastrarPartida(Campeonato campeonato, Clube clubeMandante,
                                    Clube clubeVisitante, LocalDate data, LocalTime hora) {
        if (campeonato == null || clubeMandante == null || clubeVisitante == null) return false;
        if (data == null || hora == null) return false;
        if (clubeMandante.equals(clubeVisitante)) return false;

        PartidaRegular nova = new PartidaRegular(clubeMandante, clubeVisitante, data, hora);
        campeonato.adicionarPartida(nova);
        return true;
    }

    /**
     * Registra o placar de uma partida e a encerra.
     */
    public boolean registrarResultado(Partida partida, int golMandante, int golVisitante) {
        if (partida == null) return false;
        if (partida.isEncerrada()) return false;
        if (golMandante < 0 || golVisitante < 0) return false;

        partida.registrarResultado(golMandante, golVisitante);
        return true;
    }

    /**
     * Busca uma partida pelos dois clubes.
     */
    public Partida buscarPartida(Campeonato campeonato, Clube clubeMandante, Clube clubeVisitante) {
        if (campeonato == null || clubeMandante == null || clubeVisitante == null) return null;
        for (Partida p : campeonato.getPartidas()) {
            if (p.getClubeMandante().equals(clubeMandante)
                    && p.getClubeVisitante().equals(clubeVisitante)) {
                return p;
            }
        }
        return null;
    }
}