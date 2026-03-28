package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class PartidaRegular extends Partida{

    public PartidaRegular() {
    }

    public PartidaRegular(Clube clubeMandante, Clube clubeVisitante, LocalDate data, LocalTime hora, int golMandante, int golVisitante, boolean encerrada) {
        super(clubeMandante, clubeVisitante, data, hora, golMandante, golVisitante, encerrada);
    }

    public String getResultado(){
        if(!isEncerrada()){
            return "Não foi encerrada!!!";
        }

        if(getGolMandante() > getGolVisitante()){
            return "Vencedor: " + getClubeMandante().getNome();
        } else if (getGolVisitante() > getGolMandante()) {
            return "Vencedor: " + getClubeVisitante().getNome();
        }else{
            return "Empate!";
        }
    }

    public String toString(){
        return getClubeMandante().getNome() + " x " +
                getClubeVisitante().getNome() + " | " +
                getData() + " " + getHora() + " " + getResultado();
    }
}
