package com.example.sistema_chamado.services;

import com.example.sistema_chamado.dtos.technicaldto.TechnicalRequestDTO;
import com.example.sistema_chamado.dtos.technicaldto.TechnicalResponseDTO;
import com.example.sistema_chamado.enums.Perfil;
import com.example.sistema_chamado.exceptions.TechnicalNotFoundByName;
import com.example.sistema_chamado.exceptions.TechnicalNotFoundInList;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.TechnicalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicalService {
    private final TechnicalRepository technicalRepository;
    public static Logger log = LoggerFactory.getLogger(TechnicalService.class);

    public TechnicalService(TechnicalRepository technicalRepository) {
        this.technicalRepository = technicalRepository;
    }

    public Technical findByName (String name) {
        log.info("Buscando técnico pelo nome: {}", name);
        Technical technical = this.technicalRepository.findByName(name)
                .orElseThrow(() -> {
                    log.info("Técnico com nome {} não encontrado", name);
                    return new TechnicalNotFoundByName("Técnico não encontrado");
                });
        log.info("Técnico encontrado com sucesso: {}", technical.getName());
        return technical;
    }

    public List<Technical> findByNameAll (String name) {
        log.info("Buscando todos os técnicos com nome: {}", name);
        List<Technical> list = this.technicalRepository.findAllByName(name);

        if (list.isEmpty()) {
            log.info("Nenhum técnico encontrado com o nome: {}", name);
            throw new TechnicalNotFoundInList("Técnico com o nome " + name + " não encontrado");
        }
        log.info("Encontrados {} técnicos com o nome: {}", list.size(), name);
        return list;
    }

    public TechnicalResponseDTO createTechnical (TechnicalRequestDTO data) {
        log.info("Iniciando criação de técnico com dados: {}", data);
        Technical tech = new Technical();
        tech.setName(data.name());
        tech.setEmail(data.email());
        tech.setPassword(data.password());

        tech.addPerfil(Perfil.TECH);
        log.info("Perfil TECH adicionado ao técnico");

        Technical newTech = this.technicalRepository.save(tech);
        log.info("Técnico criado com sucesso com ID: {}", newTech.getId());

        return new TechnicalResponseDTO (
                newTech.getId(),
                newTech.getName(),
                newTech.getEmail()
        );
    }
}
