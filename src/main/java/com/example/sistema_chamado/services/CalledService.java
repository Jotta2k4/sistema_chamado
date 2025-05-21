package com.example.sistema_chamado.services;

import com.example.sistema_chamado.dtos.calleddto.CalledRequestDTO;
import com.example.sistema_chamado.dtos.calleddto.CalledResponseDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerInfoDTO;
import com.example.sistema_chamado.enums.Status;
import com.example.sistema_chamado.exceptions.CalledNotFoundById;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Customer;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.CalledRepository;
import com.example.sistema_chamado.repositories.TechnicalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalledService {
    private final CalledRepository calledRepository;
    private final CustomerService customerService;
    private final TechnicalRepository technicalRepository;

    public CalledService(CalledRepository calledRepository,
                         CustomerService customerService, TechnicalRepository technicalRepository) {
        this.calledRepository = calledRepository;
        this.customerService = customerService;
        this.technicalRepository = technicalRepository;
    }

    public CalledResponseDTO createCalled(CalledRequestDTO data) {
        Customer customer = this.customerService.findCustomer(data.customerId());


        Technical technical = findTechnicalMinCalled();
        Technical findName = this.technicalRepository.findByName(technical.getName())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado na base de dados"));

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
        return this.calledRepository.findById(id)
                .orElseThrow(()-> new CalledNotFoundById("Chamado com id " + id + " não encontrado"));
    }

    private Technical findTechnicalMinCalled() {
        List<Technical> technicals = this.technicalRepository.findAll();

        if (technicals.isEmpty()) {
            throw new RuntimeException("Nenhum técnico cadastrado!");
        }

        List<Technical> availableTechnicals  = technicals.stream()
                .filter(t -> t.getCalled().stream()
                        .filter(called -> called.getStatus() == Status.ABERTO).count() <3)
                .toList();

        return availableTechnicals.stream().min((t1, t2)->{
                int qtdCalledT1 = (int) t1.getCalled().stream()
                        .filter(called -> called.getStatus() == Status.ABERTO).count();

                int qtdCalledT2 = (int) t2.getCalled().stream()
                        .filter(called -> called.getStatus() == Status.ABERTO).count();
                return Integer.compare(qtdCalledT1, qtdCalledT2);
        }).orElseThrow(() -> new RuntimeException("Nenhum Técnico disponível no momento"));
    }
}
