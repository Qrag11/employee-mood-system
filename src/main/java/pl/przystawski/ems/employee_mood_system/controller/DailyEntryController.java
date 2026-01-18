package pl.przystawski.ems.employee_mood_system.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.przystawski.ems.employee_mood_system.config.security.CustomUserDetails;
import pl.przystawski.ems.employee_mood_system.dto.request.DailyEntryRequest;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.service.DailyEntryService;

@RestController
@RequestMapping("/api/dailyEntry")
public class DailyEntryController {

    private final DailyEntryService dailyEntryService;


    public DailyEntryController(DailyEntryService dailyEntryService) {
        this.dailyEntryService = dailyEntryService;
    }

    @PostMapping("/add")
    public DailyEntry addDailyEntry(@AuthenticationPrincipal CustomUserDetails user,
                                    @Valid @RequestBody DailyEntryRequest request){

        return dailyEntryService.addOrUpdateDailyEntry(
                user.getEmployee(),
                request
        );
    }
}
