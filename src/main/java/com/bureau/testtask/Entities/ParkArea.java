package com.bureau.testtask.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@jakarta.persistence.Table(name = "park_area", schema = "public", catalog = "parking")
public class ParkArea {

    public ParkArea() {}

    public ParkArea(int placeQuan, int freePlaceQuan, float tax) {
        this.placeQuan = placeQuan;
        this.freePlaceQuan = freePlaceQuan;
        this.tax = tax;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_park_area", nullable = false)
    private int idParkArea;

    public void setIdParkArea(int idParkArea) {
        this.idParkArea = idParkArea;
    }

    @Basic
    @Column(name = "place_quan", nullable = false)
    private int placeQuan;

    public void setPlaceQuan(int placeQuan) {
        this.placeQuan = placeQuan;
    }

    @Basic
    @Column(name = "free_place_quan", nullable = false)
    private int freePlaceQuan;

    public void setFreePlaceQuan(int freePlaceQuan) {
        this.freePlaceQuan = freePlaceQuan;
    }

    @Basic
    @Column(name = "tax", nullable = false, precision = 0)
    private float tax;

    public void setTax(float tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkArea parkArea = (ParkArea) o;
        return idParkArea == parkArea.idParkArea && placeQuan == parkArea.placeQuan && freePlaceQuan == parkArea.freePlaceQuan && Float.compare(tax, parkArea.tax) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParkArea, placeQuan, freePlaceQuan, tax);
    }
}
