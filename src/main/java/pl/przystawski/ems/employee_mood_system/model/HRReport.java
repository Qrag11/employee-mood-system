package pl.przystawski.ems.employee_mood_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.przystawski.ems.employee_mood_system.enums.ActionType;
import pl.przystawski.ems.employee_mood_system.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class HRReport {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    private Alert alert;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String description;

    private LocalDate createdAt;

    private LocalDate analysisFromDate;

    private LocalDate analysisToDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
