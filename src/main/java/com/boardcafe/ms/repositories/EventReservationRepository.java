package com.boardcafe.ms.repositories;

import com.boardcafe.ms.models.entities.EventReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventReservationRepository extends JpaRepository<EventReservation, Long> {
}