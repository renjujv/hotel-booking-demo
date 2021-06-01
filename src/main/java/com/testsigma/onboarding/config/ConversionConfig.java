package com.testsigma.onboarding.config;

import com.testsigma.onboarding.converter.ReservationEntityToReservationResponseConverter;
import com.testsigma.onboarding.converter.ReservationRequestToReservationEntityConverter;
import com.testsigma.onboarding.converter.RoomEntityToReservableRoomResponseConverter;
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
