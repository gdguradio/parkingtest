package com.local.parking.test.parking;

import java.util.List;

public interface ParkingService {

    String parkingTimeIn(String entryPoint, String vehicleType);

    String parkingTimeOut(long id);

    ParkingSpace readById(long id);

    List<ParkingSpace> readParkedSlot();
}
