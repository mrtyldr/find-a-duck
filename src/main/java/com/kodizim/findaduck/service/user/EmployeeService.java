package com.kodizim.findaduck.service.user;

import com.kodizim.findaduck.domain.employee.*;
import com.kodizim.findaduck.domain.job.ApplicationDto;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void employeeInitialSetup(EmployeeInitialSetupCommand command, String userId) {
        var employee = employeeRepository.findByEmployeeId(userId)
                .orElseThrow(() -> new NotFoundException("employee not found"));
        addMissingProfessions(command.professions());
        employee.employeeInitial(
                command.name(),
                command.surname(),
                command.phoneNumber(),
                command.photoLocationKey(),
                command.birthDate(),
                command.about(),
                command.professions()
        );
        employeeRepository.save(employee);
    }

    private void addMissingProfessions(List<String> professions) {
        professions.stream()
                .filter(p -> !professionRepository.existsByProfessionName(p))
                .forEach(p -> professionRepository.save(
                        new Profession(UUID.randomUUID(), p)
                ));
    }

    public EmployeeDto getEmployeeDto(String userId) {
        return employeeRepository.getEmployeeDto(userId)
                .orElseThrow(() -> new NotFoundException("user not found!"));
    }

    public void addEmployeeUser(String userId, String email) {
        var employee = new Employee(userId, email);
        employeeRepository.save(employee);
    }

    public List<ApplicationDto> getApplications(String userId) {
        return applicationRepository.getApplicationDto(userId);
    }

    public void update(UpdateEmployeeCommand command, String employeeId) {
        var employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found!"));
        employee.update(command);
        employeeRepository.save(employee);

    }
}
