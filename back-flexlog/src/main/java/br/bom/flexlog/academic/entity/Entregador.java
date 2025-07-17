package br.bom.flexlog.academic.entity;

import br.bom.flexlog.academic.dto.EntregadorDTO;
import br.bom.flexlog.academic.dto.UsuarioDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("2") // opcional: valor para distinguir o tipo usuário, ajuste conforme seu uso
public class Entregador extends Usuario {

    @Column(nullable = false)
    private String cnh;

    @OneToMany(mappedBy = "entregador", orphanRemoval = true)
    private List<TentativaEntrega> tentativas = new ArrayList<>();

    public Entregador() {
        super();
    }



    public Entregador(EntregadorDTO dto) {
        this.setIdUsuario(dto.getIdUsuario());
        this.setNomeUsuario(dto.getNomeUsuario());
        this.setEmailUsuario(dto.getEmailUsuario());
        this.setLogin(dto.getLogin());
        this.setSenhaUsuario(dto.getSenhaUsuario());
        this.setCnh(dto.getCnh());
        this.setTelefoneUsuario(dto.getTelefoneUsuario());
        this.setIsAdm(dto.getIsAdm());
    }




    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public List<TentativaEntrega> getTentativas() {
        return tentativas;
    }

    public void setTentativas(List<TentativaEntrega> tentativas) {
        this.tentativas = tentativas;
    }
}
