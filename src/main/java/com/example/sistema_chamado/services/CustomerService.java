package com.example.sistema_chamado.services;

import com.example.sistema_chamado.dtos.customerdto.CustomerRequestDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerResponseDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerUpdateDTO;
import com.example.sistema_chamado.enums.Perfil;
import com.example.sistema_chamado.exceptions.CustomerNotFoundById;
import com.example.sistema_chamado.exceptions.CustomerNotFoundByName;
import com.example.sistema_chamado.exceptions.CustomerPasswordNotExists;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Customer;
import com.example.sistema_chamado.repositories.CalledRepository;
import com.example.sistema_chamado.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CalledRepository calledRepository;
    public static Logger log = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, CalledRepository calledRepository) {
        this.customerRepository = customerRepository;
        this.calledRepository = calledRepository;
    }

    public List<CustomerResponseDTO> findByName(String name) {
        List<CustomerResponseDTO> list = this.customerRepository.findByName(name);

        if (list.isEmpty()) {
            throw new CustomerNotFoundByName("Cliente com o nome " + name + " não encontrado");
        }

        return list;
    }

    public Customer findCustomer (Integer id) {
        return this.customerRepository.findById(id)
                .orElseThrow( () -> new CustomerNotFoundById("Cliente não encontrado"));
    }

    public CustomerResponseDTO createCustomer (CustomerRequestDTO data) {

        Customer customer = new Customer();
        customer.setName(data.name());
        customer.setEmail(data.email());
        customer.setPassword(data.password());
        customer.setPhone(data.phone());

        customer.addPerfil(Perfil.CUSTOMER);

        Customer newCustomer =  this.customerRepository.save(customer);

        return new CustomerResponseDTO(
                newCustomer.getId(),
                newCustomer.getName(),
                newCustomer.getEmail(),
                newCustomer.getPhone()
        );
    }

    public CustomerUpdateDTO updateCustomer (Integer id, CustomerUpdateDTO data) {
        Customer customer = findCustomer(id);

        customer.setName(data.name());
        customer.setEmail(data.email());
        customer.setPhone(data.phone());
        customer.setPassword(data.password());

        Customer updateCustomer = this.customerRepository.save(customer);
        return new CustomerUpdateDTO(
                updateCustomer.getName(),
                updateCustomer.getEmail(),
                updateCustomer.getPhone(),
                updateCustomer.getPassword()
        );

    }

    @Transactional
    public void deleteCustomer(Integer id) {
        Customer dta = findCustomer(id);

        List<Called> calledList = this.calledRepository.findByCustomerId(id);

        log.info("Senha passada no corpo: ");
        log.info("Senha salva no banco: ");

        if (dta.getPassword() == null) {
            throw new CustomerPasswordNotExists("Senha do cliente está nula!");
        }

        for (Called called : calledList) {
            called.getHistories().clear();
            this.calledRepository.delete(called);
        }
        this.customerRepository.delete(dta);

    }



}
