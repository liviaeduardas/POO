package Model;

import java.util.ArrayList;

/**
 * Representa um participante do sistema.
 * Herda dados de Usuario e adiciona pontos e lista de apostas.
 * No modelo anêmico, só guarda dados — sem regras.
 */
public class Participante extends Usuario {

    private int pontosTotal;
    private ArrayList<Aposta> apostas;

    public Participante() {
        super();
        this.pontosTotal = 0;
        this.apostas = new ArrayList<>();
    }

    public Participante(int id, String senha, String usuario, String nome) {
        super(id, senha, usuario, nome);
        this.pontosTotal = 0;
        this.apostas = new ArrayList<>();
    }

    // Polimorfismo: comportamento diferente do Administrador
    @Override
    public void autenticar() {
        System.out.println("Participante autenticado: " + getNome());
    }

    // Guarda a aposta na lista interna — chamado pelo ApostaController
    public void registrarAposta(Aposta aposta) {
        apostas.add(aposta);
    }

    public int getPontosTotal() { return pontosTotal; }
    public void setPontosTotal(int pontosTotal) { this.pontosTotal = pontosTotal; }

    public ArrayList<Aposta> getApostas() { return apostas; }
    public void setApostas(ArrayList<Aposta> apostas) { this.apostas = apostas; }
}