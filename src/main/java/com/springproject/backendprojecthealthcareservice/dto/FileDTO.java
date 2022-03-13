package com.springproject.backendprojecthealthcareservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

@Getter
@Setter
@AllArgsConstructor
public class FileDTO {

    private String name;
    private String url;
    private String type;
    private long size;
}
