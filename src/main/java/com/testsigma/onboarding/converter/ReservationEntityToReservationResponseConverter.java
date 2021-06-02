package com.testsigma.onboarding.converter;

import com.testsigma.onboarding.entity.Reservation;
import com.testsigma.onboarding.model.response.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationEntityToReservationResponseConverter implements Converter<Reservation, ReservationResponse> {

    @Override
    public ReservationResponse convert(Reservation source) {
        log.debug("Reservation Entity: {}"+source.toString());
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(source.getCheckin());
        reservationResponse.setCheckout(source.getCheckout());

        if(source.getId() != null) {
            log.debug("Setting Reservation Response ID from Reservation Entity ID {}",source.getId());
            reservationResponse.setId(source.getId());
        }
        else log.warn("Reservation entity ID should be fetched from reservation request. Please check logs.");

        log.debug("Reservation Response {}",reservationResponse.toString());
        return reservationResponse;
    }
}
