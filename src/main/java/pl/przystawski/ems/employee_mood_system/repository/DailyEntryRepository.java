package pl.przystawski.ems.employee_mood_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {
    Optional<DailyEntry> findByEmployeeAndEntryDate(Employee employee, LocalDate entryDate);

    List<DailyEntry> findAllByEmployeeOrderByEntryDateDesc(Employee employee);

    List<DailyEntry> findAllByEmployeeAndEntryDateBetween(
            Employee employee,
            LocalDate formDate,
            LocalDate toDate
    );
}
