package com.linkedin.learning;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component @Slf4j
public class H2BootStrapData implements CommandLineRunner {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Bootstrapping data in H2...");
        for(int i=0;i<3;i++) roomRepository.save(new RoomEntity(101+i,String.valueOf(200+(i*2))));
        reservationRepository.save(new ReservationEntity(
                LocalDate.parse("2021-02-02"), LocalDate.parse("2021-02-22"),getRoomById(2)));
        reservationRepository.save(new ReservationEntity(
                LocalDate.parse("2021-02-04"), LocalDate.parse("2021-02-21"),getRoomById(1)));
        reservationRepository.save(new ReservationEntity(
                LocalDate.parse("2021-05-24"), LocalDate.parse("2021-05-28"),getRoomById(1)));
        log.debug("H2 bootstrapped with {} rooms and {} reservations.",roomRepository.count(),reservationRepository.count());

        //logging rooms
        Iterable<RoomEntity> roomEntities = roomRepository.findAll();
        roomEntities.forEach( r -> log.debug("{} -Room Number: {}, Price {}",r.getId(),r.getRoomNumber(),r.getPrice()));

        //logging reservations
        Iterable<ReservationEntity> reservationEntities = reservationRepository.findAll();
        reservationEntities.forEach( r -> log.debug("{} -Checkin: {} , Checkout: {} , Room: {}",r.getId(),
                r.getCheckin(),r.getCheckout(),r.getRoomEntity()));
        }

    public RoomEntity getRoomById(int id){
        return roomRepository.findById(id).orElse(new RoomEntity(000,"0"));
    }
}
