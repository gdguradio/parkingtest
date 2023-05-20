package com.local.parking.test.parking;

public interface ParkingService {

    String parkingTimeIn(String entryPoint, String vehicleType);

    String parkingTimeOut(long id);

    ParkingSpace readById(long id);
}
