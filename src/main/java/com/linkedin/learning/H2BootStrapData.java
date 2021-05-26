package com.linkedin.learning;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class H2BootStrapData implements CommandLineRunner {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bootstrapping data in H2...");
        for(int i=0;i<3;i++) roomRepository.save(new RoomEntity(101+i,String.valueOf(200+(i*2))));
        reservationRepository.save(new ReservationEntity(LocalDate.parse("2021-02-02"), LocalDate.parse("2021-02-22")));
        reservationRepository.save(new ReservationEntity(LocalDate.parse("2021-02-04"), LocalDate.parse("2021-02-21"),getRoomById(1)));
        reservationRepository.save(new ReservationEntity(LocalDate.parse("2021-05-24"), LocalDate.parse("2021-05-28"),getRoomById(1)));
        System.out.println("H2 bootstrapped with "+roomRepository.count()+" rooms and "+
                reservationRepository.count()+"reservations.");
        Iterable<RoomEntity> roomEntities = roomRepository.findAll();
        for (RoomEntity room: roomEntities) System.out.println(+room.getId()+
                "-Room Number: "+room.getRoomNumber());
        Iterable<ReservationEntity> reservationEntities = reservationRepository.findAll();
        for (ReservationEntity reservationEntity: reservationEntities) System.out.println(+reservationEntity.getId()+
                "-Checkin: "+reservationEntity.getCheckin()+
                ", Checkout: "+reservationEntity.getCheckout()+
                ", Room: "+reservationEntity.getRoomEntity());
    }

    public RoomEntity getRoomById(int id){
        return roomRepository.findById(id).orElse(new RoomEntity(000,"0"));
    }
}
