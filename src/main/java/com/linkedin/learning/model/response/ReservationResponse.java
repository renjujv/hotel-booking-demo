package com.linkedin.learning.model.response;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private LocalDate checkin;
    @Getter @Setter
    private LocalDate checkout;
}
