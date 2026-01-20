package pl.przystawski.ems.employee_mood_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.przystawski.ems.employee_mood_system.config.security.CustomUserDetails;
import pl.przystawski.ems.employee_mood_system.dto.request.DailyEntryRequest;
import pl.przystawski.ems.employee_mood_system.model.DailyEntry;
import pl.przystawski.ems.employee_mood_system.service.DailyEntryService;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/daily-entry")
public class DailyEntryViewController {
    private final DailyEntryService dailyEntryService;

    @GetMapping
    public String form(Model model){
        model.addAttribute("entry", new DailyEntryRequest());
        return "daily-entry";
    }

    @PostMapping
    public String submit(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @ModelAttribute("entry") DailyEntryRequest request,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            return "daily-entry";
        }

        dailyEntryService.addOrUpdateDailyEntry(
                user.getEmployee(),
                request
        );
        return "redirect:/daily-entry";
    }

    @GetMapping("/history")
    public  String history(
            @AuthenticationPrincipal CustomUserDetails user,
            Model model
    ){
        model.addAttribute(
                "entries",
                dailyEntryService.getUserHistory(user.getEmployee())
        );
        return "daily-entry-history";
    }

}
