package pl.przystawski.ems.employee_mood_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.przystawski.ems.employee_mood_system.dto.request.HrDashboardRequest;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.service.EmployeeService;
import pl.przystawski.ems.employee_mood_system.service.HrDashBoardService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/hr-dashboard")
@RequiredArgsConstructor
public class HrDashboardController {
    private final HrDashBoardService hrDashBoardService;
    private final EmployeeService employeeService;

    @GetMapping
    public String form(Model model){

        HrDashboardRequest request = new HrDashboardRequest();
        request.setFromDate(LocalDate.now());
        request.setToDate(LocalDate.now());

        model.addAttribute("form", request);
        model.addAttribute("employees", employeeService.findAll());
        return "hr-dashboard";
    }

    @PostMapping
    public String submit(
            @Valid @ModelAttribute("form") HrDashboardRequest request,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            return "hr-dashboard";
        }

        LocalDate fromDate = request.getFromDate();
        LocalDate toDate = request.getToDate();
        Long employeeId = request.getEmployeeId();

        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        List<DailyEntry> entries = hrDashBoardService.findEntriesInRange(employeeId,
                fromDate,
                toDate
        );

        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("entries", entries);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "hr-dashboard";
    }

}
