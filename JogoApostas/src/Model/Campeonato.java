package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um campeonato de futebol.
 * No modelo anêmico, só guarda dados — sem validações.
 * Validações (limite de clubes, duplicata) ficam no CampeonatoController.
 */
public class Campeonato {

    private String nome;
    private int ano;
    private List<Clube> clubes;
    private List<Partida> partidas;

    public Campeonato() {
        this.clubes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public Campeonato(String nome, int ano) {
        this.nome = nome;
        this.ano = ano;
        this.clubes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    // Só adiciona — sem validação (validação no Controller)
    public void adicionarClube(Clube clube) {
        clubes.add(clube);
    }

    // Só adiciona — sem validação (validação no Controller)
    public void adicionarPartida(Partida partida) {
        partidas.add(partida);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public List<Clube> getClubes() { return clubes; }
    public List<Partida> getPartidas() { return partidas; }
}