package br.bom.flexlog.academic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class TentativaEntrega implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTentativa;

    @Column(nullable = false)
    private String recebedor;

    @Column(nullable = false)
    private String statusEntrega;

    @Column
    private String falhaEntrega;

    @Lob
    @Column
    @JsonIgnore
    private byte[] assinaturaRecebedor;

    @ManyToOne
    @JoinColumn(name = "id_entregador", nullable = false)
    @JsonBackReference  // evita loop

    private Entregador entregador;

    @ManyToOne
    @JoinColumn(name = "id_pacote", nullable = false)
    @JsonBackReference  // evita loop
    private Pacote pacote;



    public TentativaEntrega() {}

    public TentativaEntrega(String recebedor, String statusEntrega, String falhaEntrega, byte[] assinaturaRecebedor, Entregador entregador, Pacote pacote) {
        this.recebedor = recebedor;
        this.statusEntrega = statusEntrega;
        this.falhaEntrega = falhaEntrega;
        this.assinaturaRecebedor = assinaturaRecebedor;
        this.entregador = entregador;
        this.pacote = pacote;
    }

    // getters e setters
    public int getIdTentativa() {
        return idTentativa;
    }

    public void setIdTentativa(int idTentativa) {
        this.idTentativa = idTentativa;
    }

    public String getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(String recebedor) {
        this.recebedor = recebedor;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public String getFalhaEntrega() {
        return falhaEntrega;
    }

    public void setFalhaEntrega(String falhaEntrega) {
        this.falhaEntrega = falhaEntrega;
    }

    public byte[] getAssinaturaRecebedor() {
        return assinaturaRecebedor;
    }

    public void setAssinaturaRecebedor(byte[] assinaturaRecebedor) {
        this.assinaturaRecebedor = assinaturaRecebedor;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }
}
