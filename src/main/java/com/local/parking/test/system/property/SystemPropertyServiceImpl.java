package com.local.parking.test.system.property;

import com.local.parking.test.mappers.SystemPropertyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SystemPropertyServiceImpl implements SystemPropertyService {

    private final SystemPropertyMapper systemPropertyMapper;

    @Override
    public String createEntryPoint(SystemProperty systemProperty) {
        return insertDB(systemProperty);
    }

    @Override
    public List<SystemProperty> readByCode(SystemProperty systemProperty) {
        return systemPropertyMapper.selectByCode(systemProperty.getCode());
    }

    @Override
    public String createParkingSlot(SystemProperty systemProperty) {
        return insertDB(systemProperty);
    }

    public String insertDB(SystemProperty systemProperty) {
        String returnValue = "";
        systemProperty.setStatus("active");
        if(systemPropertyMapper.existsByValue(systemProperty.getValue())){
            returnValue = "The value " +systemProperty.getValue()+ " exists in the database";
        }else{
            if(systemPropertyMapper.insert(systemProperty) == 1){
                returnValue = "The value " +systemProperty.getValue()+ " is successfully added in the database";
            }else {
                returnValue = "The value " +systemProperty.getValue()+ " is not successfully added in the database";
            }
        }
        return returnValue;
    }
}
