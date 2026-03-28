package Controller;
import Model.Administrador;
import Model.Participante;
import Model.Usuario;
import java.util.ArrayList;

public class LoginController {
    private static final String admUsuario = "admin";
    private static final String admSenha = "admin";
    private static final String participanteUsuario = "participante";
    private static final String participanteSenha = "participante";

    private Administrador administrador;
    private ArrayList<Participante> participantes;
    private int proximo;

    public LoginController(){
        this.administrador = new Administrador(1,admSenha, admUsuario, "Administrador");
        this.participantes = new ArrayList<>();
        this.proximo = 1;
    }

    public Usuario autenticar(String email, String senha){
        if(email.equals(admUsuario) && senha.equals(admSenha)){
            administrador.autenticar();
            return administrador;
        }
        if(email.equals(participanteUsuario) && senha.equals(participanteSenha)){
            return new Participante();
        }

        return null;
    }

    public boolean cadastrar(Participante participante, String nome){
        if(nome == null || nome.trim().isEmpty()){
            return false;
        }
        if(participantes.size() >= 5){
            return false;
        }

        participante.setNome(nome.trim());
        participante.setId(proximo++);
        participante.setEmail(participanteUsuario);
        participantes.add(participante);
        return true;
    }

    public boolean temCadastro(String nome){
        for(Participante p: participantes){
            if(p.getNome().equalsIgnoreCase(nome.trim())){
                return true;
            }
        }

        return false;
    }

    public Participante buscarNome(String nome){
        for(Participante p: participantes){
            if(p.getNome().equalsIgnoreCase(nome.trim())){
                return p;
            }
        }

        return null;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }
}
