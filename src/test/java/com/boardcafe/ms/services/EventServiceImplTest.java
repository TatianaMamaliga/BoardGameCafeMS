package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.models.entities.Event;
import com.boardcafe.ms.repositories.EventRepository;
import com.boardcafe.ms.util.EventTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @Test
    @DisplayName("Event created successfully")
    void createEventTestShouldPass() {
        //given
        EventDTO eventDTO = EventTestUtil.createEventDTO();
        Event eventEntity = EventTestUtil.createEvent();

        Mockito.when(objectMapper.convertValue(eventDTO, Event.class)).thenReturn(eventEntity);
        Mockito.when(eventRepository.save(eventEntity)).thenReturn(eventEntity);
        Mockito.when(objectMapper.convertValue(eventEntity, EventDTO.class)).thenReturn(eventDTO);

        //when
        EventDTO eventDTOResponse = eventServiceImpl.createEvent(eventDTO);

        //then
        Assertions.assertEquals(eventDTOResponse, eventDTO);
        Mockito.verify(eventRepository, Mockito.times(1)).save(eventEntity);
    }
}