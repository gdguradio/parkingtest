package com.local.parking.test.system.property;

import java.util.List;

public interface SystemPropertyService {

    String createEntryPoint(SystemProperty systemProperty);

    List<SystemProperty> readByCode(SystemProperty systemProperty);

    String createParkingSlot(SystemProperty systemProperty);
}
