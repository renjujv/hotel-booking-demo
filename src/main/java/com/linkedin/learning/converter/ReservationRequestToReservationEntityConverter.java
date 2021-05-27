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
        if (source.getId() != null) {
            reservationEntity.setId(source.getId());
            log.info("Fetching and setting id for reservation entity from reseervation request");
        } else{
            log.info("Source(ReservationRequest) id is null");
        }
        if(null != source.getId()) reservationEntity.setId(source.getId());
        return reservationEntity;
    }
}
