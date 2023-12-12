package com.boardcafe.ms.repositories;

import com.boardcafe.ms.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findGamesByCategoryAndMaxPlayersAndPrice(String category, int maxPlayers, double price);
}