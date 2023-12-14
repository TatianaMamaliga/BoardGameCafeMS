package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.services.EventService;
import com.boardcafe.ms.util.EventTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    @DisplayName("When adding a new event, return status code 201.")
    void whenCreateEventTest() throws Exception {
        String eventJson = EventTestUtil.getEventJson();

        EventDTO mockEventDTO = EventTestUtil.createEventDTO();

        BDDMockito.given(eventService.createEvent(ArgumentMatchers.any(EventDTO.class))).willReturn(mockEventDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(mockEventDTO.getTitle())).andReturn();
    }

}