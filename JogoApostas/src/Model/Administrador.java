package Model;

public class Administrador extends Usuario {

    private String nivelAcesso;

    public Administrador() {
        super();
        this.nivelAcesso = "TOTAL";
    }

    public Administrador(int id, String senha, String usuario, String nome) {
        super(id, senha, usuario, nome);
        this.nivelAcesso = "TOTAL";
    }

    // Polimorfismo: comportamento diferente do Participante
    @Override
    public void autenticar() {
        System.out.println("Administrador autenticado: " + getNome());
    }

    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }
}