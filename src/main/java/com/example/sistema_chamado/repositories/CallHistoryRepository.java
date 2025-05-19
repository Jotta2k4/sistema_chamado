package com.example.sistema_chamado.repositories;

import com.example.sistema_chamado.models.CallHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CallHistoryRepository extends JpaRepository<CallHistory, Integer> {
    @Modifying
    @Query("DELETE FROM CallHistory ch WHERE ch.called.id = :calledId")
    void deleteByCalledId(@Param("calledId") Integer calledId);


}
