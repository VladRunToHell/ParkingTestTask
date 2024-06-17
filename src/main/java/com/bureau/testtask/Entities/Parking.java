package com.bureau.testtask.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Entity
@IdClass(com.bureau.testtask.Entities.ParkingPK.class)
public class Parking {

    public Parking() {}

    public Parking(int idParkArea, int idMachine, Timestamp startTime) {
        this.idParkArea = idParkArea;
        this.idMachine = idMachine;
        this.startTime = startTime;
    }

    @Id
    @Column(name = "id_machine", nullable = false)
    private int idMachine;

    public void setIdMachine(int idMachine) {
        this.idMachine = idMachine;
    }

    @Id
    @Column(name = "id_park_area", nullable = false)
    private int idParkArea;

    public void setIdParkArea(int idParkArea) {
        this.idParkArea = idParkArea;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parking parking = (Parking) o;
        return idMachine == parking.idMachine && idParkArea == parking.idParkArea && Objects.equals(startTime, parking.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMachine, idParkArea, startTime);
    }
}
