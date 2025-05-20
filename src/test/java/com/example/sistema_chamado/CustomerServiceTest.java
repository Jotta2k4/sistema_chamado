package com.example.sistema_chamado;

import com.example.sistema_chamado.dtos.customerdto.CustomerRequestDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerResponseDTO;
import com.example.sistema_chamado.dtos.customerdto.CustomerUpdateDTO;
import com.example.sistema_chamado.models.CallHistory;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Customer;
import com.example.sistema_chamado.repositories.CalledRepository;
import com.example.sistema_chamado.repositories.CustomerRepository;
import com.example.sistema_chamado.services.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CalledRepository calledRepository;

    @InjectMocks
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @Nested
    class createCustomer {

        @Test
        @DisplayName("teste que deve criar um cliente com sucesso")
        void shouldCreateACustomerWithSuccess() {
            // Arrange
            CustomerRequestDTO data = new CustomerRequestDTO("Wilson", "wilson.qdop@gmail.com", "123", "11111");

            Customer customerMockSaved = CustomerHelp.buildSavedCustomer();

            doReturn(customerMockSaved).when(customerRepository).save(customerArgumentCaptor.capture());

            //Act
            CustomerResponseDTO response = customerService.createCustomer(data);
            Customer customerCapture = customerArgumentCaptor.getValue();

            //Assert
            assertNotNull(response);
            assertEquals(customerMockSaved.getName(), customerCapture.getName());
            assertEquals(customerMockSaved.getEmail(), customerCapture.getEmail());
            assertEquals(customerMockSaved.getPhone(),customerCapture.getPhone());
        }
    }

    @Nested
    class updateCustomer {

        @Test
        @DisplayName("Teste que deve atualizar os dados dos usuários")
        void shouldUpdateACustomerWithSuccess () {

            //arrange
            CustomerUpdateDTO customerUpdate = new CustomerUpdateDTO("Wilson Francisco", "wilson@gmail.com"
                    ,"123456","AA333");

            Customer customerMockSaved = CustomerHelp.buildSavedCustomer();
            customerMockSaved.setName(customerUpdate.name());
            customerMockSaved.setEmail(customerUpdate.email());
            customerMockSaved.setPhone(customerUpdate.phone());
            customerMockSaved.setPassword(customerUpdate.password());

            Customer existsById = CustomerHelp.buildSavedCustomer();

            doReturn(customerMockSaved).when(customerRepository).save(customerArgumentCaptor.capture());
            doReturn(Optional.of(existsById)).when(customerRepository).findById(existsById.getId());

            //act
            CustomerUpdateDTO data = customerService.updateCustomer(existsById.getId(), customerUpdate);
            Customer customerCapture = customerArgumentCaptor.getValue();

            //assert
            assertEquals(data.name(), customerCapture.getName());
            assertEquals(data.email(), customerCapture.getEmail());
            assertEquals(data.phone(), customerCapture.getPhone());
            assertEquals(data.password(), customerCapture.getPassword());

        }
    }

    @Nested
    class deleteCustomer {

        @Test
        @DisplayName("Teste que deve deletar usuário com sucesso")
        void shouldDeleteACustomerWithSuccess() {
            Integer customerId = 1;

            Customer customer = CustomerHelp.buildSavedCustomer();

            Called called = new Called();
            called.setId(10);
            called.setHistories(new ArrayList<>(List.of(new CallHistory(), new CallHistory())));

            List<Called> calledList = List.of(called);

            doReturn(Optional.of(customer)).when(customerRepository).findById(customerId);
            doReturn(calledList).when(calledRepository).findByCustomerId(customerId);

            customerService.deleteCustomer(customerId);

            verify(calledRepository, times(1)).findByCustomerId(customerId);
            verify(calledRepository, times(1)).delete(called);
            verify(customerRepository, times(1)).delete(customer);

            assertTrue(called.getHistories().isEmpty());

        }
    }



}
