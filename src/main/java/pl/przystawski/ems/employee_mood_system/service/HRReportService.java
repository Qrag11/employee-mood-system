package pl.przystawski.ems.employee_mood_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.przystawski.ems.employee_mood_system.dto.request.HRReportRequest;
import pl.przystawski.ems.employee_mood_system.enums.Status;
import pl.przystawski.ems.employee_mood_system.model.Alert;
import pl.przystawski.ems.employee_mood_system.model.Employee;
import pl.przystawski.ems.employee_mood_system.model.HRReport;
import pl.przystawski.ems.employee_mood_system.repository.AlertRepository;
import pl.przystawski.ems.employee_mood_system.repository.EmployeeRepository;
import pl.przystawski.ems.employee_mood_system.repository.HRReportRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HRReportService {

    private final HRReportRepository hrReportRepository;
    private final EmployeeRepository employeeRepository;
    private final AlertRepository alertRepository;

    public void createReport(HRReportRequest dto){
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));



        HRReport report = new HRReport();
        report.setEmployee(employee);
        report.setActionType(dto.getActionType());
        report.setDescription(dto.getDescription());
        report.setAnalysisFromDate(dto.getAnalysisFromDate());
        report.setAnalysisToDate(dto.getAnalysisToDate());
        report.setCreatedAt(LocalDate.now());
        report.setStatus(Status.pending);

        if (dto.getAlertId() != null) {
            Alert alert = alertRepository.findById(dto.getAlertId())
                    .orElseThrow(() -> new IllegalArgumentException("Alert not found"));
            report.setAlert(alert);
        }

        hrReportRepository.save(report);

    }

    public List<HRReport> findAll(){
        return hrReportRepository.findAll();
    }


    public void deleteById(Long id){
        HRReport report = hrReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        hrReportRepository.delete(report);
    }

    @Transactional
    public void changeStatus(Long id, Status status) {
        HRReport report = hrReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        report.setStatus(status);
    }

    public List<HRReport> findByEmployeeEmail(String email){
        return hrReportRepository.findByEmployeeEmail(email);
    }
}
