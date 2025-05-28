package com.example.sistema_chamado.callhistorytest;

import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryDTO;
import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryResponseDTO;
import com.example.sistema_chamado.enums.Status;
import com.example.sistema_chamado.exceptions.CallAlreadyCompleted;
import com.example.sistema_chamado.exceptions.CallCanceled;
import com.example.sistema_chamado.models.CallHistory;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.CallHistoryRepository;
import com.example.sistema_chamado.repositories.CalledRepository;
import com.example.sistema_chamado.services.CallHistoryService;
import com.example.sistema_chamado.services.CalledService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CallHistoryServiceTest {

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @Mock
    private CalledService calledService;

    @Mock
    private CalledRepository calledRepository;

    @InjectMocks
    private CallHistoryService callHistoryService;

    @Captor
    private ArgumentCaptor<CallHistory> historyCaptor;

    @Nested
    class createHistoricCall {

        @Test
        @DisplayName("Teste que deve criar um histórico de chamado com sucesso")
        void shouldCreateCallHistoryWithSuccess() {
            // Arrange
            Integer calledId = 1;

            Technical technical = new Technical();
            technical.setId(10);
            technical.setName("Técnico 1");

            Called called = new Called();
            called.setId(calledId);
            called.setStatus(Status.ANDAMENTO);
            called.setTechnical(technical);

            CallHistoryResponseDTO request = new CallHistoryResponseDTO("Chamado resolvido", Status.CONCLUIDO);

            CallHistory savedHistory = new CallHistory();
            savedHistory.setId(100);
            savedHistory.setComment(request.comment());
            savedHistory.setStatus(request.status());
            savedHistory.setCalled(called);
            savedHistory.setTechnical(technical);

            doReturn(called).when(calledService).findById(calledId);
            doReturn(savedHistory).when(callHistoryRepository).save(historyCaptor.capture());
            doReturn(called).when(calledRepository).save(called);

            // Act
            CallHistoryDTO response = callHistoryService.createHistoricCall(request, calledId);
            CallHistory captured = historyCaptor.getValue();

            // Assert
            assertNotNull(response);
            assertEquals("Chamado resolvido", captured.getComment());
            assertEquals(Status.CONCLUIDO, captured.getStatus());
            assertEquals("Técnico 1", response.technicalName());
        }
    }

    @Test
    @DisplayName("Teste que deve lançar exceção se chamado estiver concluído")
    void shouldThrowExceptionWhenCallAlreadyCompleted() {
        Called called = new Called();
        called.setId(1);
        called.setStatus(Status.CONCLUIDO);

        doReturn(called).when(calledService).findById(1);

        CallHistoryResponseDTO request = new CallHistoryResponseDTO("Tentativa de update", Status.CONCLUIDO);

        assertThrows(CallAlreadyCompleted.class, () -> callHistoryService.createHistoricCall(request, 1));
    }
    @Test
    @DisplayName("Teste que deve lançar exceção se chamado estiver cancelado")
    void shouldThrowExceptionWhenCallCanceled() {
        Called called = new Called();
        called.setId(1);
        called.setStatus(Status.CANCELADO);

        doReturn(called).when(calledService).findById(1);

        CallHistoryResponseDTO request = new CallHistoryResponseDTO( "Tentativa de update", Status.ANDAMENTO);

        assertThrows(CallCanceled.class, () -> callHistoryService.createHistoricCall(request, 1));
    }
}
