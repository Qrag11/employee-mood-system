package pl.przystawski.ems.employee_mood_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;
import pl.przystawski.ems.employee_mood_system.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HrDashBoardService {

    private final DailyEntryRepository dailyEntryRepository;
    private final EmployeeRepository employeeRepository;


    public List<DailyEntry> findEntriesInRange(Long employeeId,
                                               LocalDate fromDate,
                                               LocalDate toDate){

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        return dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(employee, fromDate, toDate);
    }
}
