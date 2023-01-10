package com.example.sherlock_chan_car_rental_service.repository;

import com.example.sherlock_chan_car_rental_service.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
