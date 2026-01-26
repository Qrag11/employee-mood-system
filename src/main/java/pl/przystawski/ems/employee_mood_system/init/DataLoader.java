package pl.przystawski.ems.employee_mood_system.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.przystawski.ems.employee_mood_system.enums.Role;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.repository.EmployeeRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public DataLoader(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String hrEmail = "hr@test.com";
        String employeeEmail = "employee@test.com";

        if(employeeRepository.findByEmail(hrEmail).isEmpty()){
            Employee hr = new Employee();
            hr.setEmail(hrEmail);
            hr.setPassword(passwordEncoder.encode("hr123"));
            hr.setRole(Role.HR);
            hr.setDepartment("Human Resources");
            hr.setPosition("HR Generalist");

            employeeRepository.save(hr);
            System.out.println("HR user created: " + hrEmail);

            Employee employee = new Employee();
            employee.setEmail(employeeEmail);
            employee.setPassword(passwordEncoder.encode("emp"));
            employee.setRole(Role.EMPLOYEE);
            employee.setDepartment("Sales Department");
            employee.setPosition("Seller");

            employeeRepository.save(employee);
            System.out.println("Employee user created: " + employeeEmail);
        }

    }
}
