package pl.przystawski.ems.employee_mood_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.przystawski.ems.employee_mood_system.config.security.CustomUserDetails;
import pl.przystawski.ems.employee_mood_system.service.HRReportService;

@Controller
@RequestMapping("/employee/reports")
@RequiredArgsConstructor
public class EmployeeHRReportController {

    private final HRReportService hrReportService;

    @GetMapping
    public String employeeReports(@AuthenticationPrincipal CustomUserDetails user,
                                  Model model
                                  ){


        String email = user.getUsername();
        model.addAttribute("reports", hrReportService.findByEmployeeEmail(email));

        return "employee-hr-reports";
    }
}
