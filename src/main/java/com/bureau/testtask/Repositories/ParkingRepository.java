package com.bureau.testtask.Repositories;

import com.bureau.testtask.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, ParkingPK> {
    Optional<Parking> findByIdMachine(long idMachine);
}