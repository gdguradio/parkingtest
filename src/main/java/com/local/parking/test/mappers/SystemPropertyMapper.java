package com.local.parking.test.mappers;

import com.local.parking.test.system.property.SystemProperty;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SystemPropertyMapper {

    List<SystemProperty> selectByCode(@Param("code") String code);
    int insert(@Param("systemProperty") SystemProperty systemProperty);
    boolean existsByValue(@Param("value") String value);
    Optional<SystemProperty> selectByEntryPoint(@Param("entryPoint") String entryPoint, @Param("vehicleType") String vehicleType);
}
