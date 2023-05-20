package com.local.parking.test;

import com.local.parking.test.mappers.SystemPropertyMapper;
import com.local.parking.test.system.property.SystemProperty;
import com.local.parking.test.system.property.SystemPropertyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemPropertyServiceImplTest {

    @Mock
    private SystemPropertyMapper systemPropertyMapper;

    @InjectMocks
    private SystemPropertyServiceImpl systemPropertyService;

    @Test
    public void testSystemProperty_PropertyInsertDuplicateRecord() {
        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(true);
        String result = systemPropertyService.insertDB(systemProperty);
        assertEquals("The value " +systemProperty.getValue()+ " exists in the database", result);
    }

    @Test
    public void testSystemProperty_PropertyInsertPass() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(false);
        // Mock the parkingMapper.insert method
        doAnswer(invocation -> {
            if (systemProperty.getValue().equals("1,3,4,SP") && systemProperty.getCode().equals("parking-slot")) {
                // Simulate successful insertion
                return 1;
            } else {
                // Simulate failed insertion
                return 0;
            }
        }).when(systemPropertyMapper).insert(systemProperty);
        String result = systemPropertyService.insertDB(systemProperty);
        assertEquals("The value " +systemProperty.getValue()+ " is successfully added in the database", result);
    }

    @Test
    public void testSystemProperty_PropertyInsertFail() {

        SystemProperty systemProperty = new SystemProperty("1,3,4,SP","parking-slot");
        when(systemPropertyMapper.existsByValue("1,3,4,SP"))
                .thenReturn(false);
        // Mock the parkingMapper.insert method
        doAnswer(invocation -> {
            if (systemProperty.getValue().equals("1,3,4,LP") && systemProperty.getCode().equals("parking-slot")) {
                // Simulate successful insertion
                return 1;
            } else {
                // Simulate failed insertion
                return 0;
            }
        }).when(systemPropertyMapper).insert(systemProperty);
        String result = systemPropertyService.insertDB(systemProperty);
        assertEquals("The value " +systemProperty.getValue()+ " is not successfully added in the database", result);
    }
}
