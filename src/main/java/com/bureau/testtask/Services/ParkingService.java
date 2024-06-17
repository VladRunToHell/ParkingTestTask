package com.bureau.testtask.Services;

import com.bureau.testtask.Entities.Parking;
import com.bureau.testtask.Entities.ParkArea;
import com.bureau.testtask.Entities.Machine;
import com.bureau.testtask.Repositories.MachineRepository;
import com.bureau.testtask.Repositories.ParkAreaRepository;
import com.bureau.testtask.Repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final MachineRepository machineRepository;
    private final ParkAreaRepository parkAreaRepository;

    @Autowired
    ParkingService(ParkingRepository parkingRepository,
                      MachineRepository machineRepository, ParkAreaRepository parkAreaRepository) {
        this.parkingRepository = parkingRepository;
        this.machineRepository = machineRepository;
        this.parkAreaRepository = parkAreaRepository;
    }

    public ResponseEntity<String> createParking(Parking parking) {
        int stateResponse = checkConditionsForParking(parking.getIdParkArea(),
                parking.getIdMachine());
        return switch (stateResponse) {
            case 1 -> new ResponseEntity<>("Такого авто нет в БД", HttpStatus.BAD_REQUEST);
            case 2 -> new ResponseEntity<>("Такой парковки нет в БД", HttpStatus.BAD_REQUEST);
            case 3 -> new ResponseEntity<>("Это авто уже припарковано", HttpStatus.BAD_REQUEST);
            case 4 -> new ResponseEntity<>("На выбранной парковке нет мест под автомобиль такого размера",
                    HttpStatus.BAD_REQUEST);
            default -> {
                parking.setStartTime(new Timestamp(System.currentTimeMillis()));
                parkingRepository.save(parking);
                yield new ResponseEntity<>("Авто припарковано", HttpStatus.CREATED);
            }
        };
    }

    public ResponseEntity<String> deleteParking(int idMachine) {
        int stateResponse = checkConditionsForExit(idMachine);
        return switch (stateResponse) {
            case 1 -> new ResponseEntity<>("Это авто не припарковано", HttpStatus.BAD_REQUEST);
            case 2 -> new ResponseEntity<>("Такого авто нет в БД", HttpStatus.BAD_REQUEST);
            default -> {
                Parking parking = parkingRepository.findByIdMachine(idMachine).get();
                updateDBbyDeleteParking(parking);
                parkingRepository.delete(parking);
                yield new ResponseEntity<>("Авто снято с парковки", HttpStatus.OK);
            }
        };
    }

    public ResponseEntity<?> getAccount(int idMachine) {
        if(checkMachine(idMachine)) return new ResponseEntity<>("Такого авто нет в БД", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(machineRepository.findById(idMachine).get().getBorrow(), HttpStatus.OK);
    }

    private int checkConditionsForParking(int idParkArea, int idMachine) {
        if(checkMachine(idMachine))
            return 1;
        if(!checkParkArea(idParkArea))
            return 2;
        if(checkIsParked(idMachine))
            return 3;
        if(!checkFreePlace(idMachine, idParkArea))
            return 4;
        updateDBbyNewParking(idParkArea, idMachine);
        return 100;
    }

    private int checkConditionsForExit(int idMachine) {
        if(checkMachine(idMachine))
            return 2;
        if(!checkIsParked(idMachine))
            return 1;
        return 100;
    }

    private void updateDBbyNewParking(int idParkArea, int idMachine) {
        ParkArea parkArea = parkAreaRepository.findById(idParkArea).get();
        Machine machine = machineRepository.findById(idMachine).get();
        parkArea.setFreePlaceQuan(parkArea.getFreePlaceQuan() - machine.getSize());
        machine.setParked(true);
        parkAreaRepository.save(parkArea);
        machineRepository.save(machine);
    }

    private void updateDBbyDeleteParking(Parking parking) {
        ParkArea parkArea = parkAreaRepository.findById(parking.getIdParkArea()).get();
        Machine machine = machineRepository.findById(parking.getIdMachine()).get();
        parkArea.setFreePlaceQuan(parkArea.getFreePlaceQuan() + machine.getSize());
        machine.setParked(false);
        if(checkBorrow(machine)) machine.setBorrow(0.0F);
        machine.setBorrow(machine.getBorrow() + getAccountPerParking(parking));
        parkAreaRepository.save(parkArea);
        machineRepository.save(machine);
    }

    private boolean checkBorrow(Machine machine) {
        return machine.getBorrow().isNaN();
    }

    private float getAccountPerParking(Parking parking) {
        return parkAreaRepository.findById(parking.getIdParkArea()).get().getTax() *
                getHours(parking.getStartTime());
    }

    private long getHours(Timestamp startTime) {
        return (System.currentTimeMillis() - startTime.getTime()) % 3600000;
    }

    private boolean checkIsParked(int idMachine) {
        return machineRepository.findById(idMachine).get().isParked();
    }

    private boolean checkParkArea(int idParkArea) {
        return parkAreaRepository.findById(idParkArea).isPresent();
    }

    private boolean checkMachine(int idMachine) {
        return machineRepository.findById(idMachine).isEmpty();
    }

    private boolean checkFreePlace(int idMachine, int idParkArea) {
        return machineRepository.findById(idMachine).get().getSize() <=
                parkAreaRepository.findById(idParkArea).get().getFreePlaceQuan();
    }
}
