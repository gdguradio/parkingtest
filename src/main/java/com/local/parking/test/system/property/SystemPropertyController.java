package com.local.parking.test.system.property;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system-property")
public class SystemPropertyController {

    private final SystemPropertyService systemPropertyService;

    @GetMapping(value = "/entry-point",produces = MediaType.APPLICATION_JSON_VALUE)
        public List<SystemProperty> readEntryPoint() {
        SystemProperty systemProperty = new SystemProperty();
        systemProperty.setCode("entry-point");
        return systemPropertyService.readByCode(systemProperty);
    }

    @PostMapping(value = "/entry-point", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createEntryPoint(@RequestBody SystemProperty systemProperty) {
        return systemPropertyService.createEntryPoint(systemProperty);
    }

    @GetMapping(value = "/parking-slot",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SystemProperty> readParkingSlot() {
        SystemProperty systemProperty = new SystemProperty();
        systemProperty.setCode("parking-slot");
        return systemPropertyService.readByCode(systemProperty);
    }

    @PostMapping(value = "/parking-slot", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createParkingSlot(@RequestBody SystemProperty systemProperty) {
        return systemPropertyService.createParkingSlot(systemProperty);
    }
}

