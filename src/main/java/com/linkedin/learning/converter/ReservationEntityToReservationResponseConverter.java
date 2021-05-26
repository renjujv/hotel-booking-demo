package com.linkedin.learning.converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.response.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {

    @Override
    public ReservationResponse convert(ReservationEntity  reservationEntity) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(reservationEntity.getCheckin());
        reservationResponse.setCheckout(reservationEntity.getCheckout());

        log.info("converting reservation entity {} to reservation response.",reservationResponse.toString());
        if(null != reservationEntity.getId()) reservationResponse.setId(reservationEntity.getId());

        return reservationResponse;
    }
}
