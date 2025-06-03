package br.bom.flexlog.academic.service;


import br.bom.flexlog.academic.dto.TransportadoraDTO;
import br.bom.flexlog.academic.entity.Transportadora;
import br.bom.flexlog.academic.exeptions.CnpjDuplicadoException;
import br.bom.flexlog.academic.repository.TransportadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportadoraService {
    @Autowired
    private TransportadoraRepository transportadoraRepository;

    public List<TransportadoraDTO> listarTodos(){
        List<Transportadora> transportadoras = transportadoraRepository.findAll();
        return transportadoras.stream().map(TransportadoraDTO::new).toList();
    }

    public TransportadoraDTO inserir(TransportadoraDTO dto) {
        try {
            Transportadora transportadora = new Transportadora(dto);
            Transportadora salva = transportadoraRepository.save(transportadora);
            return new TransportadoraDTO(salva);
        } catch (DataIntegrityViolationException e) {
            throw new CnpjDuplicadoException("CNPJ já cadastrado: " + dto.getCnpj());
        }
    }



    public TransportadoraDTO alterar(TransportadoraDTO transportadoraDTO){
        Transportadora transportadora= new Transportadora(transportadoraDTO);
        return new TransportadoraDTO(transportadoraRepository.save(transportadora));
    }

    public void excluir(Integer id){
        Transportadora transportadora = transportadoraRepository.findById(id).get();
        transportadoraRepository.delete(transportadora);
    }

    public TransportadoraDTO buscarPorId(Integer id){
        return transportadoraRepository.findById(id)
                .map(TransportadoraDTO::new)
                .orElse(null);
    }

}

