package com.example.sistema_chamado.services;

import com.example.sistema_chamado.dtos.calleddto.CalledRequestDTO;
import com.example.sistema_chamado.dtos.calleddto.CalledResponseDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerInfoDTO;
import com.example.sistema_chamado.enums.Status;
import com.example.sistema_chamado.exceptions.CalledNotFoundById;
import com.example.sistema_chamado.exceptions.NoTechnicalAvailable;
import com.example.sistema_chamado.exceptions.NoTechnicalRegistered;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Customer;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.CalledRepository;
import com.example.sistema_chamado.repositories.TechnicalRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CalledService {
    private final CalledRepository calledRepository;
    private final CustomerService customerService;
    private final TechnicalRepository technicalRepository;
    private final TechnicalService technicalService;
    public static Logger log = LoggerFactory.getLogger(CalledService.class);

    public CalledService(CalledRepository calledRepository,
                         CustomerService customerService, TechnicalRepository technicalRepository, TechnicalService technicalService) {
        this.calledRepository = calledRepository;
        this.customerService = customerService;
        this.technicalRepository = technicalRepository;
        this.technicalService = technicalService;
    }

    public List<CalledResponseDTO> findCalledsResponseByStatusOpenOrInProgress() {
        List<Called> calleds = this.calledRepository.findByStatusIn(List.of(Status.ABERTO, Status.ANDAMENTO));

        return calleds.stream().map(c -> new CalledResponseDTO(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getPriority(),
                c.getCategory(),
                c.getStatus(),
                c.getDateService(),
                new CustomerInfoDTO(
                        c.getCustomer().getName(),
                        c.getCustomer().getEmail(),
                        c.getCustomer().getPhone()
                ),
                c.getTechnical().getName()
        )).toList();
    }


    public CalledResponseDTO createCalled(CalledRequestDTO data) {
        log.info("Iniciando criação de chamado com dados: {}", data);
        Customer customer = this.customerService.findCustomer(data.customerId());
        log.info("Cliente encontrado com ID: {}", data.customerId());

        Technical technical = findTechnicalMinCalled();
        log.info("Técnico com menor número de chamados encontrado: {}", technical.getName());
        Technical findName = this.technicalService.findByName(technical.getName());

        Called called = new Called();
        called.setTitle(data.title());
        called.setDescription(data.description());
        called.setPriority(data.priority());
        called.setCategory(data.category());
        called.setStatus(Status.ABERTO);
        called.setCustomer(customer);
        called.setTechnical(technical);
        called.setTechName(findName.getName());

        Called saved = this.calledRepository.save(called);
        log.info("Chamado criado com sucesso com ID: {}", saved.getId());

        return new CalledResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPriority(),
                saved.getCategory(),
                saved.getStatus(),
                saved.getDateService(),
                new CustomerInfoDTO(
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhone()
                ),
                technical.getName()
        );
    }
    public Called findById (Integer id) {
        log.info("Buscando chamado com ID: {}", id);
        Called called = this.calledRepository.findById(id)
                .orElseThrow(()-> new CalledNotFoundById("Chamado com id " + id + " não encontrado"));
        log.info("Chamado encontrado com sucesso: {}", called.getId());
        return called;
    }

    private Technical findTechnicalMinCalled() {
        log.info("Buscando técnico com menor número de chamados abertos");
        List<Technical> technicals = this.technicalRepository.findAll();
        log.info("Total de técnicos encontrados: {}", technicals.size());

        if (technicals.isEmpty()) {
            log.info("Nenhum técnico cadastrado no sistema");
            throw new NoTechnicalRegistered("Nenhum técnico cadastrado!");
        }

        List<Technical> availableTechnicals  = technicals.stream()
                .filter(t -> t.getCalled().stream()
                        .filter(called -> called.getStatus() == Status.ABERTO).count() <3)
                .toList();
        log.info("Técnicos disponíveis (com menos de 3 chamados abertos): {}", availableTechnicals.size());

        return availableTechnicals.stream().min((t1, t2)->{
            int qtdCalledT1 = (int) t1.getCalled().stream()
                    .filter(called -> called.getStatus() == Status.ABERTO).count();

            int qtdCalledT2 = (int) t2.getCalled().stream()
                    .filter(called -> called.getStatus() == Status.ABERTO).count();
            return Integer.compare(qtdCalledT1, qtdCalledT2);
        }).orElseThrow(() -> {
            log.info("Nenhum técnico disponível no momento");
            return new NoTechnicalAvailable("Nenhum Técnico disponível no momento");
        });
    }
}
