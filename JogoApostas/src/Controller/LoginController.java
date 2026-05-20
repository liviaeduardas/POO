package Controller;

import Model.Administrador;
import Model.Participante;
import Model.Usuario;

import java.util.ArrayList;

/**
 * Gerencia login e cadastro de usuários.
 */
public class LoginController {

    private static final String USUARIO_ADMIN = "admin";
    private static final String SENHA_ADMIN = "admin";
    private static final String USUARIO_PARTICIPANTE = "participante";
    private static final String SENHA_PARTICIPANTE  = "participante";

    private Administrador administrador;
    private ArrayList<Participante> participantes;
    private int proximoId;

    public LoginController() {
        this.administrador = new Administrador(1, SENHA_ADMIN, USUARIO_ADMIN, "Administrador");
        this.participantes = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Verifica credenciais e retorna o usuário autenticado.
     * Retorna null se as credenciais estiverem erradas.
     */
    public Usuario autenticar(String usuario, String senha) {
        if (usuario.equals(USUARIO_ADMIN) && senha.equals(SENHA_ADMIN)) {
            administrador.autenticar();
            return administrador;
        }
        if (usuario.equals(USUARIO_PARTICIPANTE) && senha.equals(SENHA_PARTICIPANTE)) {
            return new Participante();
        }
        return null;
    }

    /**
     * Finaliza o cadastro de um participante.
     */
    public boolean cadastrar(Participante participante, String nome) {
        if (participante == null) return false;
        if (nome == null || nome.trim().isEmpty()) return false;

        participante.setNome(nome.trim());
        participante.setId(proximoId++);
        participante.setUsuario(USUARIO_PARTICIPANTE);
        participantes.add(participante);
        return true;
    }

    public boolean possuiCadastro(String nome) {
        for (Participante p : participantes) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) return true;
        }
        return false;
    }

    public Participante buscarNome(String nome) {
        for (Participante p : participantes) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) return p;
        }
        return null;
    }

    public Administrador getAdministrador() { return administrador; }
    public ArrayList<Participante> getParticipantes() { return participantes; }
}