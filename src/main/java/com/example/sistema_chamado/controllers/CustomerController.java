package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.customerdto.CustomerRequestDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerResponseDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerUpdateDTO;
import com.example.sistema_chamado.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("name")
    public ResponseEntity<List<CustomerResponseDTO>> findByName (@RequestParam String name) {
        List<CustomerResponseDTO> customerResponseDTO = this.customerService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer (@RequestBody CustomerRequestDTO data) {
        CustomerResponseDTO customerResponseDTO = this.customerService.createCustomer(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Integer id) {
         this.customerService.deleteCustomer(id);
         return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerUpdateDTO> updateCustomer (@PathVariable Integer id
            , @RequestBody CustomerUpdateDTO data) {
       CustomerUpdateDTO customer = this.customerService.updateCustomer(id, data);
       return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
