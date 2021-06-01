package com.testsigma.onboarding.converter;

import com.testsigma.onboarding.entity.ReservationEntity;
import com.testsigma.onboarding.model.request.ReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {

    @Override
    public ReservationEntity convert(ReservationRequest source) {
        log.debug("Reservation Request {}",source.toString());
        ReservationEntity reservationEntity = new ReservationEntity();
        if(source.getId() != null && reservationEntity.getId()==null) reservationEntity.setId(source.getId());
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());

        // Reservation request ID is obtained from request and is currently supposed to be null.
        // Only RoomID, checkin and checkout dates are provided.
        //TODO Add validation for PUT request and allow updating reservation by ID
        log.debug("Reservation Entity {}",reservationEntity.toString());
        return reservationEntity;
    }
}
