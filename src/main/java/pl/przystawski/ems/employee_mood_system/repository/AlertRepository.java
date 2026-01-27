package pl.przystawski.ems.employee_mood_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przystawski.ems.employee_mood_system.enums.AlertType;
import pl.przystawski.ems.employee_mood_system.model.Alert;
import pl.przystawski.ems.employee_mood_system.model.Employee;

import java.time.LocalDate;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    boolean existsByEmployeeAndAlertTypeAndCreatedAt(
            Employee employee,
            AlertType alertType,
            LocalDate createdAt
    );

    List<Alert> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);
}
