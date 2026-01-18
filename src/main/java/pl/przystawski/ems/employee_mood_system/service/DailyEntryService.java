package pl.przystawski.ems.employee_mood_system.service;

import org.springframework.stereotype.Service;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;

import java.time.LocalDate;

@Service
public class DailyEntryService {

    private final DailyEntryRepository dailyEntryRepository;

    public DailyEntryService(DailyEntryRepository dailyEntryRepository) {
        this.dailyEntryRepository = dailyEntryRepository;
    }


    public DailyEntry addOrUpdateDailyEntry(Employee employee, DailyEntry dailyEntry){
        LocalDate today = LocalDate.now();

        return dailyEntryRepository
                .findByEmployeeAndEntryDate(employee, today)
                .map(existingEntry -> {
                    existingEntry.setMoodScore(dailyEntry.getMoodScore());
                    existingEntry.setComfortScore(dailyEntry.getComfortScore());
                    existingEntry.setWorkloadScore(dailyEntry.getWorkloadScore());
                    return dailyEntryRepository.save(existingEntry);
                }).orElseGet(() -> {
                        dailyEntry.setEmployee(employee);
                        dailyEntry.setEntryDate(today);
                        return dailyEntryRepository.save(dailyEntry);
                });
    }
}
