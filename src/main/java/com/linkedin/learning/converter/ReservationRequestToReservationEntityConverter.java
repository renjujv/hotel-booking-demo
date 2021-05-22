package com.linkedin.learning.converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import org.springframework.core.convert.converter.Converter;

public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {
    @Override
    public ReservationEntity convert(ReservationRequest reservationRequest) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(reservationRequest.getCheckin());
        reservationEntity.setCheckout(reservationRequest.getCheckout());

        if(null != reservationRequest.getId()) reservationEntity.setId(reservationRequest.getId());

        return reservationEntity;
    }
}
