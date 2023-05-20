package com.local.parking.test.parking;

public enum ParkingSize {
    SP("SP"),
    MP("MP"),
    LP("LP");

    private final String code;

    ParkingSize(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
