package com.boardcafe.ms.util;

import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.models.entities.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventTestUtil {

    private EventTestUtil() {
    }

    public static EventDTO createEventDTO() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle("Special Quiz Night");
        eventDTO.setDate(LocalDate.of(2024, 1, 2));
        eventDTO.setStartTime(LocalTime.of(19, 0));
        eventDTO.setEndTime(LocalTime.of(22, 0));
        eventDTO.setCapacity(40);
        return eventDTO;
    }

    public static Event createEvent() {
        Event event = new Event();
        event.setTitle("Special Quiz Night");
        event.setDate(LocalDate.of(2024, 1, 2));
        event.setStartTime(LocalTime.of(19, 0));
        event.setEndTime(LocalTime.of(22, 0));
        event.setCapacity(40);
        return event;
    }

    public static String getEventJson() {
        return """
                {
                	"title": "Special Quiz Event",
                    "date": "2024-01-02",
                    "startTime": "19:00",
                    "endTime": "22:00",
                    "capacity": "40"
                }
                """;
    }
}