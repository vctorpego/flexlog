package br.bom.flexlog.academic.dto;

public class AcessDTO {
    private String token;
    // implementar retornar o usuario e as permissoes

    public AcessDTO(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
