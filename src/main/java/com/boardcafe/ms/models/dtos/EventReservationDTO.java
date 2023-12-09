package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.dtos.enums.ReservationTypeDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Validated
public class EventReservationDTO extends ReservationDTO implements Serializable {
    private Long id;

    @NotNull(message = "Event ID cannot be null")
    private Long eventId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    private ReservationTypeDTO reservationTypeDTO;
}