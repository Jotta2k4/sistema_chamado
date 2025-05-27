package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.customerdto.CustomerRequestDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerResponseDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerUpdateDTO;
import com.example.sistema_chamado.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("customer")
@Tag(name = "Customer", description = "Controlador relaconado aos clientes")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("name")
    @Operation(summary = "Buscar Clientes Pelo Nome", description = "Retorna uma lista de clientes com determinado nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<List<CustomerResponseDTO>> findByName (
            @Parameter(description = "Nome a ser buscado", example = "name?name=wilson")
            @RequestParam String name) {
        List<CustomerResponseDTO> customerResponseDTO = this.customerService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Criar Novo Cliente", description = "Criar um novo cliente ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Algum dado inválido")
    })
    public ResponseEntity<CustomerResponseDTO> createCustomer (@Valid @RequestBody CustomerRequestDTO data) {
        CustomerResponseDTO customerResponseDTO = this.customerService.createCustomer(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar Um Usuário", description = "Deletar um usuário de acordo com o seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente foi deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente com o ID não foi encontrado")
    })
    public ResponseEntity<Void> deleteUser (@PathVariable Integer id) {
         this.customerService.deleteCustomer(id);
         return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar Um Usuário", description = "Atualizar um usuário passando novos dados e o id relacionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente foi atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente com o ID não foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Algum dado inválido")
    })
    public ResponseEntity<CustomerUpdateDTO> updateCustomer (@PathVariable Integer id
            , @RequestBody @Valid CustomerUpdateDTO data) {
       CustomerUpdateDTO customer = this.customerService.updateCustomer(id, data);
       return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
