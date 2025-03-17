package com.sirma.Nikolay_Vaklinov.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private Long employeeId;
    private Long projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}
