package com.bureau.testtask.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@jakarta.persistence.Table(name = "machine", schema = "public", catalog = "parking")
public class Machine {

    public Machine() {}

    public Machine(float borrow, int size, boolean isParked) {
        this.borrow = borrow;
        this.size = size;
        this.isParked = isParked;
    }

    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_machine", nullable = false)
    private int idMachine;

    public void setIdMachine(int idMachine) {
        this.idMachine = idMachine;
    }

    @Getter
    @Basic
    @Column(name = "borrow", nullable = true, precision = 0)
    private Float borrow;

    public void setBorrow(Float borrow) {
        this.borrow = borrow;
    }

    @Getter
    @Basic
    @Column(name = "size", nullable = false)
    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    @Basic
    @Column(name = "is_parked", nullable = false)
    private boolean isParked;

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return idMachine == machine.idMachine && size == machine.size && isParked == machine.isParked && Objects.equals(borrow, machine.borrow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMachine, borrow, size, isParked);
    }
}
