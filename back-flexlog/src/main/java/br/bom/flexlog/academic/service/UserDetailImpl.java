package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailImpl implements UserDetails {
    private int id;
    private String name;
    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Construtor para inicializar a classe com as permissoes
    public UserDetailImpl(String name, int id, String login, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.id = id;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }


    public static UserDetailImpl build(Usuario usuario){
        return new UserDetailImpl(usuario.getNomeUsuario(),
                usuario.getIdUsuario(),
                usuario.getLogin(),
                usuario.getSenhaUsuario(), // Adicionando a senha
                new ArrayList<>()); // Inicialmente sem permissoes
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
