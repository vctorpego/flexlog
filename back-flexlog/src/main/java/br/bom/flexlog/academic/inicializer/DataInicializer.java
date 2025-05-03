package br.bom.flexlog.academic.inicializer;

import br.bom.flexlog.academic.entity.Permissao;
import br.bom.flexlog.academic.entity.Tela;
import br.bom.flexlog.academic.entity.Usuario;
import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;
import br.bom.flexlog.academic.repository.PermissaoRepository;
import br.bom.flexlog.academic.repository.TelaRepository;
import br.bom.flexlog.academic.repository.UsuarioPermissaoTelaRepository;
import br.bom.flexlog.academic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInicializer implements CommandLineRunner {

    @Autowired
    private TelaRepository telaRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioPermissaoTelaRepository usuarioPermissaoTelaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (telaRepository.count() == 0) {
            System.out.println("-> Inserindo Telas...");
          //  telaRepository.save(new Tela("Tela de Dashboard", "/home"));
          //  telaRepository.save(new Tela("Tela de Produtos", "/produtos"));
          //  telaRepository.save(new Tela("Tela de Fornecedores", "/fornecedores"));
          //  telaRepository.save(new Tela("Tela de Clientes", "/clientes"));
          //  telaRepository.save(new Tela("Tela de Recarga", "/recarga"));
          //  telaRepository.save(new Tela("Tela de Vendas", "/vendas"));
          //  telaRepository.save(new Tela("Tela de Pagamentos", "/pagamentos"));
          //  telaRepository.save(new Tela("Tela de Relatórios", "/relatorios"));
           // telaRepository.save(new Tela("Tela de Entrada", "/entrada"));
            //telaRepository.save(new Tela("Tela de Saída", "/saida"));
            telaRepository.save(new Tela("Tela de Usuarios", "/usuarios"));
            telaRepository.save(new Tela("Tela de Tela", "/telas"));
        } else {
            System.out.println("-> Telas já existem");
        }

        if (permissaoRepository.count() == 0) {
            System.out.println("-> Inserindo Permissões...");
            permissaoRepository.save(new Permissao("Incluir", "POST"));
            permissaoRepository.save(new Permissao("Alterar", "PUT"));
            permissaoRepository.save(new Permissao("Excluir", "DELETE"));
            permissaoRepository.save(new Permissao("Visualizar", "GET"));
        } else {
            System.out.println("-> Permissões já existem");
        }



        if (usuarioRepository.count() == 0) {
            System.out.println("-> Criando SUPER ADMIN...");

            // Criptografar a senha usando BCryptPasswordEncoder
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String senhaCriptografada = passwordEncoder.encode("rick");
            Usuario superAdmin = new Usuario();
            superAdmin.setNomeUsuario("Paulo Henrique");
            superAdmin.setLogin("rick");
            superAdmin.setSenhaUsuario(senhaCriptografada);
            superAdmin.setEmailUsuario("superadm@email.com");
            superAdmin.setTelefoneUsuario("2799923-8772");
            superAdmin.setIsAdm(true);
            superAdmin.setIsSuperAdm(true);
            superAdmin = usuarioRepository.save(superAdmin); // <- salva e obtém a instância gerenciada

            List<Tela> telas = telaRepository.findAll();
            List<Permissao> permissoes = permissaoRepository.findAllByOrderByIdPermisaoAsc();

            for (Tela tela : telas) {
                int idTela = tela.getIdTela(); // pega o ID da tela
                for (Permissao permissao : permissoes) {
                    int idPermissao = permissao.getIdPermissao(); // pega o ID da permissão

                    // busca as instâncias gerenciadas diretamente do banco
                    Tela telaGerenciada = telaRepository.findById(idTela).orElse(null);
                    Permissao permissaoGerenciada = permissaoRepository.findById(idPermissao).orElse(null);

                    if (telaGerenciada != null && permissaoGerenciada != null) {
                        UsuarioPermissaoTela upt = new UsuarioPermissaoTela();
                        upt.setUsuario(superAdmin);
                        upt.setTela(telaGerenciada);
                        upt.setPermissao(permissaoGerenciada);
                        usuarioPermissaoTelaRepository.save(upt);
                    }
                }
            }
        } else {
            System.out.println("-> Super Admin já existe");
        }
    }



}

