package pl.przystawski.ems.employee_mood_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class DailyEntry {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    private int moodScore;

    private int comfortScore;

    private int workloadScore;

    private LocalDate entryDate;

}
