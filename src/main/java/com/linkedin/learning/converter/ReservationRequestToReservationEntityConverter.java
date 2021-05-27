package com.linkedin.learning.converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {

    @Override
    public ReservationEntity convert(ReservationRequest source) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());
        log.info("RoomId obtained in the POST request is {}",source.getRoomId());

        if (source.getId() != null) reservationEntity.setId(source.getId());
        else log.warn("Source(ReservationRequest) id is null.");

        return reservationEntity;
    }
}
