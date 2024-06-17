package com.bureau.testtask.Services;

import com.bureau.testtask.Entities.Machine;
import com.bureau.testtask.Entities.ParkArea;
import com.bureau.testtask.Entities.Parking;
import com.bureau.testtask.Repositories.MachineRepository;
import com.bureau.testtask.Repositories.ParkAreaRepository;
import com.bureau.testtask.Repositories.ParkingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParkingServiceTest {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ParkAreaRepository parkAreaRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Test
    void createParking() {
        Parking parking = new Parking(0, 0, new Timestamp(System.currentTimeMillis()));
        assertEquals(new ResponseEntity<>("Такого авто нет в БД", HttpStatus.BAD_REQUEST),
                parkingService.createParking(parking));
        Machine machine = new Machine(0.0f, 2, true);
        machineRepository.save(machine);
        parking.setIdMachine(machine.getIdMachine());
        assertEquals(new ResponseEntity<>("Такой парковки нет в БД", HttpStatus.BAD_REQUEST),
                parkingService.createParking(parking));
        ParkArea parkArea = new ParkArea(2, 0, 0.0f);
        parkAreaRepository.save(parkArea);
        parking.setIdParkArea(parkArea.getIdParkArea());
        assertEquals(new ResponseEntity<>("Это авто уже припарковано", HttpStatus.BAD_REQUEST),
                parkingService.createParking(parking));
        machine.setParked(false);
        machineRepository.save(machine);
        assertEquals(new ResponseEntity<>("На выбранной парковке нет мест под автомобиль такого размера",
                HttpStatus.BAD_REQUEST), parkingService.createParking(parking));
        parkArea.setFreePlaceQuan(2);
        parkAreaRepository.save(parkArea);
        assertEquals(new ResponseEntity<>("Авто припарковано", HttpStatus.CREATED),
                parkingService.createParking(parking));
        assertEquals(0, parkAreaRepository.findById(parkArea.getIdParkArea()).get().getFreePlaceQuan());
        assertTrue(machineRepository.findById(machine.getIdMachine()).get().isParked());
        parkingRepository.delete(parking);
        machineRepository.delete(machine);
        parkAreaRepository.delete(parkArea);
    }
}