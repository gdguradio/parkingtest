package com.local.parking.test.system.property;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemProperty implements Serializable {

    @ApiModelProperty(
            value = "ID of the system property",
            example = "15"
    )
    private Long id;

    @ApiModelProperty(
            value = "Unique code of the system property",
            example = "entrypoint",
            required = true
    )
    private String code;

    @ApiModelProperty(
            value = "System property value (null means unset)",
            example = "A",
            required = true
    )
    private String value;

    @ApiModelProperty(
            value = "System property value (null means unset)",
            example = "The entry point of parking",
            required = true
    )
    private String description;

    @ApiModelProperty(
            value = "status of transaction",
            example = "active"
    )
    private String status;

    public SystemProperty(String value, String code) {
        this.value = value;
        this.code = code;
    }
}
