package com.local.parking.test;

import com.local.parking.test.mappers.ParkingMapper;
import com.local.parking.test.mappers.SystemPropertyMapper;
import com.local.parking.test.parking.ParkingServiceImpl;
import com.local.parking.test.parking.ParkingSize;
import com.local.parking.test.parking.ParkingSpace;
import com.local.parking.test.parking.VehicleType;
import com.local.parking.test.system.property.SystemProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceImplTest {

    @Mock
    private SystemPropertyMapper systemPropertyMapper;

    @Mock
    private ParkingMapper parkingMapper;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Test
    public void testParkingTimeIn_NoAvailableParkingSlot() {

        when(systemPropertyMapper.selectByEntryPoint("1",VehicleType.SP.getCode()))
                .thenReturn(Optional.empty());

        String result = parkingService.parkingTimeIn("1", VehicleType.SP.getCode());

        assertEquals("No available parking slot", result);
    }

    @Test
    public void testParkingTimeIn_ParkingSlotFull() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");

        when(systemPropertyMapper.selectByEntryPoint("1",VehicleType.SP.getCode()))
                .thenReturn(Optional.of(systemProperty));
        when(parkingMapper.isFullParking("active"))
                .thenReturn(true);


        String result = parkingService.parkingTimeIn("1", VehicleType.SP.getCode());

        assertEquals("Parking is full", result);
    }

    @Test
    public void testParkingTimeIn_ParkingNotExisting() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");

        when(systemPropertyMapper.selectByEntryPoint("1",VehicleType.SP.getCode()))
                .thenReturn(Optional.of(systemProperty));
        when(parkingMapper.isFullParking("active"))
                .thenReturn(false);
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(false);
        String result = parkingService.parkingTimeIn("1", VehicleType.SP.getCode());

        assertEquals("Parking slot is not existing or not active", result);
    }

    @Test
    public void testParkingTimeIn_ParkingOccupied() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");

        when(systemPropertyMapper.selectByEntryPoint("1",VehicleType.SP.getCode()))
                .thenReturn(Optional.of(systemProperty));
        when(parkingMapper.isFullParking("active"))
                .thenReturn(false);
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(true);
        when(parkingMapper.existsByLocation("1,3,4"))
                .thenReturn(true);
        String result = parkingService.parkingTimeIn("1", VehicleType.SP.getCode());

        assertEquals("Parking slot is already occupied", result);
    }

    @Test
    public void testParkingTimeIn_ParkingInsertPass() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");
        when(systemPropertyMapper.selectByEntryPoint("1",VehicleType.SP.getCode()))
                .thenReturn(Optional.of(systemProperty));
        when(parkingMapper.isFullParking("active"))
                .thenReturn(false);
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(true);
        when(parkingMapper.existsByLocation("1,3,4"))
                .thenReturn(false);
        // Mock the parkingMapper.insert method
        doAnswer(invocation -> {
            ParkingSpace parkingSpace = invocation.getArgument(0);
            if (parkingSpace.getLocation().equals("1,3,4") && parkingSpace.getSize().getCode().equals("SP")) {
                // Simulate successful insertion
                return 1;
            } else {
                // Simulate failed insertion
                return 0;
            }
        }).when(parkingMapper).insert(any(ParkingSpace.class));
        String result = parkingService.parkingTimeIn("1", VehicleType.SP.getCode());
        assertEquals("Insert Successful", result);
    }

    @Test
    public void testParkingTimeIn_ParkingInsertFail() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");
        when(systemPropertyMapper.selectByEntryPoint("1",VehicleType.SP.getCode()))
                .thenReturn(Optional.of(systemProperty));
        when(parkingMapper.isFullParking("active"))
                .thenReturn(false);
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(true);
        when(parkingMapper.existsByLocation("1,3,4"))
                .thenReturn(false);
        // Mock the parkingMapper.insert method
        doAnswer(invocation -> {
            ParkingSpace parkingSpace = invocation.getArgument(0);
            if (parkingSpace.getLocation().equals("1,3,4") && parkingSpace.getSize().getCode().equals("SmallParking")) {
                // Simulate successful insertion
                return 1;
            } else {
                // Simulate failed insertion
                return 0;
            }
        }).when(parkingMapper).insert(any(ParkingSpace.class));
        String result = parkingService.parkingTimeIn("1", VehicleType.SP.getCode());
        assertEquals("Insert Failed", result);
    }

    @Test
    public void testParkingTimeOut_ParkingUpdateInvalidId() {

        when(parkingMapper.selectOptionalActiveById(1)).thenReturn(Optional.empty());

        String result = parkingService.parkingTimeOut(1);
        assertEquals("Id does not exists!", result);

    }

    @Test
    public void testParkingTimeOut_ParkingUpdatePass() {
        ParkingSpace parkingSpace = new ParkingSpace(1L, "1", "1,4,5", LocalDateTime.now().minusHours(5), ParkingSize.SP);
        when(parkingMapper.selectOptionalActiveById(1)).thenReturn(Optional.of(parkingSpace));
        SystemProperty systemProperty = new SystemProperty("{\"parkingA\": 10.5, \"parkingB\": 8.0}", "parking-rate");
        List<SystemProperty> systemProperties = new ArrayList<>();
        systemProperties.add(systemProperty);

        when(systemPropertyMapper.selectByCode("parking-rate")).thenReturn(systemProperties);
        when(parkingMapper.update(parkingSpace, 1)).thenReturn(1);

        String result = parkingService.parkingTimeOut(1);
        assertEquals("Update Success", result);

    }

    @Test
    public void testParkingTimeOut_ParkingUpdateFail() {
        ParkingSpace parkingSpace = new ParkingSpace(1L, "1", "1,4,5", LocalDateTime.now().minusHours(5), ParkingSize.SP);
        when(parkingMapper.selectOptionalActiveById(1)).thenReturn(Optional.of(parkingSpace));
        SystemProperty systemProperty = new SystemProperty("{\"parkingA\": 10.5, \"parkingB\": 8.0}", "parking-rate");
        List<SystemProperty> systemProperties = new ArrayList<>();
        systemProperties.add(systemProperty);

        when(systemPropertyMapper.selectByCode("parking-rate")).thenReturn(systemProperties);
        when(parkingMapper.update(parkingSpace, 1)).thenReturn(0);

        String result = parkingService.parkingTimeOut(1);
        assertEquals("Update Fail", result);

    }

}

