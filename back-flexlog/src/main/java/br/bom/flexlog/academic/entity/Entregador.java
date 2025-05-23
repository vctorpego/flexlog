package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("2") // opcional: valor para distinguir o tipo usuário, ajuste conforme seu uso
public class Entregador extends Usuario {

    @Column(nullable = false)
    private String cnh;

    @OneToMany(mappedBy = "entregador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TentativaEntrega> tentativas = new ArrayList<>();

    public Entregador() {
        super();
    }

    public Entregador(String cnh) {
        super();
        this.cnh = cnh;
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
