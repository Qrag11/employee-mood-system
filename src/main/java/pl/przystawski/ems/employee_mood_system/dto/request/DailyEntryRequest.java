package pl.przystawski.ems.employee_mood_system.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DailyEntryRequest {

    @NotNull
    @Min(1)
    @Max(10)
    private Integer moodScore;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer workloadScore;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer comfortScore;
}
