package com.example.sistema_chamado.calledtest;

import com.example.sistema_chamado.dtos.calleddto.CalledRequestDTO;
import com.example.sistema_chamado.dtos.calleddto.CalledResponseDTO;
import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;
import com.example.sistema_chamado.enums.Status;
import com.example.sistema_chamado.exceptions.CalledNotFoundById;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Customer;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.CalledRepository;
import com.example.sistema_chamado.repositories.TechnicalRepository;
import com.example.sistema_chamado.services.CalledService;
import com.example.sistema_chamado.services.CustomerService;
import com.example.sistema_chamado.services.TechnicalService;
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
class CalledServiceTest {

    @Mock
    private CalledRepository calledRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private TechnicalRepository technicalRepository;

    @Mock
    private TechnicalService technicalService;

    @InjectMocks
    private CalledService calledService;

    @Captor
    private ArgumentCaptor<Called> calledCaptor;

    @Nested
    class createCalled {

        @Test
        @DisplayName("Teste que deve criar um chamado com sucesso")
        void shouldCreateCalledWithSuccess() {
            // Arrange
            var requestDTO = new CalledRequestDTO(
                    "Erro no sistema",
                    "O sistema não está funcionando corretamente.",
                    Priority.ALTA,
                    Category.SOFTWARE,
                    1
            );

            Customer customer = new Customer();
            customer.setId(1);
            customer.setName("Wilson");
            customer.setEmail("wilson@gmail.com");
            customer.setPhone("123456");

            Technical technical = new Technical();
            technical.setId(1);
            technical.setName("Vicente");
            technical.setCalled(new ArrayList<>());

            Called calledToSave = new Called();
            calledToSave.setId(10);
            calledToSave.setTitle(requestDTO.title());
            calledToSave.setDescription(requestDTO.description());
            calledToSave.setPriority(requestDTO.priority());
            calledToSave.setCategory(requestDTO.category());
            calledToSave.setStatus(Status.ABERTO);
            calledToSave.setCustomer(customer);
            calledToSave.setTechnical(technical);
            calledToSave.setTechName(technical.getName());

            doReturn(customer).when(customerService).findCustomer(1);
            doReturn(List.of(technical)).when(technicalRepository).findAll();
            doReturn(technical).when(technicalService).findByName(technical.getName());
            doReturn(calledToSave).when(calledRepository).save(calledCaptor.capture());

            // Act
            CalledResponseDTO response = calledService.createCalled(requestDTO);
            Called calledCaptured = calledCaptor.getValue();

            // Assert
            assertNotNull(response);
            assertEquals("Erro no sistema", calledCaptured.getTitle());
            assertEquals(Status.ABERTO, calledCaptured.getStatus());
            assertEquals("Vicente", response.technicalName());
            assertEquals("Wilson", response.customer().name());
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Teste que deve encontrar um chamado pelo ID")
        void shouldFindCalledByIdWithSuccess() {
            Called called = new Called();
            called.setId(1);

            doReturn(Optional.of(called)).when(calledRepository).findById(1);

            Called result = calledService.findById(1);

            assertNotNull(result);
            assertEquals(1, result.getId());
        }

        @Test
        @DisplayName("Teste que deve lançar exceção ao buscar chamado inexistente")
        void shouldThrowExceptionWhenCalledNotFound() {
            doReturn(Optional.empty()).when(calledRepository).findById(99);

            assertThrows(CalledNotFoundById.class, () -> calledService.findById(99));
        }
    }
    @Test
    void testFindCalledsResponseByStatusOpenOrInProcess() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("Vicente");
        customer.setEmail("joao@example.com");
        customer.setPhone("99999-9999");

        Technical technical = new Technical();
        technical.setName("Wilson");

        Called chamado1 = new Called();
        chamado1.setId(1);
        chamado1.setTitle("Erro no sistema");
        chamado1.setDescription("Sistema não inicia");
        chamado1.setPriority(Priority.ALTA);
        chamado1.setCategory(Category.SOFTWARE);
        chamado1.setStatus(Status.ABERTO);
        chamado1.setCustomer(customer);
        chamado1.setTechnical(technical);

        List<Called> chamados = List.of(chamado1);

        when(calledRepository.findByStatusIn(List.of(Status.ABERTO, Status.ANDAMENTO)))
                .thenReturn(chamados);

        // Act
        List<CalledResponseDTO> result = calledService.findCalledsResponseByStatusOpenOrInProgress();

        // Assert
        assertEquals(1, result.size());
        CalledResponseDTO dto = result.getFirst();
        assertEquals("Erro no sistema", dto.title());
        assertEquals("Wilson", dto.technicalName());
        assertEquals("Vicente", dto.customer().name());

        verify(calledRepository).findByStatusIn(List.of(Status.ABERTO, Status.ANDAMENTO));
    }

}

