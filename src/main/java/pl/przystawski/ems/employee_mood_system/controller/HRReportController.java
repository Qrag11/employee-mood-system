package pl.przystawski.ems.employee_mood_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.przystawski.ems.employee_mood_system.dto.request.HRReportRequest;
import pl.przystawski.ems.employee_mood_system.dto.request.HrDashboardRequest;
import pl.przystawski.ems.employee_mood_system.enums.Status;
import pl.przystawski.ems.employee_mood_system.repository.HRReportRepository;
import pl.przystawski.ems.employee_mood_system.service.EmployeeService;
import pl.przystawski.ems.employee_mood_system.service.HRReportService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hr-dashboard")
public class HRReportController {
    private final HRReportService hrReportService;
    private final EmployeeService employeeService;

    @PostMapping("/report")
    public String saveReport(
            @Valid @ModelAttribute("reportForm") HRReportRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", new HrDashboardRequest());
            model.addAttribute("employees", employeeService.findAll());
            return "hr-dashboard";
        }

        hrReportService.createReport(request);
        return "redirect:/hr-dashboard?reportSaved=true";
    }

    @GetMapping("/reports")
    public String listReports(Model model) {
        model.addAttribute("reports", hrReportService.findAll());
        return "hr-reports";
    }

    @PostMapping("/reports/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        hrReportService.deleteById(id);
        return "redirect:/hr-dashboard/reports";
    }

    @PostMapping("/reports/status/{id}")
    public String changeStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) {
        hrReportService.changeStatus(id, status);
        return "redirect:/hr-dashboard/reports";
    }
}
