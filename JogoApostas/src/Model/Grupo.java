package Model;

import java.util.ArrayList;

/**
 * Representa um grupo de participantes.
 * No modelo anêmico, só guarda dados — sem validações.
 * Validações (limite, ranking) ficam no GrupoController.
 */
public class Grupo {

    private int id;
    private String nome;
    private String criador;
    private ArrayList<Participante> participantes;

    public Grupo() {
        this.participantes = new ArrayList<>();
    }

    public Grupo(int id, String nome, String criador) {
        this.id = id;
        this.nome = nome;
        this.criador = criador;
        this.participantes = new ArrayList<>();
    }

    // Só adiciona — sem validação (validação no Controller)
    public void addParticipante(Participante participante) {
        participantes.add(participante);
    }

    @Override
    public String toString() {
        return "Grupo " + nome + " | Participantes: " + participantes.size();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCriador() { return criador; }
    public void setCriador(String criador) { this.criador = criador; }

    public ArrayList<Participante> getParticipantes() { return participantes; }
    public void setParticipantes(ArrayList<Participante> participantes) { this.participantes = participantes; }
}