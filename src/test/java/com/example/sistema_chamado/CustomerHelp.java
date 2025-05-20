package com.example.sistema_chamado;

import com.example.sistema_chamado.enums.Perfil;
import com.example.sistema_chamado.models.Customer;

public class CustomerHelp {

    public static Customer buildSavedCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Wilson");
        customer.setEmail("wilson.qdop@gmail.com");
        customer.setPassword("123");
        customer.setPhone("11111");
        customer.addPerfil(Perfil.CUSTOMER);
        return customer;
    }
}
