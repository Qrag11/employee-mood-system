package pl.przystawski.ems.employee_mood_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;

public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {
}
