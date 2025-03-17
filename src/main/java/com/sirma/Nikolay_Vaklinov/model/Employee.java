package com.sirma.Nikolay_Vaklinov.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class Employee {
    private Long employeeId;
    private Long projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}
