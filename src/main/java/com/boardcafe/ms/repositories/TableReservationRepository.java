package com.boardcafe.ms.repositories;

import com.boardcafe.ms.models.entities.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {
}