package com.sirma.Nikolay_Vaklinov.controller;

import com.sirma.Nikolay_Vaklinov.model.Employee;
import com.sirma.Nikolay_Vaklinov.model.EmployeePair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    // the method is dealing with uploading and parsing
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        List<EmployeePair> pairs = new ArrayList<>();
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);

            List<Employee> employees = new ArrayList<>();
            for (CSVRecord record : records) {
                Employee employee = new Employee();
                employee.setEmployeeId(Long.parseLong(record.get("EmployeeID")));
                employee.setProjectId(Long.parseLong(record.get("ProjectID")));
                employee.setStartDate(LocalDate.parse(record.get("StartDate")));
                String dateTo = record.get("DateTo");
                employee.setEndDate(dateTo.equals("NULL") ? LocalDate.now() : LocalDate.parse(dateTo));

                employees.add(employee);
            }

            pairs = getEmployeePairs(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("pairs", pairs);
        return "index"; // Thymeleaf template to display data
    }


    // calculating the employee pairs working together
    private List<EmployeePair> getEmployeePairs(List<Employee> employees) {
        List<EmployeePair> pairs = new ArrayList<>();

        for (Employee e1 : employees) {
            for (Employee e2 : employees) {
                if (e1.getEmployeeId().equals(e2.getEmployeeId())) continue; // Skip same employee

                if (e1.getProjectId().equals(e2.getProjectId())) {
                    // Calculate overlap
                    LocalDate overlapStart = e1.getStartDate().isAfter(e2.getStartDate()) ? e1.getStartDate() : e2.getStartDate();
                    LocalDate overlapEnd = e1.getEndDate().isBefore(e2.getEndDate()) ? e1.getEndDate() : e2.getEndDate();

                    if (!overlapStart.isAfter(overlapEnd)) {
                        long daysWorked = overlapStart.until(overlapEnd).getDays();
                        EmployeePair pair = new EmployeePair(e1.getEmployeeId(), e2.getEmployeeId(), e1.getProjectId(), daysWorked);
                        pairs.add(pair);
                    }
                }
            }
        }

        pairs.sort((p1, p2) -> Long.compare(p2.getDaysWorked(), p1.getDaysWorked())); // Sort by days worked
        return pairs;
    }

    // the following method will show the upload form
    @GetMapping("/")
    public String showUploadForm(){
        return "index";
    }
}
