package com.testsigma.onboarding.repository;

import com.testsigma.onboarding.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    @Override
    Optional<Room> findById(Integer id);
    @Override
    List<Room> findAll();
}