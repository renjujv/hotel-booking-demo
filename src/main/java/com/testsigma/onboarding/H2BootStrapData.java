package com.testsigma.onboarding;

import com.testsigma.onboarding.entity.Reservation;
import com.testsigma.onboarding.entity.Room;
import com.testsigma.onboarding.repository.ReservationRepository;
import com.testsigma.onboarding.repository.RoomRepository;
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
        for(int i=0;i<3;i++) roomRepository.save(new Room(101+i,String.valueOf(200+(i*2))));
        reservationRepository.save(new Reservation(
                LocalDate.parse("2021-02-02"), LocalDate.parse("2021-02-22"),getRoomById(1)));
        reservationRepository.save(new Reservation(
                LocalDate.parse("2021-02-04"), LocalDate.parse("2021-02-21"),getRoomById(1)));
        reservationRepository.save(new Reservation(
                LocalDate.parse("2021-05-24"), LocalDate.parse("2021-05-28"),getRoomById(2)));
        log.debug("H2 bootstrapped with {} rooms and {} reservations.",roomRepository.count(),reservationRepository.count());

        //logging rooms for debugging
        Iterable<Room> roomEntities = roomRepository.findAll();
        roomEntities.forEach( r -> log.debug("{} -Room Number: {}, Price {}",r.getId(),r.getRoomNumber(),r.getPrice()));

        //logging reservations for debugging
        Iterable<Reservation> reservationEntities = reservationRepository.findAll();
        reservationEntities.forEach( r -> log.debug("{} -Checkin: {} , Checkout: {} , Room: {}",r.getId(),
                r.getCheckin(),r.getCheckout(),r.getBookedRoom()));
        }

    public Room getRoomById(int id){
        return roomRepository.findById(id).orElse(new Room(0,"Dummy Room"));
    }
}
