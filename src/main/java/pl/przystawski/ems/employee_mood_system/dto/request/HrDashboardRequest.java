package pl.przystawski.ems.employee_mood_system.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class HrDashboardRequest {

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private Long employeeId;

    @AssertTrue(message = "The start date cannot be later than the end date.")
    public boolean isDateRangeValid() {
        if (fromDate == null || toDate == null) {
            return true;
        }
        return !fromDate.isAfter(toDate);
    }
}
