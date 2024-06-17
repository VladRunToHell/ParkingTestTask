package com.bureau.testtask.Entities;

import java.io.Serializable;
import java.util.Objects;

public class ParkingPK implements Serializable {
    private int idMachine;
    private int idParkArea;

    public void setIdMachine(int idMachine) {
        this.idMachine = idMachine;
    }

    public void setIdParkArea(int idParkArea) {
        this.idParkArea = idParkArea;
    }

    public long getIdParkArea() {
        return idParkArea;
    }

    public long getIdMachine() {
        return idMachine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingPK parkingPK = (ParkingPK) o;
        return idMachine == parkingPK.idMachine && idParkArea == parkingPK.idParkArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMachine, idParkArea);
    }
}
