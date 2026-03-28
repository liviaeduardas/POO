package Model;

public class Administrador extends Usuario{
    private String nivelAesso;

    public Administrador(){
        super();
        this.nivelAesso = "TOTAL";
    }

    public Administrador(int id, String senha, String email, String nome) {
        super(id, senha, email, nome);
        this.nivelAesso = "TOTAL";
    }

    public void autenticar(){
        System.out.println("Administrador autenticado: " + getNome());
    }

    public String getNivelAesso() {
        return nivelAesso;
    }

    public void setNivelAesso(String nivelAesso) {
        this.nivelAesso = nivelAesso;
    }
}
