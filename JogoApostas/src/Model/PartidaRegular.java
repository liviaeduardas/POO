package Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Tipo concreto de partida — partida regular de campeonato.
 * Implementa getResultado() herdado de Partida.
 */
public class PartidaRegular extends Partida {

    public PartidaRegular() {}

    public PartidaRegular(Clube clubeMandante, Clube clubeVisitante, LocalDate data, LocalTime hora) {
        super(clubeMandante, clubeVisitante, data, hora);
    }

    // Implementação do método abstrato — polimorfismo
    @Override
    public String getResultado() {
        if (!isEncerrada()) return "Não encerrada";
        if (getGolMandante() > getGolVisitante()) return "Mandante";
        if (getGolVisitante() > getGolMandante()) return "Visitante";
        return "Empate";
    }

    @Override
    public String toString() {
        return getClubeMandante().getNome() + " x " + getClubeVisitante().getNome()
                + " | " + getData() + " " + getHora();
    }
}