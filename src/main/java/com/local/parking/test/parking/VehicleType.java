package com.local.parking.test.parking;

public enum VehicleType {
    SP("SP"),
    MP("MP"),
    LP("LP");

    private final String code;

    VehicleType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
