package com.kono.scannerbackend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceNamesDto {
    String[] deviceNames;
}
