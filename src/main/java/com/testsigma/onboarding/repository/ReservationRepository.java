package com.testsigma.onboarding.repository;

import com.testsigma.onboarding.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {}
