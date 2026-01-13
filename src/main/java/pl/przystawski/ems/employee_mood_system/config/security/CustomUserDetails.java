package pl.przystawski.ems.employee_mood_system.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.przystawski.ems.employee_mood_system.model.Employee;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails{
    private final Employee employee;


    public CustomUserDetails(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee(){
        return employee;
    }

    @Override
    public String getUsername(){
        return employee.getEmail();
    }

    @Override
    public String getPassword(){
        return employee.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + employee.getRole().name())
        );
    }
}
