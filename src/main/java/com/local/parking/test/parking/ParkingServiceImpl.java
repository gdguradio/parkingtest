package com.local.parking.test.parking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.local.parking.test.mappers.ParkingMapper;
import com.local.parking.test.mappers.SystemPropertyMapper;
import com.local.parking.test.system.property.SystemProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ParkingServiceImpl implements ParkingService {

    private final SystemPropertyMapper systemPropertyMapper;
    private final ParkingMapper parkingMapper;


    @Override
    public String parkingTimeIn(String entryPoint, String vehicleType) {
        ParkingSpace parkingSpace = new ParkingSpace();
        Optional<SystemProperty> optionalSystemProperty = systemPropertyMapper.selectByEntryPoint(entryPoint,vehicleType);

        if (optionalSystemProperty.isEmpty()) {
            return "No available parking slot";
        }

        SystemProperty systemProperty = optionalSystemProperty.get();
        String value = systemProperty.getValue();
        String[] parts = value.split(",");
        String size = parts[parts.length - 1];
        String location = String.join(",", Arrays.copyOfRange(parts, 0, parts.length - 1));
        parkingSpace.setLocation(location);
        parkingSpace.setSize(ParkingSize.valueOf(size));
        parkingSpace.setEntryPoint(entryPoint);
        parkingSpace.setVehicleType(VehicleType.valueOf(vehicleType));

        boolean isParkingFull = parkingMapper.isFullParking("active");
        if (isParkingFull) {
            return "Parking is full";
        }

        boolean isParkingExisting = systemPropertyMapper.existsByValue(parkingSpace.getLocation() + ',' + parkingSpace.getSize());
        if (isParkingExisting) {
            boolean isOccupied = parkingMapper.existsByLocation(parkingSpace.getLocation());
            if (!isOccupied) {
                LocalDateTime dateTime = LocalDateTime.now();
                parkingSpace.setTimeIn(dateTime);
                parkingSpace.setOccupied(true);
                parkingSpace.setStatus("active");

                if (parkingMapper.insert(parkingSpace) == 1) {
                    return "Insert Successful";
                } else {
                    return "Insert Failed";
                }
            } else {
                return "Parking slot is already occupied";
            }
        } else {
            return "Parking slot is not existing or not active";
        }
    }


    @Override
    public String parkingTimeOut(long id) {
        Optional<ParkingSpace> optionalParkingSpace = parkingMapper.selectOptionalActiveById(id);

        if (optionalParkingSpace.isEmpty()) {
            return "Id does not exists!";
        }
        ParkingSpace parkingSpace = optionalParkingSpace.get();

        LocalDateTime dateTimeOut = LocalDateTime.now();
        parkingSpace.setTimeOut(dateTimeOut);
        float amount = computeAmount(parkingSpace);
        parkingSpace.setAmount(amount);
        parkingSpace.setStatus("completed");

        return (parkingMapper.update(parkingSpace, id) == 1) ? "Update Success" : "Update Fail";
    }

    public float computeAmount(ParkingSpace parkingSpace) {
        List<SystemProperty> systemPropertyList = systemPropertyMapper.selectByCode("parking-rate");
        String parkingRate = systemPropertyList.get(0).getValue();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<String, Float>> typeRef = new TypeReference<HashMap<String, Float>>() {};
        HashMap<String, Float> map;
        try {
            map = objectMapper.readValue(parkingRate, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0.0f;
        }

        ParkingSize parkingSize = parkingSpace.getSize();
        String code = parkingSize.getCode();
        float parkingFee = map.getOrDefault(code, 0.0f);

        LocalDateTime dateTimeOut = parkingSpace.getTimeOut();
        LocalDateTime dateTimeIn = parkingSpace.getTimeIn();
        Duration duration = Duration.between(dateTimeIn, dateTimeOut);
        long minutes = duration.toMinutes();

        long roundedHours = (long) Math.ceil((double) minutes / 60);
        float totalAmount;

        if (roundedHours < 3) {
            totalAmount = 40.0f;
        } else if (roundedHours >= 24) {
            long oneDayCount = roundedHours / 24;
            long remainingHours = roundedHours % 24;
            totalAmount = (oneDayCount * 5000.0f) + (remainingHours * parkingFee);
        } else {
            float excessTimeAmount = (roundedHours - 3) * parkingFee;
            totalAmount = 40.0f + excessTimeAmount;
        }

        return totalAmount;
    }


    @Override
    public ParkingSpace readById(long id) {
        return parkingMapper.selectById(id);
    }
}
