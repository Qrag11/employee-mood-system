package pl.przystawski.ems.employee_mood_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przystawski.ems.employee_mood_system.model.HRReport;

import java.util.List;

public interface HRReportRepository extends JpaRepository<HRReport, Long> {
    List<HRReport> findByEmployeeEmail(String email);
}
