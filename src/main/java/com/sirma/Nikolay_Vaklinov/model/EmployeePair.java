package com.sirma.Nikolay_Vaklinov.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeePair {

    private Long employeeId1;
    private Long employeeId2;
    private Long projectId;
    private Long daysWorked;
}
