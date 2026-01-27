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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final DailyEntryRepository dailyEntryRepository;
    private final AlertRepository alertRepository;

    public void checkLowMoodWeek(Employee employee){
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(6);

        List<DailyEntry> entries = dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(
                employee,
                weekAgo,
                today);

        boolean allLowMood = entries.stream()
                .allMatch(e -> e.getMoodScore() < 4);

        if(!allLowMood){
            return;
        }

        if(alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(
                employee,
                AlertType.LOW_MOOD_WEEK,
                today
        )){
            return;
        }

        Alert alert = new Alert();
        alert.setEmployee(employee);
        alert.setAlertType(AlertType.LOW_MOOD_WEEK);
        alert.setCreatedAt(today);
        alert.setSeverity(Severity.high);

        alertRepository.save(alert);

    }
}
