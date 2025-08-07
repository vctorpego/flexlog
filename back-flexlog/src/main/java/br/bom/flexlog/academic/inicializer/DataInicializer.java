package br.bom.flexlog.academic.inicializer;

import br.bom.flexlog.academic.entity.*;
import br.bom.flexlog.academic.repository.*;
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
    private StatusPacoteRepository statusPacoteRepository;

    @Autowired
    private StatusAgendamentoRepository statusAgendamentoRepository;

    @Autowired
    private UsuarioPermissaoTelaRepository usuarioPermissaoTelaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (telaRepository.count() == 0) {
            System.out.println("-> Inserindo Telas...");
            telaRepository.save(new Tela("Tela de Entrada", "/entrada"));
            telaRepository.save(new Tela("Tela de Usuarios", "/usuarios"));
            telaRepository.save(new Tela("Tela de Transportadoras", "/transportadoras"));
            telaRepository.save(new Tela("Tela de Pacotes", "/pacotes"));
            telaRepository.save(new Tela("Tela de Meus Pacotes", "/meus-pacotes"));
            telaRepository.save(new Tela("Tela de Rollback", "/rollback"));
            telaRepository.save(new Tela("Tela de Tela", "/telas"));

        } else {
            System.out.println("-> Telas já existem");
        }
        if(statusPacoteRepository.count() == 0){
            System.out.println("-> Inserindo Status de Pacotes...");
            statusPacoteRepository.save(new StatusPacote("Chegou na cidade"));
            statusPacoteRepository.save(new StatusPacote("Saiu para entrega"));
            statusPacoteRepository.save(new StatusPacote("Entregue com sucesso"));
            statusPacoteRepository.save(new StatusPacote("Tentativa de entrega com falha"));
            statusPacoteRepository.save(new StatusPacote("Retornou ao centro logistico"));
        }else{
            System.out.println("-> Status já existem");

        }
        if(statusAgendamentoRepository.count() == 0){
            System.out.println("-> Inserindo  Status de Agendamentos");
            statusAgendamentoRepository.save(new StatusAgendamento("Aguardando Agendamento"));
            statusAgendamentoRepository.save(new StatusAgendamento("Agendado"));
            statusAgendamentoRepository.save(new StatusAgendamento("Agendado para Hoje"));


        }else{
            System.out.println("-> Agendamentos já existem");
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
            String senhaCriptografada = passwordEncoder.encode("superadmin");
            Usuario superAdmin = new Usuario();
            superAdmin.setNomeUsuario("Paulo Henrique");
            superAdmin.setLogin("superadmin");
            superAdmin.setSenhaUsuario(senhaCriptografada);
            superAdmin.setEmailUsuario("superadm@email.com");
            superAdmin.setTelefoneUsuario("273700-0000");
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

