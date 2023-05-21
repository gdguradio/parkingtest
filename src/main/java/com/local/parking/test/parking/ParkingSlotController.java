package com.local.parking.test.parking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parking-slot")
@CrossOrigin(origins = "http://localhost:4200")
public class ParkingSlotController {

    private final ParkingService parkingService;

    @PostMapping(value = "/time-in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createTimeIn(@RequestBody ParkingSpace parkingSpace) {
        return parkingService.parkingTimeIn(parkingSpace.getEntryPoint(), parkingSpace.getVehicleType().getCode());
    }

    @PostMapping(value = "/time-out", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createTimeOut(@RequestBody ParkingSpace parkingSpace) {
        return parkingService.parkingTimeOut(parkingSpace.getId());
    }

    @GetMapping(value = "/parked-slot",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingSpace> readParkedSlot() {
        return parkingService.readParkedSlot();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingSpace readParkingSlot(@PathVariable("id") long id) {
        return parkingService.readById(id);
    }
}
