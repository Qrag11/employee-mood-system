package pl.przystawski.ems.employee_mood_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.przystawski.ems.employee_mood_system.enums.ActionType;

import java.time.LocalDate;

@Getter
@Setter
public class HRReportRequest {
    @NotNull
    private Long employeeId;


    private Long alertId;

    @NotNull
    private ActionType actionType;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate analysisFromDate;

    @NotNull
    private LocalDate analysisToDate;
}
