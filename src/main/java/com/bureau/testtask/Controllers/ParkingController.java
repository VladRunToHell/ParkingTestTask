package com.bureau.testtask.Controllers;

import com.bureau.testtask.Entities.Parking;
import com.bureau.testtask.Services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/parking")
    public ResponseEntity<String> createParking(@RequestBody Parking parking) {
        return parkingService.createParking(parking);
    }

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<String> deleteParking(@PathVariable(name = "id") int idMachine) {
        return parkingService.deleteParking(idMachine);
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<?> getAccount(@PathVariable(name = "id") int idMachine) {
        return parkingService.getAccount(idMachine);
    }
}
