package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationTypeDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//@Getter
//@Setter
@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@MappedSuperclass

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "reservationType",
        visible = true
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = EventReservationDTO.class, name = "EVENT"),
        @JsonSubTypes.Type(value = TableReservationDTO.class, name = "TABLE")
})

public abstract class ReservationDTO {

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDate date;

    private ReservationTypeDTO reservationType;

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    private ReservationTypeDTO reservationTypeDTO;

    @Enumerated(EnumType.STRING)
    private ReservationStatusDTO reservationStatus;

    @NotNull(message = "Number of attendees cannot be null")
    @Min(value = 0, message = "Number of attendees cannot be negative")
    private Integer attendees;

    @NotNull(message = "Event ID cannot be null")
    private Long eventId;
    @NotNull(message = "Game table ID cannot be null")
    private Long gameTableId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}