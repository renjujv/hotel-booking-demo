package com.linkedin.learning.model.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    @Getter @Setter @NonNull
    private Long id;
    @Getter @Setter @NonNull
    private LocalDate checkin;
    @Getter @Setter @NonNull
    private LocalDate checkout;
}
