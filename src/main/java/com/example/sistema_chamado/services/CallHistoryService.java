package com.example.sistema_chamado.services;

import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryDTO;
import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryResponseDTO;
import com.example.sistema_chamado.enums.Status;
import com.example.sistema_chamado.models.CallHistory;
import com.example.sistema_chamado.models.Called;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.repositories.CallHistoryRepository;
import com.example.sistema_chamado.repositories.CalledRepository;
import org.springframework.stereotype.Service;

@Service
public class CallHistoryService {
    private final CallHistoryRepository callHistoryRepository;
    private final CalledService calledService;
    private final CalledRepository calledRepository;

    public CallHistoryService(CallHistoryRepository callHistoryRepository, CalledService calledService,
                              CalledRepository calledRepository) {
        this.callHistoryRepository = callHistoryRepository;
        this.calledService = calledService;
        this.calledRepository = calledRepository;
    }

    public CallHistoryDTO createHistoricCall (CallHistoryResponseDTO data, Integer CalledId) {
        Called called = this.calledService.findById(CalledId);

        if(called.getStatus() == Status.CONCLUIDO) {
            throw new RuntimeException("Chamada já foi concluída e não pode ser mais atualizada.");
        }
        if(called.getStatus() == Status.CANCELADO) {
            throw new RuntimeException("Chamada foi cancelada e não pode ser mais atualizada.");
        }

        Technical technical = called.getTechnical();

        CallHistory newCallHistory = new CallHistory();
        newCallHistory.setStatus(data.status());
        newCallHistory.setComment(data.comment());
        newCallHistory.setCalled(called);
        newCallHistory.setTechnical(technical);

        if (newCallHistory.getStatus().equals(data.status())) {
            called.setStatus(data.status());
            this.calledRepository.save(called);
        }

        CallHistory saved = callHistoryRepository.save(newCallHistory);

        return new CallHistoryDTO(
                saved.getId(),
                saved.getComment(),
                saved.getStatus(),
                saved.getDateTime(),
                saved.getTechnical().getName()
        );
    }
}
