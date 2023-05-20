package com.local.parking.test.parking;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingSpace implements Serializable {
    @ApiModelProperty(
            value = "ID of the system property",
            example = "15"
    )
    private Long id;

    @ApiModelProperty(
            value = "Entry point of parking spot",
            example = "1",
            required = true
    )
    private String entryPoint;

    @ApiModelProperty(
            value = "Coordinate of parking spot",
            example = "1,4,5",
            required = true
    )
    private String location;

    @ApiModelProperty(
            value = "parking spot size",
            example = "small parking"
    )
    private ParkingSize size;

    @ApiModelProperty(
            value = "Exact timestamp when the operation was registered",
            example = "2021-05-22T15:07:14",
            accessMode = ApiModelProperty.AccessMode.READ_WRITE
    )
    private LocalDateTime timeIn;

    @ApiModelProperty(
            value = "Exact timestamp when the operation was registered",
            example = "2021-05-22T15:07:14",
            accessMode = ApiModelProperty.AccessMode.READ_WRITE
    )
    private LocalDateTime timeOut;

    @ApiModelProperty(
            value = "If parking spot is taken",
            example = "True"
    )
    private boolean isOccupied;

    @ApiModelProperty(
            value = "Parking Fee",
            example = "100"
    )
    private float amount;

    @ApiModelProperty(
            value = "status of transaction",
            example = "success"
    )
    private String status;

    @ApiModelProperty(
            value = "vehicle size",
            example = "vehicle parking"
    )
    private VehicleType vehicleType;

    public ParkingSpace(Long id, String entryPoint, String location, LocalDateTime timeIn, ParkingSize parkingSize) {
        this.id = id;
        this.entryPoint = entryPoint;
        this.location = location;
        this.timeIn = timeIn;
        this.size = parkingSize;
    }
}


