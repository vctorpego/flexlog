package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.EntregadorDTO;
import br.bom.flexlog.academic.dto.TransportadoraDTO;
import br.bom.flexlog.academic.entity.Entregador;
import br.bom.flexlog.academic.entity.Transportadora;
import br.bom.flexlog.academic.repository.EntregadorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntregadorRepository entregadorRepository;

    // Injetando o encoder
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EntregadorService(EntregadorRepository entregadorRepository) {
        this.entregadorRepository = entregadorRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<EntregadorDTO> listarTodos(){
        List<Entregador> entregadores = entregadorRepository.findAll();
        return entregadores.stream().map(EntregadorDTO::new).toList();
    }

    public EntregadorDTO inserir(EntregadorDTO dto) throws Exception {
        try {
            String senhaCriptografada = passwordEncoder.encode(dto.getSenhaUsuario());
            dto.setSenhaUsuario(senhaCriptografada);

            Entregador entregador = new Entregador(dto);
            Entregador salva = entregadorRepository.save(entregador);
            return new EntregadorDTO(salva);
        } catch (DataIntegrityViolationException dive) {
            // Aqui você pode verificar a causa se quiser, ou lançar direto
            throw new Exception("Entregador já cadastrado: login '" + dto.getLogin() + "'", dive);
        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar entregador: " + e.getMessage(), e);
        }
    }
    public EntregadorDTO alterar(EntregadorDTO dto) {
        Entregador entregadorExistente = entregadorRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        // Atualize os campos necessários
        entregadorExistente.setNomeUsuario(dto.getNomeUsuario());
        entregadorExistente.setCnh(dto.getCnh());
        entregadorExistente.setEmailUsuario(dto.getEmailUsuario());
        entregadorExistente.setTelefoneUsuario(dto.getTelefoneUsuario());
        entregadorExistente.setLogin(dto.getLogin());
        entregadorExistente.setIsAdm(dto.getIsAdm());


        // Se quiser atualizar a senha, verifique se foi enviada
        if (dto.getSenhaUsuario() != null && !dto.getSenhaUsuario().isEmpty()) {
            String senhaCriptografada = passwordEncoder.encode(dto.getSenhaUsuario());
            entregadorExistente.setSenhaUsuario(senhaCriptografada);
        }

        // Salva e retorna o DTO atualizado
        Entregador salvo = entregadorRepository.save(entregadorExistente);
        return new EntregadorDTO(salvo);
    }


    public void excluir(Integer id){
        Entregador entregador = entregadorRepository.findById(id).get();
        entregadorRepository.delete(entregador);
    }

    public EntregadorDTO buscarPorId(Integer id){
        return entregadorRepository.findById(id)
                .map(EntregadorDTO::new)
                .orElse(null);
    }





}
