package pl.przystawski.ems.employee_mood_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przystawski.ems.employee_mood_system.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
