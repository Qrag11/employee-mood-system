package pl.przystawski.ems.employee_mood_system.service;

import org.springframework.stereotype.Service;
import pl.przystawski.ems.employee_mood_system.dto.request.DailyEntryRequest;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyEntryService {

    private final DailyEntryRepository dailyEntryRepository;

    public DailyEntryService(DailyEntryRepository dailyEntryRepository) {
        this.dailyEntryRepository = dailyEntryRepository;
    }


    public DailyEntry addOrUpdateDailyEntry(
            Employee employee,
            DailyEntryRequest request
    ) {
        DailyEntry entry = dailyEntryRepository.findByEmployeeAndEntryDate(employee, LocalDate.now())
                .orElse(new DailyEntry());

        entry.setEmployee(employee);
        entry.setEntryDate(LocalDate.now());
        entry.setMoodScore(request.getMoodScore());
        entry.setWorkloadScore(request.getWorkloadScore());
        entry.setComfortScore(request.getComfortScore());

        return  dailyEntryRepository.save(entry);
    }

    public List<DailyEntry> getUserHistory(Employee employee){
        return dailyEntryRepository.findAllByEmployeeOrderByEntryDateDesc(employee);
    }
}
