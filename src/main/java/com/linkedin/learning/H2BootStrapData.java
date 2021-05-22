package com.linkedin.learning;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class H2BootStrapData implements CommandLineRunner {
    @Autowired
    RoomRepository roomRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bootstrapping data in H2...");
        for(int i=0;i<3;i++) roomRepository.save(new RoomEntity(101+i,String.valueOf(200+(i*2))));
        System.out.println("H2 bootstrapped with "+roomRepository.count()+" rooms.");
        Iterable<RoomEntity> roomEntities = roomRepository.findAll();
        for (RoomEntity room: roomEntities) System.out.println(room.getRoomNumber());
    }
}
