package pl.przystawski.ems.employee_mood_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.przystawski.ems.employee_mood_system.dto.request.DailyEntryRequest;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyEntryService {

    private final DailyEntryRepository dailyEntryRepository;
    private final AlertService alertService;


    @Transactional
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

        alertService.checkWorkWellbeing(employee);
        return  dailyEntryRepository.save(entry);
    }

    public List<DailyEntry> getUserHistory(Employee employee){
        return dailyEntryRepository.findAllByEmployeeOrderByEntryDateDesc(employee);
    }
}
