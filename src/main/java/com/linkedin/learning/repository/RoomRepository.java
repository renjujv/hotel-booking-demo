package com.linkedin.learning.repository;

import com.linkedin.learning.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {
    @Override
    Optional<RoomEntity> findById(Integer id);
    @Override
    List<RoomEntity> findAll();
}