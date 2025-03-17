package com.sirma.Nikolay_Vaklinov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor 
public class EmployeePair {

    private Long employeeId1;
    private Long employeeId2;
    private Long projectId;
    private Long daysWorked;

    public EmployeePair(Long employeeId, Long employeeId1, Long projectId, long daysWorked) {
    }
}
