package com.bureau.testtask.Repositories;

import com.bureau.testtask.Entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Integer> {
}
