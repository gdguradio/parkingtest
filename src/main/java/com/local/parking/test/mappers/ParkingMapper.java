package com.local.parking.test.mappers;

import com.local.parking.test.parking.ParkingSpace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Mapper
public interface ParkingMapper {

    Boolean existsByLocation(@Param("location") String location);

    int insert(@Param("parkingSpace") ParkingSpace parkingSpace);

    int update(@Param("parkingSpace") ParkingSpace parkingSpace,@Param("id") long id);

    ParkingSpace selectById(@Param("id") long id);

    ParkingSpace selectActiveById(@Param("id") long id);

    Optional<ParkingSpace> selectOptionalActiveById(@Param("id") long id);

    boolean isFullParking(@Param("status") String status);

    ParkingSpace selectByEntryPoint(@Param("entryPoint") String entryPoint);

    boolean isParkingEmpty();
}
