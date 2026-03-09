package pl.przystawski.ems.employee_mood_system.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.przystawski.ems.employee_mood_system.dto.request.DailyEntryRequest;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyEntryServiceTest {
    @Mock
    DailyEntryRepository dailyEntryRepository;

    @Mock
    private AlertService alertService;


    @InjectMocks
    DailyEntryService dailyEntryService;

    private DailyEntryRequest entry(int mood, int workload, int comfort){
        DailyEntryRequest dailyEntry = new DailyEntryRequest();

        dailyEntry.setMoodScore(mood);
        dailyEntry.setWorkloadScore(workload);
        dailyEntry.setComfortScore(comfort);


        return dailyEntry;
    }


    @Test
    void shouldAddDailyEntryWhenNoEntryExistsForThatDay(){
        Employee employee = new Employee();
        DailyEntryRequest request = entry(2, 2, 2);

        when(dailyEntryRepository.findByEmployeeAndEntryDate(eq(employee), eq(LocalDate.now())))
                .thenReturn(Optional.empty());



        dailyEntryService.addOrUpdateDailyEntry(employee, request);

        verify(dailyEntryRepository).save(any(DailyEntry.class));
    }

}
