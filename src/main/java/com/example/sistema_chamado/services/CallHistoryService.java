package com.example.sistema_chamado.services;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CallHistoryService {
    private final CallHistoryRepository callHistoryRepository;
    private final CalledService calledService;
    private final CalledRepository calledRepository;
    public static Logger log = LoggerFactory.getLogger(CallHistoryService.class);

    public CallHistoryService(CallHistoryRepository callHistoryRepository, CalledService calledService,
                              CalledRepository calledRepository) {
        this.callHistoryRepository = callHistoryRepository;
        this.calledService = calledService;
        this.calledRepository = calledRepository;
    }

    public CallHistoryDTO createHistoricCall (CallHistoryResponseDTO data, Integer CalledId) {
        log.info("Iniciando criação de histórico de chamado para o chamado ID: {}", CalledId);
        Called called = this.calledService.findById(CalledId);
        log.info("Chamado encontrado com status: {}", called.getStatus());

        if(called.getStatus() == Status.CONCLUIDO) {
            log.info("Tentativa de atualizar chamado já concluído com ID: {}", CalledId);
            throw new CallAlreadyCompleted("Chamada já foi concluída e não pode ser mais atualizada.");
        }
        if(called.getStatus() == Status.CANCELADO) {
            log.info("Tentativa de atualizar chamado já cancelado com ID: {}", CalledId);
            throw new CallCanceled("Chamada foi cancelada e não pode ser mais atualizada.");
        }

        Technical technical = called.getTechnical();
        log.info("Técnico responsável pelo chamado: {}", technical.getName());

        CallHistory newCallHistory = new CallHistory();
        newCallHistory.setStatus(data.status());
        newCallHistory.setComment(data.comment());
        newCallHistory.setCalled(called);
        newCallHistory.setTechnical(technical);
        log.info("Novo status do chamado: {}", data.status());

        if (newCallHistory.getStatus().equals(data.status())) {
            called.setStatus(data.status());
            this.calledRepository.save(called);
            log.info("Status do chamado atualizado para: {}", data.status());
        }

        CallHistory saved = callHistoryRepository.save(newCallHistory);
        log.info("Histórico de chamado criado com sucesso com ID: {}", saved.getId());

        return new CallHistoryDTO(
                saved.getId(),
                saved.getComment(),
                saved.getStatus(),
                saved.getDateTime(),
                saved.getTechnical().getName()
        );
    }
}
