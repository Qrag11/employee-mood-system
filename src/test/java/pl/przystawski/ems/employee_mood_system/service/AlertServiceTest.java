package pl.przystawski.ems.employee_mood_system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.przystawski.ems.employee_mood_system.enums.AlertType;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.AlertRepository;
import pl.przystawski.ems.employee_mood_system.repository.DailyEntryRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {
    @Mock
    DailyEntryRepository dailyEntryRepository;

    @Mock
    AlertRepository alertRepository;

    @InjectMocks
    AlertService alertService;

    @Test
    void shouldCreateAlertWhenLowMoodOccursMoreThanTwoTimes(){
        Employee employee = new Employee();

        List<DailyEntry> entries = List.of(
                entry(1, 4, 7),
                entry(2, 10, 5),
                entry(3, 5, 1)
        );

        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);

        when(alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(any(), eq(AlertType.LOW_MOOD_WEEK), any(), any()))
                .thenReturn(false);

        alertService.checkWorkWellbeing(employee);

        verify(alertRepository).save(argThat(alert -> alert.getAlertType() == AlertType.LOW_MOOD_WEEK));
    }

    @Test
    void shouldCreateAlertWhenHighWorkloadOccursMoreThanTwoTimes(){
        Employee employee = new Employee();

        List<DailyEntry> entries = List.of(
                entry(4, 9, 3),
                entry(5, 10, 5),
                entry(3, 8, 1)
        );

        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);

        when(alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(any(), eq(AlertType.HIGH_WORKLOAD_WEEK), any(), any()))
                .thenReturn(false);

        alertService.checkWorkWellbeing(employee);

        verify(alertRepository).save(argThat(alert -> alert.getAlertType() == AlertType.HIGH_WORKLOAD_WEEK));


    }

    @Test
    void shouldCreateAlertWhenLowComfortOccursMoreThanTwoTimes(){
        Employee employee = new Employee();
        List<DailyEntry> entries = List.of(
                entry(5, 9, 3),
                entry(2, 5, 2),
                entry(4, 8, 1)
        );



        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);

        when(alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(any(), eq(AlertType.LOW_COMFORT_WEEK), any(), any()))
                .thenReturn(false);

        alertService.checkWorkWellbeing(employee);

        verify(alertRepository).save(argThat(alert -> alert.getAlertType() == AlertType.LOW_COMFORT_WEEK));
    }

    @Test
    void shouldNotCreateAlertWhenLessThanThreeEntries(){
        Employee employee = new Employee();
        List<DailyEntry> entries = List.of(
                entry(5, 9, 8),
                entry(2, 10, 4)
        );

        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);


        alertService.checkWorkWellbeing(employee);

        verify(alertRepository, never()).save(any());
    }

    @Test
    void shouldNotCreateDuplicateAlert(){
        Employee employee = new Employee();

        List<DailyEntry> entries = List.of(
                entry(5, 4, 3),
                entry(6, 1, 2),
                entry(5, 2, 1)
        );

        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);

        when(alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(any(), eq(AlertType.LOW_COMFORT_WEEK), any(), any()))
                .thenReturn(false)
                .thenReturn(true);

        alertService.checkWorkWellbeing(employee);

        alertService.checkWorkWellbeing(employee);

        verify(alertRepository, times(1)).save(
                argThat(alert -> alert.getAlertType() == AlertType.LOW_COMFORT_WEEK));

    }

    @Test
    void shouldNotCreateAlertWhenLowMoodLessThanThreeTimes(){
        Employee employee = new Employee();

        List<DailyEntry> entries = List.of(
                entry(1, 4, 7),
                entry(2, 10, 2),
                entry(5, 8, 1)
        );

        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);


        alertService.checkWorkWellbeing(employee);

        verify(alertRepository, never()).save(any());

    }

    @Test
    void shouldCreateMultipleAlertsWhenMultipleConditionsAreMet(){
        Employee employee = new Employee();

        List<DailyEntry> entries = List.of(
                entry(1, 9, 3),
                entry(2, 10, 2),
                entry(3, 8, 1)
        );

        when(dailyEntryRepository.findAllByEmployeeAndEntryDateBetween(any(), any(), any()))
                .thenReturn(entries);

        when(alertRepository.existsByEmployeeAndAlertTypeAndCreatedAt(any(), any(), any(), any()))
                .thenReturn(false);

        alertService.checkWorkWellbeing(employee);

        verify(alertRepository).save(
                argThat(a -> a.getAlertType() == AlertType.LOW_MOOD_WEEK)
        );
        verify(alertRepository).save(
                argThat(a -> a.getAlertType() == AlertType.HIGH_WORKLOAD_WEEK)
        );
        verify(alertRepository).save(
                argThat(a -> a.getAlertType() == AlertType.LOW_COMFORT_WEEK)
        );
    }


    private DailyEntry entry (int mood, int workload, int comfort){
        DailyEntry dailyEntry = new DailyEntry();

        dailyEntry.setMoodScore(mood);
        dailyEntry.setWorkloadScore(workload);
        dailyEntry.setComfortScore(comfort);

        return dailyEntry;
    }
}
