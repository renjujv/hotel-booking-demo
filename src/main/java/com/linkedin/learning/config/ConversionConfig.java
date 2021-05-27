package com.linkedin.learning.config;

import com.linkedin.learning.converter.ReservationEntityToReservationResponseConverter;
import com.linkedin.learning.converter.ReservationRequestToReservationEntityConverter;
import com.linkedin.learning.converter.RoomEntityToReservableRoomResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

    private Set<Converter> getConverters(){
        Set<Converter> convertersSet = new HashSet<>();
        convertersSet.add(new RoomEntityToReservableRoomResponseConverter());
        convertersSet.add(new ReservationEntityToReservationResponseConverter());
        convertersSet.add(new ReservationRequestToReservationEntityConverter());
        return convertersSet;
    }

    @Bean
    public ConversionService conversionService(){
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(getConverters());
        conversionServiceFactoryBean.afterPropertiesSet();
        return conversionServiceFactoryBean.getObject();
    }
}
