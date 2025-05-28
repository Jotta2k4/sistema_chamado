package com.example.sistema_chamado.repositories;

import com.example.sistema_chamado.enums.Status;
import com.example.sistema_chamado.models.Called;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalledRepository extends JpaRepository<Called, Integer> {

    List<Called> findByCustomerId(Integer customerId);
    List<Called> findByStatusIn(List<Status> statuses);

}
