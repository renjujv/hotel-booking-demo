package com.linkedin.learning.converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservationResponse;
import org.springframework.core.convert.converter.Converter;

public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {
    @Override
    public ReservationResponse convert(ReservationEntity  reservationEntity) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(reservationEntity.getCheckin());
        reservationResponse.setCheckout(reservationEntity.getCheckout());

        if(null != reservationEntity.getId()) reservationResponse.setId(reservationEntity.getId());

        return reservationResponse;
    }
}
