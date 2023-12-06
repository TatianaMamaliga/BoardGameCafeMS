package com.boardcafe.ms.repositories;

import com.boardcafe.ms.models.entities.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTableRepository extends JpaRepository<GameTable, Long> {
}