package com.linkedin.learning.converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.response.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {

    @Override
    public ReservationResponse convert(ReservationEntity source) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(source.getCheckin());
        reservationResponse.setCheckout(source.getCheckout());
        if(source.getId() != null) {
            reservationResponse.setId(source.getId());
            log.info("Fetching and setting id from reservation entity to reservation response.");
        }
        log.info("converting reservation entity '{}' to reservation response.",reservationResponse.toString());
        if(null != source.getId()) reservationResponse.setId(source.getId());

        return reservationResponse;
    }
}
