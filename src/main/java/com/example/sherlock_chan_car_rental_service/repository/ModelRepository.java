package com.example.sherlock_chan_car_rental_service.repository;

import com.example.sherlock_chan_car_rental_service.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
