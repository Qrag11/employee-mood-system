package pl.przystawski.ems.employee_mood_system.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.przystawski.ems.employee_mood_system.config.security.CustomUserDetails;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.service.DailyEntryService;

@RestController
@RequestMapping("/dailyEntry")
public class DailyEntryController {

    private final DailyEntryService dailyEntryService;


    public DailyEntryController(DailyEntryService dailyEntryService) {
        this.dailyEntryService = dailyEntryService;
    }

    @PostMapping
    public DailyEntry addDailyEntry(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DailyEntry dailyEntry){
        Employee employee = customUserDetails.getEmployee();
        return dailyEntryService.addDailyEntry(employee, dailyEntry);
    }
}
