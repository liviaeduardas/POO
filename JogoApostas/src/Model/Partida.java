package Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe abstrata que representa uma partida.
 * No modelo anêmico, só guarda dados — sem validações.
 * Validações ficam no PartidaController.
 */
public abstract class Partida {

    private Clube clubeMandante;
    private Clube clubeVisitante;
    private LocalDate data;
    private LocalTime hora;
    private int golMandante;
    private int golVisitante;
    private boolean encerrada;

    public Partida() {
        this.golMandante = 0;
        this.golVisitante = 0;
        this.encerrada = false;
    }

    public Partida(Clube clubeMandante, Clube clubeVisitante, LocalDate data, LocalTime hora) {
        this.clubeMandante = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.data = data;
        this.hora = hora;
        this.golMandante = 0;
        this.golVisitante = 0;
        this.encerrada = false;
    }

    // Só armazena o resultado — sem validação (validação está no Controller)
    public void registrarResultado(int golMandante, int golVisitante) {
        this.golMandante = golMandante;
        this.golVisitante = golVisitante;
        this.encerrada = true;
    }

    // Cada subclasse define como descreve o resultado — abstrato
    public abstract String getResultado();

    public Clube getClubeMandante() { return clubeMandante; }
    public void setClubeMandante(Clube clubeMandante) { this.clubeMandante = clubeMandante; }

    public Clube getClubeVisitante() { return clubeVisitante; }
    public void setClubeVisitante(Clube clubeVisitante) { this.clubeVisitante = clubeVisitante; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public int getGolMandante() { return golMandante; }
    public void setGolMandante(int golMandante) { this.golMandante = golMandante; }

    public int getGolVisitante() { return golVisitante; }
    public void setGolVisitante(int golVisitante) { this.golVisitante = golVisitante; }

    public boolean isEncerrada() { return encerrada; }
    public void setEncerrada(boolean encerrada) { this.encerrada = encerrada; }
}