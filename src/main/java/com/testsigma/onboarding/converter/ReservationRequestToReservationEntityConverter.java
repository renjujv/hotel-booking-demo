package com.testsigma.onboarding.converter;

import com.testsigma.onboarding.entity.Reservation;
import com.testsigma.onboarding.model.request.ReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, Reservation> {

    @Override
    public Reservation convert(ReservationRequest source) {
        log.debug("Reservation Request {}",source.toString());
        Reservation reservation = new Reservation();
        if(source.getId() != null && reservation.getId()==null) reservation.setId(source.getId());
        reservation.setCheckin(source.getCheckin());
        reservation.setCheckout(source.getCheckout());

        // Reservation request ID is obtained from request and is currently supposed to be null.
        // Only RoomID, checkin and checkout dates are provided.
        //TODO Add validation for PUT request and allow updating reservation by ID
        log.debug("Reservation Entity {}", reservation.toString());
        return reservation;
    }
}
