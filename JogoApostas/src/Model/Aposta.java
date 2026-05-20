package Model;

import java.time.LocalDateTime;

/**
 * Representa uma aposta feita por um participante em uma partida.
 * No modelo anêmico, só guarda dados — sem regras.
 * Todas as regras (prazo, pontuação) ficam no ApostaController.
 */
public class Aposta {

    private int idAposta;
    private Participante participante;
    private Partida partida;
    private int golsMandantePalpite;
    private int golsVisitantePalpite;
    private LocalDateTime dataHoraAposta;
    private int pontuacaoObtida;
    private boolean calculada; // controla se os pontos já foram somados

    public Aposta(int idAposta, Participante participante, Partida partida,
                  int golsMandantePalpite, int golsVisitantePalpite) {
        this.idAposta = idAposta;
        this.participante = participante;
        this.partida = partida;
        this.golsMandantePalpite = golsMandantePalpite;
        this.golsVisitantePalpite = golsVisitantePalpite;
        this.dataHoraAposta = LocalDateTime.now();
        this.pontuacaoObtida = 0;
        this.calculada = false;
    }

    public int getIdAposta() { return idAposta; }
    public void setIdAposta(int idAposta) { this.idAposta = idAposta; }

    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }

    public Partida getPartida() { return partida; }
    public void setPartida(Partida partida) { this.partida = partida; }

    public int getGolsMandantePalpite() { return golsMandantePalpite; }
    public void setGolsMandantePalpite(int golsMandantePalpite) { this.golsMandantePalpite = golsMandantePalpite; }

    public int getGolsVisitantePalpite() { return golsVisitantePalpite; }
    public void setGolsVisitantePalpite(int golsVisitantePalpite) { this.golsVisitantePalpite = golsVisitantePalpite; }

    public LocalDateTime getDataHoraAposta() { return dataHoraAposta; }

    public int getPontuacaoObtida() { return pontuacaoObtida; }
    public void setPontuacaoObtida(int pontuacaoObtida) { this.pontuacaoObtida = pontuacaoObtida; }

    public boolean isCalculada() { return calculada; }
    public void setCalculada(boolean calculada) { this.calculada = calculada; }
}