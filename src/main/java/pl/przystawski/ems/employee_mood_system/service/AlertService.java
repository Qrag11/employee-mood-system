package pl.przystawski.ems.employee_mood_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.przystawski.ems.employee_mood_system.enums.AlertType;
import pl.przystawski.ems.employee_mood_system.enums.Severity;
import pl.przystawski.ems.employee_mood_system.model.Alert;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.AlertRepository;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final DailyEntryRepository dailyEntryRepository;
    private final AlertRepository alertRepository;

    public void checkWorkWellbeing(Employee employee){
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);


        List<DailyEntry> entries = dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(
                employee,
                weekStart,
                weekEnd);

        if(entries.size() < 3)
            return;

        long lowMoodDays = entries.stream()
                .filter(e -> e.getMoodScore() < 4)
                .count();

        long highWorkloadDays = entries.stream()
                .filter(e -> e.getWorkloadScore() > 7)
                .count();

        long lowComfortDays = entries.stream()
                .filter(e -> e.getComfortScore() < 4)
                .count();


        if(lowMoodDays > 2){


            createAlert(employee, AlertType.LOW_MOOD_WEEK, weekStart, weekEnd);
        }

        if(highWorkloadDays > 2){


            createAlert(employee, AlertType.HIGH_WORKLOAD_WEEK, weekStart, weekEnd);


        }

        if(lowComfortDays > 2){

            createAlert(employee, AlertType.LOW_COMFORT_WEEK, weekStart, weekEnd);
        }



    }

    private void createAlert(Employee employee, AlertType type, LocalDate weekStart, LocalDate weekEnd) {
        if (alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(employee, type, weekStart, weekEnd)) {
            return;
        }

        Alert alert = new Alert();
        alert.setEmployee(employee);
        alert.setAlertType(type);
        alert.setWeekStart(weekStart);
        alert.setWeekEnd(weekEnd);
        alert.setSeverity(Severity.high);

        alertRepository.save(alert);
    }

}
