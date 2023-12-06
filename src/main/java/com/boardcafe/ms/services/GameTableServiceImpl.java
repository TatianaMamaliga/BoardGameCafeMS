package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.GameTableDTO;
import com.boardcafe.ms.repositories.GameTableRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameTableServiceImpl implements GameTableService {
    private final GameTableRepository gameTableRepository;
    private final ObjectMapper objectMapper;

    @Override
    public GameTableDTO createTable(GameTableDTO gameTableDTO) {
        return null;
    }

    @Override
    public void deleteTableById(Long id) {

    }
}