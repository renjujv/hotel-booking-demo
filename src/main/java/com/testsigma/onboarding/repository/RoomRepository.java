package com.testsigma.onboarding.repository;

import com.testsigma.onboarding.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {
    @Override
    Optional<RoomEntity> findById(Integer id);
    @Override
    List<RoomEntity> findAll();
}