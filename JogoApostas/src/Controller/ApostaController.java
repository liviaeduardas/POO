package Controller;

import Model.Aposta;
import Model.Campeonato;
import Model.Participante;
import Model.Partida;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Regras :
 * - Prazo de 20 minutos antes da partida
 * - Gols não negativos
 * - Um participante não aposta duas vezes na mesma partida
 * - Cálculo de pontuação (10, 5 ou 0 pontos)
 */
public class ApostaController implements ICalcularPontos {

    private ArrayList<Aposta> apostas;
    private int proximoId;

    public ApostaController() {
        this.apostas = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Verifica se ainda está dentro do prazo para apostar.
     * Regra: até 20 minutos antes da partida começar.
     */
    public boolean podeApostar(Aposta aposta) {
        Partida partida = aposta.getPartida();
        LocalDateTime inicioPartida = LocalDateTime.of(partida.getData(), partida.getHora());
        LocalDateTime prazoLimite = inicioPartida.minusMinutes(20);
        return LocalDateTime.now().isBefore(prazoLimite);
    }

    /**
     * Calcula quantos pontos a aposta vale após a partida encerrar.
     * - Placar exato → 10 pontos
     * - Só o vencedor certo → 5 pontos
     * - Errou tudo → 0 pontos
     */
    @Override
    public int calcularPontuacao(Aposta aposta) {
        Partida partida = aposta.getPartida();

        if (!partida.isEncerrada()) return 0;

        String resultadoPalpite;
        if (aposta.getGolsMandantePalpite() > aposta.getGolsVisitantePalpite()) {
            resultadoPalpite = "Mandante";
        } else if (aposta.getGolsMandantePalpite() < aposta.getGolsVisitantePalpite()) {
            resultadoPalpite = "Visitante";
        } else {
            resultadoPalpite = "Empate";
        }

        if (aposta.getGolsMandantePalpite() == partida.getGolMandante()
                && aposta.getGolsVisitantePalpite() == partida.getGolVisitante()) {
            return 10;
        }

        if (resultadoPalpite.equals(partida.getResultado())) {
            return 5;
        }

        return 0;
    }

    /**
     * Registra uma nova aposta após validar todas as regras.
     */
    public boolean registrarAposta(Participante participante, Partida partida,
                                   int golsMandante, int golsVisitante) {
        if (participante == null || partida == null) return false;
        if (golsMandante < 0 || golsVisitante < 0) return false;
        if (partida.isEncerrada()) return false;

        Aposta aposta = new Aposta(proximoId++, participante, partida, golsMandante, golsVisitante);

        if (!podeApostar(aposta)) return false;

        for (Aposta a : apostas) {
            if (a.getParticipante().equals(participante) && a.getPartida().equals(partida)) {
                return false;
            }
        }

        apostas.add(aposta);
        participante.registrarAposta(aposta);
        return true;
    }

    /**
     * Calcula e distribui pontos para todas as apostas de um campeonato.
     * Só calcula apostas ainda não calculadas.
     */
    public void calcularPontuacoes(Campeonato campeonato) {
        for (Aposta a : apostas) {
            boolean pertenceAoCampeonato = campeonato.getPartidas().contains(a.getPartida());
            boolean partidaEncerrada = a.getPartida().isEncerrada();
            boolean aindaNaoCalculada = !a.isCalculada();

            if (pertenceAoCampeonato && partidaEncerrada && aindaNaoCalculada) {
                int pontos = calcularPontuacao(a);
                a.setPontuacaoObtida(pontos);
                a.getParticipante().setPontosTotal(a.getParticipante().getPontosTotal() + pontos);
                a.setCalculada(true);
            }
        }
    }

    public ArrayList<Aposta> getApostasPorParticipante(Participante participante) {
        ArrayList<Aposta> resultado = new ArrayList<>();
        for (Aposta a : apostas) {
            if (a.getParticipante().equals(participante)) resultado.add(a);
        }
        return resultado;
    }

    public ArrayList<Aposta> getApostasPorPartida(Partida partida) {
        ArrayList<Aposta> resultado = new ArrayList<>();
        for (Aposta a : apostas) {
            if (a.getPartida().equals(partida)) resultado.add(a);
        }
        return resultado;
    }

    public ArrayList<Aposta> getApostas() { return apostas; }
}