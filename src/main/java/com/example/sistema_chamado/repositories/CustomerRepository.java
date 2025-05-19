package com.example.sistema_chamado.repositories;

import com.example.sistema_chamado.dtos.customerdto.CustomerResponseDTO;
import com.example.sistema_chamado.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<CustomerResponseDTO> findByName(@Param("name") String name);


}
