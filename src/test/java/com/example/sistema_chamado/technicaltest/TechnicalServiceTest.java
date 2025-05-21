package com.example.sistema_chamado.technicaltest;

import com.example.sistema_chamado.dtos.technicaldto.TechnicalRequestDTO;
import com.example.sistema_chamado.dtos.technicaldto.TechnicalResponseDTO;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.TechnicalRepository;
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

import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TechnicalServiceTest {

    @Mock
    private TechnicalRepository technicalRepository;

    @InjectMocks
    private TechnicalService technicalService;

    @Captor
    private ArgumentCaptor<Technical> technicalArgumentCaptor;

    @Nested
    class createTechnical {

        @Test
        @DisplayName("Teste que deve criar um técnico com sucesso")
        void shouldCreateATechnicalWithSuccess() {
            Technical technical = TechnicalHelper.buildSaveTechnical();

            TechnicalRequestDTO mockedTechnical = new TechnicalRequestDTO(
                     "Wilson Tech", "Wilson.Tech@Gmail.com", "12345"
            );

             doReturn(technical).when(technicalRepository).save(technicalArgumentCaptor.capture());

            TechnicalResponseDTO technicalResponse = technicalService.createTechnical(mockedTechnical);
            Technical customerCapture = technicalArgumentCaptor.getValue();

            assertNotNull(technicalResponse);
            assertEquals(mockedTechnical.name(), customerCapture.getName());
            assertEquals(mockedTechnical.email(), customerCapture.getEmail());
            assertEquals(mockedTechnical.password(), customerCapture.getPassword());
        }
    }

}
