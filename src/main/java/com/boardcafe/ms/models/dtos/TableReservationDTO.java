package com.boardcafe.ms.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Validated
public class TableReservationDTO extends ReservationDTO implements Serializable {

    private Long id;
    private Long eventId;
    @NotNull(message = "Game table ID cannot be null")
    private Long gameTableId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
}