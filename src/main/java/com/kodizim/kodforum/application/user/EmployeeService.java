package com.kodizim.kodforum.application.user;

import com.kodizim.kodforum.domain.employee.*;
import com.kodizim.kodforum.error.NotFoundException;
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

    @Transactional
    public void employeeInitialSetup(EmployeeInitialSetupCommand command, String userId) {
        addMissingProfessions(command.getProfessions());
        var professions = professionRepository.findProfessionsByName(command.getProfessions());
        var employee = new Employee(
                UUID.randomUUID(),
                userId,
                command.getName(),
                command.getSurname(),
                command.getPhoneNumber(),
                command.getPhotoLocationKey(),
                command.getBirthDate(),
                command.getAbout(),
                professions
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

        var employeeDto = employeeRepository.getEmployeeDto(userId)
                .orElseThrow(() ->new NotFoundException("user not found!"));
        var professions = employeeRepository.getProfessionName(employeeDto.getProfessionIds());
        employeeDto.setProfessions(professions);
        return employeeDto;
    }
}
