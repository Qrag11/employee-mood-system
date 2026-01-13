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


    public DailyEntry addDailyEntry(Employee employee, DailyEntry dailyEntry){
        dailyEntry.setEntryDate(LocalDate.now());
        dailyEntry.setEmployee(employee);
        return dailyEntryRepository.save(dailyEntry);
    }
}
