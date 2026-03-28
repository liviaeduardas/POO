package Model;

public abstract class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){}
    public Usuario(int id, String senha, String email, String nome) {
        this.id = id;
        this.senha = senha;
        this.email = email;
        this.nome = nome;
    }

    public abstract void autenticar();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }


}
