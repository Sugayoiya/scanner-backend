package com.kono.scannerbackend.domain;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ScanDto {
    @NotBlank
    private int index;
    @NotBlank
    private String ip;
    private String person;
}
